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
## 💻 Conceptos Clave del Stream API

| Concepto | Descripción |
| :--- | :--- |
| **Stream (Flujo)** | Una secuencia de elementos que soporta operaciones secuenciales y paralelas. No almacena datos; simplemente define las operaciones a realizar en el origen de datos. |
| **Fuente de Datos (Source)** | La colección (como `List`, `Set`), array, o generador que alimenta el Stream. |
| **Operación Intermedia (Intermediate Operation)** | Transforma un Stream en otro Stream. Son **perezosas** (lazy), lo que significa que no se ejecutan hasta que se llama a una operación terminal. Ejemplos: `filter()`, `map()`, `sorted()`. |
| **Operación Terminal (Terminal Operation)** | Produce un resultado o un efecto secundario. Consume el Stream y finaliza la secuencia de operaciones. Una vez que se llama a una operación terminal, el Stream ya no se puede utilizar. Ejemplos: `forEach()`, `collect()`, `reduce()`, `count()`. |
| **Programación Funcional y Lambdas** | El Stream API se basa en el estilo de programación funcional, utilizando **expresiones Lambda** para definir el comportamiento de las operaciones intermedias y terminales. |

-----

## 🛠️ Métodos Más Utilizados y Ejemplos con Lambdas

A continuación, se presentan los métodos del Stream API más comunes, con un ejemplo en Java y su simulación de salida en la consola, todos utilizando **funciones lambda**.

### 1\. `filter()` (Operación Intermedia)

  * **¿Para qué sirve?** Selecciona elementos del Stream que coinciden con una **condición** específica (un `Predicate`).
  * **Lambda:** Recibe un elemento, retorna un `boolean`.

<!-- end list -->

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterExample {
    public static void main(String[] args) {
        List<String> frutas = Arrays.asList("manzana", "banana", "cereza", "dátil", "mango");

        // Filtrar frutas que comienzan con la letra 'm'
        List<String> frutasM = frutas.stream()
            .filter(fruta -> fruta.startsWith("m")) // <--- Función Lambda
            .collect(Collectors.toList());

        System.out.println("Frutas originales: " + frutas);
        System.out.println("Frutas que inician con 'm': " + frutasM);
    }
}
```

#### 🖥️ Ejecución en Consola

```
Frutas originales: [manzana, banana, cereza, dátil, mango]
Frutas que inician con 'm': [manzana, mango]
```

-----

### 2\. `map()` (Operación Intermedia)

  * **¿Para qué sirve?** Aplica una **función a cada elemento** para transformarlo en un nuevo elemento. El resultado es un Stream de los elementos transformados.
  * **Lambda:** Recibe un elemento, retorna el elemento transformado de cualquier tipo (un `Function`).

<!-- end list -->

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapExample {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

        // Duplicar cada número y obtener una nueva lista
        List<Integer> numerosDuplicados = numeros.stream()
            .map(n -> n * 2) // <--- Función Lambda
            .collect(Collectors.toList());

        System.out.println("Números originales: " + numeros);
        System.out.println("Números duplicados: " + numerosDuplicados);
    }
}
```

#### 🖥️ Ejecución en Consola

```
Números originales: [1, 2, 3, 4, 5]
Números duplicados: [2, 4, 6, 8, 10]
```

-----

### 3\. `forEach()` (Operación Terminal)

  * **¿Para qué sirve?** Itera sobre cada elemento del Stream y realiza una **acción** (un `Consumer`), como imprimir.
  * **Lambda:** Recibe un elemento, no retorna nada (realiza una acción). Es una de las operaciones terminales más sencillas.

<!-- end list -->

```java
import java.util.Arrays;
import java.util.List;

public class ForEachExample {
    public static void main(String[] args) {
        List<String> nombres = Arrays.asList("Ana", "Bernardo", "Carlos");

        System.out.println("Imprimiendo nombres:");
        nombres.stream()
            .forEach(nombre -> System.out.println("Hola, " + nombre)); // <--- Función Lambda
    }
}
```

