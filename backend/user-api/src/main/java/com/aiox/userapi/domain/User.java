package com.aiox.userapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String passwordHash;

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

  protected User() {
  }

  public User(String name, String email, String passwordHash, String createdBy) {
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
    this.createdBy = createdBy;
    this.updatedBy = createdBy;
  }

  public UUID getId() { return id; }
  public String getName() { return name; }
  public String getEmail() { return email; }
  public String getPasswordHash() { return passwordHash; }
  public Instant getCreatedAt() { return createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
  public String getCreatedBy() { return createdBy; }
  public String getUpdatedBy() { return updatedBy; }
  public long getVersion() { return version; }

  public void update(String name, String email, String updatedBy) {
    this.name = name;
    this.email = email;
    this.updatedBy = updatedBy;
  }
}
