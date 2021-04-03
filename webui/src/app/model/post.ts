import {Comment} from './comment';
import {User} from './user';

export class Post {
  public id: number;
  public isActive: boolean;
  public content: string;
  public postedDate: Date;
  public likes: number;
  public commentList: Comment[];
  public appUser: User;
}


