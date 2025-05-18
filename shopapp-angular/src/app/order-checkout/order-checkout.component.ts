import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

interface PersonalInfo {
  fullName: string;
  email: string;
  phone: string;
  address: string;
}

interface CartItem {
  name: string;
  price: number;
  quantity: number;
  image: string;
}

@Component({
  selector: 'app-order-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './order-checkout.component.html',
  styleUrl: './order-checkout.component.scss'
})
export class OrderCheckoutComponent {
  personalInfo: PersonalInfo = {
    fullName: '',
    email: '',
    phone: '',
    address: ''
  };

  // Mock cart items - replace with actual cart data
  cartItems: CartItem[] = [
    {
      name: 'Premium Wireless Headphones',
      price: 89.99,
      quantity: 1,
      image: 'https://cdn2.cellphones.com.vn/x/media/catalog/product/s/a/samsung_galaxy_s25_ultra_-_4.png'
    },
    {
      name: 'Smart Watch Series 5',
      price: 199.99,
      quantity: 2,
      image: 'https://cdn2.cellphones.com.vn/x/media/catalog/product/s/a/samsung_galaxy_s25_ultra_-_5.png'
    }
  ];

  couponCode: string = '';
  appliedCoupon: string | null = null;
  discount: number = 0;
  shipping: number = 10;

  get subtotal(): number {
    return this.cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
  }

  get discountAmount(): number {
    return (this.subtotal * this.discount) / 100;
  }

  get total(): number {
    return this.subtotal - this.discountAmount + this.shipping;
  }

  applyCoupon() {
    // Mock coupon validation - replace with actual coupon logic
    if (this.couponCode.toUpperCase() === 'WELCOME10') {
      this.appliedCoupon = this.couponCode;
      this.discount = 10;
    } else {
      this.appliedCoupon = null;
      this.discount = 0;
      alert('Invalid coupon code');
    }
  }

  proceedToCheckout() {
    // Validate form
    if (!this.personalInfo.fullName || !this.personalInfo.email || !this.personalInfo.phone || !this.personalInfo.address) {
      alert('Please fill in all required fields');
      return;
    }

    // Mock checkout process - replace with actual payment integration
    console.log('Processing checkout:', {
      personalInfo: this.personalInfo,
      items: this.cartItems,
      total: this.total
    });
  }
} 