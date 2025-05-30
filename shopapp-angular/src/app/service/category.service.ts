import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../models/category';
import { environment } from '../environments/environment';


@Injectable({
    providedIn: 'root'
})
export class CategoryService {
    private readonly apiUrl = `${environment.apiUrlPrefix}/categories`;
    private selectedCategoryId = new BehaviorSubject<number | null>(null);
    selectedCategoryId$ = this.selectedCategoryId.asObservable();
  
    setSelectedCategory(categoryId: number | null) {
      this.selectedCategoryId.next(categoryId);
    }
  
    getSelectedCategory() {
      return this.selectedCategoryId.value;
    }

    constructor(private http: HttpClient) {}
    getCategories(): Observable<Category[]> {
        return this.http.get<Category[]>(this.apiUrl);
    }
}
