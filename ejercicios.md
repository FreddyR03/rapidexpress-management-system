
## 🚀 Ejercicios de Extensión para Examen (Simulación Rápida)

Estos ejercicios requieren integrar una o dos clases de tu proyecto (DAO, Controller, CLI) y se enfocan en los temas que el profesor ya evaluó en la práctica.

### Ejercicio de Extensión 1: Cálculo de Descuentos por Volumen (Patrones de Diseño y SQL)

  * **Objetivo:** Practicar el Patrón **Strategy** o el uso de **Switch/Case** avanzado para aplicar lógica de negocio con filtros SQL.
  * **Contexto:** Los clientes que envían paquetes muy pesados reciben un descuento.
  * **Requerimiento de Extensión:**
    1.  Modificar el método `crearPaquete` o crear un servicio de **Cálculo de Tarifas Finales**.
    2.  Si el `peso` del paquete es **mayor a 10 Kg**, aplicar un **5% de descuento** al costo de envío base (calculado en el Ejercicio 1).
    3.  Crear un reporte para administradores que liste todos los paquetes que recibieron este descuento.

#### 💡 Solución Rápida

| Componente | Modificación |
| :--- | :--- |
| **`PaqueteController`** | Añadir lógica de descuento en `crearPaquete` o `simularEnvio`. |
| **`PaqueteDAO`** | Nuevo método `obtenerPaquetesConDescuento()`. |
| **`MenuAdministradorCLI`** | Nueva opción en el menú de Reportes. |

**1. Lógica Java (Asumiendo que tienes el costo base `costoBase`):**

```java
public double aplicarDescuentoPorPeso(double peso, double costoBase) {
    double descuento = 0.0;
    if (peso > 10.0) {
        descuento = costoBase * 0.05; // 5% de descuento
        System.out.println("🎉 Descuento por Volumen aplicado: " + descuento);
    }
    return costoBase - descuento;
}
// Esto se llamaría después de calcular el costo base en el Ejercicio 1.
```

**2. Lógica SQL (Reporte):**

Para el reporte, asumiendo que tienes una columna `peso` en la tabla `paquetes`:

```sql
SELECT id_paquete, descripcion, peso
FROM paquetes
WHERE peso > 10.0;
```

-----

-----

### Ejercicio de Extensión 2: Gestión de Rutas Vencidas (Manejo de Fechas y Actualización Masiva)

  * **Objetivo:** Practicar validaciones y actualizaciones masivas en la base de datos basadas en fechas (`LocalDate`).
  * **Contexto:** Necesitas un proceso que automáticamente marque las rutas de envío como **'FINALIZADA'** si ya ha pasado su fecha de entrega estimada.
  * **Requerimiento de Extensión:**
    1.  Asegurarse de que la tabla `rutas` tenga una columna `fecha_estimada_entrega` (DATE).
    2.  Crear una opción en el menú de Rutas (o un método de limpieza automático) llamado **'Cerrar Rutas Antiguas'**.
    3.  Este método debe ejecutar una sola consulta SQL que actualice el `estado` de todas las rutas cuya `fecha_estimada_entrega` sea anterior a la fecha actual (`LocalDate.now()`).

#### 💡 Solución Rápida

| Componente | Modificación |
| :--- | :--- |
| **`RutaDAO`** | Nuevo método `actualizarEstadoRutasAntiguas()`. |
| **`RutaController`** | Nuevo método de orquestación. |
| **`MenuAdministradorCLI`** | Nueva opción en el menú de Rutas. |

**1. Lógica SQL (Actualización Masiva en el DAO):**

Esta es la forma más eficiente de hacerlo en una sola pasada. Utiliza la función de fecha de tu motor SQL (asumiendo MySQL para `CURDATE()`):

```sql
-- Dentro de RutaDAO.actualizarEstadoRutasAntiguas()
String sql = "UPDATE rutas SET estado = 'FINALIZADA' WHERE fecha_estimada_entrega < CURDATE() AND estado <> 'FINALIZADA'";

// Ejecutas esta sentencia con Statement o PreparedStatement
// int filasAfectadas = ps.executeUpdate();
```

