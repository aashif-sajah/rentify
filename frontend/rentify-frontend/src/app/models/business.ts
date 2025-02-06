import { User } from "./user";

export interface Business {
  id: number;
  businessName: string;
  businessType: string;
  description: string;
  address: string;
  contactEmail: string;
  phone: string;
  owner: User;
}
