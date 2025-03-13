import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent 
{
  @ViewChild('servicesContainer', { static: false }) servicesContainer!: ElementRef;

  constructor(private router:Router){}

  scrollRight(): void {
    if (this.servicesContainer) {
      this.servicesContainer.nativeElement.scrollBy({ left: 300, behavior: 'smooth' });
    }
  }
  scrollLeft(): void {
    if (this.servicesContainer) {
      this.servicesContainer.nativeElement.scrollBy({ left: -300, behavior: 'smooth' });
    }
  }

  toRegister(): void {
    console.log('Clicked');
    this.router.navigate(['/register']);
  }
}

