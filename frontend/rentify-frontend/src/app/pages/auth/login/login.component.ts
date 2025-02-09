import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  constructor(private authService: AuthService, private router: Router) {}

  userEmail = '';
  password = '';

  errorMessage: string = '';
  usernameError: boolean = false;
  passwordError: boolean = false;

  onSubmit() {
    if (!this.userEmail || !this.password) {
      this.errorMessage = 'Please enter email and password';
      return;
    }

    this.authService.login(this.userEmail, this.password).subscribe({
      next: (res) => {
        this.authService.setToken(res.jwtToken);
        if (res.businessAvailable) {
          this.router.navigate(['/dashboard']);
        } else {
          this.router.navigate(['/business-setup']);
        }
      },
      error: (err) => {
        this.handleLoginError(err.message);
      },
    });
  }

  private handleLoginError(errorMessage: string) {
    const message = errorMessage.toLowerCase();

    if (message.includes('username')) {
      this.usernameError = true;
      this.errorMessage = 'Invalid username';
    } else if (
      message.includes('password') ||
      message.includes('credentials')
    ) {
      this.passwordError = true;
      this.errorMessage = 'Invalid credentials';
    } else {
      this.errorMessage = 'Login failed. Please try again.';
    }
  }

  private resetErrors() {
    this.errorMessage = '';
    this.usernameError = false;
    this.passwordError = false;
  }
}
