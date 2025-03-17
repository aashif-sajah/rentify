import { Component } from '@angular/core';
import { RegisterService } from '../../core/services/register.service';
import { Router } from '@angular/router';
import { User } from '../../models/user';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payment',
  imports: [FormsModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css',
})
export class PaymentComponent {
  user: User = {
    userFirstName: 'Test',
    userLastName: 'User',
    userEmail: '',
    username: '',
    userPassword: 'abcd1234',
    roles: [{ role: 'Customer', roleDescription: 'Customer' }],
  };

  constructor(
    private registerService: RegisterService,
    private router: Router
  ) {}

  onSubmit() {
    console.log(this.user);
    this.registerService.register(this.user).subscribe({
      next: (response) => {
        console.log(response, ' Successfully created user');
        
      },
      error: (error) => {
        console.error('There was an error!', error);
        this.reSetForm();
      },
    });
  }

  reSetForm() {
    this.user = {
      userFirstName: 'Test',
      userLastName: 'User',
      userEmail: '',
      username: '',
      userPassword: 'abcd1234',
      roles: [{ role: 'Customer', roleDescription: 'Customer' }],
    };
  }
}
