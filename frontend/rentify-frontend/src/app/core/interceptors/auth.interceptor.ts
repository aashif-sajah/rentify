import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.getToken();

  // Clone and modify request
  let modifiedReq = req.clone();

  // Only add headers if not present
  if (!modifiedReq.headers.has('Authorization') && token && authService.isLoggedIn()) {
    modifiedReq = modifiedReq.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
        'X-Requested-With': 'XMLHttpRequest'
      }
    });
  }

  // Add security headers to all requests
  modifiedReq = modifiedReq.clone({
    setHeaders: {
      'X-Content-Type-Options': 'nosniff',
      'X-Frame-Options': 'DENY'
    }
  });

  return next(modifiedReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        // Handle unauthorized (token expired/invalid)
        authService.clearRole();
        router.navigate(['/login'], {
          queryParams: { returnUrl: router.url }
        });
      } else if (error.status === 403) {
        // Handle forbidden (insufficient permissions)
        router.navigate(['/unauthorized']);
      }

      // Re-throw the error for component-level handling
      return throwError(() => error);
    })
  );
};
