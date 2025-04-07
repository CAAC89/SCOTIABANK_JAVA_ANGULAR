package com.scotiabank.demo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "housing")
public class Housing {

    @Id
    private String id;

    @Field("departamento") // Modificable
    private String departamento;

    @Field("municipio") // Modificable
    private String municipio;

    @Field("direccion") // Modificable
    private String direccion;

    @Field("ingresosMensuales") // Modificable
    private Double ingresosMensuales;

    @Field("userId") // Relación con usuario
    private String userId;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public Housing() {
    }

    public Housing(String id, String departamento, String municipio, String direccion, Double ingresosMensuales,
            String userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.departamento = departamento;
        this.municipio = municipio;
        this.direccion = direccion;
        this.ingresosMensuales = ingresosMensuales;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getIngresosMensuales() {
        return ingresosMensuales;
    }

    public void setIngresosMensuales(Double ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
