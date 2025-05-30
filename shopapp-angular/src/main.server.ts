import { bootstrapApplication } from '@angular/platform-browser';
import { HomeComponent } from './app/components/home/home.component';
import { ProductDetailComponent } from './app/components/product-detail/product-detail.component';
import { OrderCheckoutComponent } from './app/components/order-checkout/order-checkout.component';
import { OrderConfirmComponent } from './app/components/order-confirm/order-confirm.component';
import { LoginComponent } from './app/components/login/login.component';
import { RegisterComponent } from './app/components/register/register.component';
import { config } from './app/app.config.server';

const bootstrap = () => bootstrapApplication
(
    // HomeComponent
    // LoginComponent
    // RegisterComponent
    // OrderConfirmComponent
    ProductDetailComponent
    , config);

export default bootstrap;
