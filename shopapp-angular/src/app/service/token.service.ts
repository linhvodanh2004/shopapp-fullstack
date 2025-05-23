import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class TokenService {
    private accessTokenKey = 'access_token';

    constructor() { }

    getAccessToken(): string | null {
        return localStorage.getItem(this.accessTokenKey);
    }

    setAccessToken(token: string) {
        localStorage.setItem(this.accessTokenKey, token);
    }
    removeAccessToken(): void {
        localStorage.removeItem(this.accessTokenKey);
    }
}