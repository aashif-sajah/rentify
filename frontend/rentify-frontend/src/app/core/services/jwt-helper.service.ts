import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {jwtDecode} from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtHelperService {

  constructor(private router:Router) {}

  isTokenExpired(): boolean {
    const token = localStorage.getItem('jwtToken');
    if (!token) return true;

    try {
      const decoded: any = jwtDecode(token);
      const currentTime = Math.floor(Date.now() / 1000);
      return decoded.exp < currentTime;
    } catch (error) {
      return true;
    }

  }


}
