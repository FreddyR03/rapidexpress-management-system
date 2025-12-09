# Manejo de Archivos en Java (Texto Plano y Serialización)

## Texto Plano (.txt, .csv, .json)

### Lectura con FileReader y BufferedReader (sin abreviar variables)

``` java
try (FileReader fileReader = new FileReader("datos.txt");
     BufferedReader bufferedReader = new BufferedReader(fileReader)) {

    String linea;
    while ((linea = bufferedReader.readLine()) != null) {
        System.out.println("Línea: " + linea);
    }

} catch (IOException e) {
    System.out.println("Error al leer archivo");
}
```

### Escritura con FileWriter y BufferedWriter

``` java
try (FileWriter fileWriter = new FileWriter("salida.txt");
     BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

    bufferedWriter.write("Hola mundo");
    bufferedReader.newLine();
    bufferedReader.write("Otra línea");

} catch (IOException e) {
    System.out.println("Error al escribir archivo");
}
```

### Escritura con PrintWriter (más fácil)

``` java
try (PrintWriter printWriter = new PrintWriter("salida.txt")) {
    printWriter.println("Freddy");
    printWriter.println("Ejemplo de escritura");
} catch (IOException e) {
    System.out.println("Error al escribir");
}
```

## Serialización (Objetos Java)

### Clase serializable

``` java
public class Cliente implements Serializable {
    private String nombre;
    private String cedula;
    private String telefono;
}
```

### Guardar un objeto

``` java
try (FileOutputStream fileOut = new FileOutputStream("cliente.dat");
     ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

    Cliente cliente = new Cliente("Freddy", "1094247116", "3142798125");
    objectOut.writeObject(cliente);

} catch (IOException e) {
    System.out.println("Error guardando objeto");
}
```

### Leer un objeto

``` java
try (FileInputStream fileIn = new FileInputStream("cliente.dat");
     ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

    Cliente cliente = (Cliente) objectIn.readObject();
    System.out.println(cliente.getNombre());

} catch (IOException | ClassNotFoundException e) {
    System.out.println("Error leyendo objeto");
}
```
