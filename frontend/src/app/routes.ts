import { Routes } from '@angular/router';
import { DashboardPage } from '@/pages/dashboard.page';
import { LoginPage } from '@/pages/login.page';
import { NotificationsPage } from '@/pages/notifications.page';
import { ProductsPage } from '@/pages/products.page';
import { RegisterPage } from '@/pages/register.page';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'login', component: LoginPage },
  { path: 'register', component: RegisterPage },
  { path: 'dashboard', component: DashboardPage },
  { path: 'products', component: ProductsPage },
  { path: 'notifications', component: NotificationsPage }
];
