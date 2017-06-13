/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrincipalEjecuccion;

/**
 *
 * @author Jonnathan Campoberde
 */
import java.io.*;
import javax.swing.*;
import InterDirecta.*;
import InterNatural.*;
import InterfazGrafica.*;
import java.util.ArrayList;
import java.util.Iterator;
import CopiarArchivo.*;

public class Principal {

    //private IntercalacionDirecta directa;
    private static boolean ejecuccionCorrecta;

    private static int veces;

    private static ArrayList<Double> tiempo;

    private static double tinicio;

    private static double tfin;

    private static double tEjecuccion;

    private String algoritmoOrdenamiento = PantallaPrincipal.getAlgortimo();

    private String campoOrdenar = PantallaPrincipal.getCampoOrdenar();

    public static void main(String[] args) {

        PantallaPrincipal.ejecutar();
    }

    public static double tiempoPromedio(ArrayList<Double> listaTiempo) {
        double suma = 0;
        Iterator<Double> recorrer = listaTiempo.iterator();
        while (recorrer.hasNext()) {
            suma = suma + recorrer.next();

        }
        return suma / 100;
    }

    public static void ejecuccionOrdenamientosAlgoritmo() {
        
        String copiaArchivoriginalRuta = "AchivosOrdenamiento/ficheroRespaldoOrdenar.CSV";
        
        String rutaArchivo = PantallaPrincipal.getRuta();

        if (CopiarArchivo.copyFile(rutaArchivo, copiaArchivoriginalRuta)==true) {

            IntercalacionDirecta directa;
            
            IntercalacionNatural natural;

            veces = PantallaPrincipal.getVecesEjecutar();
            
            String algoritmoOrdenamiento = PantallaPrincipal.getAlgortimo();

            String campoOrdenar = PantallaPrincipal.getCampoOrdenar();

            File archivo= new File(copiaArchivoriginalRuta);

            tiempo = new ArrayList<Double>();

            if (archivo.exists()) {
                switch (algoritmoOrdenamiento) {
                    case "Intercalación Directa":

                        for (int i = 0; i < veces; i++) {
                            tinicio = (double) System.currentTimeMillis();
                            
                            if(i==(veces-1)){
                                archivo=new File(rutaArchivo);
                            }

                            directa = new IntercalacionDirecta(campoOrdenar, archivo);

                            directa.ejecutarAlgoritmo();
         
                            tfin = (double) System.currentTimeMillis();

                            tEjecuccion = tfin - tinicio;

                            tiempo.add(tEjecuccion);
                        }
                        
                        mensajePantalla(tiempo);
                        tiempo.clear();
                        break;

                    case "Intercalación Natural":
                        
                        for (int i = 0; i < veces; i++) {
                            tinicio = (double) System.currentTimeMillis();

                            if(i==(veces-1)){
                                archivo=new File(rutaArchivo);
                            }
                            
                            natural = new IntercalacionNatural(campoOrdenar, archivo);

                            natural.ejecutarAlgoritmo();

                            tfin = (double) System.currentTimeMillis();

                            tEjecuccion = tfin - tinicio;

                            tiempo.add(tEjecuccion);

                        }
                        mensajePantalla(tiempo);
                        
                        tiempo.clear();

                        break;
                }

            } 

        }

    }
    
    
    public static void mensajePantalla(ArrayList<Double> tiempo){
    JOptionPane.showMessageDialog(null, "Ordenamiento finalizado\n"
                                + "El tiempo promedio de "+veces+" ejecucciones es: " + tiempoPromedio(tiempo) + " milisegundos", "", JOptionPane.INFORMATION_MESSAGE);
    
    }
}
