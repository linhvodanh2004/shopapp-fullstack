import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

interface Product {
  name: string;
  price: number;
  originalPrice: number;
  discount: number;
  description: string;
  images: string[];
  reviews: number;
  inStock: boolean;
  brand: string;
  category: string;
}

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.scss'
})
export class ProductDetailComponent {
  quantity: number = 1;
  selectedImage: string = '';
  
  // Mock product data - replace with actual data from your service
  product: Product = {
    name: 'Premium Wireless Headphones',
    price: 89.99,
    originalPrice: 99.99,
    discount: 10,
    description: 'Experience crystal-clear sound with our premium wireless headphones. Featuring active noise cancellation, 30-hour battery life, and comfortable over-ear design.',
    images: [
      // 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/d/i/dien-thoai-tecno-camon-40_1_.png',
      // 'https://cdn2.cellphones.com.vn/x/media/catalog/product/s/a/samsung_galaxy_s25_ultra_-_2.png',
      'https://cdn2.cellphones.com.vn/x/media/catalog/product/s/a/samsung_galaxy_s25_ultra_-_4.png',
      'https://cdn2.cellphones.com.vn/x/media/catalog/product/s/a/samsung_galaxy_s25_ultra_-_5.png',
      'https://cdn2.cellphones.com.vn/x/media/catalog/product/m/a/macbook-air-m2-2022-16gb_3_.png',
      'https://cdn2.cellphones.com.vn/x/media/catalog/product/m/a/macbook-air-m2-2022-16gb_9_.png'
    ],
    reviews: 128,
    inStock: true,
    brand: 'TechPro',
    category: 'Electronics'
  };

  constructor() {
    // Set the first image as selected by default
    this.selectedImage = this.product.images[0];
  }

  increaseQuantity() {
    if (this.quantity < 99) {
      this.quantity++;
    }
  }

  decreaseQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  addToCart() {
    // Implement add to cart functionality
    console.log('Adding to cart:', this.product.name, 'Quantity:', this.quantity);
  }

  buyNow() {
    // Implement buy now functionality
    console.log('Buying now:', this.product.name, 'Quantity:', this.quantity);
  }
}
