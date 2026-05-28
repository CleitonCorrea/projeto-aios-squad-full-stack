package com.aiox.productapi.application;

import com.aiox.productapi.contracts.ProductRepository;
import com.aiox.productapi.domain.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
  private final ProductRepository products;

  public ProductService(ProductRepository products) {
    this.products = products;
  }

  public Product create(String name, String description, BigDecimal price, String actor) {
    return products.save(new Product(name, description, price, actor));
  }

  public List<Product> list() {
    return products.findAll();
  }

  public Product get(UUID id) {
    return products.findById(id).orElseThrow(() -> new IllegalArgumentException("product not found"));
  }

  @Transactional
  public Product update(UUID id, String name, String description, BigDecimal price, String actor) {
    Product product = get(id);
    product.update(name, description, price, actor);
    return product;
  }

  public void delete(UUID id) {
    products.deleteById(id);
  }
}
