import { Component } from '@angular/core';
import { FooterComponent } from "../../../shared/footer/footer.component";
import { HeaderComponent } from "../../../shared/header/header.component";

@Component({
  selector: 'app-home',
  imports: [FooterComponent, HeaderComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
