import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {JwtHelperService} from '@auth0/angular-jwt';
import {User} from '../model/user';
import {Post} from '../model/post';
import {ServerConstant} from '../constant/server-constant';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  constant: ServerConstant = new ServerConstant();
  public host: string = this.constant.host;
  public token: string;
  public loggInUsername: string | null;
  public redirectUrl: string;
  private jwtHelper = new JwtHelperService();
  public role: string;

  constructor(private http: HttpClient) {
  }

  login(user: User): Observable<HttpErrorResponse | HttpResponse<any>> {
    return this.http.post<HttpErrorResponse | HttpResponse<any>>(`${this.host}/user/login`, user, {observe: 'response'});
  }

  register(user: User): Observable<User | HttpErrorResponse> {
    return this.http.post<User>(`${this.host}/user/register`, user);
  }

  logOut(): void {
    this.token = null;
    this.loggInUsername = null;
    localStorage.removeItem('token');
  }

  saveToken(token: string): void {
    this.token = token;
    this.loggInUsername = this.jwtHelper.decodeToken(this.token).sub;
    let roles = this.jwtHelper.decodeToken(this.token).roles;
    this.role = roles[0]
    localStorage.setItem('token', token);
  }

  loadToken(): void {
    this.token = localStorage.getItem('token');
  }

  getToken(): string {
    return this.token;
  }
  getRole() {
    return this.role;
  }

  isLoggedIn(): boolean {
    this.loadToken();
    if (this.token != null && this.token !== '') {
      if (this.jwtHelper.decodeToken(this.token).sub != null || '') {
        if (!this.jwtHelper.isTokenExpired(this.token)) {
          this.loggInUsername = this.jwtHelper.decodeToken(this.token).sub;
          return true;
        }
      }
    } else {
      this.logOut();
      return false;
    }
  }

  getUserInformation(username: string): Observable<User> {
    return this.http.get<User>(`${this.host}/user/${username}`);
  }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.host}/post/list`);
  }

  getActivePosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.host}/post/activeList`);
  }

  searchUsers(username: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.host}/user/findByUsername/${username}`);
  }

  updateUser(updateUser: User): Observable<User> {
    return this.http.post<User>(`${this.host}/user/update`, updateUser);
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.host}/user/list`);
  }
  activateUser(username: string) {
    return this.http.post(`${this.host}/user/activate`, {username}, {
      responseType: 'text'
    });
  }
  deactivateUser(username: string) {
    return this.http.post(`${this.host}/user/deactivate`, {username}, {
      responseType: 'text'
    });
  }

  activatePost(id: number) {
    return this.http.post(`${this.host}/post/activate`, {id}, {
      responseType: 'text'
    });
  }
  deactivatePost(id: number) {
    return this.http.post(`${this.host}/post/deactivate`, {id}, {
      responseType: 'text'
    });
  }
}
