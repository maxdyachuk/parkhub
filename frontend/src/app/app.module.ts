
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations'
import {FormsModule} from '@angular/forms';

import {MaterialModule} from './material.module';
import {AppComponent} from './app.component';
import {SingupComponent} from "./singup/singup.component";
import {AppRoutingModule} from "./app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {MatInputModule} from "@angular/material/input";
import {MatCardModule} from "@angular/material/card";

import { MatPaginatorModule, MatProgressSpinnerModule,
  MatSortModule, MatTableModule } from "@angular/material";

import { PageComponent } from './homePage/homePage.component';
import { ParkingListComponent } from './parking-list/parking-list.component';
import { ParkingService} from "./service/http-client.service";


@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    MaterialModule,
    AppRoutingModule,
    HttpClientModule,
    MatInputModule,
    MatCardModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule
  ],
  declarations: [
    AppComponent,
    SingupComponent,
    PageComponent,
    ParkingListComponent
  ],
  providers: [ParkingService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

