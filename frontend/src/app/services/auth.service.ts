import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { API_BASE_URL } from '@/core/api.config';

interface TokenResponse {
  accessToken: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);

  login(email: string, password: string): Observable<TokenResponse> {
    return this.http.post<TokenResponse>(`${API_BASE_URL}/auth/login`, { email, password }).pipe(
      tap((response) => sessionStorage.setItem('accessToken', response.accessToken))
    );
  }

  register(name: string, email: string, password: string): Observable<TokenResponse> {
    return this.http.post<TokenResponse>(`${API_BASE_URL}/auth/register`, { name, email, password }).pipe(
      tap((response) => sessionStorage.setItem('accessToken', response.accessToken))
    );
  }
}
