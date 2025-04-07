import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { UserService } from '../../services/user/user.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-register',
  standalone: true, // Necesario para poder usar "imports"
  imports: [RouterLink, CommonModule, ReactiveFormsModule, RouterModule, HttpClientModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;

  submitted = false;
  serverError: string | null = null;
  serverOk: string | null = null;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required]],
      apellido: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', [Validators.required], Validators.pattern(/^(?:\d{8}|\d{4}-\d{4}|\+506\d{8}|\d{10}|[\d()+\-\s]+)$/)],
      tipoIdentificacion: ['', [Validators.required]],
      numeroIdentificacion: ['', [Validators.required, Validators.pattern(/^\d{4}-\d{1}$/)]],
      password: '12345'
    });
  }

  get f() {
    return this.registerForm.controls;
  }

  okMessage() {
    this.serverOk = 'Inserción correcta, vaya a iniciar sesion';
    setTimeout(() => {
      this.serverOk = null;
    }, 3000);
  }

  errorMessage(message: any) {
    this.serverError = message;
    setTimeout(() => {
      this.serverError = null;
    }, 3000);
  }


  onSubmit(): void {
    this.submitted = true;

    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      this.errorMessage('Formulario inválido');
      return;
    }

    const formData = {
      ...this.registerForm.value,
    };

    console.log('Enviando datos:', formData);

    this.userService.registerUser(formData).subscribe({
      next: (response) => {
        this.registerForm.reset();
        this.okMessage();
      },
      error: (err) => {
        this.errorMessage('Ocurrió un error en el servidor. Intenta nuevamente más tarde. O usuario existente');
      }
    });
  }

}
