import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryStateService {
  private selectedCategoryId = new BehaviorSubject<number | null>(null);
  selectedCategoryId$ = this.selectedCategoryId.asObservable();

  setSelectedCategory(categoryId: number | null) {
    this.selectedCategoryId.next(categoryId);
  }

  getSelectedCategory() {
    return this.selectedCategoryId.value;
  }
} 