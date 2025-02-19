export interface ProductRequest {
  name: string;
  description: string;
  pricePerDay: number;
  availability: boolean;
  category: string;
  businessId: number;
  images: File[];
}


