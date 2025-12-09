/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ejercicio5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author camper
 */
public class AuditLogger {
    private static AuditLogger instancia;
    private static final String RUTA_ARCHIVO = "src/main/resources/historial_cambios.txt";
    private static final DateTimeFormatter DATEFORMATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private AuditLogger(){}
    
    public static AuditLogger getInstancia(){
        if(instancia == null){
            instancia = new AuditLogger();
        }
        return instancia;
    }
    
    public void crearArchivo(){
        try{
            File archivo = new File(RUTA_ARCHIVO);
            
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
    
    public static void log(String idPaquete, String estadoNuevo){
        String timestamp = LocalDateTime.now().format(DATEFORMATE);
        String log = String.format("[%s] - Paquete [%s] - cambio de estado: [%s]", timestamp, idPaquete, estadoNuevo);
        
        try(FileWriter fw = new FileWriter(RUTA_ARCHIVO, true)){
            fw.append(log + "\n");
        } catch(IOException e){
            System.out.println("Error al crear archivo " + e.getMessage());
        }
    }
}
