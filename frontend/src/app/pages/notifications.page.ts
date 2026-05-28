import { Component, OnInit, inject } from '@angular/core';
import { Notification } from '@/models/notification.model';
import { NotificationService } from '@/services/notification.service';

@Component({
  standalone: true,
  template: `
    <section class="panel">
      <h2>Notificações</h2>
      <table class="table">
        <thead><tr><th>Destinatário</th><th>Assunto</th><th>Status</th></tr></thead>
        <tbody>
          @for (notification of notifications; track notification.id) {
            <tr><td>{{ notification.recipient }}</td><td>{{ notification.subject }}</td><td>{{ notification.status }}</td></tr>
          }
        </tbody>
      </table>
    </section>
  `
})
export class NotificationsPage implements OnInit {
  private readonly notificationsApi = inject(NotificationService);
  notifications: Notification[] = [];

  ngOnInit(): void {
    this.notificationsApi.list().subscribe((notifications) => this.notifications = notifications);
  }
}
