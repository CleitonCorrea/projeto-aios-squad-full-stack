package com.aiox.userapi.api;

import com.aiox.userapi.application.UserService;
import com.aiox.userapi.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/users")
public class UserController {
  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
    return UserResponse.from(service.create(request.name(), request.email(), request.password(), "system"));
  }

  @GetMapping
  public List<UserResponse> list() {
    return service.list().stream().map(UserResponse::from).toList();
  }

  @GetMapping("/{id}")
  public UserResponse get(@PathVariable UUID id) {
    return UserResponse.from(service.get(id));
  }

  @PutMapping("/{id}")
  public UserResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {
    return UserResponse.from(service.update(id, request.name(), request.email(), "system"));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }

  public record CreateUserRequest(@NotBlank String name, @Email @NotBlank String email, @NotBlank String password) {}
  public record UpdateUserRequest(@NotBlank String name, @Email @NotBlank String email) {}
  public record UserResponse(UUID id, String name, String email, Instant createdAt, Instant updatedAt, long version) {
    static UserResponse from(User user) {
      return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt(), user.getVersion());
    }
  }
}
