import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { HomeComponent } from './app/components/home/home.component';
import { ProductDetailComponent } from './app/components/product-detail/product-detail.component';
import { OrderCheckoutComponent } from './app/components/order-checkout/order-checkout.component';
import { OrderConfirmComponent } from './app/components/order-confirm/order-confirm.component';
import { LoginComponent } from './app/components/login/login.component';
import { RegisterComponent } from './app/components/register/register.component';

bootstrapApplication(
  // HomeComponent
  // LoginComponent
  // RegisterComponent
  ProductDetailComponent
  , appConfig)
  .catch((err) => console.error(err));
