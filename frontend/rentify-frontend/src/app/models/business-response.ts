import { StoreThemeResponse } from "./store-theme-response";

export interface BusinessResponse {
  id: number;
  businessName: string;
  businessType: string;
  description: string;
  contactEmail: string;
  phone: string;
  storeSlug: string;
  storeTheme: StoreThemeResponse;  // StoreThemeResponse instead of StoreTheme
  isProductAvailable: boolean;
}
