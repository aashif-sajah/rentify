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

  stats = [
    { label: 'Total Listings', targetValue: 10000, currentValue: 0, formattedValue: '0' },
    { label: 'Happy Renters', targetValue: 13200, currentValue: 0, formattedValue: '0' },
    { label: 'Verified Owners', targetValue: 5100, currentValue: 0, formattedValue: '0' },
    { label: 'Completed Transactions', targetValue: 55500, currentValue: 0, formattedValue: '0' }
  ];

  constructor(private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {}

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

        // Format numbers correctly
        stat.formattedValue = this.formatNumber(stat.currentValue);

        // Ensure Angular detects changes
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
