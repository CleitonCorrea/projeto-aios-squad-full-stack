import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '@/services/auth.service';

@Component({
  standalone: true,
  imports: [FormsModule],
  template: `
    <section class="panel">
      <h2>Login</h2>
      <form class="form-grid" (ngSubmit)="submit()">
        <input name="email" type="email" placeholder="Email" [(ngModel)]="email" required>
        <input name="password" type="password" placeholder="Senha" [(ngModel)]="password" required>
        <button type="submit">Entrar</button>
      </form>
    </section>
  `
})
export class LoginPage {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  email = '';
  password = '';

  submit(): void {
    this.auth.login(this.email, this.password).subscribe(() => this.router.navigateByUrl('/dashboard'));
  }
}
