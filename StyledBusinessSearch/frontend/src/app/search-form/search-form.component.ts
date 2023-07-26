import { Component, OnInit } from '@angular/core';
import { SearchFormService } from '../search-form.service';
import {FormBuilder, NgForm, FormControl, FormGroup, Validators} from '@angular/forms'
import { debounceTime } from 'rxjs/operators';
import { Observable } from 'rxjs';
import {map, startWith, switchMap, distinctUntilChanged, filter} from 'rxjs/operators';
import { BusinessTableComponent } from '../business-table/business-table.component';
declare var $: any;

export class searchModel {
  keyword! : any
  distance! : any
  category! : any
  location! : any
}

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

  flag = false;
  flag2 = false;
  id: String = '';
  disabledValue = false;
  options : any;
  responseData: any = {};
  formGroup : any;
  default : String = '';
  filteredOptions : any;
  lst : any = [];
  searchFormControl = new FormControl();

  autokey = new FormControl();
  keyword : any;

  searchForm : any;

  searchModel = new searchModel()
  
  constructor(private service : SearchFormService, private fb : FormBuilder){
    this.default = 'Default'

    this.searchForm = new FormGroup({
      keyword :  new FormControl('', Validators.required),
      distance: new FormControl(''),
      category: new FormControl('', Validators.required),
      location: new FormControl('', Validators.required),
      check_box: new FormControl('')
    })

  }

  ngOnInit(){
    this.autokey.valueChanges.subscribe(response => {
      this.getKeyword(response)
      this.filterData(response)
    })
    // this.initForm();
    // this.getNames();
    // this.getKeyword();
  }

// dummy(){
//   this.keyword.valueChanges

