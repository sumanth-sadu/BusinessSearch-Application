import { NgForm } from '@angular/forms';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SearchFormService {

  private url: string = '/api/search';

  constructor(private http: HttpClient) { }

  getBusinessData(data:Object){
    // console.log('ngform data : ',data)
    let dataNew = JSON.parse(JSON.stringify(data))
    // console.log(dataNew.key)
    let querystring = "?keyword=" + dataNew.key + "&category=" + dataNew.cat + "&distance=" + dataNew.dist + "&latitude=" + dataNew.lat + "&longitude=" + dataNew.lng
    return this.http.get(this.url + querystring);
  }

  // getLocation(data:NgForm){
  //   let dataNew = JSON.parse(JSON.stringify(data))
  //   let googlekey = 'AIzaSyBLEkKp9FHS7iECybzv9AesVs-fuoFXlIE';
    
 
  //   return this.http.get('https://maps.googleapis.com/maps/api/geocode/json?address=' + dataNew.loc + '&key=' + googlekey)
  
  // }

  getLocation(data : any){
    // let dataNew = JSON.parse(JSON.stringify(data))
    let googlekey = 'AIzaSyBLEkKp9FHS7iECybzv9AesVs-fuoFXlIE';
    
 
    return this.http.get('https://maps.googleapis.com/maps/api/geocode/json?address=' + data.location + '&key=' + googlekey)
  
  }

  getKeywordData(entererData: any){
    // console.log('string to get data:',entererData)
    return this.http.get('/api/auto?text=' + entererData);
  }

  getCheckedLocation(){
    return this.http.get('https://ipinfo.io/json?token=7b827d7a0f6858')
  }

  getMoreInfoData(id:String){
    // console.log(this.http.get('api/moreinfo?id=' + id))
    return this.http.get('/api/moreinfo?id=' + id);
  }

  getReviewsData(id:String){
    return this.http.get('/api/reviews?id=' + id)
  }

}
