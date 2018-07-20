package db;

public class Global {

    private static String host = "";
    private static String puerto = "3306";
    private static String baseDatos = "";
    private static String usuario = "";
    private static String password = "";
    private static String tabla = "";

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Global.host = host;
    }

    public static String getPuerto() {
        return puerto;
    }

    public static void setPuerto(String puerto) {
        Global.puerto = puerto;
    }

    public static String getBaseDatos() {
        return baseDatos;
    }

    public static void setBaseDatos(String baseDatos) {
        Global.baseDatos = baseDatos;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        Global.usuario = usuario;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Global.password = password;
    }

    public static String getTabla() {
        return tabla;
    }

    public static void setTabla(String tabla) {
        Global.tabla = tabla;
    }

}
