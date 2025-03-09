import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductResponse } from '../../models/product-response';
import { ProductServiceService } from '../../core/services/product-service.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-details',
  standalone: true,
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css'],
  imports: [CommonModule]
})
export class ProductDetailsComponent implements OnInit {
  product: ProductResponse | null = null; // ✅ Initialize as null to avoid undefined errors

  constructor(
    private route: ActivatedRoute,
    private productService: ProductServiceService
  ) {}

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get('id');

    if (productId) {
      const id = Number(productId); // ✅ Convert id to number
      this.productService.getProductById(id).subscribe({
        next: (data) => {
          this.product = data;
        },
        error: (err) => {
          console.error('Error fetching product:', err);
        }
      });
    }
  }
}
