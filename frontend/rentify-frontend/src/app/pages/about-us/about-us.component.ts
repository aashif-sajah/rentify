import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common'; // ✅ Import CommonModule

@Component({
  selector: 'app-about-us',
  standalone: true, // ✅ Mark component as standalone
  imports: [CommonModule], // ✅ Include CommonModule
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.css']
})
export class AboutUsComponent implements OnInit {
  
  stats = [
    { label: 'Total Listings', targetValue: 10000, currentValue: 0, formattedValue: '0' },
    { label: 'Happy Renters', targetValue: 13200, currentValue: 0, formattedValue: '0' },
    { label: 'Verified Owners', targetValue: 5100, currentValue: 0, formattedValue: '0' },
    { label: 'Completed Transactions', targetValue: 55500, currentValue: 0, formattedValue: '0' }
  ];

  constructor(private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.animateStats();
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

        // Format numbers
        if (stat.targetValue >= 1000000) {
          stat.formattedValue = (stat.currentValue / 1000000).toFixed(1) + 'M';
        } else if (stat.targetValue >= 1000) {
          stat.formattedValue = (stat.currentValue / 1000).toFixed(1) + 'K';
        } else {
          stat.formattedValue = Math.round(stat.currentValue).toString() + '+';
        }

        // Ensure Angular detects changes
        this.cdr.detectChanges();
      };

      updateValue();
    });
  }
}
