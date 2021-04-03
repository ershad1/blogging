import {Injectable} from '@angular/core';
import {NotifierService} from 'angular-notifier';
import {NotificationType} from '../enum/notification-type.enum';

@Injectable({providedIn: 'root'})
export class NotificationService {
  constructor(private notifier: NotifierService) {
  }

  public notifySuccess(message: string) {
    this.notifier.notify(NotificationType.SUCCESS, message);
  }

  public notifyError(message: string) {
    this.notifier.notify(NotificationType.ERROR, message);
  }
}
