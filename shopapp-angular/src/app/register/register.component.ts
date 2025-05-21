import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { RegisterDto } from '../dtos/users/register.dto';
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  phone: string;
  password: string;
  retypePassword: string;
  fullName: string;
  address: string;
  isAccepted: boolean;
  dateOfBirth: Date;
  private _dateOfBirthString: string = '';
  phonePattern: RegExp = /^[0-9]{10}$/;

  constructor(private router: Router, private userService: UserService) {
    this.phone = '';
    this.password = '';
    this.retypePassword = '';
    this.fullName = '';
    this.address = '';
    this.isAccepted = true;
    this.dateOfBirth = new Date(Date.now() - 1000 * 60 * 60 * 24 * 365 * 18);
    this._dateOfBirthString = this.dateOfBirth.toISOString().split('T')[0];
  }

  // Getter for the date string (used in template)
  get dateOfBirthString(): string {
    return this._dateOfBirthString;
  }

  // Setter for the date string (used in template)
  set dateOfBirthString(value: string) {
    this._dateOfBirthString = value;
    this.dateOfBirth = new Date(value);
    this.validateDate();
  }

  onPhoneNumberChange() {
    console.log(`Phone number: ${this.phone}`);
  }
  onRegister() {
    this.validateDate();
    this.validatePassword();
    if (this.registerForm.valid) {
      
      const body:RegisterDto = {
        "fullname": this.fullName,
        "phone_number": this.phone,
        "address": this.address,
        "password": this.password,
        "retype_password": this.retypePassword,
        "date_of_birth": this.dateOfBirth,
        "facebook_account_id": 0,
        "google_account_id": 0,
        "role_id": 1
      }
      this.userService.register(body)
        .subscribe({
          next: (response: any) => {
            if (response && (response.status === 200 || response.status === 201)) {
              this.router.navigate(['/login']);
            }
            else {
              console.log("Error");
            }
          },
          complete() {
          },
          error: (error) => {
            alert(error.error);
            console.log('Error details:', error);
          }
        })
    }
  }
  validatePassword() {
    if (this.password !== this.retypePassword) {
      this.registerForm.controls['retypePassword'].setErrors({ "mismatch": true });
    }
    else {
      this.registerForm.controls['retypePassword'].setErrors(null);
    }
  }
  validateDate() {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    if (!this.dateOfBirth) {
      this.registerForm.controls['dateOfBirth'].setErrors({ "invalidDate": true });
      return;
    }

    const birthDate = new Date(this.dateOfBirth);
    birthDate.setHours(0, 0, 0, 0);

    const ageDiff = today.getTime() - birthDate.getTime();
    const ageInYears = ageDiff / (1000 * 60 * 60 * 24 * 365);
    if (ageInYears < 0 || ageInYears > 120) {
      this.registerForm.controls['dateOfBirth'].setErrors({ "invalidDate": true });
    }
    else {
      this.registerForm.controls['dateOfBirth'].setErrors(null);
    }
  }
} 