**2. Lógica Java (En el Controller, para informar):**

```java
public void cerrarRutasAntiguas() {
    try {
        int count = rutaDAO.actualizarEstadoRutasAntiguas();
        if (count > 0) {
            System.out.println("✅ Se finalizaron automáticamente " + count + " rutas con fecha de entrega vencida.");
        } else {
            System.out.println("No se encontraron rutas pendientes para finalizar automáticamente.");
        }
        // Opcional: registrar esto en el AuditLogger.
    } catch (Exception e) {
        System.err.println("Error al cerrar rutas: " + e.getMessage());
    }
}
```

-----

-----

### Ejercicio de Extensión 3: Importación de Rutas Críticas (Manejo de Archivos Avanzado y Log)

  * **Objetivo:** Combinar la lectura de archivos (Ej. 2) con el registro de auditoría (Ej. 5) y la lógica de negocio.
  * **Contexto:** Los administradores deben poder cargar una lista de códigos de seguimiento (Tracking Codes) para marcarlos como **"Paquetes Críticos"** y registrar quién cargó esta lista.
  * **Requerimiento de Extensión:**
    1.  Crear un archivo **`criticos.txt`** con una lista de Tracking Codes, uno por línea.
    2.  Crear una opción **'Cargar Paquetes Críticos'** en el menú de administrador.
    3.  El sistema debe leer el archivo y, por cada `tracking_code`:
          * Cambiar el estado del paquete a `ASIGNADO_A_RUTA`.
          * **Registrar la acción completa** en el **`AuditLogger`** con el usuario actual.

#### 💡 Solución Rápida

| Componente | Modificación |
| :--- | :--- |
| **`MenuAdministradorCLI`** | Nueva opción. |
| **`PaqueteController`** | Nuevo método `cargarPaquetesCriticos(String username)`. |
| **`PaqueteDAO`** | Nuevo método `actualizarEstadoPorTracking(String tracking, EstadoPaquete nuevoEstado)`. |

**1. Lógica Java (En `PaqueteController`):**

```java
public void cargarPaquetesCriticos(String username) {
    String archivo = "criticos.txt";
    int count = 0;
    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String trackingCode;
        while ((trackingCode = br.readLine()) != null) {
            trackingCode = trackingCode.trim();
            if (!trackingCode.isEmpty()) {
                // 1. Ejecutar la actualización en la BD (DAO lo hace por tracking code)
                paqueteDAO.actualizarEstadoPorTracking(trackingCode, EstadoPaquete.ASIGNADO_A_RUTA);
                
                // 2. Registrar en el log de auditoría
                AuditLogger.getInstancia().log(
                    username, 
                    "MARCADO_CRITICO", 
                    "Tracking: " + trackingCode
                );
                count++;
            }
        }
        System.out.println("✅ Se procesaron " + count + " paquetes críticos.");
        auditoriaController.registrarAccion(username, "IMPORTACION_CRITICOS", "Total procesados: " + count);

    } catch (IOException e) {
        System.out.println("❌ Error al leer el archivo criticos.txt: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("❌ Error al actualizar paquete: " + e.getMessage());
    }
}
```

**2. Lógica SQL (En `PaqueteDAO`):**

```java
// Implementación de actualizarEstadoPorTracking
String sql = "UPDATE paquetes SET estado = ? WHERE tracking_code = ?";
// ...
ps.setString(1, nuevoEstado.name());
ps.setString(2, tracking);
ps.executeUpdate();
```

-----

### Conclusión para el Examen

Estos ejercicios te obligan a reutilizar métodos existentes (`AuditLogger`, `DAO.update`, `LocalDate`) y a integrarlos en un nuevo flujo de trabajo CLI. **Practica la estructura de los `try-catch` y la sintaxis básica de los `UPDATE` y `SELECT` con `WHERE` en SQL.**


## 🎯 Ejercicios de Extensión Adicionales para el Examen

