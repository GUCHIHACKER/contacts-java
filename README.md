# contacts-java
Una paqueña agenda de contactos en java.

```
// Crear tabla de contactos en la base de datos contactos de sqlite, archivo contactos.db en le mismo directorio que el .class

CREATE TABLE IF NOT EXISTS contactos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    telefono TEXT NOT NULL UNIQUE,
    email TEXT
);

// Insertar contactos de prueba a la base de datos manualmente
INSERT INTO contactos (nombre, telefono, email) VALUES ('Juan Pérez', '123456789', 'juan.perez@example.com');
INSERT INTO contactos (nombre, telefono, email) VALUES ('Ana López', '987654321', 'ana.lopez@example.com');
INSERT INTO contactos (nombre, telefono, email) VALUES ('Luis Martínez', '555666777', 'luis.martinez@example.com');
INSERT INTO contactos (nombre, telefono, email) VALUES ('María Fernández', '444333222', 'maria.fernandez@example.com');

// Necesario descargar sqlite-jdbc https://github.com/xerial/sqlite-jdbc/releases/tag/3.47.0.0
// Ejecutar la class con el dirver
java -cp ".:(sqlite-jdbc.jar/<Directorio del driver .jar>)" Contactos // Para linux
java -cp ".;(sqlite-jdbc.jar/<Directorio del driver .jar>)" Contactos // Para windows
```
