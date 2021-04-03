import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AccountService} from '../service/account.service';
import {NotificationService} from '../service/notification.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {
  constructor(private accountService: AccountService, private notificationService: NotificationService,
              private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    return this.isLoggedIn(state.url);
  }

  private isLoggedIn(url: string): boolean {
    if (this.accountService.isLoggedIn) {
      return true;
    }
    this.accountService.redirectUrl = url;
    this.router.navigate(['/login']);
    this.notificationService.notifyError('You must be logged in to access this page');
    return false;
  }
}
