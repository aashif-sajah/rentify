import { Component, ElementRef, AfterViewInit, Renderer2 } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportComponent implements AfterViewInit {

  constructor(private el: ElementRef, private renderer: Renderer2, private route: ActivatedRoute) {}

  ngAfterViewInit(): void {
    // Automatically expand FAQ items on hover
    const details = this.el.nativeElement.querySelectorAll("details");

    details.forEach((detail: HTMLElement) => {
      this.renderer.listen(detail, 'mouseenter', () => {
        detail.setAttribute("open", "true");
      });

      this.renderer.listen(detail, 'mouseleave', () => {
        detail.removeAttribute("open");
      });
    });

    // Check if the URL contains a fragment and scroll to FAQ if present
    this.route.fragment.subscribe(fragment => {
      if (fragment === 'faq-section') {
        this.scrollToFAQ();
      }
    });
  }

  // Function to smoothly scroll to the FAQ section
  scrollToFAQ(): void {
    const faqSection = this.el.nativeElement.querySelector("#faq-section");
    if (faqSection) {
      faqSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }
}
