-- ===================================================
--                  2_data_dml.sql
-- ===================================================

USE rapidexpress;

INSERT INTO usuarios (username, password, rol) VALUES
('admin', 'admin123', 'ADMIN'),
('trabajador', 'trab123', 'TRABAJADOR');


INSERT INTO vehiculos (placa, marca, modelo, anio_fabricacion, capacidad_kg, estado, tipoVehiculo) VALUES
('JKL321', 'Ford', 'Ranger', 2020, 1600, 'EN_MANTENIMIENTO', 'Motocicleta'),
('MNO654', 'Mazda', 'BT-50', 2021, 1500, 'DISPONIBLE', 'Camion'),
('PQR987', 'Isuzu', 'KB', 2016, 1200, 'EN_RUTA', 'Motocicleta'),
('STU147', 'Hyundai', 'H100', 2015, 1000, 'DISPONIBLE', 'Camion'),
('VWX258', 'Kia', 'K2700', 2020, 1100, 'DISPONIBLE', 'Motocicleta'),
('YZA369', 'Renault', 'Master', 2022, 2000, 'DISPONIBLE', 'Camion'),
('BCD741', 'Peugeot', 'Boxer', 2019, 1900, 'EN_RUTA', 'Camion'),
('EFG852', 'Citroen', 'Jumper', 2018, 1800, 'DISPONIBLE', 'Camion'),
('HIJ963', 'Volkswagen', 'Amarok', 2021, 1700, 'DISPONIBLE', 'Camion'),
('KLM159', 'Mercedes', 'Sprinter', 2017, 2200, 'EN_MANTENIMIENTO', 'Camion'),
('NOP357', 'Fiat', 'Ducato', 2016, 1800, 'DISPONIBLE', 'Camion'),
('QRS456', 'Chevrolet', 'NHR', 2020, 3000, 'DISPONIBLE', 'Carro'),
('TUV753', 'Hino', '300', 2019, 3500, 'DISPONIBLE', 'Carro'),
('WXY951', 'JAC', 'X200', 2018, 1200, 'EN_RUTA', 'Carro'),
('ZAB258', 'Dongfeng', 'DFSK', 2021, 1000, 'DISPONIBLE', 'Carro'),
('CDE147', 'Kia', 'Sportage', 2015, 900, 'DISPONIBLE', 'Carro'),
('FGH369', 'Toyota', 'Land Cruiser', 2014, 800, 'EN_RUTA', 'Carro');


INSERT INTO conductores (nombre_completo, identificacion, tipo_licencia, numero_contacto, estado) VALUES
('Juan Pérez', 'CC1001', 'C1', '3001111111', 'ACTIVO'),
('Luis Gómez', 'CC1002', 'C2', '3002222222', 'INACTIVO'),
('Carlos Ruiz', 'CC1003', 'C3', '3003333333', 'ACTIVO'),
('Pedro Herrera', 'CC1004', 'C1', '3004444444', 'DE_VACACIONES'),
('Sergio López', 'CC1005', 'C2', '3005555555', 'ACTIVO'),
('Miguel Torres', 'CC1006', 'C3', '3006666666', 'INACTIVO'),
('Oscar Medina', 'CC1007', 'C1', '3007777777', 'ACTIVO'),
('Jorge Vega', 'CC1008', 'C2', '3008888888', 'DE_VACACIONES'),
('Ricardo Ríos', 'CC1009', 'C3', '3009999999', 'ACTIVO'),
('Daniel Silva', 'CC1010', 'C1', '3010000000', 'ACTIVO'),
('Andrés Páez', 'CC1011', 'C1', '3011111111', 'INACTIVO'),
('Mario Rojas', 'CC1012', 'C2', '3012222222', 'ACTIVO'),
('Felipe Castro', 'CC1013', 'C3', '3013333333', 'ACTIVO'),
('Pablo Nieto', 'CC1014', 'C2', '3014444444', 'DE_VACACIONES'),
('Héctor Beltrán', 'CC1015', 'C1', '3015555555', 'ACTIVO'),
('Iván Salazar', 'CC1016', 'C3', '3016666666', 'ACTIVO'),
('Julián Ortiz', 'CC1017', 'C2', '3017777777', 'INACTIVO'),
('Fernando Ávila', 'CC1018', 'C1', '3018888888', 'ACTIVO'),
('Adrián Mora', 'CC1019', 'C3', '3019999999', 'ACTIVO'),
('Santiago León', 'CC1020', 'C2', '3020000000', 'DE_VACACIONES');


