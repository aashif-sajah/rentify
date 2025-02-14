import { NgIf } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BusinessService } from '../../core/services/business.service';
import { BusinessRequest } from '../../models/business-request';

@Component({
  selector: 'app-business-setup',
  imports: [FormsModule],
  templateUrl: './business-setup.component.html',
  styleUrl: './business-setup.component.css'
})
export class BusinessSetupComponent {
  businessRequest:BusinessRequest = {
    businessName: '',
    businessType: '',
    description: '',
    contactEmail: '',
    phone: '',
    storeTheme: {
      fontStyle: '',
      primaryColor: '',
      logoUrl: '',
    }
  };
  selectedFile: File | null = null;

  constructor(private businessService: BusinessService) {}

  // Handle file selection
  onFileSelected(event: any) {
    if (event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
  }

  // Submit form data
  onSubmit() {
    if (!this.selectedFile) {
      alert('Please select an image.');
      return;
    }

    const formData = new FormData();

    // Convert object to JSON string before appending
    formData.append('businessRequest', JSON.stringify(this.businessRequest));
    formData.append('image', this.selectedFile);

    this.businessService.createBusiness(formData).subscribe({
      next: (response) => {
        console.log('Business Created:', response);
        alert('Business created successfully!');
      },
      error: (error) => {
        console.error('Error creating business:', error);
        alert('Failed to create business.');
      }
    })
  }

}
