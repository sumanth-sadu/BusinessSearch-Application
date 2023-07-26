import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-business-table',
  templateUrl: './business-table.component.html',
  styleUrls: ['./business-table.component.css']
})
export class BusinessTableComponent implements OnInit {

  @Output() callBusinessDetails: EventEmitter<any> = new EventEmitter();

  @Input() business_list: any;
  constructor() { }

  ngOnInit(): void {
    console.warn(this.business_list)
  }

  infoCard(id: String){
    this.callBusinessDetails.emit(id)
  }

}
