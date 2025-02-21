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

  createBusiness(formData: FormData): Observable<BusinessResponse>
  {
    return this.http.post<BusinessResponse>(this.apiUrl, formData);
  }


  setBusiness(business: BusinessResponse) {
    localStorage.setItem('businessData', JSON.stringify(business));
  }

  getBusiness(): BusinessResponse {
    const business = localStorage.getItem('businessData');
    return business ? JSON.parse(business) : null;
  }

  getBusinessId(): number {
    const business = this.getBusiness();
    return business ? business.id : 0;
  }

}
