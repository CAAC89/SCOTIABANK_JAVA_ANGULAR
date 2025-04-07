import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HousingService {

  constructor(private http: HttpClient) { }

  getHousingByUserId(userId: string): Observable<any> {
    const url = `http://localhost:8080/housing/user/${userId}`;

    let headers = new HttpHeaders();

    if (localStorage.getItem('token')) {
      headers = headers.set('Authorization', `Bearer ${localStorage.getItem('token')}`);
    } else {
      console.warn('Token is null, not setting Authorization header');
    }

    return this.http.get(url, { headers });
  }


  getAllHousing(): Observable<any> {
    const url = `http://localhost:8080/housing`;

    let headers = new HttpHeaders();

    if (localStorage.getItem('token')) {
      headers = headers.set('Authorization', `Bearer ${localStorage.getItem('token')}`);
    } else {
      console.warn('Token is null, not setting Authorization header');
    }

    return this.http.get(url, { headers });
  }

  insertHousing(object: any): Observable<any> {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    // Solo agrega el Authorization si hay token
    if (localStorage.getItem('token')) {
      headers = headers.set('Authorization', `Bearer ${localStorage.getItem('token')}`);
    }

    return this.http.post('http://localhost:8080/housing', object, { headers });
  }
}
