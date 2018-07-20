package db;

import com.mysql.jdbc.Connection;
import java.sql.*;
import java.sql.DriverManager;

public final class DBConnection {

    public Connection conn;
    private Statement statement;
    public static DBConnection db;

    private DBConnection() throws Exception {
        String url = "jdbc:mysql://" + Global.getHost() + ":" + Global.getPuerto() + "/";
        String driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection) DriverManager.getConnection(url + Global.getBaseDatos() + "?autoReconnect=true&noAccessToProcedureBodies=true", Global.getUsuario(), Global.getPassword());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            String stateCode = SQLError.mysqlToXOpen(e.getErrorCode());
            throw new Exception(SQLError.get(stateCode));

        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            throw new Exception("Clase " + driver + " no encontrada.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {

        }
    }

    private DBConnection(String ip, String dbName, String port, String userName, String password) throws Exception {
        String url = "jdbc:mysql://" + ip + ":" + port + "/";
        String driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection) DriverManager.getConnection(url + dbName + "?noAccessToProcedureBodies=true", userName, password);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            String stateCode = SQLError.mysqlToXOpen(e.getErrorCode());
            throw new Exception(SQLError.get(stateCode));
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            throw new Exception("Clase " + driver + " no encontrada.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {

        }
    }

    public static synchronized DBConnection getDbCon() throws Exception {
        try {
            if (db == null || db.conn.isClosed()) {
                db = new DBConnection();
            }
            return db;
        } catch (SQLException ex) {
            return null;
        }
    }

    public ResultSet query(String query) throws SQLException {
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }
   
    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;

    }
}