#### 🖥️ Ejecución en Consola

```
Imprimiendo nombres:
Hola, Ana
Hola, Bernardo
Hola, Carlos
```

-----

### 4\. `reduce()` (Operación Terminal)

  * **¿Para qué sirve?** Combina todos los elementos del Stream en un **único resultado**, aplicando repetidamente una función de combinación (un `BinaryOperator`). Útil para sumas, productos, o encontrar el máximo/mínimo.
  * **Lambda:** Recibe dos elementos del mismo tipo, retorna un elemento del mismo tipo (la combinación de los dos).

<!-- end list -->

```java
import java.util.Arrays;
import java.util.List;

public class ReduceExample {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

        // Sumar todos los números
        // El '0' es el valor de identidad (valor inicial)
        int suma = numeros.stream()
            .reduce(0, (acumulador, elemento) -> acumulador + elemento); // <--- Función Lambda

        System.out.println("Números originales: " + numeros);
        System.out.println("Suma total: " + suma);
    }
}
```

#### 🖥️ Ejecución en Consola

```
Números originales: [1, 2, 3, 4, 5]
Suma total: 15
```

-----

### 5\. `sorted()` (Operación Intermedia)

  * **¿Para qué sirve?** Ordena los elementos del Stream. Puede usar el orden natural de los elementos o un `Comparator` personalizado.
  * **Lambda:** Si se le pasa un `Comparator`, recibe dos elementos, retorna un `int` (comparación).

<!-- end list -->

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortedExample {
    public static void main(String[] args) {
        List<String> palabras = Arrays.asList("Java", "Stream", "API", "Lambda", "Funcional");

        // Ordenar por longitud de la palabra
        List<String> ordenadoPorLongitud = palabras.stream()
            // Comparar (s1, s2) basado en la longitud de las cadenas
            .sorted((s1, s2) -> Integer.compare(s1.length(), s2.length())) // <--- Función Lambda
            .collect(Collectors.toList());

        System.out.println("Palabras originales: " + palabras);
        System.out.println("Ordenado por longitud: " + ordenadoPorLongitud);
    }
}
```

#### 🖥️ Ejecución en Consola

```
Palabras originales: [Java, Stream, API, Lambda, Funcional]
Ordenado por longitud: [API, Java, Stream, Lambda, Funcional]
```

-----

## 🔗 Encadenamiento de Operaciones (Pipeline)

El poder del Stream API reside en la capacidad de **encadenar** varias operaciones intermedias seguidas de una terminal, creando un *pipeline* de procesamiento de datos.

  * **Escenario:** Filtrar números pares, duplicarlos y luego sumarlos.

<!-- end list -->

```java
import java.util.Arrays;
import java.util.List;

public class PipelineExample {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int resultado = numeros.stream()
            .filter(n -> n % 2 == 0) // 1. Intermedia: Quedan [2, 4, 6, 8, 10]
            .map(n -> n * 2)         // 2. Intermedia: Se transforman a [4, 8, 12, 16, 20]
            .reduce(0, (a, b) -> a + b); // 3. Terminal: Sumar todos

