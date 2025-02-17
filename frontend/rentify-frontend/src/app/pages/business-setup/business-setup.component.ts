import { NgIf } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BusinessService } from '../../core/services/business.service';
import { BusinessRequest } from '../../models/business-request';
import { Router } from '@angular/router';

@Component({
  selector: 'app-business-setup',
  imports: [FormsModule],
  templateUrl: './business-setup.component.html',
  styleUrl: './business-setup.component.css'
})
export class BusinessSetupComponent {
  step = 0;
  loading = false;
  errorMessage = '';
  selectedFile: File | null = null;

  businessData:BusinessRequest = {
    businessName: '',
    description: '',
    contactEmail: '',
    phone: '',
    businessType: '',
    address: '',
    storeTheme:{
      fontStyle: '',
      primaryColor: '',
      logoUrl: ''
    }
  };

  constructor(private businessService:BusinessService, private router: Router) {}

  handleFileInput(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  nextStep() {
    if (this.step < 2) {
      this.step++;
    } else {
      this.submitForm();
    }
  }

  prevStep() {
    if (this.step > 0) {
      this.step--;
    }
  }

  submitForm() {
    console.log(this.businessData);
    if (!this.selectedFile) {
      alert('Please select an image.');
      return;
    }

    this.loading = true;
    const formData = new FormData();

    formData.append('businessRequest', JSON.stringify(this.businessData));
    formData.append('image', this.selectedFile);

    this.businessService.createBusiness(formData).subscribe({
      next: (response: any) => {
        this.loading = false;
        alert('Business created successfully!');
        if (response.isProductAvailable) {
          this.router.navigate(['/dashboard']);
        } else {
          this.router.navigate(['/add-product']);
        }
      },
      error: (error) => {
        this.loading = false;
        alert('Failed to create business.');
        this.errorMessage = 'Failed to create business. Try again! :' + error.message;
        alert("Resetting form");
        this.resetForm();
      },
    });
  }

  resetForm() {
    this.businessData = {
      businessName: '',
      description: '',
      contactEmail: '',
      phone: '',
      businessType: '',
      address: '',
      storeTheme:{
        fontStyle: '',
        primaryColor: '',
        logoUrl: ''
      }
    };
    this.selectedFile = null;
    this.step = 0;
  }


}
