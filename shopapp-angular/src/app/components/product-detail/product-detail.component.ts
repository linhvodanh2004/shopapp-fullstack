import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { Product } from '../../models/product';
import { ProductService } from '../../service/product.service';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../environments/environment';
import { Category } from '../../models/category';
import { CategoryService } from '../../service/category.service';
import { CartService } from '../../service/cart.service';
@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.scss'
})
export class ProductDetailComponent implements OnInit {
  quantity: number = 1;
  currentImageIndex: number = 0;
  visibleImages: string[] = [];
  startVisibleIndex: number = 0;
  endVisibleIndex: number = 4;
  isAnimating: boolean = false;
  product: Product | null = null;
  category: Category | null = null;
  loading: boolean = true;
  error: string | null = null;

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private cartService: CartService
  ) {}

  ngOnInit() {
    // For now, we'll use a fixed product ID of 
    const productId = 5;
    this.loadProduct(productId);
    this.loadProductImages(productId);
    this.loadCategory(this.product?.category_id);
    this.updateVisibleImages();
  }
  loadCategory(id: number | undefined):void {
    if (id) {
      this.categoryService.getCategoryById(id).subscribe((category) => {
        this.category = category;
      });
    }
  }

  loadProduct(id: number):void {
    this.loading = true;
    this.error = null;
    
    this.productService.getProductById(id).subscribe({
      next: (product) => {
        this.product = product;
        this.loading = false;
      },
      error: (err) => {
        this.error = err.error;
        this.loading = false;
        console.error('Error loading product:', err);
      }
    });
  }
  loadProductImages(id: number):void {
    this.productService.getImagesByProductId(id).subscribe({
      next: (images) => {
        if (this.product) {
          this.product.images = images.map(image => `${environment.apiUrlPrefix}/products/images/${image.image_url}`);
        }
      }
    });
  }

  updateVisibleImages() {
    if (this.product) {
      this.visibleImages = this.product.images.slice(this.startVisibleIndex, this.endVisibleIndex);
    }
  }

  togglePreviousImage() {
    if (!this.product || this.isAnimating || this.product.images.length <= 4) return;
    
    if (this.currentImageIndex > 0) {
      this.isAnimating = true;
      this.currentImageIndex--;
      
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
    if (!this.product || this.isAnimating || this.product.images.length <= 4) return;
    
    if (this.currentImageIndex < this.product.images.length - 1) {
      this.isAnimating = true;
      this.currentImageIndex++;
      
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
    if (!this.product || this.isAnimating) return;
    
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
    if (this.product) {
      this.cartService.addToCart(this.product.id, this.quantity);
    }
    else{
      alert('Product not found');
    }
  }

  buyNow() {
    if (this.product) {
      console.log('Buying now:', this.product.name, 'Quantity:', this.quantity);
    }
  }
}
