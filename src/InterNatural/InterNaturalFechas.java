package InterNatural;

import java.io.*;
import com.csvreader.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class InterNaturalFechas {

    private File archivo;

    public InterNaturalFechas(File archivo) {
        this.archivo = archivo;
    }

    private final String[] campos = new String[4];

    private SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

    public void ejecutar() {
        File F1 = new File("AchivosOrdenamiento/fichero_aux_1.CSV");
        File F2 = new File("AchivosOrdenamiento/fichero_aux_2.CSV");

        try {
            ordenar(archivo, F1, F2);
        } catch (FileNotFoundException except) {
            JOptionPane.showMessageDialog(null, "Archivo no encontrado", "", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException except) {
            JOptionPane.showMessageDialog(null, "Error de ejecución", "", JOptionPane.INFORMATION_MESSAGE);
        }
        //JOptionPane.showMessageDialog(null, "Ordenamiento finalizado!", "", JOptionPane.INFORMATION_MESSAGE);
    }

    private int contarRegistros(File F) throws FileNotFoundException, IOException {
        int index = 0;
        CsvReader archivo = new CsvReader(new FileReader(F));
        archivo.readHeaders();
        while (archivo.readRecord()) {
            index++;
        }
        archivo.close();
        return index;
    }

    private void ordenar(File F, File F1, File F2) throws FileNotFoundException, IOException {
        try {
            while (particion(F, F1, F2)) {

                fusion(F, F1, F2);

            }
        } catch (ParseException ex) {
            Logger.getLogger(InterNaturalFechas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo para generar particiones de secuencias
    private boolean particion(File F, File F1, File F2) throws IOException, ParseException {
        String actual = null;
        String anterior = null;
        int indexOutputStream = 0;
        boolean hayCambioDeSecuencia = false;
        CsvWriter Auxiliares[] = new CsvWriter[2];
        CsvReader Archivo = null;
        Auxiliares[0] = new CsvWriter(new FileWriter(F1), ',');
        Auxiliares[1] = new CsvWriter(new FileWriter(F2), ',');
        Archivo = new CsvReader(new FileReader(F));
        Archivo.readHeaders();
        Auxiliares[0].writeRecord(Archivo.getHeaders());
        Auxiliares[1].writeRecord(Archivo.getHeaders());

        while (Archivo.readRecord()) {
            anterior = actual;
            actual = Archivo.get(3);
            if (anterior == null) {
                anterior = actual;
            }

            if (formateador.parse(anterior).compareTo(formateador.parse(actual)) > 0) {
                indexOutputStream = indexOutputStream == 0 ? 1 : 0;
                hayCambioDeSecuencia = true;
            }
            save(Auxiliares[indexOutputStream], Archivo);
        }
        Archivo.close();
        Auxiliares[0].flush();
        Auxiliares[1].flush();
        Auxiliares[0].close();
        Auxiliares[1].close();

        return hayCambioDeSecuencia;
    }

    //Metodo de fusion de los datos obtenidos en el metodo de particion
    private void fusion(File F, File F1, File F2) throws IOException, ParseException {
        String[] actual = new String[2];
        String[] anterior = new String[2];
        boolean[] finArchivo = new boolean[2];
        int indexArchivo = 0;
        CsvReader Auxiliares[] = new CsvReader[2];

        int contAux1 = contarRegistros(F1);
        int contAux2 = contarRegistros(F2);
        Auxiliares[0] = new CsvReader(new FileReader(F1));
        Auxiliares[1] = new CsvReader(new FileReader(F2));
        CsvWriter Archivo = new CsvWriter(new FileWriter(F, false), ',');
        Auxiliares[0].readHeaders();
        Auxiliares[1].readHeaders();
        Archivo.writeRecord(Auxiliares[0].getHeaders());

        while (contAux1 > 0 && contAux2 > 0) {
            if (anterior[0] == null && anterior[1] == null) {
                Auxiliares[0].readRecord();
                anterior[0] = actual[0] = Auxiliares[0].get(3);
                contAux1--;
                Auxiliares[1].readRecord();
                anterior[1] = actual[1] = Auxiliares[1].get(3);
                contAux2--;
            }
            anterior[0] = actual[0];
            anterior[1] = actual[1];

            //while (anterior[0].compareTo(actual[0]) <= 0 && anterior[1].compareTo(actual[1]) <= 0) {
            while (formateador.parse(anterior[0]).compareTo(formateador.parse(actual[0])) <= 0 && (formateador.parse(anterior[1]).compareTo(formateador.parse(actual[1])) <= 0)) {
                //indexArchivo = (actual[0].compareTo(actual[1]) <= 0) ? 0 : 1;
                indexArchivo = (formateador.parse(actual[0]).compareTo(formateador.parse(actual[1])) <= 0) ? 0 : 1;
                save(Archivo, Auxiliares[indexArchivo]);

                anterior[indexArchivo] = actual[indexArchivo];
                if (indexArchivo == 0) {
                    if (contAux1 > 0) {
                        Auxiliares[0].readRecord();
                        actual[0] = Auxiliares[0].get(3);
                        contAux1--;
                    } else {
                        finArchivo[0] = true;
                        break;
                    }
                }
                if (indexArchivo == 1) {
                    if (contAux2 > 0) {
                        Auxiliares[1].readRecord();
                        actual[1] = Auxiliares[1].get(3);
                        contAux2--;
                    } else {
                        finArchivo[1] = true;
                        break;
                    }
                }
            }

            if (indexArchivo == 0) {
                //while (anterior[1].compareTo(actual[1]) <= 0) {
                while (formateador.parse(anterior[1]).compareTo(formateador.parse(actual[1])) <= 0) {
                    save(Archivo, Auxiliares[1]);
                    anterior[1] = actual[1];
                    if (contAux2 > 0) {
                        Auxiliares[1].readRecord();
                        actual[1] = Auxiliares[1].get(3);
                        contAux2--;
                    } else {
                        finArchivo[1] = true;
                        break;
                    }
                }
            }
            if (indexArchivo == 1) {
                //while (anterior[0].compareTo(actual[0]) <= 0) {
                while (formateador.parse(anterior[0]).compareTo(formateador.parse(actual[0])) <= 0) {
                    save(Archivo, Auxiliares[0]);
                    anterior[0] = actual[0];
                    if (contAux1 > 0) {
                        Auxiliares[0].readRecord();
                        actual[0] = Auxiliares[0].get(3);
                        contAux1--;
                    } else {
                        finArchivo[0] = true;
                        break;
                    }
                }
            }

        }
        if (!finArchivo[0]) {
            save(Archivo, Auxiliares[0]);
            while (contAux1 > 0) {
                Auxiliares[0].readRecord();
                save(Archivo, Auxiliares[0]);
                contAux1--;
            }
        }

        if (!finArchivo[1]) {
            save(Archivo, Auxiliares[1]);
            while (contAux2 > 0) {
                Auxiliares[1].readRecord();
                save(Archivo, Auxiliares[1]);
                contAux2--;
            }
        }
        Auxiliares[0].close();
        Auxiliares[1].close();
        Archivo.flush();
        Archivo.close();
    }

    private void save(CsvWriter write, CsvReader read) throws IOException {
        campos[0] = read.get(0);
        campos[1] = read.get(1);
        campos[2] = read.get(2);
        campos[3] = read.get(3);
        write.writeRecord(campos);
    }
}
