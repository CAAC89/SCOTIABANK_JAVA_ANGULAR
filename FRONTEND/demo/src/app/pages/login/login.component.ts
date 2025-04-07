import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user/user.service';
import { HousingService } from '../../services/housing/housing.service';


@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;

  submitted = false;

  serverError: string | null = null;

  serverOk: string | null = null;

  constructor(private fb: FormBuilder, private userService: UserService, private housingService: HousingService, private router: Router) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: '12345'
    });
  }

  get f() {
    return this.loginForm.controls;
  }

  isNullOrEmpty(obj: any): boolean {
    if (obj === null || obj === undefined) {
      return true; // El objeto es nulo o indefinido
    }

    // Si es una cadena, verifica si está vacía
    if (typeof obj === 'string') {
      return obj.trim().length === 0; // Verifica si la cadena está vacía o solo contiene espacios
    }

    // Si es un arreglo o lista, verifica si está vacío
    if (Array.isArray(obj)) {
      return obj.length === 0;
    }

    // Si es un objeto, verifica si tiene propiedades
    if (typeof obj === 'object') {
      return Object.keys(obj).length === 0;
    }

    return false; // Si no es ninguno de los casos anteriores, el objeto no es nulo ni vacío
  }

  okMessage() {
    this.serverOk = 'Logueo Exitoso. Espere estamos redigiendolo ........';
    setTimeout(() => {
      this.serverOk = null;
    }, 3000);
  }

  errorMessage(message: any) {
    this.serverError = message;

    // Eliminar el mensaje de error después de 3 segundos
    setTimeout(() => {
      this.serverError = null;
    }, 3000);
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const formData = this.loginForm.value;
      // Aquí puedes agregar la lógica para enviar los datos o autenticar
      this.userService.login(formData).subscribe({
        next: (response) => {
          console.log(response.token);
          localStorage.setItem('token', response.token); // Guardar el token

          // redirige o realiza otra acción
          this.okMessage();
          //Verificar si hay viviendas asociadas al usuario
          //Necesitamos sacar la info de usuario por el email
          // Obtener solo el valor del email
          this.userService.getUserByEmail(formData.email).subscribe({
            next: (response) => {
              localStorage.setItem('userId', response.id);
              console.log(response)
              this.housingService.getHousingByUserId(response.id).subscribe({
                next: (response) => {
                  let isNullOrEmpty = this.isNullOrEmpty(response);
                  if (isNullOrEmpty === true) {
                    this.router.navigate(['/housing']);
                  } else {
                    this.router.navigate(['/history']);
                  }
                }, error: (err) => {
                  this.router.navigate(['/housing']);
                }
              })
            }, error: (err) => {
              this.errorMessage('Ocurrió un error en el servidor O usuario no existente.');
            }
          })
        },
        error: (err) => {
          this.errorMessage('Ocurrió un error en el servidor. Intenta nuevamente más tarde.');
        }
      });

    } else {
      this.loginForm.markAllAsTouched(); // Para mostrar errores en todos los campos
      this.errorMessage('Formulario inválido');
    }
  }

}
