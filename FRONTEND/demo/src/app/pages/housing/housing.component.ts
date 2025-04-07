import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { HousingService } from '../../services/housing/housing.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-housing',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, HttpClientModule],
  templateUrl: './housing.component.html',
  styleUrl: './housing.component.css'
})
export class HousingComponent implements OnInit {
  housingForm!: FormGroup;
  submitted = false;
  serverError: string | null = null;
  serverOk: string | null = null;

  constructor(
    private fb: FormBuilder,
    private housingService: HousingService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.housingForm = this.fb.group({
      departamento: ['', [Validators.required]],
      municipio: ['', [Validators.required]],
      direccion: ['', [Validators.required]],
      ingresosMensuales: ['', [Validators.required, Validators.pattern('^[0-9]*\\.?[0-9]+$')]]
    });
  }

  get f() {
    return this.housingForm.controls;
  }

  okMessage() {
    this.serverOk = 'Inserción correcta, redirigiendo al historial...';
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

  onCancel(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    this.router.navigate(['/']); // Ir al login
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.housingForm.invalid) {
      this.housingForm.markAllAsTouched();
      this.errorMessage('Formulario inválido');
      return;
    }

    const formData = {
      ...this.housingForm.value,
      userId:  localStorage.getItem('userId')?.toString()
    };

    console.log('Enviando datos:', formData);


    this.housingService.insertHousing(formData).subscribe({
      next: (response) => {
        this.okMessage();
        setTimeout(() => this.router.navigate(['/history']), 1500);
      },
      error: (err) => {
        console.error('Error en la petición:', err);
        this.errorMessage('Ocurrió un error en el servidor. Intenta nuevamente más tarde.');
      }
    });
  }
}