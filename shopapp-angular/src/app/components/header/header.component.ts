import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../service/category.service';
import { Category } from '../../models/category';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartService } from '../../service/cart.service';
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  isDarkMode = false;
  categories: Category[] = [];
  cartCount: number = 0;

  constructor(
    private readonly categoryService: CategoryService,
    private readonly cartService: CartService
  ) { }

  ngOnInit(): void {
    this.loadCategories();
    this.loadCartCount();
  }
  loadCartCount(): void {
    this.cartService.cartCount$.subscribe((count) => {
      this.cartCount = count;
    });
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (error) => {
        console.error('Error loading categories:', error);
      }
    });
  }

  filterProducts(categoryId: number) {
    this.categoryService.setSelectedCategory(categoryId);
  }

  toggleMode() {
    this.isDarkMode = !this.isDarkMode;
  }

  getMode() {
    return this.isDarkMode ? 'dark' : 'light';
  }

  getModeIcon() {
    return this.isDarkMode ? 'sun' : 'moon';
  }

  getModeText() {
    return this.isDarkMode ? 'Dark' : 'Light';
  }
}