        System.out.println("Números originales: " + numeros);
        System.out.println("Resultado (pares duplicados y sumados): " + resultado);
    }
}
```

#### 🖥️ Ejecución en Consola

```
Números originales: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
Resultado (pares duplicados y sumados): 60
```

-----

## 🎯 Resumen de Tipos Funcionales (Lambdas)

Las expresiones lambda en el Stream API se utilizan para implementar las interfaces funcionales básicas de Java, que son:

| Interfaz Funcional | Lambda de Ejemplo | Descripción en Stream | Uso Típico |
| :--- | :--- | :--- | :--- |
| **`Predicate<T>`** | `t -> t.esVerdadero()` | Recibe $T$, retorna `boolean`. | Usado en **`filter()`**. |
| **`Function<T, R>`** | `t -> t.transformacion()` | Recibe $T$, retorna $R$. | Usado en **`map()`**. |
| **`Consumer<T>`** | `t -> System.out.println(t)` | Recibe $T$, retorna `void`. | Usado en **`forEach()`**. |
| **`BinaryOperator<T>`** | `(t1, t2) -> t1 + t2` | Recibe dos $T$, retorna $T$. | Usado en **`reduce()`**. |

## 🌟 Funciones Avanzadas del Stream API

-----

### 6\. `flatMap()` (Operación Intermedia)

  * **¿Para qué sirve?** Es similar a `map()`, pero se utiliza cuando la función de mapeo (la Lambda) retorna un **Stream** en lugar de un elemento. `flatMap()` "aplana" estos Streams resultantes en un **único Stream** unificado. Es crucial para trabajar con colecciones anidadas (una lista de listas).
  * **Lambda:** Recibe un elemento $T$, retorna un `Stream<R>`.

<!-- end list -->

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMapExample {
    public static void main(String[] args) {
        // Lista de listas (una lista de "equipos", donde cada equipo es una lista de "jugadores")
        List<List<String>> equipos = Arrays.asList(
            Arrays.asList("Juan", "Pedro"),
            Arrays.asList("Ana", "Maria", "Elena")
        );

        // Obtener una lista plana con todos los jugadores
        List<String> todosLosJugadores = equipos.stream()
            // Transformar cada lista (equipo) en un Stream de jugadores y aplanarlos
            .flatMap(equipo -> equipo.stream()) // <--- Función Lambda
            .collect(Collectors.toList());

        System.out.println("Equipos anidados: " + equipos);
        System.out.println("Lista de todos los jugadores (plana): " + todosLosJugadores);
    }
}
```

#### 🖥️ Ejecución en Consola

```
Equipos anidados: [[Juan, Pedro], [Ana, Maria, Elena]]
Lista de todos los jugadores (plana): [Juan, Pedro, Ana, Maria, Elena]
```

-----

### 7\. `collect(Collectors.groupingBy(...))` (Operación Terminal)

  * **¿Para qué sirve?** Es una de las formas más poderosas de la operación terminal `collect()`. Permite **agrupar** elementos del Stream basándose en una clave. El resultado es un `Map<K, List<V>>`.
  * **Lambda:** Se utiliza un `Function` dentro de `groupingBy` para definir la **clave de agrupación**. Recibe $T$, retorna $K$ (la clave).

<!-- end list -->

```java
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupingByExample {
    
    // Clase simple para demostrar el ejemplo
    static class Persona {
        String nombre;
        String ciudad;

        public Persona(String nombre, String ciudad) {
            this.nombre = nombre;
            this.ciudad = ciudad;
        }

        public String getCiudad() {
            return ciudad;
        }

        public String toString() {
            return nombre;
        }
    }

    public static void main(String[] args) {
        List<Persona> personas = Arrays.asList(
            new Persona("Luis", "Bogotá"),
            new Persona("Marta", "Medellín"),
            new Persona("Ricardo", "Bogotá"),
            new Persona("Sofía", "Cali")
        );

        // Agrupar las personas por su ciudad de origen
        Map<String, List<Persona>> personasPorCiudad = personas.stream()
            // Usamos una Lambda para indicar la clave de agrupación (la ciudad)
            .collect(Collectors.groupingBy(Persona::getCiudad)); // <--- Método Referencia (similar a p -> p.getCiudad())

        System.out.println("Lista original de personas: " + personas);
        System.out.println("\nPersonas agrupadas por ciudad:");
        personasPorCiudad.forEach((ciudad, lista) -> 
            System.out.println(ciudad + ": " + lista) // <--- Función Lambda en forEach
        );
    }
}
```

#### 🖥️ Ejecución en Consola

