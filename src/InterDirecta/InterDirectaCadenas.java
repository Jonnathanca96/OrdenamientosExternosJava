package InterDirecta;

import java.io.*;
import com.csvreader.*;
import javax.swing.JOptionPane;

public class InterDirectaCadenas {

    private File archivo;

    public InterDirectaCadenas(File archivo) {
        this.archivo = archivo;
    }

    private String[] arreglo = new String[4];

    public void ejecutar() {

        
            File file1 = new File("AchivosOrdenamiento/fichero_aux_1.CSV");
            File file2 = new File("AchivosOrdenamiento/fichero_aux_2.CSV");

            try {
                int n = contarRegistros(archivo);
                MezclaDirecta(archivo, file1, file2, n);
            }catch(FileNotFoundException except){
                JOptionPane.showMessageDialog(null, "Archivo no encontrado", "", JOptionPane.INFORMATION_MESSAGE);
            }catch(IOException except){
                JOptionPane.showMessageDialog(null, "Error de ejecución", "", JOptionPane.INFORMATION_MESSAGE); 
            }
            

            //JOptionPane.showMessageDialog(null, "Ordenamiento finalizado..", "", JOptionPane.INFORMATION_MESSAGE);

        
    }

    private int contarRegistros(File F) throws FileNotFoundException, IOException {
        CsvReader BR = new CsvReader(new FileReader(F), ',');
        int n = 0;
        BR.readHeaders();
        while (BR.readRecord()) {
            n++;
        }
        return n;
    }

    private void MezclaDirecta(File F, File F1, File F2, int n) throws FileNotFoundException, IOException {
        int part = 1;
        while (part <= ((int) ((n + 1) / 2))) {
            Particiona(F, F1, F2, part);
            Fusiona(F, F1, F2, part);
            part *= 2;
        }
        Particiona(F, F1, F2, part);
        Fusiona(F, F1, F2, part);
    }

    private void Particiona(File F, File F1, File F2, int part) throws FileNotFoundException, IOException {
        CsvReader f = new CsvReader(new FileReader(F), ',');
        CsvWriter f1 = new CsvWriter(new FileWriter(F1), ',');
        CsvWriter f2 = new CsvWriter(new FileWriter(F2), ',');
        int k = 0, l = 0;
        f.readHeaders();
        f1.writeRecord(f.getHeaders());
        f2.writeRecord(f.getHeaders());

        boolean is_end = f.readRecord();
        while (is_end) {
            k = 0;
            while ((k < part) && is_end) {
                save(f1, f);
                k++;
                is_end = f.readRecord();
            }
            l = 0;
            while ((l < part) && is_end) {
                save(f2, f);
                l++;
                is_end = f.readRecord();
            }
        }
        f1.flush();
        f2.flush();
        f1.close();
        f2.close();
        f.close();
    }

    private void Fusiona(File F, File F1, File F2, int part) throws FileNotFoundException, IOException {
        CsvWriter f = new CsvWriter(new FileWriter(F), ',');
        CsvReader f1 = new CsvReader(new FileReader(F1), ',');
        CsvReader f2 = new CsvReader(new FileReader(F2), ',');
        int k = 0, l = 0;
        boolean B1 = true, B2 = true, is_end_f1, is_end_f2;
        f1.readHeaders();
        f2.readHeaders();
        f.writeRecord(f1.getHeaders());

        is_end_f1 = f1.readRecord();
        if (is_end_f1) {
            B1 = false;
        }
        is_end_f2 = f2.readRecord();
        if (is_end_f2) {
            B2 = false;
        }
        while ((is_end_f1 || (B1 == false)) && (is_end_f2 || (B2 == false))) {
            k = l = 0;
            while (((k < part) && (B1 == false)) && ((l < part) && (B2 == false))) {
                //if (Integer.parseInt(f1.get(0)) <= Integer.parseInt(f2.get(0))) {
                if (f1.get(1).compareTo(f2.get(1)) <= 0) {
                    save(f, f1);
                    B1 = true;
                    k++;
                    is_end_f1 = f1.readRecord();
                    if (is_end_f1) {
                        B1 = false;
                    }
                } else {
                    save(f, f2);
                    B2 = true;
                    l++;
                    is_end_f2 = f2.readRecord();
                    if (is_end_f2) {
                        B2 = false;
                    }
                }
            }
            while ((k < part) && (B1 == false)) {
                save(f, f1);
                B1 = true;
                k++;
                is_end_f1 = f1.readRecord();
                if (is_end_f1) {
                    B1 = false;
                }
            }
            while ((l < part) && (B2 == false)) {
                save(f, f2);
                B2 = true;
                l++;
                is_end_f2 = f2.readRecord();
                if (is_end_f2) {
                    B2 = false;
                }
            }
        }
        if (B1 == false) {
            save(f, f1);
        }
        if (B2 == false) {
            save(f, f2);
        }
        is_end_f1 = f1.readRecord();
        while (is_end_f1) {
            save(f, f1);
            is_end_f1 = f1.readRecord();
        }
        is_end_f2 = f2.readRecord();
        while (is_end_f2) {
            save(f, f2);
            is_end_f2 = f2.readRecord();
        }
        f.flush();
        f1.close();
        f2.close();
        f.close();
    }

    private void save(CsvWriter write, CsvReader read) throws IOException {
        arreglo[0] = read.get(0);
        arreglo[1] = read.get(1);
        arreglo[2] = read.get(2);
        arreglo[3] = read.get(3);
        write.writeRecord(arreglo);
    }
}

