import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  phone: string = '';
  password: string = '';
  rememberMe: boolean = false;

  onSubmit() {
    // Handle login logic here
    console.log('Login attempt:', {
      phone: this.phone,
      password: this.password,
      rememberMe: this.rememberMe
    });
  }
} 