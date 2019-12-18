import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http'
import { Observable } from 'rxjs'
import { Admin } from '../Classes/admin'

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  getUserById(id: number): Observable<Admin>{
    return this.http.get<Admin>(`http://localhost:8080/admin/${id}`);
  }

  updateRole(admin: Admin) {
    this.http.post("http://localhost:8080/admin/1", admin).subscribe(res => console.log("ok"));
  }
}