### Ejercicio de Extensión 4: Validación de Cobertura por Origen (Manejo de Datos y Validaciones)

  * **Objetivo:** Practicar validaciones de negocio en el *Controller* y el manejo de estructuras de datos in-memory (como `Set` o `List`).
  * **Contexto:** Para optimizar costos, RapidExpress solo acepta paquetes cuyo origen sea una de las tres ciudades principales.
  * **Requerimiento de Extensión:**
    1.  En el `PaqueteController`, define una lista **`CIUDADES_COBERTURA`** (e.g., Bogotá, Medellín, Cali).
    2.  Modificar el método **`crearPaquete`** para que, antes de llamar al DAO para insertar, valide la `direccionOrigen` del nuevo paquete.
    3.  Si la dirección de origen **no** está en la lista de cobertura, debe **bloquear la creación** y lanzar una excepción o imprimir un mensaje de error: "❌ La ciudad de origen [Ciudad] no está dentro de nuestra zona de cobertura."

#### 💡 Solución Rápida

| Componente | Modificación |
| :--- | :--- |
| **`PaqueteController`** | Inicializar una `Set<String>` y modificar `crearPaquete`. |

**1. Lógica Java (En `PaqueteController`):**

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PaqueteController {
    // Definición de las ciudades de cobertura (Mejor usar Set para búsquedas rápidas)
    private static final Set<String> CIUDADES_COBERTURA = new HashSet<>(
        Arrays.asList("Bogotá", "Medellín", "Cali")
    );

    // ... (otros métodos)

    // Modificación al método crearPaquete
    public void crearPaquete(String tracking, String descripcion, double peso, String dimensiones, 
                             String origen, String destino, String remitente, String destinatario, 
                             EstadoPaquete estado, LocalDate fechaIngreso) {
        
        // 1. Validación de Cobertura
        if (!CIUDADES_COBERTURA.contains(origen)) {
            System.err.println("❌ ALERTA DE COBERTURA: La ciudad de origen " + origen + " no está dentro de nuestra zona de cobertura.");
            return; // Bloquea la inserción en la BD
        }
        
        try {
            // 2. Si la validación pasa, llama al service/DAO para crear
            paqueteService.crearPaquete(new Paquete(..., origen, ...));
            // ... Registrar auditoría
            
        } catch (Exception e) {
            System.err.println("Error al crear paquete: " + e.getMessage());
        }
    }
}
```

-----

-----

### Ejercicio de Extensión 5: Resumen Diario de Envíos Entregados (SQL y Reporte CLI)

  * **Objetivo:** Practicar consultas SQL usando funciones de agregación (`COUNT`), filtros de fecha (`DATE`), y la presentación de datos en la consola (CLI).
  * **Contexto:** El gerente necesita saber cuántos paquetes fueron entregados **hoy** para generar un reporte de cierre diario.
  * **Requerimiento de Extensión:**
    1.  Agregar una columna `fecha_entrega_real` (DATE o DATETIME) a la tabla `paquetes`. (Modificar el DAO para que la registre al cambiar el estado a `ENTREGADO`).
    2.  Crear una opción en el menú de Reportes llamada **"Reporte Diario de Entregas"**.
    3.  El reporte debe mostrar el **número total de paquetes** cuyo estado actual es `ENTREGADO` y cuya `fecha_entrega_real` sea la fecha actual (`LocalDate.now()`).

#### 💡 Solución Rápida

| Componente | Modificación |
| :--- | :--- |
| **`PaqueteDAO`** | Nuevo método `contarEntregasDeHoy()`. |
| **`PaqueteController`** | Nuevo método `generarReporteDiario()`. |
| **`MenuAdministradorCLI`** | Nueva opción en el menú de Reportes. |

**1. Lógica SQL (En `PaqueteDAO`):**

El método del DAO solo necesita devolver un entero (`int`) que es el resultado de la función `COUNT()`.

```java
public int contarEntregasDeHoy() throws Exception {
    // Usamos CURDATE() o NOW() para comparar con la fecha actual de la BD
    String sql = "SELECT COUNT(*) FROM paquetes WHERE estado = 'ENTREGADO' AND fecha_entrega_real = CURDATE()"; 
    
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        if (rs.next()) {
            return rs.getInt(1); // El COUNT(*) es el primer (y único) resultado
        }
        return 0;
    }
}
```

**2. Lógica Java (En `PaqueteController`):**

```java
public void generarReporteDiario() {
    try {
        int totalEntregas = paqueteDAO.contarEntregasDeHoy();
        System.out.println("\n==================================");
        System.out.println("📦 REPORTE DIARIO DE ENTREGAS (" + LocalDate.now() + ")");
        System.out.println("----------------------------------");
        System.out.println("Total de Paquetes Entregados Hoy: " + totalEntregas);
        System.out.println("==================================");
    } catch (Exception e) {
        System.err.println("Error al generar reporte: " + e.getMessage());
    }
}
```

## 🛠️ Implementación de Extensiones Clave

### 1\. Ejercicio de Extensión 6: Validación de Capacidad de Vehículo

Este ejercicio requiere modificar el Controller de Ruta y usar un método en el DAO de Vehículo.

#### A. `VehiculoDAO.java` (Nueva consulta)

Necesitas un método para obtener la capacidad de carga solo con el ID.

```java
// Archivo: dao/VehiculoDAO.java

