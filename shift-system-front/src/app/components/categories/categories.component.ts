import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/models/category';
import { CategoriesService } from 'src/app/services/categories.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  categories: Array<Category>;
  
  constructor(private categoryService: CategoriesService) { 
    this.categories = [];
  }

  ngOnInit(): void {

    this.categoryService.getAllCategories().subscribe(response => {
      console.log(response);      
      this.categories =  response; 
    });
  }

}
