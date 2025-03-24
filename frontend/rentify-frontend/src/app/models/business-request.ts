import { StoreTheme } from "./store-theme";

export interface BusinessRequest {
  businessName: string;
  businessType: string;
  description: string;
  address: string;
  contactEmail: string;
  phone: string;
  storeTheme: StoreTheme;
}
