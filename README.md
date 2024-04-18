# Proyecto Final - Programacion Movil II: Gestor de Tareas

Es un proyecto desarrollado para la asignatura IDS-376: Desarrollo de aplicaciones moviles moviles, 
es un sistema para leer, crear modificar o modificar tareas en especifico, donde la informacion puede 
estar almacenada a traves de una base de datos alojada en un host externo.

## Funcionalidades

- Leer Tareas
- Ver Tarea
- Crear Tareas
- Modificar Tarea
- Borrar Tarea
- Registrar cuenta
- Iniciar sesion

## Configuracion

### Servicio

El servicio está implementado en **NodeJS** utilizando el ORM **Sequelize**, actualmente está desplegado
en el siguiente enlace: _https://tasktify-kzi4.onrender.com_ y la base de datos (SQL Server) está alojada en Azure. 
Este paso de configuracion lo puedes saltar si el servidor sigue aun disponible.

En caso de querer probarlo localemente o falle el servidor, realiza los siguientes pasos:

1. Clona el repositiorio
2. Abre la terminal y ejecuta: cd task-managment-service
3. dentro del directorio **task-managment-service** crea un archivo .env en donde vamos a configurar asigna las siguientes variables:
   
> PORT=YOUR_SERVER_PORT
> 
> DATABASE_NAME="Tasktify"
> 
> SERVER_CONNECTION_USERNAME="Example"
> 
> SERVER_CONNECTION_PASSWORD="1234"
> 
> BD_SERVER_PORT=YOUR_DATABASE_PORT

4. Conectate a un servidor y crea una base de datos a nivel local de **Sql Server**, puede usar el programa **Sql server management studio** 
para manejar tu base de datos a nivel local
5. Ejecuta **npm run update-database** o **yarn update-database**
6.Ejecuta **npm start** o **yarn start**

### Aplicación

En este directorio se concentra toda la parte visual de la aplicación móvil, está desarrollada en Kotlin, para ejecutar la aplicación te
recomiendo tener al menos la version de 8 de java, sigue los pasos para iniciar con la aplicacion:

1. Clonar el repositorio(puedes saltar este paso si ya lo hiciste en la configuracion del servicio).
2. Abrir el proyecto **Tasktify** en Android Studio. En de que quieras probar tu servicio a nivel local reemplaza el valor de la variable **BASE_URL** que se localiza en **/app/src/main/java/com/example/tasktify/network/RetrofitClient.kt**
3. Sincronizar el proyecto con Gradle para descargar las dependencias necesarias y esperar a que este proceso termine.
4. Configurar el dispositivo o emulador.
5. Ejecutar la aplicación.
