import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {AccountService} from '../service/account.service';
import {PostService} from '../service/post.service';
import {LoadingService} from '../service/loading.service';
import {Post} from '../model/post';
import {User} from '../model/user';
import {HttpErrorResponse} from '@angular/common/http';
import {NotificationService} from '../service/notification.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, OnDestroy {
  postId: number;
  user: User;
  users: User[];
  host: string;
  posts: Post[] = [];

  userHost: string;
  postHost: string;
  username: string;
  profilePictureChange: boolean;
  profilePicture: File;
  private subscriptions: Subscription[] = [];

  constructor(
    private route: ActivatedRoute,
    public accountService: AccountService,
    private postService: PostService,
    private router: Router,
    private loadingService: LoadingService,
    private notificationService: NotificationService
  ) {
  }

  ngOnInit() {
    this.getPosts();
    this.loadingService.isLoading.next(true);
    this.username = this.route.snapshot.paramMap.get('username');
    this.host = this.postService.host;
    this.userHost = this.postService.userHost;
    this.postHost = this.postService.postHost;
    this.getUserInfo(this.username);
    this.getUsers();
    this.loadingService.isLoading.next(false);
  }

  getPosts(): void {
    this.subscriptions.push(this.accountService.getPosts().subscribe(
      (response: Post[]) => {
        this.posts = response;
        console.log(this.posts);
        this.loadingService.isLoading.next(false);
      },
      error => {
        console.log(error);
        this.loadingService.isLoading.next(false);
      }
    ));
  }

  getUserInfo(username: string): void {
    this.subscriptions.push(
      this.accountService.getUserInformation(username).subscribe(
        (response: User) => {
          this.user = response;
          this.getPostsByUsername(this.user.username);
        },
        error => {
          console.log(error);
          this.user = null;
        }
      )
    );
  }

  getUsers(): void {
    this.subscriptions.push(
      this.accountService.getUsers().subscribe(
        (response: User[]) => {
          this.users = response;
          // var table = document.getElementById('table');
          // table.bootstrapTable({data: this.users})
        },
        error => {
          console.log(error);
          this.user = null;
        }
      )
    );
  }

  getPostsByUsername(username: string): void {
    this.subscriptions.push(
      this.postService.getPostsByUsername(username).subscribe(
        (response: Post[]) => {
          this.user.post = response;
        },
        error => {
          console.log(error);
          this.user.post = null;
        }
      )
    );
  }

  onProfilePictureSelected(event: any): void {
    console.log(event);
    this.profilePicture = event.target.files[0] as File;
    console.log(this.profilePicture);
    this.profilePictureChange = true;
  }

  onUpdateUser(updatedUser: User): void {
    this.loadingService.isLoading.next(true);
    this.subscriptions.push(
      this.accountService.updateUser(updatedUser).subscribe(
        response => {
          console.log(response);
          if (this.profilePictureChange) {
          }
          this.loadingService.isLoading.next(false);
          this.notificationService.notifySuccess(
            'Profile updated successfully.',
          );
        },
        error => {
          console.log(error);
          this.loadingService.isLoading.next(false);
          this.notificationService.notifyError(
            'Profile update failed. Please try again..',
          );
        }
      )
    );
  }

  seeOnePost(postId): void {
    this.router.navigate(['/post', postId]);
    console.log(postId);
  }

  onRegister(user): void {
    const element: HTMLElement = document.getElementById('dismissOnSubmitUser') as HTMLElement;
    element.click();
    this.loadingService.isLoading.next(true);
    console.log(user);
    this.subscriptions.push(
      this.accountService.register(user).subscribe(
        response => {
          this.loadingService.isLoading.next(false);
          this.notificationService.notifySuccess(
            'You have registered successfully.',
          );
          this.getUsers();
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
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  activateUser(username: string) {
    this.loadingService.isLoading.next(true);
    this.subscriptions.push(
      this.accountService.activateUser(username).subscribe(
        response => {
          console.log(response);
          this.loadingService.isLoading.next(false);
          this.notificationService.notifySuccess('Activated successfully.',);
          this.getUsers();
        },
        error => {
          console.log(error);
          this.loadingService.isLoading.next(false);
          this.notificationService.notifyError(
            'Activation Failed. Please try again..',
          );
        }
      )
    );
  }

  deactivateUser(username: string) {
    this.loadingService.isLoading.next(true);
    this.subscriptions.push(
      this.accountService.deactivateUser(username).subscribe(
        response => {
          console.log(response);
          this.loadingService.isLoading.next(false);
          this.notificationService.notifySuccess('Deactivated successfully.',);
          this.getUsers();
        },
        error => {
          console.log(error);
          this.loadingService.isLoading.next(false);
          this.notificationService.notifyError(
            'Deactivation Failed. Please try again..',
          );
        }
      )
    );
  }

  private showErrorMessage(errorMessage: string): void {
    if (errorMessage === 'PasswordNotMatched') {
      this.notificationService.notifyError(
        'Passwords do not match. Please try again.',
      );
    } else if (errorMessage === 'IncorrectCurrentPassword') {
      this.notificationService.notifyError(
        'The current password is incorrect. Please try again.',
      );
    } else {
      this.notificationService.notifyError(
        'Password change failed. Please try again.',
      );
    }
  }

  activatePost(id: number) {
    this.loadingService.isLoading.next(true);
    this.subscriptions.push(
      this.accountService.activatePost(id).subscribe(
        response => {
          console.log(response);
          this.loadingService.isLoading.next(false);
          this.notificationService.notifySuccess('Activated successfully.',);
          this.getPosts();
        },
        error => {
          console.log(error);
          this.loadingService.isLoading.next(false);
          this.notificationService.notifyError(
            'Activation Failed. Please try again..',
          );
        }
      )
    );
  }

  deactivatePost(id: number) {
    this.loadingService.isLoading.next(true);
    this.subscriptions.push(
      this.accountService.deactivatePost(id).subscribe(
        response => {
          console.log(response);
          this.loadingService.isLoading.next(false);
          this.notificationService.notifySuccess('Deactivated successfully.',);
          this.getPosts();
        },
        error => {
          console.log(error);
          this.loadingService.isLoading.next(false);
          this.notificationService.notifyError(
            'Deactivation Failed. Please try again..',
          );
        }
      )
    );
  }
}
