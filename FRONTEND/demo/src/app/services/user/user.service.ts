import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {


  constructor(private http: HttpClient) { }

  login(credentials: { email: string; password: string }): Observable<any> {
    const rawJson = JSON.stringify(credentials); // Convierte el objeto a un string JSON

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',  // Explicitly set Content-Type
    });

    return this.http.post('http://localhost:8080/user/login', rawJson, { headers });
  }

  registerUser(object: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',  // Explicitly set Content-Type
    });

    return this.http.post('http://localhost:8080/user/register', object, { headers });
  }

  // Método para obtener datos del usuario utilizando un token
  getUserByEmail(email: string): Observable<any> {
    const url = `${'http://localhost:8080/user/email/'}${email}`;  // Construir la URL completa
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`  // Pasar el token en el encabezado Authorization
    });

    return this.http.get(url, { headers });
  }

  getUsers(token:string): Observable<Usuario[]> {
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    } else {
      console.warn('Token is null, not setting Authorization header');
    }
    return this.http.get<Usuario[]>('http://localhost:8080/user', { headers });
  }

}
