import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../service/token.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    constructor(private tokenService: TokenService) { }

    // before sending the request to the server, add the token to the headers
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = this.tokenService.getAccessToken();
        if (token) {
            // Duplicate the request and add the token to the headers
            req = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`
                }
            });
        }
        return next.handle(req);
    }
}