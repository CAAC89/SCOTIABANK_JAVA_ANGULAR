# SCOTIABANK_JAVA_ANGULAR
PROYECTO DE PRUEBA TECNICA GRUPO SINTEPRO

REQUERIMIENTOS DE INSTALACION-----------------------------------

1) GESTOR DE BASES MONGODB

TENER ESTA INSTANCIA ARRIBA, YO LO INSTALE MODO SIN PONER CREDENCIALES AUTENTIFICACION DIRECTA
mongodb://localhost:27017

2) BACKEND (TENER MAVEN-3.9.9 Y JDK-21 VERSION)

Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Java version: 21.0.5, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-21


3) FRONTEND

Angular CLI: 19.2.6
Node: 22.14.0
Package Manager: npm 10.9.2
OS: win32 x64



COMANDOS PARA CORRER EL PROGRAMA-----------------------------------

1) BACKEND

cd ...\BACKEND\demo\
mvn clean install
mvn spring-boot:run

2) FRONTEND

cd ...\FRONTEND\demo\
npm install
ng serve


COMO FUNCIONA EL FRONTEND-----------------------------------
* SE VALIDAN  LOS FORMULARIOS
* SE TIENEN 4 PANTALLAS DE LOGIN, REGISTRO, INSERCION DE VIVIENDA Y PAGINA QUE MUESTRA LA TABLA
* SE HACEN MEDIA QUERYS PARA DISPOSITIVOS
* ALGUNAS CLASES LAS VARIE UN POCO
* LA PAGINA DE REGISTRO SOLO INSERTA NO ENVIA A NINGUN LUGAR
* EL INICIO DE SESION FUNCIONA ASI SI EL USUARIO NO TIENE VIVIENDA, LO MANDA A LA PAGINA DE REGISTRO DE VIVIENDA, SINO A MUESTRA LA PAGINA DE TABLA
* EN LA PAGINA DE VIVIENDA CUANDO SE INSERTAN LOS DATOS LO ENVIA A LA TABLA PAGINA DANDOLE EN CONTINUAR



