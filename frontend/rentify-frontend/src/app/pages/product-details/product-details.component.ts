import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-details',
  standalone: true,  // ✅ Make it standalone
  imports: [CommonModule], // ✅ Import CommonModule for *ngFor, *ngIf
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  product: any;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Fetching product ID from the URL
    const productId = this.route.snapshot.paramMap.get('id');

    // Temporary placeholder until backend logic is added
    this.product = {
      id: productId,
      name: 'Car Model',
      description: 'A high-quality car with great features.',
      pricePerDay: 3000,
      availability: true,
      category: 'Luxury Car',
      businessId: 12345,
      images: [] // This will be replaced by backend API data
    };
  }
}
