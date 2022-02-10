import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { primengConfig } from './primengConfig';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { CategoriesComponent } from './components/categories/categories.component';
import { SidebarComponent } from './template/sidebar/sidebar.component';
import { TopnavComponent } from './template/topnav/topnav.component';

@NgModule({
  declarations: [
    AppComponent,
    CategoriesComponent,
    SidebarComponent,
    TopnavComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule, 
    BrowserAnimationsModule,
    primengConfig,  
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
