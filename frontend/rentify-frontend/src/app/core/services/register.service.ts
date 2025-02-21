import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../models/user';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private apiUrl = 'http://localhost:8080/api/auth/registerNewUser';

  constructor(private http: HttpClient) { }

  register(user: User): Observable<User> {
    console.log(user);
    return this.http.post<User>(this.apiUrl, user).pipe(
      catchError(this.handleError)
    );

  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Client-side error: ${error.error.message}`;
    } else {
      // Server-side error
      if (error.status === 400) {
        errorMessage = 'Invalid input data. Please check your details.';
      } else if (error.status === 409) {
        errorMessage = 'User with this email already exists.';
      } else {
        errorMessage = `Server returned code ${error.status}, message: ${error.message}`;
      }
    }
    return throwError(() => new Error(errorMessage));
  }
}
