import {Post} from './post';

export class User {
  id: number;
  fullName: string;
  role: string;
  isActive: boolean;
  username: string;
  email: string;
  password: string;
  bio: string;
  post: Post[];
  likedPost: Post[];
}
