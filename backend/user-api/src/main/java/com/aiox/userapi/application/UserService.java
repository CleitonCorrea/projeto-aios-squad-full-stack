package com.aiox.userapi.application;

import com.aiox.userapi.contracts.UserCreatedEvent;
import com.aiox.userapi.contracts.UserRepository;
import com.aiox.userapi.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  private final UserRepository users;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final RabbitTemplate rabbitTemplate;
  private final String exchange;
  private final String routingKey;

  public UserService(
      UserRepository users,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      RabbitTemplate rabbitTemplate,
      @Value("${app.events.user-exchange}") String exchange,
      @Value("${app.events.user-created-routing-key}") String routingKey) {
    this.users = users;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.rabbitTemplate = rabbitTemplate;
    this.exchange = exchange;
    this.routingKey = routingKey;
  }

  @Transactional
  public User create(String name, String email, String password, String actor) {
    if (users.existsByEmail(email)) {
      throw new IllegalArgumentException("email already registered");
    }
    User user = users.save(new User(name, email, passwordEncoder.encode(password), actor));
    rabbitTemplate.convertAndSend(exchange, routingKey,
        new UserCreatedEvent(UUID.randomUUID(), Instant.now(), user.getId(), user.getEmail(), user.getName()));
    return user;
  }

  public String login(String email, String password) {
    User user = users.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("invalid credentials"));
    if (!passwordEncoder.matches(password, user.getPasswordHash())) {
      throw new IllegalArgumentException("invalid credentials");
    }
    return jwtService.issue(user.getId().toString());
  }

  public List<User> list() {
    return users.findAll();
  }

  public User get(UUID id) {
    return users.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
  }

  @Transactional
  public User update(UUID id, String name, String email, String actor) {
    User user = get(id);
    user.update(name, email, actor);
    return user;
  }

  public void delete(UUID id) {
    users.deleteById(id);
  }
}
