import { Component } from '@angular/core';
import { BusinessResponse } from '../../models/business-response';
import { ProductResponse } from '../../models/product-response';
import { AuthService } from '../../core/services/auth.service';
import { BusinessService } from '../../core/services/business.service';
import { ProductServiceService } from '../../core/services/product-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-view',
  imports: [],
  templateUrl: './customer-view.component.html',
  styleUrl: './customer-view.component.css'
})
export class CustomerViewComponent 
{
  business!: BusinessResponse;
  products: ProductResponse[] = [];

  constructor(
      private authService: AuthService,
      private businessService: BusinessService,
      private productService: ProductServiceService,
      private router: Router
    ) {}

    
}
