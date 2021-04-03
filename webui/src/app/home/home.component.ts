import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../service/account.service';
import {LoadingService} from '../service/loading.service';
import {PostService} from '../service/post.service';
import {Subscription} from 'rxjs';
import {Post} from '../model/post';
import {User} from '../model/user';
import {NotificationService} from '../service/notification.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  user = new User();
  posts: Post[] = [];
  host: string;
  userHost: string;
  postHost: string;
  private subscriptions: Subscription[] = [];

  constructor(
    private router: Router,
    private accountService: AccountService,
    private postService: PostService,
    private loadingService: LoadingService,
    private notificationService: NotificationService
  ) {
  }

  ngOnInit() {
    this.loadingService.isLoading.next(true);
    this.getUserInfo(this.accountService.loggInUsername);
    this.getPosts();
    this.host = this.postService.host;
    this.userHost = this.postService.userHost;
    this.postHost = this.postService.postHost;
    this.loadingService.isLoading.next(false);
  }

  getUserInfo(username: string): void {
    this.subscriptions.push(
      this.accountService.getUserInformation(username).subscribe(
        (response: User) => {
          this.user = response;
        },
        error => {
          console.log(error);
          this.user = null;
          this.logOut();
          this.router.navigateByUrl('/login');
        }
      ));
  }

  logOut(): void {
    this.accountService.logOut();
    this.router.navigateByUrl('/login');
    this.notificationService.notifyError(
      'You need to log in to access this page.',
    );
  }

  getUserProfile(username: string): void {
    this.router.navigate(['/profile', username]);
    console.log(username);
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

  onDelete(id: number): void {
    this.subscriptions.push(
      this.postService.delete(id).subscribe(
        response => {
          console.log('The deleted post: ', response);
          this.notificationService.notifySuccess(
            'Post was deleted successfully.',
          );
          this.getPosts();
        },
        error => {
          console.log(error);
          this.notificationService.notifyError(
            'Post was not deleted. Please try again.',
          );
          this.getPosts();
        }
      ));
  }

  seeOnePost(postId): void {
    this.router.navigate(['/post', postId]);
    console.log(postId);
  }

  ngOnDestroy() {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
