import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  phone: string;
  password: string;
  retypePassword: string;
  fullName: string;
  address: string;
  isAccepted: boolean;
  dateOfBirth: Date;
  constructor() {
    this.phone = '';
    this.password = '';
    this.retypePassword = '';
    this.fullName = '';
    this.address = '';
    this.isAccepted = false;
    this.dateOfBirth = new Date(Date.now() - 1000 * 60 * 60 * 24 * 365 * 18);
  }
  onPhoneNumberChange() {
    console.log(`Phone number: ${this.phone}`);
  }
  onRegister() {
    const message = `
                    Phone: ${this.phone}
                    Password: ${this.password}
                    Retype Password: ${this.retypePassword}
                    Full Name: ${this.fullName}
                    Address: ${this.address}
                    Is Accepted: ${this.isAccepted}
                    Date of Birth: ${this.dateOfBirth}
                  `;
    alert(message);
  }
} 