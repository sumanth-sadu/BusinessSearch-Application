
import { Component, OnInit, Input, Output, EventEmitter, ComponentFactoryResolver } from '@angular/core';
import { SearchFormService } from '../search-form.service';
import {FormBuilder, NgForm, FormControl, FormGroup, Validators} from '@angular/forms'
import { GoogleMapsModule } from '@angular/google-maps'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@Component({
  selector: 'app-business-details',
  templateUrl: './business-details.component.html',
  styleUrls: ['./business-details.component.css']
})
export class BusinessDetailsComponent implements OnInit {

  @Input() business_id: any;

  @Output() sendFlag = new EventEmitter<string>();

  name : String = '';
  address : String = '';
  phone : String = '';
  status : String = '';
  category : String = '';
  price_range : String = '';
  url : String = '';
  transactions : String = '';
  photos : Array<string> = [];
  lat : number = 0;
  lng : number = 0;
  num:number = 12;
  mapOptions! : any; 
  marker! : any; 

  show : boolean = true;
  open : boolean = true;

  
  form  ={

    // fname: "",

    // lname: "",

    // pan: "",

    // mobile: "",

    email: "",

    // age: null

  }

  address_boolean = true;
  phone_boolean = true
  status_boolean =true
  category_boolean =true
  price_range_boolean =true
  url_boolean =true
  transactions_boolean= true
  
  reviews : any = [];

  bookingForm : any;

  constructor(private service : SearchFormService) {
    this.bookingForm = new FormGroup({
      email : new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      date: new FormControl('', [Validators.required]),
      time_hr: new FormControl('', [Validators.required]),
      time_min: new FormControl('', [Validators.required]),
    })
  }

  get primEmail(){
    return this.userEmails.get('primaryEmail')
    }
  
    get secondEmail(){
    return this.userEmails.get('secondaryEmail')
    }
  
    title = 'email-validation-tutorial';
    userEmails = new FormGroup({
    primaryEmail: new FormControl('',[
      Validators.required, Validators.email])
      // Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    // secondaryEmail: new FormControl('',[
    //   Validators.required]),
      // Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")])
    });

  ngOnInit(): void {
    console.log('in details')
    this.getMoreInfo()
    this.getReviews()
    var today = new Date().toISOString().split('T')[0];
    document.getElementsByName("date")[0].setAttribute('min', today);
  }

  // displayStyle = "none";
  
  // openPopup() {
  //   this.displayStyle = "block";
  // }
  // closePopup() {

  //   this.displayStyle = "none";
  // }
 
  counter(i: number) {
    return new Array(i);
}

  goToTable(){
    this.sendFlag.emit()
  }

