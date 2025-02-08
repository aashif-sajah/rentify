import { User } from './../../../models/user';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  user: User = {
    userFirstName: '',
    userLastName: '',
    userEmail: '',
    username: '',
    userPassword: '',
    roles: [],
  };

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(form: NgForm) {
    if (form.invalid) {
      return;
    }
  }

}
