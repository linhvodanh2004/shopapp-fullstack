import { Injectable } from '@angular/core';
import { ProductService } from './product.service';
import { LocalStorageService } from './local-storage.service';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: Map<number, number> = new Map();
  private cartCount = new BehaviorSubject<number>(0);
  cartCount$ = this.cartCount.asObservable();

  constructor(private productService: ProductService, private localStorage: LocalStorageService) {
    // get cart from local storage
    const storedCart = this.localStorage.getItem('cart');
    if (storedCart) {
      this.cart = new Map(storedCart);
      this.updateCartCount();
    }
  }

  updateCartCount(): void {
    this.cartCount.next(this.cart.size);
  }

  getCartCount(): number {
    return this.cart.size;
  }

  addToCart(productId: number, quantity: number = 1): void {
    if (this.cart.has(productId)) {
      this.cart.set(productId, this.cart.get(productId)! + quantity);
    } else {
      this.cart.set(productId, quantity);

    }
    this.saveCartToLocalStorage();
    this.updateCartCount();
  }

  getCart(): Map<number, number> {
    return this.cart;
  }

  getProductIdToStr(): string {
    return Array.from(this.cart.keys()).join(',');
  }

  private saveCartToLocalStorage(): void {
    this.localStorage.setItem('cart', Array.from(this.cart.entries()));
  }

  clearCart(): void {
    this.cart.clear();
    this.saveCartToLocalStorage();
    this.updateCartCount();
  }
}
