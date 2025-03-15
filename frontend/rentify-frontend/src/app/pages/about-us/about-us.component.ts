import { Component, OnInit, ChangeDetectorRef, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; 

@Component({
  selector: 'app-about-us',
  standalone: true,
  imports: [CommonModule, RouterModule],
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
    { 
      name: 'John Doe', 
      message: `"Rentify made my apartment hunt stress-free!
                I found a verified owner quickly and easily.
                Highly recommended for hassle-free rentals!"`, 
      image: 'assets/About Us/user1.jpg' 
    },
    { 
      name: 'Olivia Smith', 
      message: `"Great platform with amazing support!
                The booking process was smooth and secure.
                I'll definitely use Rentify again!"`, 
      image: 'assets/About Us/user2.jpg' 
    },
    { 
      name: 'Mike Johnson', 
      message: `"Fast, reliable, and super user-friendly.
                Loved how transparent the listings were.
                Found my dream place in just two days!"`, 
      image: 'assets/About Us/user3.jpg' 
    },
    { 
      name: 'Sarah Brown', 
      message: `"Rentify helped me find tenants fast.
                The verification process gave me peace of mind.
                A game-changer for property owners!"`, 
      image: 'assets/About Us/user4.jpg' 
    },
    { 
      name: 'David Wilson', 
      message: `"Best rental service I've ever used.
                I felt safe with their secure payment system.
                Everything was easy and straightforward!`, 
      image: 'assets/About Us/user5.jpg' 
    },
    { 
      name: 'Emma Taylor', 
      message: `"Finding a home has never been this simple.
                Clear details and honest reviews helped a lot.
                Rentify is my go-to for future rentals!"`, 
      image: 'assets/About Us/user6.jpg' 
    }
  ];

  reviewPairs: any[] = [];

  // New Team Section Data
  teamMembers = [
    {
      name: 'Aashif Sajah',
      role: 'Full Stack Developer',
      image: 'assets/Team Members/master.jpeg',
      socials: [
        { link: 'https://www.instagram.com/cliff.adventurer_/', icon: 'fa-brands fa-instagram' },
        { link: 'https://github.com/aashif-sajah', icon: 'fa-brands fa-github' },
        { link: 'https://www.linkedin.com/in/aashif-sajah/', icon: 'fa-brands fa-linkedin' },
      ]
    },
    {
      name: 'Sanithu Muthukumarana',
      role: 'Backend Web Developer',
      image: 'assets/Team Members/zanithu.jpg',
      socials: [
        { link: 'https://instagram.com', icon: 'fa-brands fa-instagram' },
        { link: 'https://github.com/Zanithu', icon: 'fa-brands fa-github' },
        { link: 'https://www.linkedin.com/in/sanithu-muthukumarana', icon: 'fa-brands fa-linkedin' },
      ]
    },
    {
      name: 'Ruzaiq Fahim',
      role: 'Frontend Web Developer',
      image: 'assets/Team Members/ruzaiq.png',
      socials: [
        { link: 'https://www.instagram.com/ruzaiqfahim/', icon: 'fa-brands fa-instagram' },
        { link: 'https://github.com/ruzaiqf', icon: 'fa-brands fa-github' },
        { link: 'https://www.linkedin.com/in/ruzaiq-fahim/', icon: 'fa-brands fa-linkedin' },
      ]
    },
    {
      name: 'Kaveesha Devindi',
      role: 'Frontend Web Developer',
      image: 'assets/Team Members/kaveesha.jpeg',
      socials: [
        { link: 'https://www.instagram.com/kaveesha_ed/', icon: 'fa-brands fa-instagram' },
        { link: 'https://github.com/kaveesha-ed', icon: 'fa-brands fa-github' },
        { link: 'https://www.linkedin.com/in/kd-ed/', icon: 'fa-brands fa-linkedin' },
      ]
    },
    {
      name: 'Uveen Sureshchandra',
      role: 'Frontend Web Developer',
      image: 'assets/Team Members/Uveen.jpg',
      socials: [
        { link: 'https://instagram.com/uveensura/', icon: 'fa-brands fa-instagram' },
        { link: 'https://github.com/UveenSura', icon: 'fa-brands fa-github' },
        { link: 'https://www.linkedin.com/in/uveen-sureshchandra-375a17311/', icon: 'fa-brands fa-linkedin' },
      ]
    },
    {
      name: 'Niduk Samarakoon',
      role: 'Frontend Web Developer',
      image: 'assets/About Us/user1.jpg',
      socials: [
        { link: 'https://instagram.com', icon: 'fa-brands fa-instagram' },
        { link: 'https://github.com', icon: 'fa-brands fa-github' },
        { link: 'https://linkedin.com', icon: 'fa-brands fa-linkedin' },
      ]
    },
    
  ];

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
  scrollToContact(): void {
    const contactSection = document.getElementById('contact-section');
    if (contactSection) {
      contactSection.scrollIntoView({ behavior: 'smooth' });
    }
  }
  
}
