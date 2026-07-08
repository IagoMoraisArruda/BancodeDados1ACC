import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL = "jdbc:mysql://acc-unifesspa-bd.mysql.database.azure.com:3306/acc_database?useSSL=true&requireSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "admin_acc";
    private static final String SENHA = "";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