```
Lista original de personas: [Luis, Marta, Ricardo, Sofía]

Personas agrupadas por ciudad:
Medellín: [Marta]
Bogotá: [Luis, Ricardo]
Cali: [Sofía]
```

-----

## 🏗️ Creando Streams (Fuentes Comunes)

Los Streams no solo provienen de colecciones (`list.stream()`). También se pueden crear a partir de:

### 8\. Streams a partir de Arrays

  * **Método:** `Arrays.stream(array)`

<!-- end list -->

```java
import java.util.Arrays;
import java.util.stream.IntStream;

public class ArrayStreamExample {
    public static void main(String[] args) {
        int[] numeros = {10, 20, 30, 40};

        // Crear un IntStream (un Stream especializado para int)
        IntStream.of(numeros)
             .forEach(n -> System.out.println("Elemento: " + n)); // <--- Función Lambda
    }
}
```

#### 🖥️ Ejecución en Consola

```
Elemento: 10
Elemento: 20
Elemento: 30
Elemento: 40
```

### 9\. Streams de Rangos (Numéricos)

  * **Método:** `IntStream.range(inicio, fin)` (exclusivo) o `IntStream.rangeClosed(inicio, fin)` (inclusivo)

<!-- end list -->

```java
import java.util.stream.IntStream;

public class RangeStreamExample {
    public static void main(String[] args) {
        // Generar números del 1 al 5 (inclusive)
        IntStream.rangeClosed(1, 5)
            .filter(n -> n % 2 != 0) // Filtrar impares: [1, 3, 5]
            .forEach(n -> System.out.print(n + " ")); // <--- Función Lambda
            
        System.out.println();
    }
}
```

#### 🖥️ Ejecución en Consola

```
1 3 5 
```

-----

## 💡 Conceptos Adicionales de Lambda y Stream

### Métodos de Referencia (`::`)

Aunque ya hemos usado Lambdas, el Stream API a menudo utiliza **métodos de referencia**, que son una forma **abreviada** de la expresión Lambda cuando la Lambda solo llama a un método existente.

  * **Lambda:** `s -> s.length()`
  * **Método de Referencia:** `String::length`

**Tipos:**

1.  **Estático:** `Math::random` (equivale a `() -> Math.random()`)
2.  **De Instancia de un objeto particular:** `System.out::println` (equivale a `s -> System.out.println(s)`)
3.  **De Instancia de un tipo arbitrario:** `String::length` (equivale a `s -> s.length()`)

<!-- end list -->

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodReferenceExample {
    public static void main(String[] args) {
        List<String> nombres = Arrays.asList("diego", "ana", "carlos");

        // 1. Lambda para capitalizar
        List<String> capitalizadosLambda = nombres.stream()
            .map(s -> s.toUpperCase()) 
            .collect(Collectors.toList());

        // 2. Método de Referencia para capitalizar (abreviado)
        List<String> capitalizadosRef = nombres.stream()
            .map(String::toUpperCase) // <--- Método de Referencia
            .collect(Collectors.toList());

        System.out.println("Con Lambda: " + capitalizadosLambda);
        System.out.println("Con Referencia: " + capitalizadosRef);
    }
}
```

#### 🖥️ Ejecución en Consola

```
Con Lambda: [DIEGO, ANA, CARLOS]
Con Referencia: [DIEGO, ANA, CARLOS]
```

-----

### ¿Cómo sé cuándo usar qué Operación?

| Objetivo | Operación | Tipo | Retorno |
| :--- | :--- | :--- | :--- |
| **Limitar/Seleccionar** elementos por una regla. | `filter()` | Intermedia | `Stream` |
| **Transformar** cada elemento a un nuevo elemento. | `map()` | Intermedia | `Stream` |
| **Aplanar** colecciones anidadas. | `flatMap()` | Intermedia | `Stream` |
| **Ordenar** los elementos. | `sorted()` | Intermedia | `Stream` |
| **Producir una única colección (List/Set/Map).** | `collect()` | Terminal | `Collection` |
| **Agrupar** elementos en un `Map`. | `collect(groupingBy)` | Terminal | `Map` |
| **Combinar** todos los elementos en un valor final. | `reduce()` | Terminal | `Optional` o Valor |
| **Ejecutar una acción** en cada elemento. | `forEach()` | Terminal | `void` |

El Stream API ofrece un enfoque **declarativo** poderoso. En lugar de decir *cómo* iterar (como con un bucle `for` tradicional), se declara **qué** se quiere lograr.



## 🏗️ 1. Patrones Creacionales (Creación de Objetos)

Estos patrones manejan la instanciación de objetos de la mejor manera para una situación dada.

### A. Factory Method (Método de Fábrica) 🏭

  * **Propósito:** Define una interfaz para crear un objeto, pero deja que las subclases decidan qué clase instanciar. Esto delega la responsabilidad de la instanciación a las clases hijas.
  * **¿Cuándo usarlo?** Cuando una clase no puede anticipar la clase de objetos que debe crear, o cuando se quiere aislar el código que crea objetos de la lógica de negocio.

#### 🛠️ Ejemplo: Creación de Documentos

Imaginemos una aplicación que necesita crear diferentes tipos de documentos (PDF, Word, etc.).

```java
// Interfaz Producto (Documento)
interface Documento {
    void abrir();
}

