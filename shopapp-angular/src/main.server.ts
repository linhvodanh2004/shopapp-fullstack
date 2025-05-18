import { bootstrapApplication } from '@angular/platform-browser';
import { HomeComponent } from './app/home/home.component';
import { ProductDetailComponent } from './app/product-detail/product-detail.component';
import { OrderCheckoutComponent } from './app/order-checkout/order-checkout.component';
import { OrderConfirmComponent } from './app/order-confirm/order-confirm.component';
import { config } from './app/app.config.server';

const bootstrap = () => bootstrapApplication
(
    //  HomeComponent
    OrderConfirmComponent
    , config);

export default bootstrap;
