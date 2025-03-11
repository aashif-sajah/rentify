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
  deleteProductConfirm: boolean = false;
  showSuccessMessage: boolean = false;
  productIdToDelete: number | null = null;
  message: string = '';
  

  constructor(
    private authService: AuthService,
    private businessService: BusinessService,
    private productService: ProductServiceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.business = this.businessService.getBusiness();
    this.user = this.authService.getUser();
    console.log(this.business + 'This is Business Response');
    console.log(this.user + 'This is User');

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

  deleteProduct(): void {
    console.log('Delete product');
    this.productService.deleteProduct(this.products[0].id).subscribe({
      next: (data) => {
        console.log('Product deleted:', data);
        this.fetchProducts(this.business.id);
      },
      error: (err) => {
        console.error('Error deleting product:', err);
      },
    });
  }

  deleteTheProduct(id: number): void {
    console.log('deleteThe Product Clicked!!!!!');
    this.deleteProductConfirm = true;
    this.productIdToDelete = id;
    this.message = 'Are you sure you want to delete this product?';
  }

  confirmDelete(): void {
    if (this.productIdToDelete !== null) {
      this.productService.deleteProduct(this.productIdToDelete).subscribe({
        next: (data) => {
          console.log('Product deleted:', data);
          this.cancelDelete();
          this.fetchProducts(this.business.id);
          this.showSuccessMessage = true;
          this.message = 'Product deleted successfully';
          setTimeout(() => (this.showSuccessMessage = false), 6000);
        },
        error: (err) => {
          console.error('Error deleting product:', err);
          this.showSuccessMessage = true;
          this.message = 'Error deleting the product';
          setTimeout(() => (this.showSuccessMessage = false), 6000);
        },
      });
    }
  }

  cancelDelete(): void {
    this.deleteProductConfirm = false;
    this.productIdToDelete = null;
  }

  editProduct(): void {
    this.router.navigate(['/edit-product']);
  }

  editTheProduct(productId: number): void {
    const productToEdit = this.products.find(
      (product) => product.id === productId
    );
    console.log('Edit product');
    if (productToEdit) {
      this.productService.setSelectedProduct(productToEdit);
      this.router.navigate(['/add-product']);
    } else {
      console.error('Product not found!');
    }
  }
  viewProductDetails(product: ProductResponse) {
    this.productService.setSelectedProduct(product);
    this.router.navigate(['/product-details', product.id]);
  }

}
