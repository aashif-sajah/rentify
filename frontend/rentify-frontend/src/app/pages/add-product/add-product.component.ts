import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ProductServiceService } from '../../core/services/product-service.service';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { BusinessResponse } from '../../models/business-response';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],
  imports: [FormsModule]
})
export class AddProductComponent implements OnInit {
  productData = {
    name: '',
    description: '',
    pricePerDay: 0,
    availability: true,
    category: '',
    businessId: 0,
  };

  selectedFiles: File[] = [];
  errorMessage = '';
  businessData!: BusinessResponse; // Store theme details
  storeTheme = {
    fontStyle: '',
    primaryColor: '',
    logoUrl: '',
  };

  constructor(
    private productService: ProductServiceService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const businessDataString = localStorage.getItem('businessData');
    if (businessDataString) {
      this.businessData = JSON.parse(businessDataString);
      this.productData.businessId = this.businessData.id;
      this.storeTheme = this.businessData.storeTheme;
      this.applyTheme();
    }
  }

  applyTheme() {
    document.documentElement.style.setProperty('--primary-color', this.storeTheme.primaryColor);
    document.documentElement.style.setProperty('--font-style', this.storeTheme.fontStyle);
  }

  handleFileInput(event: any) {
    this.selectedFiles = Array.from(event.target.files);
  }

  submitProductForm(form: NgForm) {
    if (form.invalid) {
      this.errorMessage = 'Please fill in all fields correctly!';
      return;
    }

    if (this.selectedFiles.length === 0) {
      this.errorMessage = 'Please upload at least one image!';
      return;
    }

    const formData = new FormData();
    formData.append('product', JSON.stringify(this.productData));
    this.selectedFiles.forEach((file) => formData.append('images', file));

    this.productService.addProduct(formData).subscribe({
      next: () => {
        alert('Product added successfully!');
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        this.errorMessage = `Error: ${error.message}`;
      },
    });
  }
}
