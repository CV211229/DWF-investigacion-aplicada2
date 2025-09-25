#Descripción
-------------------------------------------------------------------------------------------------------------------------
Este proyecto consiste en una API que se ejecuta junto con RabbitMQ en contenedores Docker utilizando Docker Compose.

#Prerrequisitos
-------------------------------------------------------------------------------------------------------------------------
Docker instalado y en ejecución

PowerShell (Windows) o terminal compatible

#Instalación y Ejecución
-------------------------------------------------------------------------------------------------------------------------
1. Clonar el repositorio o descargar la carpeta desde github.
   
2. Ejecutar powershell e ingresar el comando (docker compose up --build)
   
3. Este comando creará y levantará dos contenedores interconectados:

API - Servicio principal de la aplicación

RabbitMQ - Servicio de mensajería

#URLs de Acceso
-------------------------------------------------------------------------------------------------------------------------------
API
http://localhost:8080/api/products

RabbitMQ Management Console
http://localhost:15672/

H2
http://localhost:8080/h2-console

#Credenciales RabbitMQ
--------------------------------------------------------------------------------------------------------------------------------
Usuario: guest

Contraseña: guest

#Credenciales de H2
--------------------------------------------------------------------------------------------------------------------------------------
JDBC URL: jdbc:h2:mem:testdb

User Name: sa

Password: (dejar vacío)

#Uso
------------------------------------------------------------------------------------------------------------------------------------
Utiliza Postman o cualquier cliente HTTP para interactuar con la API

La API estará disponible en el puerto 8080

La consola de administración de RabbitMQ estará en el puerto 15672

#Notas
-----------------------------------------------------------------------------------------------------------------------------------
La primera ejecución puede tomar algunos minutos mientras descarga las imágenes y construye los contenedores

Para detener la aplicación, presiona Ctrl + C en la terminal
