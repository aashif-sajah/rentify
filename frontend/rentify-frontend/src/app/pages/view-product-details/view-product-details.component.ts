import { Component, OnInit } from '@angular/core';
import { ProductServiceService } from '../../core/services/product-service.service';
import { ProductResponse } from '../../models/product-response';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-view-product-details',
  imports: [FormsModule, NgIf],
  templateUrl: './view-product-details.component.html',
  styleUrl: './view-product-details.component.css'
})
export class ViewProductDetailsComponent implements OnInit {
bookNow() {
throw new Error('Method not implemented.');
}

  productDetails: ProductResponse | null = null;
  numberOfDays: number = 1;
  totalPrice: number = 0;

  constructor(private productService: ProductServiceService) {}

  ngOnInit() 
  {
    this.productDetails = this.productService.getSelectedProduct();
  }

  calculateTotalPrice() {
    if(this.productDetails) {
    this.totalPrice = this.productDetails.pricePerDay * this.numberOfDays;
    }
  }

}
