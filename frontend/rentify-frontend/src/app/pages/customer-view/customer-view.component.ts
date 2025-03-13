import { Component, OnDestroy, OnInit } from '@angular/core';
import { BusinessResponse } from '../../models/business-response';
import { ProductResponse } from '../../models/product-response';
import { AuthService } from '../../core/services/auth.service';
import { BusinessService } from '../../core/services/business.service';
import { ProductServiceService } from '../../core/services/product-service.service';
import { Router } from '@angular/router';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-customer-view',
  imports: [NgFor],
  templateUrl: './customer-view.component.html',
  styleUrl: './customer-view.component.css'
})
export class CustomerViewComponent implements OnInit,OnDestroy
{
  business!: BusinessResponse;
  products: ProductResponse[] = [];
  isActive:boolean = false;

  constructor(
      private authService: AuthService,
      private businessService: BusinessService,
      private productService: ProductServiceService,
      private router: Router
    ) {}


    ngOnInit(): void {
      this.business = this.businessService.getBusiness();
      console.log(this.business + 'This is Business Response');

      if (this.business && this.business.id) {
        this.fetchProducts(this.business.id);
      }

      this.isActive = true;
      this.authService.setActive(this.isActive);
    }

    ngOnDestroy(): void {
      this.isActive = false;
      this.authService.setActive(this.isActive);
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

    viewProductDetails(product: ProductResponse) {
      this.productService.setSelectedProduct(product);
      this.router.navigate(['/product-details', product.id]);
    }
}
