import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '@/services/auth.service';

@Component({
  standalone: true,
  imports: [FormsModule],
  template: `
    <section class="panel">
      <h2>Cadastro de usuário</h2>
      <form class="form-grid" (ngSubmit)="submit()">
        <input name="name" placeholder="Nome" [(ngModel)]="name" required>
        <input name="email" type="email" placeholder="Email" [(ngModel)]="email" required>
        <input name="password" type="password" placeholder="Senha" [(ngModel)]="password" required>
        <button type="submit">Cadastrar</button>
      </form>
    </section>
  `
})
export class RegisterPage {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  name = '';
  email = '';
  password = '';

  submit(): void {
    this.auth.register(this.name, this.email, this.password).subscribe(() => this.router.navigateByUrl('/dashboard'));
  }
}
