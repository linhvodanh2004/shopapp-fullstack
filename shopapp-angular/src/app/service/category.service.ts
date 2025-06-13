import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Category } from '../models/category';
import { environment } from '../environments/environment';
import { map } from 'rxjs/operators';
import { ApiResponse } from '../responses/api.response';


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
        return this.http.get<ApiResponse<Category[]>>(this.apiUrl).pipe(
            map(response => response.data)
        );
    }
    getCategoryById(id: number): Observable<Category> {
        return this.http.get<ApiResponse<Category>>(`${this.apiUrl}/${id}`).pipe(
            map(response => response.data)
        );
    }
}