  getMoreInfo(){
    this.service.getMoreInfoData(this.business_id).subscribe((res) => {
      let resNew = JSON.parse(JSON.stringify(res));
      this.name = resNew.name;
      for(let i=0; i<resNew.location?.display_address?.length; i++){
        this.address += resNew.location.display_address[i];
      }
      this.address = this.address? this.address : '';

      this.phone = resNew['display_phone']? resNew['display_phone'] : '';
      if(resNew.hours != null){
        if(resNew.hours[0]['is_open_now'] == true){
            this.status = 'Open Now';
            this.open = true;
        }else{
            this.status = 'Closed';
            this.open = false;
        }
      }
      
      for(let i = 0; i<resNew['categories']?.length; i++){
        if(i == resNew['categories']?.length - 1){
            this.category += `${resNew['categories'][i]['title']}`;
        }
        else{
            this.category += `${resNew['categories'][i]['title']} | `;
        }
    }

    this.category = this.category? this.category : '';

    for(let i = 0; i<resNew['transactions']?.length; i++){
      if(i == resNew['transactions']?.length - 1){
          this.transactions += ` ${resNew['transactions'][i].charAt(0).toUpperCase() + resNew['transactions'][i].slice(1)}`;
      }
      else{
          this.transactions += `${resNew['transactions'][i].charAt(0).toUpperCase() + resNew['transactions'][i].slice(1)} |`;
      }
  }

  this.transactions = this.transactions? this.transactions : '';

  this.price_range = resNew['price']? resNew['price'] : '';
  this.url = resNew['url']

  for(let i =0; i<resNew['photos']?.length;i++){
    this.photos?.push(resNew['photos'][i])
  }

  this.lat! = parseFloat(resNew['coordinates']['latitude'])

  this.lng! = parseFloat(resNew['coordinates']['longitude'])

  if(this.address === ''){
    this.address_boolean = false
  }else if(this.phone === ''){
    this.phone_boolean = false
  }else if(this.status === ''){
    this.status_boolean = false
  }else if(this.category === ''){
    this.category_boolean = false
  }else if(this.price_range === ''){
    this.price_range_boolean = false
  }else if(this.url === ''){
    this.url_boolean = false
  }else if(this.transactions === ''){
    this.transactions_boolean = false
  }

  this.mapOptions = {
    center: { lat: this.lat, lng: this.lng },
    zoom : 14
  }

  this.marker = {
    position: { lat: this.lat, lng: this.lng },
  }

  let data = JSON.parse(localStorage.getItem('reservation')!);
      for(let i=0; i < data.length; ++i){
        if(data[i].id === this.business_id){
          this.show = false
        }

      }
})
}

reservesubmit = false;

get reserveFormControls(){
  
  return this.bookingForm.controls;}

onSubmit(id: any){
  this.reservesubmit = true;

  // console.log(this.userEmails.valid)
  // if(!this.userEmails.valid)
  // {
  //  console.log('verify')
  //  this.userEmails.markAllAsTouched();
  //  return 

  // }

  if(!this.bookingForm.valid){
    console.log('reserve form is invalid')
    this.bookingForm.markAllAsTouched();
    return;
  }

  if(this.reservesubmit){
    this.reservation(id)
  }
}



reservation(id: any){
 
  let dataNew = this.bookingForm.value
  dataNew['id'] = id
  dataNew.business_name = this.name;

  this.addReservation(dataNew);
  alert('Reservation created!')

  this.show = false

}


addReservation(data : any){
  console.log('adding reserve : ',data)
  let reservations = [];
  if(localStorage.getItem('reservation')){

    reservations = JSON.parse(localStorage.getItem('reservation')!);
    reservations = [data, ...reservations];

    console.log(typeof(JSON.stringify(reservations)))
  } else{
    reservations = [data];
  }
  localStorage.setItem('reservation', JSON.stringify(reservations))

  document.getElementById('rclose')?.click()
}



  // reservation(data:NgForm, id: any){
    
  //   console.log(data)

  //   let dataNew = JSON.parse(JSON.stringify(data))
  //   dataNew['id'] = id
  //   dataNew.business_name = this.name;

  //   this.addReservation(dataNew);
  //   alert('Reservation created!')
  //   this.show = false
  // }

  // addReservation(data : any){
  //   let reservations = [];
  //   if(localStorage.getItem('reservation')){

  //     reservations = JSON.parse(localStorage.getItem('reservation')!);
  //     reservations = [data, ...reservations];

  //     console.log(typeof(JSON.stringify(reservations)))
  //   } else{
  //     reservations = [data];
  //   }
  //   localStorage.setItem('reservation', JSON.stringify(reservations))

  //   console.log('here')

  //   document.getElementById('rclose')?.click()
  // }

  cancel(b_id:any){
    console.log('in cancel')
    
    if(this.show===false){
      this.show = true
      let data = JSON.parse(localStorage.getItem('reservation')!);
      // let ind = data.indexOf(data[b_id])
      for(let i=0; i < data.length; ++i){
        if(data[i].id === b_id){
          console.log(data, data[i], data[i].id)
          data.pop(i)
          localStorage.setItem('reservation', JSON.stringify(data))
          alert('Reservation canceled!')
     
        }
      }
  }
}

  getReviews(){
    this.service.getReviewsData(this.business_id).subscribe((res) => {
      let resReview = JSON.parse(JSON.stringify(res));
      for(let i=1; i<=3; i++){
        if(resReview['reviews']?.length < 3 && resReview['reviews']?.length == i){
          this.reviews?.push(resReview['reviews'][i-1])
          console.log('here')
          break;          
        }else{
          this.reviews?.push(resReview['reviews'][i-1])
        }
       
      }

    })
  }


}
