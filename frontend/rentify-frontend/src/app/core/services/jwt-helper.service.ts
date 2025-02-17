import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtHelperService {

  constructor(private router:) {}

  isTokenExpired(): boolean {
    const token = localStorage.getItem('jwtToken');
    if (!token) return true;

    try {
      const decoded: any = jwt_decode(token);
      const currentTime = Math.floor(Date.now() / 1000);
      return decoded.exp < currentTime;
    } catch (error) {
      return true;
    }

  }


}