// Productos Concretos
class DocumentoWord implements Documento {
    @Override
    public void abrir() {
        System.out.println("Documento Word abierto.");
    }
}

class DocumentoPDF implements Documento {
    @Override
    public void abrir() {
        System.out.println("Documento PDF abierto.");
    }
}

// Clase Creadora Abstracta (Fábrica)
abstract class CreadorDocumento {
    // Factory Method (Método de Fábrica)
    public abstract Documento crear(); 
    
    // Lógica principal que usa el Factory Method
    public void iniciarApp() {
        Documento doc = crear(); // Llama al método abstracto
        doc.abrir();
    }
}

// Creadores Concretos (Deciden qué implementar)
class CreadorWord extends CreadorDocumento {
    @Override
    public Documento crear() {
        return new DocumentoWord(); // Decisión de instanciación
    }
}

class CreadorPDF extends CreadorDocumento {
    @Override
    public Documento crear() {
        return new DocumentoPDF(); // Decisión de instanciación
    }
}

public class FactoryMethodExample {
    public static void main(String[] args) {
        // Uso del Cliente: Trabaja con el Creador, no con el Producto concreto.
        CreadorDocumento creador1 = new CreadorWord();
        System.out.print("Creando Word: ");
        creador1.iniciarApp(); // El cliente no sabe que crea un Word.

        CreadorDocumento creador2 = new CreadorPDF();
        System.out.print("Creando PDF: ");
        creador2.iniciarApp(); // El cliente no sabe que crea un PDF.
    }
}
```

#### 🖥️ Ejecución en Consola

```
Creando Word: Documento Word abierto.
Creando PDF: Documento PDF abierto.
```

-----

### B. Singleton (Instancia Única) 👤

  * **Propósito:** Asegura que una clase tenga **una sola instancia** y proporciona un punto de acceso global a ella.
  * **¿Cuándo usarlo?** Para recursos compartidos o globales, como una configuración de aplicación, un gestor de bases de datos o un registro (logger).

#### 🛠️ Ejemplo: Configuración de la Aplicación

```java
public class Configuracion {
    // 1. Instancia estática privada de la misma clase.
    private static Configuracion instancia; 
    
    private String version;

    // 2. Constructor privado para evitar instanciación externa.
    private Configuracion() {
        this.version = "1.0.0";
        System.out.println("Configuración inicializada (una sola vez).");
    }

    // 3. Método estático público para obtener la única instancia.
    public static Configuracion obtenerInstancia() {
        if (instancia == null) {
            // Inicialización perezosa (solo se crea si es nula)
            instancia = new Configuracion(); 
        }
        return instancia;
    }