// Añade este método a tu clase VehiculoDAO
public double obtenerCapacidadPorId(int idVehiculo) throws Exception {
    String sql = "SELECT capacidad_kg FROM vehiculos WHERE id_vehiculo = ?";
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idVehiculo);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("capacidad_kg");
            }
        }
    }
    // Si no encuentra el vehículo, lanza una excepción o devuelve 0.0, dependiendo de tu manejo de errores.
    throw new Exception("Vehículo con ID " + idVehiculo + " no encontrado.");
}
```

#### B. `RutaController.java` (Nueva Lógica de Validación)

Modifica el método donde creas la ruta (ej. `crearRuta`).

```java
// Archivo: controller/RutaController.java
// Asegúrate de inyectar o inicializar VehiculoDAO
private VehiculoDAO vehiculoDAO; 

public RutaController(RutaService rutaService, PaqueteService paqueteService, VehiculoDAO vehiculoDAO) {
    this.rutaService = rutaService;
    // ... otros DAOs/Services
    this.vehiculoDAO = vehiculoDAO; 
}

// Suponiendo que tu método de creación de ruta recibe los parámetros clave
public void crearRuta(int idVehiculo, int idConductor, double totalPesoKg) {
    try {
        // 1. OBTENER LA CAPACIDAD DEL VEHÍCULO
        double capacidadVehiculo = vehiculoDAO.obtenerCapacidadPorId(idVehiculo);

        // 2. VALIDACIÓN DE CAPACIDAD
        if (totalPesoKg > capacidadVehiculo) {
            System.err.println("❌ ERROR: La carga total (" + totalPesoKg + " Kg) excede la capacidad del vehículo (" + capacidadVehiculo + " Kg).");
            return; // Bloquea la creación
        }

        // 3. Si la validación pasa, procede a crear la ruta (Llama al Service/DAO de ruta)
        // rutaService.crearRuta(idVehiculo, idConductor, totalPesoKg, ...);
        System.out.println("✅ Validación de capacidad exitosa. Ruta lista para ser creada.");

    } catch (Exception e) {
        System.err.println("Error al validar o crear ruta: " + e.getMessage());
    }
}
```

-----

### 2\. Ejercicio de Extensión 7: Reporte de Flota Crítica por Mantenimiento (SQL y Reporte)

Este ejercicio requiere una modificación en la tabla y una consulta compleja en el DAO.

#### A. Modificación SQL (DDL)

Debes ejecutar esta sentencia en tu base de datos:

```sql
-- Ejecutar este SQL para agregar la columna 'costo' a mantenimientos
ALTER TABLE mantenimientos
ADD COLUMN costo DOUBLE NOT NULL DEFAULT 0.0;
```

#### B. `VehiculoDAO.java` (Reporte de Flota Crítica)

Implementa la consulta con `JOIN`, `GROUP BY` y `HAVING`.

```java
// Archivo: dao/VehiculoDAO.java

