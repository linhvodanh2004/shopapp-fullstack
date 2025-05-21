import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterDto } from '../dtos/users/register.dto';
import { LoginDto } from '../dtos/users/login.dto';
import { environment } from '../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  private registerUrl = `${environment.apiUrlPrefix}/users/register`;
  private loginUrl = `${environment.apiUrlPrefix}/users/login`;
  private apiConfig = {
    headers: this.createHeader()
  }

  constructor(private http: HttpClient) { }
  private createHeader(): HttpHeaders {
    return new HttpHeaders({ 'Content-Type': 'application/json' , 'Accept-Language': 'en' });
  }

  register(registerDto: RegisterDto): Observable<any> {
    return this.http.post(this.registerUrl, registerDto, this.apiConfig);  
  }
  login(loginDto: LoginDto): Observable<any> {
    return this.http.post(this.loginUrl, loginDto, this.apiConfig);
  }

}
