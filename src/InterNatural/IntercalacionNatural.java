/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterNatural;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntercalacionNatural {

    private String campoOrdenar;

    private File archivo;

    public IntercalacionNatural(String campoOrdenar, File archivo) {
        this.campoOrdenar = campoOrdenar;
        this.archivo = archivo;
    }

    public void ejecutarAlgoritmo() {
        InterNaturalBooleanos ordBoolean;
        InterNaturalCadenas ordCadenas;
        InterNaturalEnteros ordEnteros;
        InterNaturalFechas ordFechas;

        switch (campoOrdenar) {
            case "String":

                ordCadenas = new InterNaturalCadenas(archivo);

                ordCadenas.ejecutar();
                break;

            case "Enteros":
                ordEnteros = new InterNaturalEnteros(archivo);

                ordEnteros.ejecutar();

                break;

            case "Booleano":
                ordBoolean = new InterNaturalBooleanos(archivo);

                ordBoolean.ejecutar();

                break;

            case "Fecha":
                ordFechas = new InterNaturalFechas(archivo);

                ordFechas.ejecutar();
                break;

        }

    }

}
