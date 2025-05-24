import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { Product } from '../../models/products';
import { ProductService } from '../../service/product.service';
import { environment } from '../../environments/environment';
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, HeaderComponent, FooterComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  ngOnInit(): void {
    this.getProducts(this.currentPage, this.itemsPerPage);

  }
  products: Product[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 10;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = [];
  constructor(private productService: ProductService) { }
  getProducts(page: number, limit: number) {
    this.productService.getProducts(page, limit).subscribe({
      next: (response: any) => {
        response.products.forEach((product: Product) => {
          product.url = `${environment.apiUrlPrefix}/products/images/${product.thumbnail}`;
        });
        this.products = response.products;
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {
        // this.pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
      },
      error: (error: any) => {
        console.error('Error fetching products: ' + error);
      },
    })
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
    this.getProducts(page, this.itemsPerPage);
  }
}
