import { Component, OnInit } from '@angular/core';
import { BusinessResponse } from '../../models/business-response';
import { User } from '../../models/user';
import { AuthService } from '../../core/services/auth.service';
import { BusinessService } from '../../core/services/business.service';
import { Router } from '@angular/router';
import { ProductResponse } from '../../models/product-response';
import { ProductServiceService } from '../../core/services/product-service.service';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  imports: [NgFor],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  business!: BusinessResponse;
  user!: User;
  products: ProductResponse[] = [];

  constructor(
    private authService: AuthService,
    private businessService: BusinessService,
    private productService: ProductServiceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.business = this.businessService.getBusiness();
    this.user = this.authService.getUser();
    console.log(this.business);
    console.log(this.user);

    if (this.business && this.business.id) {
      this.fetchProducts(this.business.id);
    }
  }

  fetchProducts(businessId: number): void {
    this.productService.getAllProductsByBusiness(businessId).subscribe({
      next: (data) => {
        this.products = data;
        console.log('Products:', this.products);
      },
      error: (err) => {
        console.error('Error fetching products:', err);
      },
    });
  }

  addProduct(): void {
    console.log('Add product');
    this.router.navigate(['/add-product']);
  }

}
