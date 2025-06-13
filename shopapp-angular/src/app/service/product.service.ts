import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Product } from '../models/product';
import { ApiResponse } from '../responses/api.response';
import { ProductImage } from '../models/product.image';
@Injectable({
    providedIn: 'root'
})
export class ProductService {
    private readonly apiUrl = `${environment.apiUrlPrefix}/products`;
    private readonly apiUrlProductIds = `${environment.apiUrlPrefix}/products/by-ids`;
    constructor(private readonly http: HttpClient) { }
    getProducts(page: number, limit: number, keyword: string, category_id: number): Observable<Product[]> {
        const params = new HttpParams()
            .set('page', page.toString())
            .set('limit', limit.toString())
            .set('keyword', keyword)
            .set('category_id', category_id.toString());
        return this.http.get<ApiResponse<Product[]>>(this.apiUrl, { params }).pipe(
            map(response => response.data)
        );
    }
    getImagesByProductId(productId: number): Observable<ProductImage[]> {
        const url = `${this.apiUrl}/${productId}/images`;
        return this.http.get<ApiResponse<ProductImage[]>>(url).pipe(
            map(response => response.data)
        );
    }
    getProductById(id: number): Observable<Product> {
        return this.http.get<ApiResponse<Product>>(`${this.apiUrl}/${id}`).pipe(
            map(response => response.data)
        );
    }   
    getProductByIds(ids: string): Observable<Product[]> {
        const params = new HttpParams()
            .set('ids', ids);
        return this.http.get<ApiResponse<Product[]>>(this.apiUrlProductIds, { params }).pipe(
            map(response => response.data)
        );
    }
}

