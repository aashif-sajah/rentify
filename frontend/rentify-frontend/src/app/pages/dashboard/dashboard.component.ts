import { Component, OnInit } from '@angular/core';
import { BusinessResponse } from '../../models/business-response';
import { User } from '../../models/user';
import { AuthService } from '../../core/services/auth.service';
import { BusinessService } from '../../core/services/business.service';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [RouterOutlet],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit
{
    business!: BusinessResponse;
    user!: User;

    constructor(
      private authService: AuthService,
      private businessService:BusinessService
    ) {}

    ngOnInit(): void {
      this.business = this.businessService.getBusiness();
      this.user = this.authService.getUser();
    }

    editProfile() {
      console.log("Edit user profile clicked");
    }

    viewProducts() {
      console.log("Navigate to products");
    }

    viewOrders() {
      console.log("Navigate to orders");
    }

    viewAnalytics() {
      console.log("Navigate to analytics");
    }

}