    public String obtenerVersion() {
        return this.version;
    }
}

public class SingletonExample {
    public static void main(String[] args) {
        // Obtenemos la primera instancia
        Configuracion config1 = Configuracion.obtenerInstancia();
        System.out.println("Instancia 1, versión: " + config1.obtenerVersion());

        // Intentamos obtener una segunda instancia
        Configuracion config2 = Configuracion.obtenerInstancia();
        System.out.println("Instancia 2, versión: " + config2.obtenerVersion());

        // Comprobamos si son el mismo objeto en memoria
        System.out.println("¿Son la misma instancia? " + (config1 == config2));
    }
}
```

#### 🖥️ Ejecución en Consola

```
Configuración inicializada (una sola vez).
Instancia 1, versión: 1.0.0
Instancia 2, versión: 1.0.0
¿Son la misma instancia? true
```

-----

## 🧱 2. Patrones Estructurales (Composición de Clases)

Estos patrones se centran en cómo las clases y los objetos se componen para formar estructuras más grandes y flexibles.

### C. Adapter (Adaptador) 🔌

  * **Propósito:** Convierte la interfaz de una clase en otra interfaz que el cliente espera. Permite que clases con interfaces incompatibles trabajen juntas.
  * **¿Cuándo usarlo?** Cuando se necesita utilizar una clase existente (el "adaptee") cuya interfaz no coincide con el resto del código (el "target"). Es común al integrar librerías de terceros.

#### 🛠️ Ejemplo: Adaptar Enchufes (Clásico)

Queremos usar un enchufe europeo (cliente) con una fuente de alimentación americana (adaptee).

```java
// Interfaz Objetivo (Target) que el cliente espera
interface EnchufeEuropeo {
    void conectarEuro();
}

// Clase Existente (Adaptee) que necesitamos usar
class EnchufeAmericano {
    public void conectarUSA() {
        System.out.println("Conectado a toma americana (110V).");
    }
}

// Clase Adaptador
class AdaptadorUSAaEURO implements EnchufeEuropeo {
    private EnchufeAmericano americano;

    // Constructor que recibe el Adaptee
    public AdaptadorUSAaEURO(EnchufeAmericano americano) {
        this.americano = americano;
    }

    // El método requerido por el Target llama al método del Adaptee
    @Override
    public void conectarEuro() {
        System.out.print("Adaptando... ");
        americano.conectarUSA(); // Llama al método incompatible
    }
}

public class AdapterExample {
    public static void main(String[] args) {
        // La clase incompatible
        EnchufeAmericano enchufeUSA = new EnchufeAmericano();

        // Creamos el adaptador
        EnchufeEuropeo adaptador = new AdaptadorUSAaEURO(enchufeUSA);

        // El cliente usa la interfaz que espera
        System.out.print("Cliente usando interfaz Europea: ");
        adaptador.conectarEuro(); 
    }
}
```

#### 🖥️ Ejecución en Consola

```
Cliente usando interfaz Europea: Adaptando... Conectado a toma americana (110V).
```

-----

### D. Decorator (Decorador) 🖼️

  * **Propósito:** Permite **adjuntar dinámicamente responsabilidades adicionales** a un objeto. Proporciona una alternativa flexible a la herencia para extender la funcionalidad.
  * **¿Cuándo usarlo?** Cuando se quiere añadir comportamientos a un objeto individual sin afectar otros objetos de la misma clase, o cuando la herencia es impráctica (para evitar una explosión de subclases).

#### 🛠️ Ejemplo: Café con Adicionales

El componente base es el café. Cada adición (leche, azúcar) es un decorador que añade costo y descripción.

```java
// Componente Base e Interfaz de Decoradores
interface Cafe {
    String getDescripcion();
    double getCosto();
}

