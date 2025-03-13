import { Component, OnDestroy, OnInit } from '@angular/core';
import { BusinessResponse } from '../../models/business-response';
import { ProductResponse } from '../../models/product-response';
import { AuthService } from '../../core/services/auth.service';
import { BusinessService } from '../../core/services/business.service';
import { ProductServiceService } from '../../core/services/product-service.service';
import { ActivatedRoute, Router } from '@angular/router';
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
      private router: Router,
      private activatedRoute: ActivatedRoute
    ) {}


    ngOnInit(): void {
      console.log("This is working!!!!")
      this.activatedRoute.paramMap.subscribe(params => {
        const storeSlug = params.get('slug');
        console.log('Store slug:', storeSlug);
        if (storeSlug) {
          this.fetchProductsBySlug(storeSlug);
        }
      });


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

    fetchProductsBySlug(storeSlug: string): void {
      this.businessService.getBusinessBySlug(storeSlug).subscribe({
        next: (data) => {
          this.business = data;
          console.log('Business fetched:', this.business);
          if (this.business && this.business.id) {
            this.fetchProducts(this.business.id);
          } else{console.log('business not found');}
        },
        error: (err) => {
          console.error('Error fetching business:', err);
        },
      });
    }

    viewProductDetails(product: ProductResponse) {
      this.productService.setSelectedProduct(product);
      this.router.navigate(['/product-details', product.id]);
    }
}
