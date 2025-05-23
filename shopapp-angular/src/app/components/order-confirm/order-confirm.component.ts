import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

interface OrderItem {
  name: string;
  price: number;
  quantity: number;
  image: string;
}

@Component({
  selector: 'app-order-confirm',
  standalone: true,
  imports: [CommonModule, HeaderComponent, FooterComponent],
  templateUrl: './order-confirm.component.html',
  styleUrls: ['./order-confirm.component.scss', './order-confirm.component.css']
})
export class OrderConfirmComponent {
  currentDate = new Date();
  estimatedDelivery = new Date(this.currentDate.getTime() + 7 * 24 * 60 * 60 * 1000); // 7 days from now

  // Mock order data - replace with actual order data from your service
  orderItems: OrderItem[] = [
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

  shippingAddress = '123 Main Street, Apt 4B, New York, NY 10001';
  shipping = 10;
  discount = 25;

  get subtotal(): number {
    return this.orderItems.reduce((total, item) => total + (item.price * item.quantity), 0);
  }

  get total(): number {
    return this.subtotal + this.shipping - this.discount;
  }

}
