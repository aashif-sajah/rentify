import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BusinessResponse } from '../../models/business-response';

@Injectable({
  providedIn: 'root'
})
export class BusinessService {

  private apiUrl = 'http://localhost:8080/api/business/create';

  constructor(private http: HttpClient) {}

  createBusiness(formData: FormData): Observable<BusinessResponse> {
    return this.http.post<BusinessResponse>(this.apiUrl, formData);
  }
}
