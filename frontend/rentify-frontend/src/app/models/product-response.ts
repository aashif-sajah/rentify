export interface ProductResponse {
  id: number;
  name: string;
  description: string;
  pricePerDay: number;
  availability: boolean;
  category: string;
  imageUrls: string[];
  businessId: number;
}
