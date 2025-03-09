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

    // Temporary mock product data (Replace this with API data)
    this.product = {
      id: productId,
      name: 'Toyota Supra',
      description: 'A high-performance sports car with a sleek design.',
      pricePerDay: 250,
      availability: true,
      category: 'Sports Car',
      businessId: 12345,
      images: [
        'assets/images/supra1.jpg',
        'assets/images/supra2.jpg',
        'assets/images/supra3.jpg'
      ]
    };
  }
}
