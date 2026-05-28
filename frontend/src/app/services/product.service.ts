import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { PRODUCT_API_BASE_URL } from '@/core/api.config';
import { Product } from '@/models/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly http = inject(HttpClient);

  list(): Observable<Product[]> {
    return this.http.get<Product[]>(`${PRODUCT_API_BASE_URL}/products`);
  }

  create(product: Pick<Product, 'name' | 'description' | 'price'>): Observable<Product> {
    return this.http.post<Product>(`${PRODUCT_API_BASE_URL}/products`, product);
  }
}
