import { Component, OnInit, ChangeDetectorRef, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-about-us',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.css']
})
export class AboutUsComponent implements OnInit, AfterViewInit {
  @ViewChild('statsSection') statsSection!: ElementRef;
  private observer!: IntersectionObserver;
  private hasAnimated = false;
  currentIndex = 0;

  stats = [
    { label: 'Total Listings', targetValue: 10000, currentValue: 0, formattedValue: '0' },
    { label: 'Happy Renters', targetValue: 13200, currentValue: 0, formattedValue: '0' },
    { label: 'Verified Owners', targetValue: 5100, currentValue: 0, formattedValue: '0' },
    { label: 'Completed Transactions', targetValue: 55500, currentValue: 0, formattedValue: '0' }
  ];

  reviews = [
    { name: 'John Doe', message: 'Excellent service!', image: 'assets/user1.jpg' },
    { name: 'Jane Smith', message: 'Very professional and friendly!', image: 'assets/user2.jpg' },
    { name: 'Mike Johnson', message: 'Highly recommended!', image: 'assets/user3.jpg' },
    { name: 'Sarah Brown', message: 'Fantastic experience!', image: 'assets/user4.jpg' },
    { name: 'David Wilson', message: 'Top-notch service!', image: 'assets/user5.jpg' },
    { name: 'Emma Taylor', message: 'Very reliable!', image: 'assets/user6.jpg' }
  ];

  reviewPairs: any[] = [];

  constructor(private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.splitReviewsIntoPairs();
    setInterval(() => this.nextSlide(), 4000);
  }

  ngAfterViewInit(): void {
    this.observer = new IntersectionObserver(entries => {
      entries.forEach(entry => {
        if (entry.isIntersecting && !this.hasAnimated) {
          this.animateStats();
          this.hasAnimated = true;
          this.observer.disconnect();
        }
      });
    });

    if (this.statsSection) {
      this.observer.observe(this.statsSection.nativeElement);
    }
  }

  splitReviewsIntoPairs(): void {
    this.reviewPairs = [];
    for (let i = 0; i < this.reviews.length; i += 2) {
      this.reviewPairs.push([this.reviews[i], this.reviews[i + 1]]);
    }
  }

  nextSlide(): void {
    this.currentIndex = (this.currentIndex + 1) % this.reviewPairs.length;
    this.cdr.detectChanges();
  }

  goToSlide(index: number): void {
    this.currentIndex = index;
    this.cdr.detectChanges();
  }

  animateStats(): void {
    this.stats.forEach(stat => {
      let duration = 2000; // Animation duration (in ms)
      let stepTime = 20; // Time per step (in ms)
      let totalSteps = duration / stepTime;
      let increment = stat.targetValue / totalSteps;

      const updateValue = () => {
        stat.currentValue += increment;
        if (stat.currentValue >= stat.targetValue) {
          stat.currentValue = stat.targetValue;
        } else {
          setTimeout(updateValue, stepTime);
        }

        stat.formattedValue = this.formatNumber(stat.currentValue);
        this.cdr.detectChanges();
      };

      updateValue();
    });
  }

  formatNumber(value: number): string {
    if (value >= 1000000) {
      return (value / 1000000).toFixed(1) + 'M+';
    } else if (value >= 1000) {
      return (value / 1000).toFixed(1) + 'K+';
    } else {
      return Math.round(value).toString() + '+';
    }
  }
}
