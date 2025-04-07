export interface Housing {
    id: string;
    departamento: string;
    municipio: string;
    direccion: string;
    ingresosMensuales: number;
    userId: string;
    createdAt: string; // ISO 8601 date string format
    updatedAt: string; // ISO 8601 date string format
}