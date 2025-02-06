import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { Router } from '@angular/router';
import { JwtResponse } from '../../models/jwt-response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private router: Router) {}

  login(userEmail: string, password: string) {
    return this.http
      .post<JwtResponse>(`${this.apiUrl}/authenticate`, { userEmail, password })
      .pipe(
        tap((res) => {
          localStorage.setItem('token', res.jwtToken);
          if (res.businessAvailable) {
            this.router.navigate(['/dashboard']);
          } else {
            this.router.navigate(['/business-setup']);
          }
        })
      );
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
