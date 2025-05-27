import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { Product } from '../../models/products';
import { ProductService } from '../../service/product.service';
import { CategoryStateService } from '../../service/category-state.service';
import { environment } from '../../environments/environment';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit, OnDestroy {
  products: Product[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 12;
  totalPages: number = 0;
  visiblePages: number[] = [];
  isLoading: boolean = false;
  searchQuery: string = '';
  category_id: number = 0;
  private categorySubscription: Subscription = new Subscription();

  constructor(
    private productService: ProductService,
    private categoryStateService: CategoryStateService
  ) {}

  ngOnInit(): void {
    this.loadProducts();
    this.categorySubscription = this.categoryStateService.selectedCategoryId$.subscribe(categoryId => {
      this.category_id = categoryId || 0;
      this.currentPage = 0;
      this.loadProducts();
    });
  }

  ngOnDestroy(): void {
    if (this.categorySubscription) {
      this.categorySubscription.unsubscribe();
    }
  }

  loadProducts(): void {
    this.getProducts(this.currentPage, this.itemsPerPage, this.searchQuery, this.category_id);
  }

  getProducts(page: number, limit: number, keyword: string, category_id: number) {
    this.isLoading = true;
    this.productService.getProducts(page, limit, keyword, category_id).subscribe({
      next: (response: any) => {
        response.products.forEach((product: Product) => {
          product.url = `${environment.apiUrlPrefix}/products/images/${product.thumbnail}`;
        });
        this.products = response.products;
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error fetching products: ' + error);
        this.isLoading = false;
      }
    });
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const visiblePages = [];
    const maxVisiblePages = 5;
    let startPage: number;
    let endPage: number;

    if (totalPages <= maxVisiblePages) {
      startPage = 1;
      endPage = totalPages;
    } else {
      const middlePage = Math.floor(maxVisiblePages / 2);
      
      if (currentPage <= middlePage + 1) {
        startPage = 1;
        endPage = maxVisiblePages;
      } else if (currentPage >= totalPages - middlePage) {
        startPage = totalPages - maxVisiblePages + 1;
        endPage = totalPages;
      } else {
        startPage = currentPage - middlePage;
        endPage = currentPage + middlePage;
      }
    }

    for (let i = startPage; i <= endPage; i++) {
      visiblePages.push(i);
    }
    return visiblePages;
  }

  onPageChange(page: number) {
    this.currentPage = page;
    this.loadProducts();
  }

  onSearch() {
    this.currentPage = 0;
    this.loadProducts();
  }

  onQuickView(product: Product) {
    // TODO: Implement quick view functionality
  }

  onAddToCart(product: Product) {
    // TODO: Implement add to cart functionality
  }

  onBuyNow(product: Product) {
    // TODO: Implement buy now functionality
    console.log('Buying now:', product);
  }
}