// Define una clase simple para el reporte (puedes anidarla o crear una nueva)
public class ReporteGasto {
    public String placa;
    public String marca;
    public double gastoTotal;
    
    public ReporteGasto(String p, String m, double g) {
        this.placa = p; this.marca = m; this.gastoTotal = g;
    }
    @Override
    public String toString() {
        return "Placa: " + placa + ", Marca: " + marca + ", Gasto Total: $" + String.format("%.2f", gastoTotal);
    }
}

public List<ReporteGasto> obtenerFlotaCritica(double umbralGasto) throws Exception {
    List<ReporteGasto> criticos = new ArrayList<>();
    
    String sql = "SELECT v.placa, v.marca, SUM(m.costo) AS gasto_total " +
                 "FROM vehiculos v " +
                 "JOIN mantenimientos m ON v.id_vehiculo = m.id_vehiculo " +
                 "GROUP BY v.id_vehiculo, v.placa, v.marca " +
                 "HAVING gasto_total > ?";

    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setDouble(1, umbralGasto);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                criticos.add(new ReporteGasto(
                    rs.getString("placa"),
                    rs.getString("marca"),
                    rs.getDouble("gasto_total")
                ));
            }
        }
    }
    return criticos;
}
```

#### C. `MenuAdministradorCLI.java` (Nueva Opción de Reporte)

Añade el método de presentación en la consola.

```java
// Archivo: view/MenuAdministradorCLI.java

private void menuReportes() {
    // ... Agregar opción 2 en el menú de Reportes
}

private void generarReporteFlotaCritica() {
    double umbral = 5000000.0; // Umbral de $5,000,000
    try {
        // Asumiendo que VehiculoController tiene un método para llamar al DAO
        List<VehiculoDAO.ReporteGasto> flotaCritica = vehiculoController.obtenerFlotaCritica(umbral);

        System.out.println("\n--- 🚨 REPORTE DE FLOTA CRÍTICA (Gasto > $" + umbral + ") ---");
        if (flotaCritica.isEmpty()) {
            System.out.println("No se encontraron vehículos que superen el umbral de gasto.");
            return;
        }

        for (VehiculoDAO.ReporteGasto reporte : flotaCritica) {
            System.out.println(reporte);
        }
        System.out.println("---------------------------------------------------------");

    } catch (Exception e) {
        System.err.println("Error al generar el reporte: " + e.getMessage());
    }
}
```

-----

### 3\. Ejercicio de Extensión 8: Bloqueo por Inactividad de Conductor (Lógica de Fechas Masiva)

Este ejercicio requiere una modificación DDL para el Ejercicio 3 (licencia) y lógica SQL/Java para la inactividad.

#### A. Modificación SQL (DDL)

Si no lo hiciste en el Ejercicio 3, añade la columna de licencia:

```sql
-- Ejecutar este SQL para el Ejercicio 3
ALTER TABLE conductores
ADD COLUMN fecha_vencimiento_licencia DATE;
```

#### B. `ConductorDAO.java` (Consultas de Fecha y Actualización)

Necesitas dos métodos: uno para consultar la fecha y otro para actualizar el estado.

```java
// Archivo: dao/ConductorDAO.java
import java.sql.Date;
import java.time.LocalDate;

