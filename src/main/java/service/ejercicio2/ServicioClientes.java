/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.ejercicio2;

import dao.implementacion.ejercicio2.ClienteDAOImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import model.ejercicio2.Cliente;

/**
 *
 * @author fredd
 */
public class ServicioClientes {
    
    public ServicioClientes(){};
    private ClienteDAOImpl clienteDAO = new ClienteDAOImpl();
    
    public void crearArchivo(){
        try{
            File archivo = new File("src/main/resources/clientes_nuevos.csv");
            
            if(archivo.exists()){
                return;
            }else{
                archivo.createNewFile();
                System.out.println("Creado el archivo en la carpeta " + archivo.getPath());
            }
        }catch(IOException e){
            System.out.println("error al crear archivo " + e.getMessage());
        }
    }

    public void importarClientesDesdeCSV() {

        String archivo = "src/main/resources/clientes_nuevos.csv";
        int contador = 0;

        try (FileReader fr= new FileReader(archivo);
                BufferedReader br= new BufferedReader(fr)) {

            String linea;
            while ((linea = br.readLine()) != null) {

                try {
                    String[] partes = linea.split(",");
                    if (partes.length != 3) {
                        System.out.println("Línea inválida: " + linea);
                        continue;
                    }

                    String nombre = partes[0].trim();
                    String cedula = partes[1].trim();
                    String telefono = partes[2].trim();
                    
                    if (clienteDAO.existeCedula(cedula)) {
                        System.out.println("Cliente ya existe, se omite: " + cedula);
                        continue;
                    }

                    Cliente c = new Cliente(nombre, cedula, telefono);
                    clienteDAO.insertarCliente(c);

                    contador++;

                } catch (Exception e) {
                    System.out.println("Error procesando línea: " + linea);
                }
            }

            System.out.println("Importación finalizada. Clientes agregados: " + contador);

        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo: " + archivo);
        }
    }
}
