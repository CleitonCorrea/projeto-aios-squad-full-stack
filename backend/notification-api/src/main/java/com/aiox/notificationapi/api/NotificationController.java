package com.aiox.notificationapi.api;

import com.aiox.notificationapi.application.NotificationService;
import com.aiox.notificationapi.domain.Notification;
import com.aiox.notificationapi.domain.NotificationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
  private final NotificationService service;

  public NotificationController(NotificationService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public NotificationResponse create(@Valid @RequestBody NotificationRequest request) {
    return NotificationResponse.from(service.create(request.recipient(), request.subject(), request.body(), "api"));
  }

  @GetMapping
  public List<NotificationResponse> list() {
    return service.list().stream().map(NotificationResponse::from).toList();
  }

  @GetMapping("/{id}")
  public NotificationResponse get(@PathVariable UUID id) {
    return NotificationResponse.from(service.get(id));
  }

  @PutMapping("/{id}")
  public NotificationResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateNotificationRequest request) {
    return NotificationResponse.from(service.update(id, request.recipient(), request.subject(), request.body(), request.status(), "api"));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }

  public record NotificationRequest(@Email @NotBlank String recipient, @NotBlank String subject, @NotBlank String body) {}
  public record UpdateNotificationRequest(@Email @NotBlank String recipient, @NotBlank String subject, @NotBlank String body, @NotNull NotificationStatus status) {}
  public record NotificationResponse(UUID id, String recipient, String subject, String body, NotificationStatus status, Instant createdAt, Instant updatedAt, long version) {
    static NotificationResponse from(Notification notification) {
      return new NotificationResponse(notification.getId(), notification.getRecipient(), notification.getSubject(), notification.getBody(), notification.getStatus(), notification.getCreatedAt(), notification.getUpdatedAt(), notification.getVersion());
    }
  }
}
