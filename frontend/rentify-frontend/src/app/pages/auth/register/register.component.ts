import { User } from './../../../models/user';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [FormsModule,RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent
{
  user: User = {
    userFirstName: '',
    userLastName: '',
    userEmail: '',
    username: '',
    userPassword: '',
    roles: [{ role: 'USER', roleDescription: 'Standard User' }],
  };

  


}
