import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { HomeComponent } from './app/home/home.component';
import { ProductDetailComponent } from './app/product-detail/product-detail.component';
import { OrderCheckoutComponent } from './app/order-checkout/order-checkout.component';
import { OrderConfirmComponent } from './app/order-confirm/order-confirm.component';
import { LoginComponent } from './app/login/login.component';
import { RegisterComponent } from './app/register/register.component';

bootstrapApplication(
  // HomeComponent
  LoginComponent
  // RegisterComponent
  , appConfig)
  .catch((err) => console.error(err));
