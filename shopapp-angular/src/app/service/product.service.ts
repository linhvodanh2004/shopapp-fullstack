import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Product } from '../models/products';



@Injectable({
    providedIn: 'root'
})
export class ProductService {
    private readonly apiUrl = `${environment.apiUrlPrefix}/products`;
    constructor(private readonly http: HttpClient) { }
    getProducts(page: number, limit: number, keyword: string, category_id: number): Observable<Product[]> {
        const params = new HttpParams()
            .set('page', page.toString())
            .set('limit', limit.toString())
            .set('keyword', keyword)
            .set('category_id', category_id.toString());
        return this.http.get<Product[]>(this.apiUrl, { params });
    }
    getImagesByProductId(productId: number): Observable<string[]> {
        const url = `${this.apiUrl}/${productId}/images`;
        return this.http.get<string[]>(url);
    }
    getProductById(productId: number): Observable<Product> {
        const url = `${this.apiUrl}/${productId}`;
        return this.http.get<Product>(url);
    }
}

