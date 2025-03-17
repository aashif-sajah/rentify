import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BookingRequest } from '../../models/booking-request';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  apiUrl = 'http://localhost:8080/api/bookings';

  constructor(private http: HttpClient) { }


  createBooking(bookingRequest: BookingRequest, userId: number): Observable<any> {
    console.log(bookingRequest, " this getting called");
    return this.http.post<any>(this.apiUrl + "/create/" + userId, bookingRequest);
  }
  


}
