import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Category } from 'src/app/models/category';
import { CategoriesService } from 'src/app/services/categories.service';
import { Message, MessageService } from 'primeng/api';
import {ConfirmationService} from 'primeng/api';


@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css'],
  providers: [CategoriesService, MessageService, ConfirmationService]
})
export class CategoriesComponent implements OnInit {

  categories: Array<Category>;
  displayForm: boolean;
  editing: boolean;
  formGroup = new FormGroup({
    id: new FormControl(''),
    name: new FormControl('',[Validators.required, Validators.minLength(3)]),
    weight: new FormControl('', [Validators.required, Validators.min(1)])
  });
  msgs: Message[];
  
  constructor(
    private categoryService: CategoriesService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService) { 
    this.categories = [];
    this.displayForm =  false;
    this.editing = false;
    this.msgs = [];
  }

  ngOnInit(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (categories: Category[]) => this.categories = categories,
      error: (error: any) => this.messageService.add({severity:'error', summary:'Error', detail: error.error})
    });
  }


  onSubmit(){
    if(this.formGroup.valid){
      if(!this.editing){
        this.onSave();
      }else{
        this.onEdit();
      }
    }else{
      this.msgs.push({severity:'error', detail: 'Incorrect fields' });
    }
  }


  onSave(){            
    this.categoryService.createCategory(this.formGroup.value).subscribe({
      next: (category: Category) =>{
        this.categories.push(category);
        this.messageService.add({severity:'success', summary: 'Success', detail: 'Category saved'});
        this.cleanForm();
      },
      error: (error: any) =>{
        error.error.forEach((element: string)  => {
          this.msgs.push({severity:'error', detail: element });
        });                    
      }
    });
  }

  onEdit(){
    this.categoryService.editCategory(this.formGroup.get('id')?.value, this.formGroup.value).subscribe({
      next: (category: Category) => {
        this.categories = this.categories.filter(cat => cat.id != category.id);
        this.categories.push(category);
        this.messageService.add({severity:'success', summary: 'Success', detail: 'Category saved'});
        this.cleanForm();
      },
      error: (error: any) =>{
        error.error.forEach((element: string)  => {
          this.msgs.push({severity:'error', detail: element });
        });                    
      }
    });
  }

  onDelete(id: string){     
    this.confirmationService.confirm({
      message: 'Do you want to delete this record?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.categoryService.deleteCategory(id).subscribe({
          next: () => {
            this.categories = this.categories.filter(cat => cat.id != id);
            this.messageService.add({severity:'info', summary:'Confirmed', detail:'Record deleted'});
          },
          error: (error: any) => this.messageService.add({severity:'error', summary:'Error', detail: error.error})
        });  
      },
      reject: ( ) => this.messageService.add({severity:'warn', summary:'Cancelled', detail:'You have cancelled'})
    });
  }

  selectEdit(category: Category){        
    this.messageService.add({severity:'info', summary:'Confirmed', detail:'Editing record'});
    this.formGroup.get('id')?.setValue(category.id);
    this.formGroup.get('name')?.setValue(category.name);
    this.formGroup.get('weight')?.setValue(category.weight);
    this.editing = true;
    this.displayForm = true;
  }

  onCancelForm(){
    this.messageService.add({severity:'warn', summary:'Cancelled', detail:'You have cancelled'});
    this.cleanForm();
  }

  cleanForm(){
    this.formGroup.reset();
    this.displayForm = false;
    this.editing = false;
  }

}
