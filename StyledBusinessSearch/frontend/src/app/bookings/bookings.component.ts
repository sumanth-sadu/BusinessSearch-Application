import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css']
})
export class BookingsComponent implements OnInit {

  name : String = '';
  date : String = '';
  time_hr : string = '';
  time_min : String = '';
  listOfReservations : any;
  bookings_list : boolean = false;

  constructor() { }

  ngOnInit(): void {
    this.getBookings();
  }

  getBookings(){
    this.listOfReservations = JSON.parse(localStorage.getItem('reservation')!)
    if(this.listOfReservations != null && this.listOfReservations.length > 0){
      this.bookings_list = true;
    }
 
  }

  removeReservation(booking:any, i:number){
 
    let data = JSON.parse(localStorage.getItem('reservation')!);
  
    var table = document.getElementById('table-body');
 

    for(var i = 0; i < data.length; i++) {
      if(data[i].id == booking.id) {
          console.log(data.splice(i, 1));
          break;
      }
  }
    console.log(data)
    localStorage.setItem('reservation', JSON.stringify(data))
    this.listOfReservations = JSON.parse(localStorage.getItem('reservation')!)

    if(this.listOfReservations != null && this.listOfReservations.length > 0){
      this.bookings_list = true;
    }else{
      this.bookings_list = false;
    }
    table?.children[i].remove()
    alert('Reservation cancelled!')
  

    for(var i = 0; i < table!.children.length; i++)
    {
      table!.children[i].children[i].innerHTML = `${i}+1`;
        
    }
  }

  // window.addEventListener('storage', () => {
  //   // When local storage changes, dump the list to
  //   // the console.
  //   console.log(JSON.parse(window.localStorage.getItem('reservation')));
  //   return 0;
  // });



}