// 1. Obtener la última ruta de cada conductor (Mapa: ID_Conductor -> Fecha_Última_Ruta)
public Map<Integer, LocalDate> obtenerUltimaFechaRuta() throws Exception {
    Map<Integer, LocalDate> ultimasRutas = new HashMap<>();
    
    // Consulta para obtener la fecha máxima de ruta por conductor
    String sql = "SELECT id_conductor, MAX(fecha) AS ultima_ruta FROM rutas GROUP BY id_conductor";
    
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            int id = rs.getInt("id_conductor");
            Date sqlDate = rs.getDate("ultima_ruta");
            
            // Si tiene rutas, mapea la fecha; si no tiene rutas (por LEFT JOIN), puede ser null
            LocalDate ultimaRuta = (sqlDate != null) ? sqlDate.toLocalDate() : null; 
            ultimasRutas.put(id, ultimaRuta);
        }
    }
    return ultimasRutas;
}

// 2. Método para actualizar el estado del conductor
public void actualizarEstado(int idConductor, String nuevoEstado) throws Exception {
    String sql = "UPDATE conductores SET estado = ? WHERE id_conductor = ?";
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, nuevoEstado);
        ps.setInt(2, idConductor);
        ps.executeUpdate();
    }
}
```

#### C. `ConductorController.java` (Lógica de 90 días)

```java
// Archivo: controller/ConductorController.java

public void revisarInactividadYActualizar(String usuarioActual) {
    final int DIAS_INACTIVIDAD = 90;
    LocalDate fechaLimite = LocalDate.now().minusDays(DIAS_INACTIVIDAD);
    int conductoresActualizados = 0;
    
    try {
        // Necesitas un método en ConductorDAO para listar todos los conductores activos
        List<Conductor> todosLosConductores = conductorDAO.listarTodosLosActivos(); 
        Map<Integer, LocalDate> ultimasRutas = conductorDAO.obtenerUltimaFechaRuta();

        for (Conductor conductor : todosLosConductores) {
            LocalDate ultimaRuta = ultimasRutas.get(conductor.getIdConductor());
            
            // Si el conductor nunca ha tenido ruta, o si la última ruta es anterior al límite de 90 días
            if (ultimaRuta == null || ultimaRuta.isBefore(fechaLimite)) {
                
                // Actualizar estado a INACTIVO
                conductorDAO.actualizarEstado(conductor.getIdConductor(), "INACTIVO");
                conductoresActualizados++;
                
                // Registrar en Auditoría (Opcional, pero buena práctica)
                auditoriaController.registrarAccion(usuarioActual, "CAMBIO_ESTADO_CONDUCTOR", 
                    "Conductor ID " + conductor.getIdConductor() + " marcado INACTIVO por antigüedad.");
            }
        }

        System.out.println("✅ REVISIÓN COMPLETADA: " + conductoresActualizados + " conductores marcados como INACTIVOS por inactividad.");

    } catch (Exception e) {
        System.err.println("Error durante la revisión de inactividad: " + e.getMessage());
    }
}
```

-----

-----

## 🚀 Ejercicio de Extensión 9: Múltiples Puntos de Parada (Nueva Tabla y DAO)

Este ejercicio requiere crear una nueva tabla DDL y modificar la lógica de creación de rutas para manejar una colección de datos relacionados.

### A. 📝 Modificación SQL (DDL)

Necesitas crear la nueva tabla `puntos_parada` y agregar la llave foránea a `rutas`:

```sql
-- Archivo: 1_schema_ddl.sql (Añadir al final)

CREATE TABLE puntos_parada (
    id_parada INT AUTO_INCREMENT PRIMARY KEY,
    id_ruta INT NOT NULL,
    secuencia INT NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    fecha_hora_estimada DATETIME,
    FOREIGN KEY (id_ruta) REFERENCES rutas(id_ruta)
);
```

### B. 💻 Nueva Entidad `PuntoParada.java`

Crea la clase modelo correspondiente para manejar los datos en Java.

```java
// Archivo: model/PuntoParada.java
import java.time.LocalDateTime;

public class PuntoParada {
    private int idParada;
    private int idRuta;
    private int secuencia;
    private String ciudad;
    private LocalDateTime fechaHoraEstimada;

    // Constructor sin idParada para la inserción
    public PuntoParada(int idRuta, int secuencia, String ciudad, LocalDateTime fechaHoraEstimada) {
        this.idRuta = idRuta;
        this.secuencia = secuencia;
        this.ciudad = ciudad;
        this.fechaHoraEstimada = fechaHoraEstimada;
    }

