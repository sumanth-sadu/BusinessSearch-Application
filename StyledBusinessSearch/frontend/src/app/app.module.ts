import { NgModule, Input } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SearchFormComponent } from './search-form/search-form.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { BookingsComponent } from './bookings/bookings.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import {  } from 'bootstrap';
import { MatTabsModule } from '@angular/material/tabs';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { BusinessTableComponent } from './business-table/business-table.component';
import {MatInputModule} from '@angular/material/input';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { BusinessDetailsComponent } from './business-details/business-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PopupTempComponent } from './popup-temp/popup-temp.component';
import { GoogleMapsModule } from '@angular/google-maps'
import { MatFormFieldModule } from '@angular/material/form-field'

@NgModule({
  declarations: [
    AppComponent,
    SearchFormComponent,
    TopBarComponent,
    BookingsComponent,
    BusinessTableComponent,
    BusinessDetailsComponent,
    PopupTempComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    MatInputModule,
    MatAutocompleteModule,
    MatTabsModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatButtonModule,
    GoogleMapsModule,
    MatFormFieldModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
