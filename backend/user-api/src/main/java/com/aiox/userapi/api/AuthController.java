package com.aiox.userapi.api;

import com.aiox.userapi.application.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService users;

  public AuthController(UserService users) {
    this.users = users;
  }

  @PostMapping("/register")
  public TokenResponse register(@Valid @RequestBody RegisterRequest request) {
    users.create(request.name(), request.email(), request.password(), "self");
    return new TokenResponse(users.login(request.email(), request.password()));
  }

  @PostMapping("/login")
  public TokenResponse login(@Valid @RequestBody LoginRequest request) {
    return new TokenResponse(users.login(request.email(), request.password()));
  }

  public record RegisterRequest(@NotBlank String name, @Email @NotBlank String email, @NotBlank String password) {}
  public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}
  public record TokenResponse(String accessToken) {}
}
