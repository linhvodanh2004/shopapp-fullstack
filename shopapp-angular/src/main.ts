import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { HomeComponent } from './app/home/home.component';
import { ProductDetailComponent } from './app/product-detail/product-detail.component';
import { OrderCheckoutComponent } from './app/order-checkout/order-checkout.component';
import { OrderConfirmComponent } from './app/order-confirm/order-confirm.component';
bootstrapApplication(
  // HomeComponent
  OrderConfirmComponent
  , appConfig)
  .catch((err) => console.error(err));
