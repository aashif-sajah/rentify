import { User } from './../../../models/user';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterService } from '../../../core/services/register.service';

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
  errorMessage: any;

  constructor(private registerService: RegisterService, private router: Router) {}

  onSubmit(form: NgForm) {
    if (form.invalid) return;


    this.registerService.register(this.user).subscribe({
      next: (response) => {
        alert(response.userEmail + ' has been registered successfully!'); // Alert user of successful registration
        this.router.navigate(['/login']); // Redirect to login after successful registration
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
        this.reSetForm();
      },
    });
  }

  reSetForm(){
    this.user = {
      userFirstName: '',
      userLastName: '',
      userEmail: '',
      username: '',
      userPassword: '',
      roles: [],
  }
}

}