// }


  initForm(){
    // this.formGroup = this.fb.group({
    //   'keyword' : ['']
    // })
    // this.formGroup = new FormControl({
    //   'keyword' : ['']
    // })
    // this.formGroup.get('keyword')!.valueChanges.subscribe((response:any) => {
    //   console.log('entered data is ', response);
    //   this.getKeyword(response)
      
    //   this.filterData(response)
    // })

    this.searchFormControl.valueChanges
    .pipe(
      filter((res : any) => {
        console.log(res)
        console.log(res.keyword)
        if(res.keyword){
          console.log(res.keyword)
          return res.keyword !== null && res.keyword.length >= 1
        }else{
          return false;
        }
      }),
      distinctUntilChanged(),
      debounceTime(1000),
      switchMap((value: any) => {
          console.log(value.keyword)
          return this.service.getKeywordData(value.keyword)
          // return  this.service.getKeywordData(entererData).subscribe(response => {
          //   let responseData = JSON.parse(JSON.stringify(response));
          //   let categories = responseData['categories']
          //   let terms = responseData['terms']
          //   this.lst = [];
            
          //   for(let i=0;i<categories.length; ++i){
          //     this.lst.push(categories[i]['title'])
          //   }
          //   for(let j=0;j<terms.length; ++j){
          //     this.lst.push(terms[j]['text'])
          //   }
      
          //   console.log(this.lst)
          //   this.filteredOptions = this.lst
          // })
      })  
      ).subscribe((suggestions : any) => {
        console.log(suggestions)
        this.filteredOptions = suggestions
      });
  }

  filterData(enteredData: any){
    this.filteredOptions = this.lst?.filter((item:any) => {
      return item.toLowerCase().includes(enteredData.toLowerCase())
  })
  }

  getKeyword(entererData: any){
    this.service.getKeywordData(entererData).subscribe(response => {
      let responseData = JSON.parse(JSON.stringify(response));
      let categories = responseData['categories']
      let terms = responseData['terms']
      this.lst = [];
      
      for(let i=0;i<categories.length; ++i){
        this.lst.push(categories[i]['title'])
      }
      for(let j=0;j<terms.length; ++j){
        this.lst.push(terms[j]['text'])
      }

      console.log(this.lst)
      this.filteredOptions = this.lst
    })
  }

  counter(i: number) {
    return new Array(i);
  }



  getDataFromAPI(data: any){
    // console.log(this.searchForm.value)
    console.log('data from getDataFromAPI : ',data)

    this.service.getBusinessData(data).subscribe((response) => {
      let newResponse = JSON.parse(JSON.stringify(response))
      this.responseData = newResponse['businesses'];
      console.log('response for the above data : ',this.responseData)
      this.flag=true;
    }, (error) => {
      console.log('Error is : ',error);
    })
  }

  getLatLng(){
    console.log(this.searchForm.value)
    console.log(this.keyword)
    console.log(this.searchForm.value.category)
    console.log(this.searchForm.value.distance)
    // console.log(this.searchForm.check_box)
    // console.log('from getLatLng : ',data)
   
    // let dataNew = JSON.parse(JSON.stringify(data))
    // console.log(dataNew.key, this.searchModel.keyword, this.filteredOptions)

    if(this.searchForm.value.check_box==='' || this.searchForm.value.check_box===false || this.searchForm.value.check_box === null){
      this.service.getLocation(this.searchForm.value).subscribe((res) => {
        let resNew = JSON.parse(JSON.stringify(res));
        console.log(resNew)
        let geometry = resNew['results'][0]['geometry']
        let lat = geometry['location']['lat']
        let lng = geometry['location']['lng']
        console.log('current : ',lat)
        console.log('current : ',lng)
        // let distance = dataNew.dist ? dataNew.dist : 10;
        // let category = dataNew.cat ? dataNew.cat : this.default;
        let distance = this.searchForm.value.distance ? this.searchForm.value.distance : 10;
        if(this.searchForm.value.category===false || this.searchForm.value.category===null){
          this.searchForm.value.category='Default'
        } 
      
        // let category = this.searchForm.value.category ? this.searchForm.value.category : this.default;
        
        let data = {
          'key' : this.keyword,
          'cat' : this.searchForm.value.category,
          'lat' : lat,
          'lng' : lng,
          'dist' : distance*(1609.344)
        }
        this.getDataFromAPI(data)
      })
    }

    if(this.searchForm.value.check_box===true){

      this.service.getCheckedLocation().subscribe((res)=>{
        let resNew = JSON.parse(JSON.stringify(res));
        let lat = resNew['loc'].split(",")[0]
        let lng = resNew['loc'].split(",")[1]
        let distance = this.searchForm.value.distance ? this.searchForm.value.distance : 10;
        // let category = this.searchForm.value.category ? this.searchForm.value.category : this.default;
        if(this.searchForm.value.category===false || this.searchForm.value.category===null){
          console.log(this.default)
          this.searchForm.value.category='Default'
        } 
        let data = {
          'key' : this.keyword,
          'cat' : this.searchForm.value.category,
          'lat' : lat,
          'lng' : lng,
          'dist' : distance*(1609.344)
        } 
        this.getDataFromAPI(data)
      })
    }
  }

  // getDataFromAPI(data: any){
  //   // console.log(this.searchForm.value)
  //   console.log('data from getDataFromAPI : ',data)

  //   this.service.getBusinessData(data).subscribe((response) => {
  //     let newResponse = JSON.parse(JSON.stringify(response))
  //     this.responseData = newResponse['businesses'];
  //     console.log('response for the above data : ',this.responseData)
  //     this.flag=true;
  //   }, (error) => {
  //     console.log('Error is : ',error);
  //   })
  // }

  // getLatLng(data:NgForm){
  //   // console.log(this.searchForm.value)
  //   // console.log(this.keyword)
  //   console.log('from getLatLng : ',data)
   
  //   let dataNew = JSON.parse(JSON.stringify(data))
  //   console.log(dataNew.key, this.searchModel.keyword, this.filteredOptions)

  //   if(dataNew.check==='' || dataNew.check===false || dataNew.check === null){
  //     this.service.getLocation(data).subscribe((res) => {
  //       let resNew = JSON.parse(JSON.stringify(res));
  //       console.log(resNew)
  //       let geometry = resNew['results'][0]['geometry']
  //       let lat = geometry['location']['lat']
  //       let lng = geometry['location']['lng']
  //       console.log('current : ',lat)
  //       console.log('current : ',lng)
  //       let distance = dataNew.dist ? dataNew.dist : 10;
  //       let category = dataNew.cat ? dataNew.cat : this.default;
  //       let data = {
  //         'key' : dataNew.key,
  //         'cat' : category,
  //         'lat' : lat,
  //         'lng' : lng,
  //         'dist' : distance*(1609.344)
  //       }
  //       this.getDataFromAPI(data)
  //     })
  //   }

  //   if(dataNew.check===true){

  //     this.service.getCheckedLocation().subscribe((res)=>{
  //       let resNew = JSON.parse(JSON.stringify(res));
  //       let lat = resNew['loc'].split(",")[0]
  //       let lng = resNew['loc'].split(",")[1]
  //       let distance = dataNew.dist ? dataNew.dist : 10;
  //       let category = dataNew.cat ? dataNew.cat : this.default;
  //       let data = {
  //         'key' : dataNew.key,
  //         'cat' : category,
  //         'lat' : lat,
  //         'lng' : lng,
  //         'dist' : distance*(1609.344)
  //       } 
  //       this.getDataFromAPI(data)
  //     })
  //   }
  // }

  callBusinessDetails(id: String){
    this.flag = false;
    this.flag2 = true;
    this.id = id;
    console.log('in call business details func')
  }

  sendFlag(){
    this.flag = true;
    this.flag2 = false;
    console.log('in call send flag func')
  }

  enableDisableLocation(){
    console.log('disabled')
    console.log(this.searchForm.value.check_box)
    if(this.searchForm.value.check_box === false || this.searchForm.value.check_box === '' || this.searchForm.value.check_box === null){
      this.searchForm.get('location').reset();
      this.searchForm.get('location')?.disable()
    }else if(this.searchForm.value.check_box === true){
      this.searchForm.get('location')?.enable()
    }
    // this.searchForm.get('location')?.disable()

    // this.searchForm.get('location')?.enable()
    // this.disabledValue = this.disabledValue? false:true;
    // console.log(this.disabledValue)
  }

  clear(){
    this.flag = false;
    this.flag2 = false;
    this.disabledValue = false;
    this.searchForm.reset();
    this.searchForm.get('location')?.enable()
    // this.searchForm.value = null
    // console.log(this.searchForm.value)
  }
}





  // initForm(){
  //   this.formGroup = this.fb.group({
  //     'employee' : ['']
  //   })
  //   this.formGroup.get('employee')?.valueChanges
  //   .pipe(debounceTime(1000))
  //   .subscribe(response => {
  //     console.log('entered data is ', response);
  //     if(response && response.length){
  //       this.filterData(response);
  //     } else {
  //       this.filteredOptions = [];
  //     }
      
  //   })
  // }

  // filterData(enteredData:any){
  //   this.filteredOptions = this.options.filter(item => {
  //     return item.toLowerCase().indexOf(enteredData.toLowerCase()) > -1
  //   })
  // }

  // getNames(){
  //   this.service.getKeywordData().subscribe(response => {
  //     this.options = response;
  //   })
  // }


  // initForm(){
  //   this.formGroup = this.fb.group({
  //     'keyword' : ['']
  //   })
  //   this.formGroup.get('keyword').valueChanges.subscribe((response:any) => {
  //     console.log('entered data is ', response);
  //     this.getKeyword(response)
  //     this.filterData(response)
  //   })
  // }

  // filterData(enteredData: any){
  //   this.filteredOptions = this.options?.filter((item:any) => {
  //       return item['text'].toLowerCase().indexOf(enteredData.toLowerCase()) > -1;
  //   })
  // }

  // getKeyword(entererData: any){
  //   this.service.getKeywordData(entererData).subscribe(response => {
  //     console.log('response : ',response)
  //     console.log('auto complete words : ',JSON.parse(JSON.stringify(response)))
  //     this.options = JSON.parse(JSON.stringify(response));
  //     this.filteredOptions = JSON.parse(JSON.stringify(response));
  //     console.log(this.filteredOptions)
  //     console.log(this.filteredOptions[0]?.text)
  //   })
  // }


    // this.filteredOptions = this.options?.filter((item:any) => {
    //     return item['text'].toLowerCase().indexOf(enteredData.toLowerCase()) > -1;
    // })



      // this.options = JSON.parse(JSON.stringify(response));
      // console.log('options : ',this.options)
      // this.filteredOptions = JSON.parse(JSON.stringify(response));
      // console.log(this.filteredOptions)
      // console.log(this.filteredOptions[0]?.text)