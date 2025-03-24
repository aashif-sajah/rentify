export interface BookingRequest
{
  productId: number;
  daysBooked: number;
  address: string;
  booked: boolean;
}