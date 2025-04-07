export interface Usuario {
    id: string;  // @Id en Java
    nombre: string;  // @Field("nombre") en Java
    apellido: string;  // @Field("apellido") en Java
    email: string;  // @Email + @Indexed(unique = true) en Java
    telefono: string;
    tipoIdentificacion: string;  // @Field("tipoIdentificacion") en Java
    numeroIdentificacion: string;  // @Field("numeroIdentificacion") en Java
    password: string;  // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) en Java
    createdAt: string;  // @CreatedDate con formato en Java
    updatedAt: string;  // @LastModifiedDate con formato en Java
}