// Componente Concreto
class CafeSimple implements Cafe {
    @Override
    public String getDescripcion() {
        return "Café Simple";
    }
    @Override
    public double getCosto() {
        return 2.0;
    }
}

// Clase Decorador Abstracta
abstract class AdicionalDecorator implements Cafe {
    protected Cafe cafeDecorado;
    
    public AdicionalDecorator(Cafe cafeDecorado) {
        this.cafeDecorado = cafeDecorado;
    }
    // Estos métodos serán delegados o modificados por los decoradores concretos
    public abstract String getDescripcion();
    public abstract double getCosto();
}

// Decoradores Concretos
class LecheDecorator extends AdicionalDecorator {
    public LecheDecorator(Cafe cafeDecorado) {
        super(cafeDecorado);
    }
    @Override
    public String getDescripcion() {
        return cafeDecorado.getDescripcion() + ", Leche"; // Añade descripción
    }
    @Override
    public double getCosto() {
        return cafeDecorado.getCosto() + 0.5; // Añade costo
    }
}

class AzucarDecorator extends AdicionalDecorator {
    // ... similar a LecheDecorator ...
    public AzucarDecorator(Cafe cafeDecorado) {
        super(cafeDecorado);
    }
    @Override
    public String getDescripcion() {
        return cafeDecorado.getDescripcion() + ", Azúcar";
    }
    @Override
    public double getCosto() {
        return cafeDecorado.getCosto() + 0.2;
    }
}

public class DecoratorExample2 {
    public static void main(String[] args) {
        // Café simple
        Cafe miCafe = new CafeSimple();
        
        // Decoramos con leche
        miCafe = new LecheDecorator(miCafe);
        
        // Decoramos con azúcar (encadenamiento)
        miCafe = new AzucarDecorator(miCafe);
        
        System.out.println("Pedido: " + miCafe.getDescripcion());
        System.out.println("Costo total: $" + miCafe.getCosto());
    }
}
```

#### 🖥️ Ejecución en Consola

```
Pedido: Café Simple, Leche, Azúcar
Costo total: $2.7
```

-----

## 🔄 3. Patrones Comportamentales (Interacción de Objetos)

Estos patrones se enfocan en la comunicación y la asignación de responsabilidades entre objetos.

### E. Observer (Observador) 🔭

  * **Propósito:** Define una dependencia **uno-a-muchos** entre objetos, de forma que cuando el **objeto principal** (el `Sujeto` o `Observable`) cambia de estado, todos sus objetos dependientes (los `Observadores`) son notificados y actualizados automáticamente.
  * **¿Cuándo usarlo?** Para sistemas de notificación, modelos MVC, y cualquier lugar donde un cambio en un lugar deba propagarse automáticamente a muchos otros lugares.

#### 🛠️ Ejemplo: Notificación de Noticias

Una agencia de noticias (Sujeto) notifica a varios suscriptores (Observadores) cuando hay una noticia nueva.

```java
import java.util.ArrayList;
import java.util.List;

// Interfaz Observador
interface Suscriptor {
    void actualizar(String noticia);
}

// Clase Sujeto (Observable)
class AgenciaNoticias {
    private List<Suscriptor> suscriptores = new ArrayList<>();
    private String ultimaNoticia;

    public void agregar(Suscriptor s) {
        suscriptores.add(s);
    }

    public void notificar() {
        for (Suscriptor s : suscriptores) {
            s.actualizar(ultimaNoticia);
        }
    }

    public void publicarNoticia(String noticia) {
        this.ultimaNoticia = noticia;
        System.out.println("\nAGENCIA: Publicando noticia: " + noticia);
        notificar(); // El sujeto notifica a todos
    }
}

// Observador Concreto 1
class ClienteEmail implements Suscriptor {
    @Override
    public void actualizar(String noticia) {
        System.out.println("Email: Nueva noticia recibida: " + noticia);
    }
}

