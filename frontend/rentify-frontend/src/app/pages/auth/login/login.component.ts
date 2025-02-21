import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { BusinessService } from '../../../core/services/business.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule, NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  constructor(private authService: AuthService, private router: Router,
    private businessService: BusinessService
  ) {}

  userEmail = '';
  userPassword = '';

  errorMessage: string = '';
  usernameError: boolean = false;
  passwordError: boolean = false;

  onSubmit() {
    console.log('submitting login form' + this.userEmail +' : '+ this.userPassword);
    if (!this.userEmail || !this.userPassword) {
      this.errorMessage = 'Please enter email and password';
      return;
    }

    this.authService.login(this.userEmail, this.userPassword).subscribe({
      next: (res) => {
        console.log('login response', res);
        this.authService.setToken(res.jwtToken);
        this.authService.setRole(res.user.roles.map(role => role.role));
        this.businessService.setBusiness(res.businessResponse);
        this.authService.setUser(res.user);
        console.log("jwt from local storage: " + this.businessService.getBusiness());
        console.log("jwt from local storage: " + this.authService.getToken());
        console.log("Roles from local storage: " + this.authService.getRole());
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
      if (errorMessage.includes('disabled')) {
        this.errorMessage = 'Your account is disabled. Contact support.';
      } else if (errorMessage.includes('username') || errorMessage.includes('password')) {
        this.errorMessage = 'Invalid username or password.';
        this.passwordError = true;
      } else {
        this.errorMessage = 'Login failed. Please try again later.';
      }
    }


  private resetErrors() {
    this.errorMessage = '';
    this.usernameError = false;
    this.passwordError = false;
  }
}
