import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http'
import { Category } from '../models/category'

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {

  apiUrl: string = 'http://localhost:8080/api/categories';
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private httpClient: HttpClient) { }

  getAllCategories(): Observable<Array<Category>>{
    console.log(this.headers);
    return this.httpClient.get<Array<Category>>(this.apiUrl, { headers: this.headers });
  }

  getCategoryById(id: String): Observable<Category>{
    return this.httpClient.get<Category>(`${this.apiUrl}/${id}`, { headers: this.headers });
  }

  createCategory(category: Category): Observable<Category>{
    return this.httpClient.post<Category>(this.apiUrl, category, { headers: this.headers });
  }

  editCategory(id: String, category: Category): Observable<Category>{
    return this.httpClient.put<Category>(`${this.apiUrl}/${id}`, category, { headers: this.headers });
  }

  deleteCategory(id: string): Observable<void>{
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`, { headers: this.headers }).pipe(
      catchError(this.error)
    );
  }

   // Handle Errors 
   error(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }
}
