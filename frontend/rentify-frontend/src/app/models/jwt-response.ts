import { BusinessResponse } from './business-response';
import { User } from './user';

export interface JwtResponse {
  user: User;
  jwtToken: string;
  businessAvailable: boolean;
  businessResponse: BusinessResponse;
}
