import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css'],
  providers: [CategoryService]
})
export class CategoriesComponent implements OnInit {

  categories: Array<Category> | undefined;
  
  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {

    this.categoryService.getAllCategories().subscribe(response => {
      console.log(response);      
      this.categories =  response; 
    });
  }

  

}
