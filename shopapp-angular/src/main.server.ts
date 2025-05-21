import { bootstrapApplication } from '@angular/platform-browser';
import { HomeComponent } from './app/home/home.component';
import { ProductDetailComponent } from './app/product-detail/product-detail.component';
import { OrderCheckoutComponent } from './app/order-checkout/order-checkout.component';
import { OrderConfirmComponent } from './app/order-confirm/order-confirm.component';
import { LoginComponent } from './app/login/login.component';
import { RegisterComponent } from './app/register/register.component';
import { config } from './app/app.config.server';

const bootstrap = () => bootstrapApplication
(
    //  HomeComponent
    LoginComponent
    // RegisterComponent
    // OrderConfirmComponent
    , config);

export default bootstrap;
