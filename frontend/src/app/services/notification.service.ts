import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { NOTIFICATION_API_BASE_URL } from '@/core/api.config';
import { Notification } from '@/models/notification.model';

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private readonly http = inject(HttpClient);

  list(): Observable<Notification[]> {
    return this.http.get<Notification[]>(`${NOTIFICATION_API_BASE_URL}/notifications`);
  }
}
