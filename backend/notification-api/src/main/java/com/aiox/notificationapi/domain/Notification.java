package com.aiox.notificationapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "notifications")
public class Notification {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private String recipient;

  @Column(nullable = false)
  private String subject;

  @Column(nullable = false, length = 4000)
  private String body;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private NotificationStatus status;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private Instant updatedAt;

  private String createdBy;
  private String updatedBy;

  @Version
  private long version;

  protected Notification() {
  }

  public Notification(String recipient, String subject, String body, NotificationStatus status, String actor) {
    this.recipient = recipient;
    this.subject = subject;
    this.body = body;
    this.status = status;
    this.createdBy = actor;
    this.updatedBy = actor;
  }

  public UUID getId() { return id; }
  public String getRecipient() { return recipient; }
  public String getSubject() { return subject; }
  public String getBody() { return body; }
  public NotificationStatus getStatus() { return status; }
  public Instant getCreatedAt() { return createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
  public long getVersion() { return version; }

  public void update(String recipient, String subject, String body, NotificationStatus status, String actor) {
    this.recipient = recipient;
    this.subject = subject;
    this.body = body;
    this.status = status;
    this.updatedBy = actor;
  }
}
