package com.aiox.notificationapi.application;

import com.aiox.notificationapi.contracts.NotificationRepository;
import com.aiox.notificationapi.contracts.UserCreatedEvent;
import com.aiox.notificationapi.domain.Notification;
import com.aiox.notificationapi.domain.NotificationStatus;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {
  private final NotificationRepository notifications;
  private final JavaMailSender mailSender;

  public NotificationService(NotificationRepository notifications, JavaMailSender mailSender) {
    this.notifications = notifications;
    this.mailSender = mailSender;
  }

  public Notification create(String recipient, String subject, String body, String actor) {
    return notifications.save(new Notification(recipient, subject, body, NotificationStatus.PENDING, actor));
  }

  public List<Notification> list() {
    return notifications.findAll();
  }

  public Notification get(UUID id) {
    return notifications.findById(id).orElseThrow(() -> new IllegalArgumentException("notification not found"));
  }

  @Transactional
  public Notification update(UUID id, String recipient, String subject, String body, NotificationStatus status, String actor) {
    Notification notification = get(id);
    notification.update(recipient, subject, body, status, actor);
    return notification;
  }

  public void delete(UUID id) {
    notifications.deleteById(id);
  }

  @RabbitListener(queues = "${app.events.user-created-queue}")
  public void onUserCreated(UserCreatedEvent event) throws MessagingException {
    Notification notification = create(event.email(), "Bem-vindo ao AIOX", "Ola " + event.name() + ", seu cadastro foi criado.", "event:user.created");
    send(notification);
  }

  private void send(Notification notification) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
    helper.setFrom("no-reply@aiox.local");
    helper.setTo(notification.getRecipient());
    helper.setSubject(notification.getSubject());
    helper.setText(notification.getBody());
    mailSender.send(message);
    notification.update(notification.getRecipient(), notification.getSubject(), notification.getBody(), NotificationStatus.SENT, "mailhog");
  }
}
