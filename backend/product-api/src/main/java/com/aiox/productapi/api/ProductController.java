package com.aiox.productapi.api;

import com.aiox.productapi.application.ProductService;
import com.aiox.productapi.domain.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
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
@RequestMapping("/products")
public class ProductController {
  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse create(@Valid @RequestBody ProductRequest request) {
    return ProductResponse.from(service.create(request.name(), request.description(), request.price(), "authenticated-user"));
  }

  @GetMapping
  public List<ProductResponse> list() {
    return service.list().stream().map(ProductResponse::from).toList();
  }

  @GetMapping("/{id}")
  public ProductResponse get(@PathVariable UUID id) {
    return ProductResponse.from(service.get(id));
  }

  @PutMapping("/{id}")
  public ProductResponse update(@PathVariable UUID id, @Valid @RequestBody ProductRequest request) {
    return ProductResponse.from(service.update(id, request.name(), request.description(), request.price(), "authenticated-user"));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }

  public record ProductRequest(@NotBlank String name, String description, @NotNull @DecimalMin("0.00") BigDecimal price) {}
  public record ProductResponse(UUID id, String name, String description, BigDecimal price, Instant createdAt, Instant updatedAt, long version) {
    static ProductResponse from(Product product) {
      return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCreatedAt(), product.getUpdatedAt(), product.getVersion());
    }
  }
}
