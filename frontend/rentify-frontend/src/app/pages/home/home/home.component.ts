import { Component, ElementRef, ViewChild } from '@angular/core';


@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent 
{
  @ViewChild('servicesContainer', { static: false }) servicesContainer!: ElementRef;

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
}

