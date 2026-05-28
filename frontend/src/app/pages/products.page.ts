import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Product } from '@/models/product.model';
import { ProductService } from '@/services/product.service';

@Component({
  standalone: true,
  imports: [FormsModule],
  template: `
    <section class="panel">
      <h2>Cadastro de produtos</h2>
      <form class="form-grid" (ngSubmit)="create()">
        <input name="name" placeholder="Nome" [(ngModel)]="name" required>
        <textarea name="description" placeholder="Descrição" [(ngModel)]="description"></textarea>
        <input name="price" type="number" min="0" step="0.01" placeholder="Preço" [(ngModel)]="price" required>
        <button type="submit">Salvar produto</button>
      </form>
    </section>
    <section class="panel">
      <h2>Produtos</h2>
      <table class="table">
        <thead><tr><th>Nome</th><th>Descrição</th><th>Preço</th></tr></thead>
        <tbody>
          @for (product of products; track product.id) {
            <tr><td>{{ product.name }}</td><td>{{ product.description }}</td><td>{{ product.price }}</td></tr>
          }
        </tbody>
      </table>
    </section>
  `
})
export class ProductsPage implements OnInit {
  private readonly productsApi = inject(ProductService);
  products: Product[] = [];
  name = '';
  description = '';
  price = 0;

  ngOnInit(): void {
    this.load();
  }

  create(): void {
    this.productsApi.create({ name: this.name, description: this.description, price: this.price }).subscribe(() => {
      this.name = '';
      this.description = '';
      this.price = 0;
      this.load();
    });
  }

  private load(): void {
    this.productsApi.list().subscribe((products) => this.products = products);
  }
}
