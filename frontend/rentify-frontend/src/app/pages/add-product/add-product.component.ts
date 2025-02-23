import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ProductServiceService } from '../../core/services/product-service.service';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { BusinessResponse } from '../../models/business-response';
import { BusinessService } from '../../core/services/business.service';

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
    private businessService: BusinessService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.setBusinessDataToProduct();
  }

  setBusinessDataToProduct()
  {
    const businessData = this.businessService.getBusiness();

    if (businessData) {
      this.productData.businessId = businessData.id;
      console.log(this.productData.businessId);
      this.storeTheme = businessData.storeTheme;
      this.applyTheme();
    } else
    {
      console.error('Business data not found! from product component line 49!');
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

    if(!this.productData.businessId)
    {
      this.setBusinessDataToProduct();
    }

    console.log(this.productData.businessId);

    const formData = new FormData();
    formData.append('product', JSON.stringify(this.productData));
    this.selectedFiles.forEach((file) => formData.append('images', file));
    //formData.append('images', this.selectedFiles[0]);

    this.productService.addProduct(formData).subscribe({
      next: (res) => {
        console.log('Product added successfully!', res);
        alert('Product added successfully!');
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        this.errorMessage = `Error: ${error.message} line 96`;
      },
    });
  }
}
