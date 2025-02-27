import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductResponse } from '../../models/product-response';

@Injectable({
  providedIn: 'root'
})
export class ProductServiceService {

  private apiUrl = 'http://localhost:8080/api/products';

  constructor(private http:HttpClient) { }

  addProduct(formData: FormData):Observable<ProductResponse>{
    return this.http.post<ProductResponse>(`${this.apiUrl}`, formData);
  }

  getAllProductsByBusiness(businessId: number):Observable<ProductResponse[]>{
    return this.http.get<ProductResponse[]>(`${this.apiUrl}/business/${businessId}`);
  }

  getProductById(productId: number):Observable<ProductResponse>{
    return this.http.get<ProductResponse>(`${this.apiUrl}/product/${productId}`);
  }

  deleteProduct(productId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/product/${productId}`);
  }

}
