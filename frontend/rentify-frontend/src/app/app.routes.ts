import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { BusinessSetupComponent } from './pages/business-setup/business-setup.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { HomeComponent } from './pages/home/home/home.component';
import { AboutUsComponent } from './pages/about-us/about-us.component';
import { SupportComponent } from './pages/support/support.component';
import { AddProductComponent } from './pages/add-product/add-product.component';
import { ProductDetailsComponent } from './pages/product-details/product-details.component';
import { CustomerViewComponent } from './pages/customer-view/customer-view.component';
import { PaymentComponent } from './pages/payment/payment.component';

export const routes: Routes = [

  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'business-setup',
    component: BusinessSetupComponent,
    canActivate: [authGuard],
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
     canActivate: [authGuard]
  },
  {
    path: 'add-product',
    component: AddProductComponent,
     canActivate: [authGuard],
  },

  {
    path:'about-us',
    component: AboutUsComponent

  },
  {
    path:'support',
    component: SupportComponent
  },
  {
    path: 'product-details/:id', // Add route for Product Details with a dynamic ID
    component: ProductDetailsComponent
  },
  {
    path:'business/:slug',
    component:CustomerViewComponent
  },
  {
    path:'payment/:id',
    component:PaymentComponent
  },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {
    path: '**',
    redirectTo: '/home',
  }
];
