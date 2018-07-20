/*
 * Importer Big. 
 * NewData Solutions. Carlos Coronado.
 *
 * Aplicativo para importar datos de archivos grandes en MySQL
 *
 * Home Page: http://www.newwdata.com.pe
 * 
 * 19, jul 2018
 * 
 */
package importerbig;

import classes.Handler;
import db.Global;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class importerbig {

    private int linea = 0;

    public importerbig() {
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public String getHour() {
        SimpleDateFormat xf = new SimpleDateFormat("hh:mm:ss");
        java.util.Date f = new java.util.Date();
        return xf.format(f);
    }

    public void importFile(String pathFile) {
        Handler handler = new Handler();

        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(pathFile);
            sc = new Scanner(inputStream, "UTF-8");
            int i = 0;
            System.out.println("Iniciando: " + getHour());
            while (sc.hasNextLine()) {
                setLinea(i);
                String line = sc.nextLine().replace("'", " ");

                if (i == 0) {
                    handler.verifyColumns(line);
                } else {
//                    System.out.println(line);
                    handler.importer(line, getLinea());
                }

                i++;
            }
            System.out.println("Terminado: " + getHour());
            System.out.println("Se importaron " + (i - 1) + " registros.");
            if (sc.ioException() != null) {
                throw sc.ioException();
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Linea: " + getLinea() + "\n" + ex.getMessage());
        } catch (IOException ex2) {
            System.out.println("Linea: " + getLinea() + "\n" + ex2.getMessage());
        } catch (Exception ex3) {
            System.out.println("Linea: " + getLinea() + "\n" + ex3.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    System.out.println("Linea: " + getLinea() + "\n" + ex.getMessage());
                }
            }
            if (sc != null) {
                sc.close();
            }
        }

    }

    public static void main(String[] args) {

        System.out.println("Bienvenido a Importer Big");
        System.out.println("NewData Solutions, 2018. Licencia de uso libre.");
        System.out.println("=======================================================");
        System.out.println("Reglas:");
        System.out.println("  - Utilice TAB para separar cada columna en el archivo.");
        System.out.println("  - Debe incluir un encabezado en la primera linea.");
        System.out.println("  - La tabla debe existir en la base de datos y con la misma estructura del archivo.");
        System.out.println("Sintaxis:");
        System.out.println("  java -jar importerbig \"Host[:Port]\" \"DataBaseName\" \"User\" \"Password|*\" \"TableName\" \"FilePath\" ");

        if (args.length != 6) {
            System.out.println("Faltan parametros.");
        } else {
            String host = args[0];
            String basedatos = args[1];
            String usuario = args[2];
            String clave = args[3];
            String tabla = args[4];
            String ruta = args[5]; //"D:\\files\\persons.txt";

            /**
             * *
             *
             * Cargar los parametros de conexión a la base de datos
             *
             *
             */
            String[] arrH = host.split("\\|");

            if (arrH.length == 2) {
                Global.setHost(arrH[0]);
                Global.setPuerto(arrH[1]);
            } else {
                Global.setHost(host);
            }

            Global.setBaseDatos(basedatos);
            Global.setUsuario(usuario);
            Global.setPassword("*".equals(clave) ? "" : clave);
            Global.setUsuario(usuario);
            Global.setTabla(tabla);

            importerbig imp = new importerbig();
            imp.importFile(ruta);

        }

    }

}
