import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { HousingComponent } from './pages/housing/housing.component';
import { HistoryComponent } from './pages/history/history.component';
import { LoginComponent } from './pages/login/login.component';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'housing', component: HousingComponent }, 
    { path: 'history', component: HistoryComponent }, 
];
