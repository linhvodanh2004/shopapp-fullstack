import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { CartService } from '../../service/cart.service';
import { ProductService } from '../../service/product.service';
import { Product } from '../../models/product';
interface PersonalInfo {
  fullName: string;
  email: string;
  phone: string;
  address: string;
}

interface CartItem {
  product_id: number;
  quantity: number;
}

@Component({
  selector: 'app-order-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './order-checkout.component.html',
  styleUrl: './order-checkout.component.scss'
})
export class OrderCheckoutComponent implements OnInit {
  constructor(private cartService: CartService, private productService: ProductService) { }

  cart: Map<number, number> = new Map();
  cartItems: CartItem[] = [];
  products: Product[] = [];
  productIds: string = '';
  ngOnInit(): void {
    this.productIds = this.cartService.getProductIdToStr();
    this.productService.getProductByIds(this.productIds).subscribe((products) => {
      this.products = products;
    });
  }

  get subtotal(): number {
    return this.cartItems.reduce((total, item) => total + (this.products.find(p => p.id === item.product_id)?.price || 0) * item.quantity, 0);
  }

  get discountAmount(): number {
    // return (this.subtotal * this.discount) / 100;
    return 0;
  }

  get total(): number {
    return this.subtotal - this.discountAmount
    // + this.shipping;
  }

  applyCoupon() {
    // Mock coupon validation - replace with actual coupon logic
    // if (this.couponCode.toUpperCase() === 'WELCOME10') {
    //   this.appliedCoupon = this.couponCode;
    //   this.discount = 10;
    // } else {
    //   this.appliedCoupon = null;
    //   this.discount = 0;
    //   alert('Invalid coupon code');
    // }
  }

  proceedToCheckout() {
    // Validate form
    // if (!this.personalInfo.fullName || !this.personalInfo.email || !this.personalInfo.phone || !this.personalInfo.address) {
    //   alert('Please fill in all required fields');
    //   return;
    // }

    // // Mock checkout process - replace with actual payment integration
    // console.log('Processing checkout:', {
    //   personalInfo: this.personalInfo,
    //   items: this.cartItems,
    //   total: this.total
    // });
  }
} 