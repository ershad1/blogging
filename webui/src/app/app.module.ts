import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CommonModule} from '@angular/common';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
// components
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {NavbarComponent} from './navbar/navbar.component';
import {PostDetailComponent} from './post-detail/post-detail.component';
// services
import {AccountService} from './service/account.service';
import {LoadingService} from './service/loading.service';
import {PostService} from './service/post.service';
import {PostresolverService} from './service/postresolver.service';
// guards
import {AuthenticationGuard} from './guard/authentication.guard';
// interceptors
import {AuthInterceptor} from './interceptor/auth.interceptor';
// external modules
import {NgxLoadingModule} from 'ngx-loading';
import {NotificationModule} from './notification.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ProfileComponent} from './profile/profile.component';

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthenticationGuard]},
  {
    path: 'post/:postId', component: PostDetailComponent,
    resolve: {resolvedPost: PostresolverService}, canActivate: [AuthenticationGuard]
  },
  {path: 'profile/:username', component: ProfileComponent, canActivate: [AuthenticationGuard]},
  {path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    NavbarComponent,
    PostDetailComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CommonModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    NgxLoadingModule.forRoot({}),
    NotificationModule,
  ],
  providers: [
    AccountService,
    LoadingService,
    PostService,
    PostresolverService,
    AuthenticationGuard,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
