import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterLink, RouterOutlet],
  template: `
    <main class="shell">
      <nav class="nav">
        <h1>AIOX</h1>
        <a routerLink="/login">Login</a>
        <a routerLink="/register">Cadastro</a>
        <a routerLink="/dashboard">Dashboard</a>
        <a routerLink="/products">Produtos</a>
        <a routerLink="/notifications">Notificações</a>
      </nav>
      <section class="content">
        <router-outlet />
      </section>
    </main>
  `
})
export class AppComponent {}
