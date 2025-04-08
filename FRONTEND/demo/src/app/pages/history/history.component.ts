import { Component, OnInit, PLATFORM_ID, Inject } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { HousingService } from '../../services/housing/housing.service';
import { Router } from '@angular/router';
import { Usuario } from '../../models/usuario.model';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Housing } from '../../models/housing.model';

@Component({
  selector: 'app-history',
  imports: [CommonModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit {
  usuarios: Usuario[] = [];
  housings: Housing[] = [];
  cargando = true;

  modalOpen = false;
  selectedUsuario: any = null;
  selectedHousing: any = null;

  currentPage = 1; // Página actual
  pageSize = 5; // Número de usuarios por página


  constructor(
    private housingService: HousingService,
    private userService: UserService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('token');
      if (token) {
        this.userService.getUsers(token).subscribe({
          next: (data) => {
            this.usuarios = data;
            console.log(data);
            this.cargando = false;
          },
          error: (err) => {
            console.error('Error al cargar usuarios', err);
            this.cargando = false;
          }
        });
      } else {
        console.warn('Token no disponible');
        this.cargando = false;
      }
    }else{
      if (typeof window !== 'undefined') {
        window.location.reload();
      }
    }
  }


 

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      console.log('Logout ejecutado');
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
      this.router.navigate(['/']);
    }
  }

  openModal(usuario: any) {
    this.selectedUsuario = usuario;
    this.housingService.getHousingByUserId(usuario.id).subscribe({
      next: (data) => {
        this.selectedHousing = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar housing', err);
        this.cargando = false;
      }
    });
    this.modalOpen = true;
  }

  closeModal() {
    this.modalOpen = false;
    this.selectedUsuario = null;
  }

  


   // Método para obtener los usuarios paginados
   paginatedUsuarios() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.usuarios.slice(startIndex, startIndex + this.pageSize);
  }

  // Calcular el número total de páginas
  totalPages() {
    return Math.ceil(this.usuarios.length / this.pageSize);
  }

  // Generar un array de números de página
  pages() {
    const total = this.totalPages();
    return Array.from({ length: total }, (_, i) => i + 1);
  }

  // Cambiar a la página anterior
  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  // Cambiar a la siguiente página
  nextPage() {
    if (this.currentPage < this.totalPages()) {
      this.currentPage++;
    }
  }

  // Cambiar a una página específica
  goToPage(page: number) {
    this.currentPage = page;
  }
}
