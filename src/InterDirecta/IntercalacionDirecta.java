package InterDirecta;

import java.io.FileNotFoundException;
import java.io.*;
import java.text.ParseException;
import java.util.Scanner;
import InterfazGrafica.PantallaPrincipal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class IntercalacionDirecta {

    public static String campoOrdenar = "";

    private static File archivo = null;

    public IntercalacionDirecta(String campoOrdenar, File archivo) {
        this.campoOrdenar = campoOrdenar;
        this.archivo = archivo;
    }

    public void ejecutarAlgoritmo()  {
        InterDirectaBoleanos ordBoolean;
        InterDirectaCadenas ordCadenas;
        InterDirectaEnteros ordEnteros;
        InterDirectaFechas ordFechas;

        switch (campoOrdenar) {
            case "String":
                ordCadenas = new InterDirectaCadenas(archivo);
                ordCadenas.ejecutar();
                

            case "Enteros":
                ordEnteros = new InterDirectaEnteros(archivo);
                ordEnteros.ejecutar();
                

            case "Booleano":
                ordBoolean = new InterDirectaBoleanos(archivo);
                ordBoolean.ejecutar();
                

            case "Fecha":
                ordFechas = new InterDirectaFechas(archivo);

                ordFechas.ejecutar();

                
        }
        
    }
}