INSERT INTO paquetes (tracking_code, descripcion, peso, dimensiones, direccion_origen, direccion_destino, remitente, destinatario, estado) VALUES
('PK001', 'Ropa', 2.5, '30x20x10', 'Bogotá', 'Medellín', 'Ana Ruiz', 'Carlos Pérez', 'EN_BODEGA'),
('PK002', 'Zapatos', 1.2, '25x20x12', 'Cali', 'Bogotá', 'Laura M', 'José G', 'ASIGNADO_A_RUTA'),
('PK003', 'Electrónico', 3.0, '40x30x20', 'Barranquilla', 'Cali', 'Pedro L', 'Sergio H', 'EN_TRANSITO'),
('PK004', 'Celular', 0.8, '15x10x5', 'Medellín', 'Bogotá', 'Sofía P', 'Luisa A', 'ENTREGADO'),
('PK005', 'Libros', 4.0, '35x25x15', 'Bogotá', 'Bucaramanga', 'Andrés A', 'Miguel C', 'DEVUELTO'),
('PK006', 'Documentos', 0.5, '20x15x3', 'Cali', 'Cartagena', 'Juan R', 'María L', 'EN_BODEGA'),
('PK007', 'Computador', 5.0, '50x40x20', 'Medellín', 'Barranquilla', 'Diego V', 'Pablo R', 'EN_TRANSITO'),
('PK008', 'Perfume', 0.3, '10x10x5', 'Bogotá', 'Santa Marta', 'Paola Q', 'Erika Z', 'EN_BODEGA'),
('PK009', 'Tablet', 1.0, '25x20x5', 'Cali', 'Bogotá', 'Mauricio B', 'Daniel T', 'ASIGNADO_A_RUTA'),
('PK010', 'Accesorios', 0.6, '15x10x8', 'Barranquilla', 'Cali', 'Yolanda M', 'Esteban P', 'ENTREGADO'),
('PK011', 'Medicamentos', 1.5, '20x15x10', 'Cartagena', 'Bogotá', 'Clínica Norte', 'Farmacia Sur', 'EN_TRANSITO'),
('PK012', 'Reloj', 0.2, '10x8x5', 'Medellín', 'Cali', 'Rolo S', 'Kevin Y', 'EN_BODEGA'),
('PK013', 'Juguetes', 3.5, '40x30x20', 'Bogotá', 'Neiva', 'Juguetería ABC', 'Sandra F', 'DEVUELTO'),
('PK014', 'Cámara', 0.9, '20x15x10', 'Bucaramanga', 'Bogotá', 'Marco T', 'Rafael O', 'ENTREGADO'),
('PK015', 'Auriculares', 0.4, '15x10x5', 'Santa Marta', 'Medellín', 'Lina H', 'Brayan C', 'ASIGNADO_A_RUTA'),
('PK016', 'Monitor', 4.5, '60x40x15', 'Bogotá', 'Cali', 'Carlos B', 'David H', 'EN_TRANSITO'),
('PK017', 'Teclado', 0.7, '30x15x5', 'Cali', 'Bogotá', 'Sara W', 'Martín Q', 'EN_BODEGA'),
('PK018', 'Bicicleta', 12.0, '100x70x20', 'Medellín', 'Cartagena', 'CicloStore', 'Pedro M', 'ASIGNADO_A_RUTA'),
('PK019', 'Relojería', 0.4, '15x10x8', 'Pereira', 'Bogotá', 'Marta Z', 'Laura Y', 'EN_TRANSITO'),
('PK020', 'Herramientas', 8.0, '50x30x20', 'Bogotá', 'Manizales', 'Ferretería JL', 'Juan D', 'EN_BODEGA');


INSERT INTO rutas (fecha, id_vehiculo, id_conductor, total_peso_kg, estado) VALUES
('2025-01-10', 1, 1, 300, 'PENDIENTE'),
('2025-01-11', 2, 2, 450, 'EN_RUTA'),
('2025-01-12', 3, 3, 500, 'FINALIZADA'),
('2025-01-13', 4, 4, 200, 'PENDIENTE'),
('2025-01-14', 5, 5, 150, 'EN_RUTA'),
('2025-01-15', 6, 6, 700, 'FINALIZADA'),
('2025-01-16', 7, 7, 280, 'EN_RUTA'),
('2025-01-17', 8, 8, 320, 'PENDIENTE'),
('2025-01-18', 9, 9, 650, 'FINALIZADA'),
('2025-01-19', 10, 10, 150, 'PENDIENTE'),
('2025-01-20', 11, 11, 210, 'EN_RUTA'),
('2025-01-21', 12, 12, 390, 'FINALIZADA'),
('2025-01-22', 13, 13, 420, 'PENDIENTE'),
('2025-01-23', 14, 14, 230, 'EN_RUTA'),
('2025-01-24', 15, 15, 520, 'FINALIZADA'),
('2025-01-25', 16, 16, 380, 'PENDIENTE'),
('2025-01-26', 17, 17, 250, 'EN_RUTA'),
('2025-01-27', 18, 18, 470, 'FINALIZADA'),
('2025-01-28', 19, 19, 340, 'PENDIENTE'),
('2025-01-29', 20, 20, 600, 'EN_RUTA');


