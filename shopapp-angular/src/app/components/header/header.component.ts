import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  isDarkMode = false;

  toggleMode() {
    this.isDarkMode = !this.isDarkMode;
  }

  getMode() {
    return this.isDarkMode ? 'dark' : 'light';
  }

  getModeIcon() {
    return this.isDarkMode ? 'sun' : 'moon';
  }

  getModeText() {
    return this.isDarkMode ? 'Dark' : 'Light';
  }
}