    // Getters y Setters
    // ...
}
```

### C. 🛠️ `RutaDAO.java` (Inserción Masiva)

Implementa la inserción de múltiples puntos de parada, que se llamará inmediatamente después de insertar la ruta principal.

```java
// Archivo: dao/RutaDAO.java
// Asegúrate de importar java.sql.Timestamp

public void insertarPuntosParada(int idRuta, List<PuntoParada> puntos) throws Exception {
    String sql = "INSERT INTO puntos_parada (id_ruta, secuencia, ciudad, fecha_hora_estimada) VALUES (?, ?, ?, ?)";
    
    // Usamos el mismo patrón de conexión
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        for (PuntoParada p : puntos) {
            ps.setInt(1, idRuta);
            ps.setInt(2, p.getSecuencia());
            ps.setString(3, p.getCiudad());
            ps.setTimestamp(4, Timestamp.valueOf(p.getFechaHoraEstimada()));
            
            ps.addBatch(); // Añade la sentencia al lote
        }
        
        ps.executeBatch(); // Ejecuta todas las inserciones a la vez
        
    }
}
```

-----

-----

## 🎯 Ejercicio de Extensión 10: Exportación de Auditoría a CSV (Manejo de Archivos)

Este ejercicio combina la lectura de la base de datos con la escritura de archivos.

### A. 🛠️ `AuditoriaDAO.java` (Listar Todos)

Necesitas un método para obtener *todos* los registros del historial, usando tu entidad `Auditoria`.

```java
// Archivo: dao/AuditoriaDAO.java
// Asume que tienes una entidad 'Auditoria' con los campos de la tabla

public List<Auditoria> listarTodos() throws Exception {
    List<Auditoria> auditorias = new ArrayList<>();
    String sql = "SELECT id_auditoria, usuario, accion, detalles, fecha_hora FROM auditoria ORDER BY fecha_hora DESC";
    
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            // Asume que tienes un método de mapeo (mapearAuditoria) o constructor
            auditorias.add(new Auditoria(
                rs.getInt("id_auditoria"),
                rs.getString("usuario"),
                rs.getString("accion"),
                rs.getString("detalles"),
                rs.getTimestamp("fecha_hora").toLocalDateTime()
            ));
        }
    }
    return auditorias;
}
```

### B. 💻 `AuditoriaController.java` (Lógica de Exportación)

Este método maneja la lógica de archivo (`FileWriter`).

```java
// Archivo: controller/AuditoriaController.java
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AuditoriaController {
    
    // Asegúrate de inyectar/inicializar el AuditoriaDAO
    private AuditoriaDAO auditoriaDAO; 

    public void exportarAuditoriaCSV() {
        final String NOMBRE_ARCHIVO = "auditoria_exportada.csv";
        
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO)) {
            
            // 1. Escribir Encabezado CSV
            fw.append("ID,Usuario,Accion,Detalles,Fecha_Hora\n"); 
            
            // 2. Obtener datos de la BD
            List<Auditoria> registros = auditoriaDAO.listarTodos();
            
            // 3. Escribir datos, línea por línea, separados por coma
            for (Auditoria a : registros) {
                // Prepara la línea con el formato CSV
                String linea = a.getIdAuditoria() + "," +
                               a.getUsuario() + "," +
                               a.getAccion() + "," +
                               // Asegúrate de limpiar detalles si contienen comas, si no, usa replace
                               a.getDetalles().replace(",", ";") + "," + 
                               a.getFechaHora().toString() + "\n";
                
                fw.append(linea);
            }
            
            System.out.println("\n✅ Exportación exitosa! Archivo guardado como: " + NOMBRE_ARCHIVO);
            
        } catch (IOException e) {
            System.err.println("❌ Error de archivo al exportar: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error de base de datos al exportar: " + e.getMessage());
        }
    }
}
```