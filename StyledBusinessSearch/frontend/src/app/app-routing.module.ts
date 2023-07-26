import { SearchFormComponent } from './search-form/search-form.component';
import { BookingsComponent } from './bookings/bookings.component';
import {BusinessDetailsComponent} from './business-details/business-details.component'
// import { BusinessDetailsComponent } from './business-details/business-details.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: '', redirectTo: 'search', pathMatch: 'full' },
  {path: 'search', component: SearchFormComponent},
  {path: 'bookings', component: BookingsComponent},
  {path: 'business', component: BusinessDetailsComponent}
  // {path: 'moreinfo', component: BusinessDetailsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }


