import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  
  constructor( private authService: AuthService, private router: Router ) { }


  isLoggedIn(): boolean
  {
    return this.authService.isLoggedIn();
  }

  logout() {
    this.authService.clearRole();
    this.authService.setToken('');
    this.router.navigate(['/login']);
  }

  isActive(): boolean {
    return this.authService.getActive();
  }
  
}
