import {Component, Inject} from '@angular/core';
import {Payment} from '../interfaces/payment';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

export interface DialogData {
  payment: Payment;
}

@Component({
  selector: 'app-moneris',
  templateUrl: './moneris.component.html',
  styleUrls: ['./moneris.component.scss']
})
export class MonerisComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogData) {}
}

