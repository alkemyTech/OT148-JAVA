# OT148-JAVA

## Variables de Ambiente requeridas

Antes de ejecutar el proyecto o correr los test, hay que tener los valores correctos seteados en las siguientes
variables de ambiente:

AWS_ENDPOINT_URL

AWS_ACCESS_KEY

AWS_SECRET_KEY

AWS_BUCKET_NAME

JWT_SECRET

## Run Project

mvn spring-boot:run

## Build Project

Ctrl + F9. Click derecho en la carpeta de proyecto, pinchar en la opcion build module.

## Run Test

mvn test

## Tecnología

La aplicación se desarrolló utilizando ``` Spring Boot``` siguiendo el patron REST

## Users

Los ```Users``` se cargan cuando se levanta la aplicación.

| EMAIL  |    PASSWORD |
|-----|------|
|user1@gmail.com      | passworduser |
|user2@gmail.com      | passworduser    |
|user3@gmail.com      | passworduser    |
|user4@gmail.com      | passworduser|
|user5@gmail.com      | passworduser    |
|user6@gmail.com      | passworduser    |
|user7@gmail.com      | passworduser|
|user8@gmail.com      | passworduser    |
|user9@gmail.com      | passworduser|
|user10@gmail.com    | passworduser    |
|admin1@gmail.com    | passwordadmin    |
|admin2@gmail.com    | passwordadmin|	
|admin3@gmail.com    | passwordadmin    |
|admin4@gmail.com    | passwordadmin    |
|admin5@gmail.com    | passwordadmin|	
|admin6@gmail.com    | passwordadmin|	
|admin7@gmail.com    | passwordadmin    |
|admin8@gmail.com    | passwordadmin|	
|admin9@gmail.com    | passwordadmin|	
|admin10@gmail.com    | passwordadmin    |

De esta manera, ya se cuenta con un set de usuarios para probar la aplicación.

## Documentación

Para acceder a la documentación de la api, solo debe levantar la aplicación e
ingresar [aqui](http://localhost:8080/api/docs).
