package Servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Recordarme {
    
    private static final String PROPERTIES_FILE = "remembered.properties";
    
    public void guardarCredenciales(String userName, String password) {
        
        try {
            Properties properties = new Properties();
            properties.setProperty("username", userName);
            
            File file = new File(PROPERTIES_FILE);
            FileOutputStream fos = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Utilidades.manejarExcepcion("ERROR al guardar las preferencias: ", ex);
            Logger.getLogger(Recordarme.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
