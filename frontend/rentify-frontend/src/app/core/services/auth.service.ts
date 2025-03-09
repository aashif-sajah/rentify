import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { JwtResponse } from '../../models/jwt-response';
import { User } from '../../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  login(userEmail: string, userPassword: string): Observable<JwtResponse> {
    return this.http
      .post<JwtResponse>(`${this.apiUrl}/authenticate`, { userEmail, userPassword })
      .pipe(
        catchError((error) => {
          let errorMessage = 'Unknown error occurred';
          if (error.status === 401) {
            errorMessage = error.error?.message || 'Invalid credentials';
          }
          return throwError(() => new Error(errorMessage));
        })
      );
  }


  public setRole(roles: String[]) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  public getRole(): { role: string; roleDescription: string }[] {
    const roles = localStorage.getItem('roles');
    return roles ? JSON.parse(roles) : [];
  }

  public setToken(token: string) {
    localStorage.setItem('jwtToken', token);
  }

  public getToken(): string {
    return localStorage.getItem('jwtToken') || '';
  }

  
  public clearRole() {
    localStorage.clear();
  }

  public isLoggedIn(): boolean {
    const token = this.getToken();
    const roles = this.getRole();
    return !!token && roles.length > 0;
  }

  public hasRole(role: string): boolean {
    return this.getRole().some((r) => r.role === role);
  }

  public setUser(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUser(): User {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : {};
  }

}
