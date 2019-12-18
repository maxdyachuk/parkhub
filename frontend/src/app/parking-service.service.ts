import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Parking } from './parking';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ParkingServiceService {

  private parkingUrl: string;

  constructor(private http: HttpClient,private router: Router ) {
    this.parkingUrl = 'http://localhost:8080/home/cabinet/addParking';
   }

   public save(parking: Parking)  {
    return this.http.post<Parking>(this.parkingUrl, parking);
    //this.router.navigate(['login'], { queryParams: { returnUrl: this.parkingUrl }});
  }
}
