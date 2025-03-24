import { Component, OnInit } from '@angular/core';
import { RegisterService } from '../../core/services/register.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../models/user';
import { FormsModule } from '@angular/forms';
import { BookingService } from '../../core/services/booking.service';
import { ProductServiceService } from '../../core/services/product-service.service';
import { BookingRequest } from '../../models/booking-request';
;

@Component({
  selector: 'app-payment',
  imports: [FormsModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css',
})
export class PaymentComponent implements OnInit {
  user: User = {
    userFirstName: 'Test',
    userLastName: 'User',
    userEmail: '',
    username: '',
    userPassword: 'abcd1234',
    roles: [{ role: 'Customer', roleDescription: 'Customer' }],
  };

  constructor(
    private registerService: RegisterService,
    private router: Router,
    private route: ActivatedRoute,
    private bookingService: BookingService,
    private productService: ProductServiceService
  ) {}

  bookingRequest: BookingRequest = {
    productId: 0,
    daysBooked: 0,
    address: '',
    booked: false,
  };
  
  ngOnInit(): void{
    
  }


  onSubmit() {
    console.log(this.user);
    const productId = this.route.snapshot.paramMap.get('id');
    this.bookingRequest.productId = Number(productId);
    this.registerService.register(this.user).subscribe({
      next: (response) => {
        console.log(response, ' Successfully created user');
        this.bookingService.createBooking(this.bookingRequest, response.userId ).subscribe({
          next: (response) => {
            console.log(response, ' Successfully created booking');
          },
          error: (error) => {
            console.error('There was an error!', error);
            this.reSetForm();
          },
        });
      },
      error: (error) => {
        console.error('There was an error!', error);
        this.reSetForm();
      }
    });
  }

  reSetForm() {
    this.user = {
      userFirstName: 'Test',
      userLastName: 'User',
      userEmail: '',
      username: '',
      userPassword: 'abcd1234',
      roles: [{ role: 'Customer', roleDescription: 'Customer' }],
    };
  }
}