INSERT INTO ruta_envio (id_ruta, id_paquete) VALUES
(21, 1),
(22, 2),
(23, 3),
(24, 4),
(25, 5),
(26, 6),
(27, 7),
(28, 8),
(29, 9),
(30, 10),
(31, 11),
(32, 12),
(33, 13),
(34, 14),
(35, 15),
(36, 16),
(37, 17),
(38, 18),
(39, 19),
(40, 20);


INSERT INTO mantenimientos (id_vehiculo, fecha, descripcion, tipo) VALUES
(1, '2025-01-05', 'Cambio de aceite', 'Preventivo'),
(2, '2025-01-06', 'Ajuste frenos', 'Correctivo'),
(3, '2025-01-07', 'Revisión motor', 'Preventivo'),
(4, '2025-01-08', 'Cambio llantas', 'Correctivo'),
(5, '2025-01-09', 'Chequeo general', 'Preventivo'),
(6, '2025-01-10', 'Alineación', 'Preventivo'),
(7, '2025-01-11', 'Cambio filtros', 'Preventivo'),
(8, '2025-01-12', 'Reparación caja', 'Correctivo'),
(9, '2025-01-13', 'Mantenimiento general', 'Preventivo'),
(10, '2025-01-14', 'Cambio de batería', 'Correctivo'),
(11, '2025-01-15', 'Limpieza sistema de frenos', 'Preventivo'),
(12, '2025-01-16', 'Cambio pastillas', 'Correctivo'),
(13, '2025-01-17', 'Diagnóstico eléctrico', 'Preventivo'),
(14, '2025-01-18', 'Reparación amortiguadores', 'Correctivo'),
(15, '2025-01-19', 'Cambio de correas', 'Preventivo'),
(16, '2025-01-20', 'Revisión transmisión', 'Correctivo'),
(17, '2025-01-21', 'Cambio bujías', 'Preventivo'),
(18, '2025-01-22', 'Reparación alternador', 'Correctivo'),
(19, '2025-01-23', 'Ajuste suspensión', 'Correctivo'),
(20, '2025-01-24', 'Revisión general', 'Preventivo');



INSERT INTO auditoria (usuario, accion, detalles) VALUES
('Carlos', 'Login', 'Inicio de sesión exitoso'),
('Lucia', 'Registrar vehículo', 'Vehículo ABC123 creado'),
('Jorge', 'Registrar ruta', 'Ruta creada para vehículo 3'),
('Mariana', 'Actualizar paquete', 'Cambio de estado PK003'),
('Santiago', 'Asignar paquete a ruta', 'Ruta 4 con paquete 8'),
('Diana', 'Eliminar conductor', 'ID 12 eliminado'),
('Ricardo', 'Actualizar vehículo', 'Vehículo 5 pasado a EN_RUTA'),
('Valentina', 'Registrar paquete', 'Paquete PK010 registrado'),
('Hector', 'Cerrar ruta', 'Ruta 7 finalizada'),
('Paula', 'Registrar mantenimiento', 'Mantenimiento a vehículo 9'),
('Andres', 'Logout', 'Fin de sesión'),
('Camila', 'Actualizar ruta', 'Ruta 2 actualizada'),
('Sebastian', 'Insert conductor', 'Nuevo conductor CC1018'),
('Monica', 'Insert vehículo', 'Nuevo vehículo Kia'),
('Julian', 'Eliminar ruta', 'Ruta ID 3 eliminada'),
('Natalia', 'Asignar paquete', 'Paquete PK014 asignado'),
('Felipe', 'Actualizar estado paquete', 'PK005 marcado DEVUELTO'),
('Cristina', 'Actualizar conductor', 'Conductor 7 actualizado'),
('Oscar', 'Registrar auditoría', 'Prueba'),
('Laura', 'Supervisión', 'Revisión general realizada');
