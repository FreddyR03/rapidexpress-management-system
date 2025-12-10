-- ===================================================
--                 1_schema_ddl.sql
-- ===================================================
DROP DATABASE IF EXISTS rapidexpress;
CREATE DATABASE rapidexpress;
USE rapidexpress;

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'TRABAJADOR') NOT NULL
);

CREATE TABLE vehiculos (
    id_vehiculo INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(20) NOT NULL UNIQUE,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    anio_fabricacion INT NOT NULL,
    capacidad_kg DOUBLE NOT NULL,
    estado ENUM('DISPONIBLE', 'EN_RUTA', 'EN_MANTENIMIENTO') NOT NULL,
    tipoVehiculo VARCHAR(50) NOT NULL
);

CREATE TABLE conductores (
    id_conductor INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    tipo_licencia VARCHAR(20) NOT NULL,
    numero_contacto VARCHAR(20) NOT NULL,
    estado ENUM('ACTIVO', 'DE_VACACIONES', 'INACTIVO') NOT NULL
);

CREATE TABLE paquetes (
    id_paquete INT AUTO_INCREMENT PRIMARY KEY,
    tracking_code VARCHAR(20) UNIQUE NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    peso DOUBLE NOT NULL,
    dimensiones VARCHAR(50),
    direccion_origen VARCHAR(255) NOT NULL,
    direccion_destino VARCHAR(255) NOT NULL,
    remitente VARCHAR(100) NOT NULL,
    destinatario VARCHAR(100) NOT NULL,
    estado ENUM('EN_BODEGA', 'ASIGNADO_A_RUTA', 'EN_TRANSITO', 'ENTREGADO', 'DEVUELTO') NOT NULL
);

CREATE TABLE rutas (
    id_ruta INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    id_vehiculo INT NOT NULL,
    id_conductor INT NOT NULL,
    total_peso_kg DOUBLE NOT NULL,
    estado ENUM('PENDIENTE','EN_RUTA','FINALIZADA') NOT NULL,
    FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo),
    FOREIGN KEY (id_conductor) REFERENCES conductores(id_conductor)
);

CREATE TABLE ruta_envio (
    id_rutaEnvio INT AUTO_INCREMENT PRIMARY KEY,
    id_ruta INT NOT NULL,
    id_paquete INT NOT NULL,
    FOREIGN KEY (id_ruta) REFERENCES rutas(id_ruta),
    FOREIGN KEY (id_paquete) REFERENCES paquetes(id_paquete)
);

CREATE TABLE mantenimientos (
    id_mantenimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_vehiculo INT NOT NULL,
    fecha DATE NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo)
);

CREATE TABLE auditoria (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50),
    accion VARCHAR(255),
    detalles TEXT,
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
