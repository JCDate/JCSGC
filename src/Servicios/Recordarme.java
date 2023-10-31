/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JC
 */
public class Recordarme {

    private static final String PROPERTIES_FILE = "remembered.properties";

    public void guardarCredenciales(String userName, String password) {

        try {
            Properties properties = new Properties();
            properties.setProperty("username", userName);
            //  properties.setProperty("password", Encryptor.encrypt(password));

            File file = new File(PROPERTIES_FILE);
            FileOutputStream fos = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Recordarme.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*
    import java.io.*;
import java.util.Properties;

public class RememberMeService {
    
    public void saveCredentials(String username, String password) {
        try {
            Properties properties = new Properties();
            properties.setProperty("username", username);
            properties.setProperty("password", Encryptor.encrypt(password)); // Encriptar la contraseña antes de guardarla
            
            File file = new File(PROPERTIES_FILE);
            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos, "Remembered Credentials");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getRememberedUsername() {
        Properties properties = loadProperties();
        return properties.getProperty("username");
    }

    public String getRememberedPassword() {
        Properties properties = loadProperties();
        return Encryptor.decrypt(properties.getProperty("password")); // Desencriptar la contraseña al obtenerla
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try {
            File file = new File(PROPERTIES_FILE);
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
     */
}
