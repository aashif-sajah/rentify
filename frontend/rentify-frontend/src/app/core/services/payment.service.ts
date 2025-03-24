import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private stripe = Stripe('your_stripe_public_key');

  constructor(private http: HttpClient) {}

  createPaymentIntent(bookingId: number) {
    return this.http.post<string>(`/api/payments/create-intent/${bookingId}`, {});
  }

  confirmPayment(paymentIntentId: string, bookingId: number) {
    return this.http.post(`/api/payments/confirm/${bookingId}`, { paymentIntentId });
  }
}


