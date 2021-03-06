import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {AccountService} from '../service/account.service';
import {LoadingService} from '../service/loading.service';
import {User} from '../model/user';
import {NotificationService} from '../service/notification.service';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];

  constructor(
    private router: Router,
    private accountService: AccountService,
    private loadingService: LoadingService,
    private notificationService: NotificationService
  ) {
  }

  ngOnInit() {
    if (this.accountService.isLoggedIn()) {
      if (this.accountService.redirectUrl) {
        this.router.navigateByUrl(this.accountService.redirectUrl);
      } else {
        this.router.navigateByUrl('/home');
      }
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  onLogin(user: User): void {
    this.loadingService.isLoading.next(true);
    console.log(user);
    this.subscriptions.push(
      this.accountService.login(user).subscribe(
        response => {
          const token: string = response.headers.get('Authorization');
          this.accountService.saveToken(token);
          if (this.accountService.redirectUrl) {
            this.router.navigateByUrl(this.accountService.redirectUrl);
          } else {
            this.router.navigateByUrl('/home');
          }
          this.loadingService.isLoading.next(false);
        },
        error => {
          console.log(error);
          this.loadingService.isLoading.next(false);
          this.notificationService.notifyError(
            'Username or password incorrect or You are not activated yet.'
          );
        }
      )
    );
  }
  onRegister(user): void {
    this.loadingService.isLoading.next(true);
    console.log(user);
    this.subscriptions.push(
      this.accountService.register(user).subscribe(
        response => {
          this.loadingService.isLoading.next(false);
          this.notificationService.notifySuccess(
            'You have registered successfully.'
          );
          console.log(response);
        },
        (error: HttpErrorResponse) => {
          console.log(error);
          this.loadingService.isLoading.next(false);
          const errorMsg: string = error.error;
          if (errorMsg === 'usernameExist') {
            this.notificationService.notifyError(
              'This username already exists. Please try with a different username',
            );
          } else if (errorMsg === 'emailExist') {
            this.notificationService.notifyError(
              'This email address already exists. Please try with a different email',
            );
          } else {
            this.notificationService.notifyError(
              'Something went wrong. Please try again.',
            );
          }
        }
      )
    );
  }


  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe);
  }
}