// Observador Concreto 2
class ClienteMovil implements Suscriptor {
    @Override
    public void actualizar(String noticia) {
        System.out.println("Móvil: Notificación Push: " + noticia);
    }
}

public class ObserverExample {
    public static void main(String[] args) {
        AgenciaNoticias agencia = new AgenciaNoticias();

        // Creamos y suscribimos observadores
        agencia.agregar(new ClienteEmail());
        agencia.agregar(new ClienteMovil());
        
        // Un observador extra que no está suscrito
        Suscriptor clienteExtra = new ClienteMovil();

        agencia.publicarNoticia("¡Gran avance tecnológico!");
        
        // El cliente extra se suscribe más tarde
        agencia.agregar(clienteExtra);

        agencia.publicarNoticia("El precio del café se estabiliza.");
    }
}
```

#### 🖥️ Ejecución en Consola

```
AGENCIA: Publicando noticia: ¡Gran avance tecnológico!
Email: Nueva noticia recibida: ¡Gran avance tecnológico!
Móvil: Notificación Push: ¡Gran avance tecnológico!

AGENCIA: Publicando noticia: El precio del café se estabiliza.
Email: Nueva noticia recibida: El precio del café se estabiliza.
Móvil: Notificación Push: El precio del café se estabiliza.
Móvil: Notificación Push: El precio del café se estabiliza.
```

-----

### F. Strategy (Estrategia) ♟️

  * **Propósito:** Define una familia de algoritmos, encapsula cada uno de ellos y los hace intercambiables. Permite que el algoritmo varíe independientemente de los clientes que lo usan.
  * **¿Cuándo usarlo?** Cuando se tienen varios algoritmos para una misma tarea y se quiere que el cliente pueda cambiar entre ellos en tiempo de ejecución. Común para métodos de pago, estrategias de ordenamiento o cálculo de impuestos.

#### 🛠️ Ejemplo: Estrategias de Pago

El contexto es la compra, y las estrategias son los diferentes métodos de pago.

```java
// Interfaz Estrategia
interface EstrategiaPago {
    void pagar(int cantidad);
}

// Estrategias Concretas
class PagoTarjeta implements EstrategiaPago {
    private String numeroTarjeta;

    public PagoTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public void pagar(int cantidad) {
        System.out.println("Pagado $" + cantidad + " con Tarjeta " + numeroTarjeta);
    }
}

class PagoPaypal implements EstrategiaPago {
    private String email;

    public PagoPaypal(String email) {
        this.email = email;
    }

    @Override
    public void pagar(int cantidad) {
        System.out.println("Pagado $" + cantidad + " con PayPal a la cuenta " + email);
    }
}

// Clase Contexto (Utiliza la estrategia)
class Compra {
    private EstrategiaPago estrategia;

    public void setEstrategiaPago(EstrategiaPago estrategia) {
        this.estrategia = estrategia;
    }

    public void checkout(int cantidad) {
        if (estrategia != null) {
            estrategia.pagar(cantidad); // Delega la ejecución a la estrategia
        } else {
            System.out.println("Debe seleccionar una estrategia de pago.");
        }
    }
}

public class StrategyExample2 {
    public static void main(String[] args) {
        Compra miCompra = new Compra();

        // El cliente elige la primera estrategia
        EstrategiaPago pago1 = new PagoTarjeta("4561-xxxx-xxxx-0000");
        miCompra.setEstrategiaPago(pago1);
        System.out.print("Intento 1: ");
        miCompra.checkout(150);

        // El cliente cambia la estrategia en tiempo de ejecución
        EstrategiaPago pago2 = new PagoPaypal("usuario@mail.com");
        miCompra.setEstrategiaPago(pago2);
        System.out.print("Intento 2: ");
        miCompra.checkout(50);
    }
}
```

#### 🖥️ Ejecución en Consola

```
Intento 1: Pagado $150 con Tarjeta 4561-xxxx-xxxx-0000
Intento 2: Pagado $50 con PayPal a la cuenta usuario@mail.com