package classes;

import db.DBConnection;
import db.Global;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Handler {

    private int nCols = 0;
    ArrayList fieldsTarget = new ArrayList<>();

    public Handler() {
    }

    public void verifyColumns(String lineOne) throws Exception {

        String[] fieldsSource = lineOne.split("\\t");

        if (fieldsSource.length > 0) {

            //
            // colocar el caracter de escape en cada columna
            //
            nCols = 0;
            for (int i = 0; i < fieldsSource.length; i++) {
                nCols++;
            }
            //
            // identificar los nombres de columnas de la tabla
            //

            Statement tableStruct = DBConnection.getDbCon().conn.createStatement();

            ResultSet data = tableStruct.executeQuery("SHOW COLUMNS FROM " + Global.getBaseDatos() + "." + Global.getTabla());
            while (data.next()) {
                this.fieldsTarget.add(data.getString("Field"));
            }

            data.close();
        }

    }

    public void importer(String line, int numLine) throws Exception {
        try {

            String SQL = "INSERT INTO " + Global.getTabla() + " (";

            for (int i = 0; i < this.nCols; i++) {
                SQL += this.fieldsTarget.get(i) + (i == (this.fieldsTarget.size() - 1) ? "" : ", ");
            }
            SQL += ") VALUES (";

            String[] values = line.split("\\t");

            if (values.length == nCols) {

                // considerar solo las primera "n" columnas del archivo
                for (int j = 0; j < values.length; j++) {
                    SQL += "'" + values[j] + "'" + (j == (values.length - 1) ? "" : ", ");
                }
                SQL += ")";

                PreparedStatement ps1 = DBConnection.getDbCon().conn.prepareStatement(SQL);
                ps1.executeUpdate();
                ps1.close();
            } else {
                SQL += ")";
                System.out.println("Warning (" + numLine + "):" + SQL);
            }

        } catch (Exception ex) {
            DBConnection.getDbCon().conn.close();
            ex.printStackTrace();
            throw new Exception("importer():" + Global.getTabla() + ". \n" + ex.getMessage());
        }

    }
}
