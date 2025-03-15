import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductResponse } from '../../models/product-response';

@Injectable({
  providedIn: 'root'
})
export class ProductServiceService {

  private apiUrl = 'http://localhost:8080/api/products';
  private selectedProduct: ProductResponse | null = null;

  constructor(private http:HttpClient) { }

  addProduct(formData: FormData):Observable<ProductResponse>{
    return this.http.post<ProductResponse>(`${this.apiUrl}`, formData);
  }

  getAllProductsByBusiness(businessId: number):Observable<ProductResponse[]>{
    console.log("this is getting called")
    return this.http.get<ProductResponse[]>(`${this.apiUrl}/business/${businessId}`);
  }

  getProductById(productId: number):Observable<ProductResponse>{
    return this.http.get<ProductResponse>(`${this.apiUrl}/product/${productId}`);
  }

  deleteProduct(productId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/product/${productId}`);
  }
  
  updateProduct(productId: number, formData: FormData): Observable<ProductResponse> {
    return this.http.put<ProductResponse>(`${this.apiUrl}/product/${productId}`, formData);
  }

  setSelectedProduct(product: ProductResponse) {
    this.selectedProduct = product;
  }

  getSelectedProduct() {
    return this.selectedProduct;
  }

  clearSelectedProduct() {
    this.selectedProduct = null;
  }

}
