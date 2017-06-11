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

public class Principal {

    //private IntercalacionDirecta directa;
    private static boolean ejecuccionCorrecta;

    private static ArrayList<Double> tiempo;

    private static double tinicio;

    private static double tfin;

    private static double tEjecuccion;

    String algoritmoOrdenamiento = PantallaPrincipal.getAlgortimo();

    String rutaArchivo = PantallaPrincipal.getRuta();

    String campoOrdenar = PantallaPrincipal.getCampoOrdenar();

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

        IntercalacionDirecta directa;

        IntercalacionNatural natural;

        String algoritmoOrdenamiento = PantallaPrincipal.getAlgortimo();

        String rutaArchivo = PantallaPrincipal.getRuta();

        String campoOrdenar = PantallaPrincipal.getCampoOrdenar();

        File archivo;

        archivo = new File(rutaArchivo);

        tiempo = new ArrayList<Double>();

        if (archivo.exists()) {
            switch (algoritmoOrdenamiento) {
                case "Intercalación Directa":

                    for (int i = 0; i < 100; i++) {
                        tinicio = (double) System.currentTimeMillis();

                        directa = new IntercalacionDirecta(campoOrdenar, archivo);

                        directa.ejecutarAlgoritmo();

                        tfin = (double) System.currentTimeMillis();

                        tEjecuccion = tfin - tinicio;

                        tiempo.add(tEjecuccion);
                    }

                    JOptionPane.showMessageDialog(null, "Ordenamiento finalizado\n"
                            + "El tiempo promedio de 100 ejecucciones es: " + tiempoPromedio(tiempo) + " milisegundos", "", JOptionPane.INFORMATION_MESSAGE);

                    tiempo.clear();
                    break;

                case "Intercalación Natural":

                    for (int i = 0; i < 100; i++) {
                        tinicio = (double) System.currentTimeMillis();

                        natural = new IntercalacionNatural(campoOrdenar, archivo);

                        natural.ejecutarAlgoritmo();

                        tfin = (double) System.currentTimeMillis();

                        tEjecuccion = tfin - tinicio;

                        tiempo.add(tEjecuccion);

                    }

                    JOptionPane.showMessageDialog(null, "Ordenamiento finalizado\n"
                            + "El tiempo promedio de 100 ejecucciones es: " + tiempoPromedio(tiempo) + " milisegundos", "", JOptionPane.INFORMATION_MESSAGE);

                    tiempo.clear();

                    break;

                case "Intercalacion Polifasica":
                    System.out.println("hola");//Falta el codigo para poner 
                    break;
            }

        } else {

            JOptionPane.showMessageDialog(null, "El archivo no se encuentra la ruta es incorrecta");

        }

    }

}
