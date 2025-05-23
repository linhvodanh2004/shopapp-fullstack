import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';
import { LoginDto } from '../../dtos/users/login.dto';
import { LoginResponse } from '../../responses/user/login.response';
import { TokenService } from '../../service/token.service';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phone: string;
  password: string;
  rememberMe: boolean;
  phonePattern: RegExp = /^[0-9]{10}$/;

  constructor(
    private userService: UserService,
    private router: Router,
    private tokenService: TokenService) {
    this.phone = '';
    this.password = '';
    this.rememberMe = false;
  }
  onPhoneNumberChange() {
    console.log(`Phone number: ${this.phone}`);
  }
  validatePhoneNumber() {
    if (!this.phonePattern.test(this.phone)) {
      console.log("Invalid phone number");
      this.loginForm.controls['phone'].setErrors({ 'invalidPhoneNumber': true });
    }
    else {
      this.loginForm.controls['phone'].setErrors(null);
    }
  }
  validatePassword() {
    if (this.password.length < 6 || this.password.includes(' ')) {
      this.loginForm.controls['password'].setErrors({ 'invalidPassword': true });
    }
    else {
      this.loginForm.controls['password'].setErrors(null);
    }
  }
  onLogin() {
    this.validatePhoneNumber();
    this.validatePassword();
    if (this.loginForm.valid) {

      const body: LoginDto = {
        "phone_number": this.phone,
        "password": this.password,
      }
      this.userService.login(body)
        .subscribe({
          next: (response: LoginResponse) => {
            const { token } = response;
            this.tokenService.setAccessToken(token);
            // use interceptor to add token to all requests
            console.log(response);
            alert(token);
          },
          complete() {
          },
          error: (error) => {
            console.log(error.error);
            alert(error.error.message);
          }
        })
    }
    else {
      alert("Please fill in all fields");
    }
  }
} 