import { HomeComponent } from './home/home.component';
import { ErrorComponent } from './error/error.component';
import { UpdateStructureComponent } from './update-structure/update-structure.component';
import { DeleteStructureComponent } from './delete-structure/delete-structure.component';
import { AddStructureComponent } from './add-structure/add-structure.component';
import{ROUTING} from './app-routing';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FilesGeneratorComponent } from './files-generator/files-generator.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {FormsModule} from '@angular/forms'






@NgModule({
  declarations: [
    AppComponent,
    FilesGeneratorComponent,AddStructureComponent,DeleteStructureComponent,UpdateStructureComponent,ErrorComponent,HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,ROUTING, BrowserAnimationsModule, MatSortModule,
    MatTableModule,
    MatSortModule,
    MatTableModule,MatPaginatorModule,MatIconModule,FormsModule
  
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }