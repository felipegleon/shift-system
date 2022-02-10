import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  items: MenuItem[];

  constructor() {
    this.items = [
      {
        label: 'Categories',
        icon: 'fa fa-university',
        routerLink: ['/categories'],
    }
    ];
   }

  ngOnInit(): void {
  }

}
