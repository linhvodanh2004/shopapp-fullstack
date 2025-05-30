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
  currentImageIndex: number = 0;
  visibleImages: string[] = [];
  startVisibleIndex: number = 0;
  endVisibleIndex: number = 4;
  isAnimating: boolean = false;
  
  // Mock product data - replace with actual data from your service
  product: Product = {
    name: 'Premium Wireless Headphones',
    price: 89.99,
    originalPrice: 99.99,
    discount: 10,
    description: 'Experience crystal-clear sound with our premium wireless headphones. Featuring active noise cancellation, 30-hour battery life, and comfortable over-ear design.',
    images: [
      'https://cdn2.cellphones.com.vn/x/media/catalog/product/s/a/samsung_galaxy_s25_ultra_-_4.png',
      'https://cdn2.cellphones.com.vn/x/media/catalog/product/s/a/samsung_galaxy_s25_ultra_-_5.png',
      'https://cdn2.cellphones.com.vn/x/media/catalog/product/m/a/macbook-air-m2-2022-16gb_3_.png',
      'https://cdn2.cellphones.com.vn/x/media/catalog/product/m/a/macbook-air-m2-2022-16gb_9_.png',
      'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/d/i/dien-thoai-tecno-camon-40-pro_4_.png',
      'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/d/i/dien-thoai-tecno-camon-40-pro_8_.png'
    ],
    reviews: 128,
    inStock: true,
    brand: 'TechPro',
    category: 'Electronics'
  };

  constructor() {
    this.updateVisibleImages();
  }

  updateVisibleImages() {
    this.visibleImages = this.product.images.slice(this.startVisibleIndex, this.endVisibleIndex);
  }

  togglePreviousImage() {
    if (this.isAnimating || this.product.images.length <= 4) return;
    
    if (this.currentImageIndex > 0) {
      this.isAnimating = true;
      this.currentImageIndex--;
      
      // Update gallery if current image is at the start of visible images
      if (this.currentImageIndex < this.startVisibleIndex) {
        this.startVisibleIndex = Math.max(0, this.currentImageIndex);
        this.endVisibleIndex = Math.min(this.product.images.length, this.startVisibleIndex + 4);
        this.updateVisibleImages();
      }
      
      setTimeout(() => {
        this.isAnimating = false;
      }, 300);
    }
  }

  toggleNextImage() {
    if (this.isAnimating || this.product.images.length <= 4) return;
    
    if (this.currentImageIndex < this.product.images.length - 1) {
      this.isAnimating = true;
      this.currentImageIndex++;
      
      // Update gallery if current image is at the end of visible images
      if (this.currentImageIndex >= this.endVisibleIndex) {
        this.startVisibleIndex = Math.min(
          this.product.images.length - 4,
          this.currentImageIndex - 3
        );
        this.endVisibleIndex = this.startVisibleIndex + 4;
        this.updateVisibleImages();
      }
      
      setTimeout(() => {
        this.isAnimating = false;
      }, 300);
    }
  }

  selectImage(index: number) {
    if (this.isAnimating) return;
    
    this.isAnimating = true;
    this.currentImageIndex = this.startVisibleIndex + index;
    
    setTimeout(() => {
      this.isAnimating = false;
    }, 300);
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
    console.log('Adding to cart:', this.product.name, 'Quantity:', this.quantity);
  }

  buyNow() {
    console.log('Buying now:', this.product.name, 'Quantity:', this.quantity);
  }
}
