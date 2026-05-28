package com.aiox.userapi.contracts;

import java.time.Instant;
import java.util.UUID;

public record UserCreatedEvent(UUID eventId, Instant occurredAt, UUID userId, String email, String name) {
}
