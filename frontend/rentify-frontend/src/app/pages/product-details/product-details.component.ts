import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router  } from '@angular/router';
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
  product: ProductResponse | null = null; 
  selectedImage: string = ''; 
  startIndex: number = 0; // Track starting index for image thumbnails
  fallbackImage: string = 'assets/default-image.jpg'; // Added fallback image

  constructor(
    private route: ActivatedRoute,
    private productService: ProductServiceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get('id');

    if (productId) {
      const id = Number(productId);
      this.productService.getProductById(id).subscribe({
        next: (data) => {
          this.product = data;

          // Ensure imageUrls exists before setting selectedImage
          if (this.product?.imageUrls?.length) {
            this.selectedImage = this.product.imageUrls[0]; 
          } else {
            this.selectedImage = this.fallbackImage; // Use fallback image
          }
        },
        error: (err) => {
          console.error('Error fetching product:', err);
        }
      });
    }
  }

  switchImage(imageUrl: string): void {
    this.selectedImage = imageUrl;
  }

  prevImages(): void {
    if (this.startIndex > 0) {
      this.startIndex -= 4;
    }
  }

  nextImages(): void {
    if (this.product?.imageUrls && this.startIndex + 4 < this.product.imageUrls.length) {
      this.startIndex += 4;
    }
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }
}
