# APLICACIÓN ANDROID VETERINARIA CON WEBSERVICES
## REQUISITOS:
- Android 12 o superior;
- Conexion a la misma red del WebService;
## LIBRERIAS:
- Volley(para la conexion HTTP);

## PERMISOS QUE LA APLICACIÓN USA:
- INTERNET;
## PASOS PARA CORRER LA APLICACIÓN:
 1. clonar el proyecto a un repositorio local.
    ```bash
    git clone https://github.com/danielayala-06/AppVeterinaria.git
    ```
 2. Cambiar la URL del endPoint en las clases Registrar, Buscar, Listar de la carpeta "com.senati.appveterinariaseminario". Por su dirección IP  
  ### Dirección de los archivos
    app/
    ├── manifest/
    ├── java/
      ├── com.senati.appveterinariaseminario/
        ├── Buscar <-
        ├── Listar <-
        ├── MainActivity 
        └── Registrar <-
      ├── com.senati.appveterinariaseminario(androidTest)/ 
      └── com.senati.appveterinariaseminario(test)/
    └── res/
  ### Ejemplo para configurar la URL por su ip :
  ```java
  private final String URL = "http://su_dirección_ip:3000/mascotas";
  ```
