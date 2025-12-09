# Conceptos de Manejo de Archivos en Java  
Guía completa sobre lectura, escritura, manipulación de texto plano y manejo de objetos serializables.

---

## 1. Tipos de Archivos Comunes

### **Archivos de texto plano**
Extensiones típicas:
- `.txt`
- `.md`
- `.csv`
- `.json`

Características:
- Se leen como texto.
- Contenido visible para humanos.
- Ideales para apuntes, conceptos, documentación, etc.

### **Archivos binarios**
Extensiones:
- `.dat`
- `.bin`
- `.obj`

Características:
- No son legibles por humanos.
- Guardan datos en formato compacto.
- Se usan para guardar **objetos serializados**.

---

# 2. Clases para manejar rutas y archivos

### **Files**
La clase `Files` permite:
- Crear archivos
- Leer archivos
- Escribir archivos
- Copiar y mover archivos
- Borrar archivos

Ejemplos de métodos:

```java
Files.writeString(ruta, "Texto");
Files.readString(ruta);
Files.exists(ruta);
Files.createFile(ruta);
```

### **Paths**
Representa una ruta en el sistema.

```java
Path ruta = Paths.get("archivoJava.txt");
```

Métodos útiles:
- `getFileName()`
- `getParent()`
- `toAbsolutePath()`

Ejemplo:

```java
System.out.println(ruta.toAbsolutePath());
```

---

# 3. Escritura de archivos de texto

## 3.1. Usando FileWriter + BufferedWriter
Permite escribir texto línea por línea.

```java
try (FileWriter fw = new FileWriter("archivoJava.txt", true);
     BufferedWriter bw = new BufferedWriter(fw);
     PrintWriter pw = new PrintWriter(bw)) {

    pw.println("Linea de ejemplo usando PrintWriter");
    pw.append("Texto agregado con append()");
}
catch (IOException e) {
    e.printStackTrace();
}
```

### Conceptos:
- `println()` → escribe y hace salto de línea
- `print()` → escribe sin salto de línea
- `append()` → agrega texto al final
- El `true` en `FileWriter` es para **modo append**

---

# 4. Lectura de archivos de texto

## 4.1. Usando FileReader + BufferedReader
```java
try (FileReader fr = new FileReader("archivoJava.txt");
     BufferedReader br = new BufferedReader(fr)) {

    String linea;
    while ((linea = br.readLine()) != null) {
        System.out.println(linea);
    }

} catch (IOException e) {
    e.printStackTrace();
}
```

### Conceptos:
- `readLine()` lee una línea completa
- Devuelve `null` cuando llega al final del archivo

---

# 5. Uso de Files para leer y escribir más fácil

## Escribir:
```java
Path ruta = Paths.get("archivoJava.txt");
Files.writeString(ruta, "Texto escrito con Files\n");
```

## Leer:
```java
String contenido = Files.readString(ruta);
System.out.println(contenido);
```

---

# 6. Serialización de Objetos

## ¿Qué es serializar?
Es el proceso de **convertir un objeto en bytes** para guardarlo en un archivo.

## ¿Para qué sirve?
- Guardar objetos completos (estados, datos).
- Enviar objetos por red.
- Guardar configuraciones estructuradas.
- Persistencia sin base de datos.

## ¿Qué archivo se usa?
Generalmente:
- `.dat`
- `.bin`
- `.obj`

Ejemplo: `objetos.dat`

---

# 7. Interfaz Serializable

Una clase debe implementar `Serializable` para poder guardarse en archivo.

```java
import java.io.Serializable;

public class Persona implements Serializable {
    private String nombre;
    private int edad;

    public Persona(String n, int e) {
        this.nombre = n;
        this.edad = e;
    }
}
```

---

# 8. Guardar (serializar) objetos en un archivo

```java
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("objetos.dat"))) {

    Persona p = new Persona("Freddy", 22);
    oos.writeObject(p);

    System.out.println("Objeto guardado exitosamente");

} catch (IOException e) {
    e.printStackTrace();
}
```

### Conceptos:
- `ObjectOutputStream` → escribe objetos
- `writeObject(obj)` → serializa y escribe
- El archivo es **binario**, no se puede leer como texto

---

# 9. Leer objetos desde archivo (deserialización)

```java
try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("objetos.dat"))) {

    Persona p = (Persona) ois.readObject();
    System.out.println("Objeto leído: " + p);

} catch (IOException | ClassNotFoundException e) {
    e.printStackTrace();
}
```

### Conceptos:
- `ObjectInputStream` → lee objetos
- `readObject()` → devuelve `Object`, hay que castear
- Se necesita que la clase exista y sea Serializable

---

# 10. Salida esperada en consola (para los ejemplos de texto)

```
Linea de ejemplo usando PrintWriter
Texto agregado con append()
```

Y si lees el archivo:

```
Linea de ejemplo usando PrintWriter
Texto agregado con append()
```

Para la serialización:

```
Objeto guardado exitosamente
Objeto leído: Persona{nombre='Freddy', edad=22}
```

---

# 11. ¿Cuándo usar cada tipo de archivo?

## Archivos de Texto (.txt, .md)
- Apuntes
- Logs
- Documentación
- Configuraciones simples

## Archivos JSON / CSV
- Datos estructurados
- Listas, configuraciones
- Intercambio con otros sistemas

## Archivos binarios (.dat)
- Guardar objetos completos
- Estados de un programa
- Datos sensibles no legibles

---

# 12. Resumen General

- `FileWriter`, `BufferedWriter`, `PrintWriter` → escribir texto
- `FileReader`, `BufferedReader` → leer texto
- `Files` → lectura/escritura fácil
- `Serializable` → guardar objetos
- `ObjectOutputStream` → escribir objetos
- `ObjectInputStream` → leer objetos
- `.txt` = texto humano
- `.dat` = datos binarios (objetos)

---

# Fin del documento
