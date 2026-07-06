package br.edu.unifesspa.acc.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionFactory {

    private static final Properties CONFIG = new Properties();

    static {
        try (InputStream input = ConnectionFactory.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "application.properties nao encontrado em src/main/resources");
            }
            CONFIG.load(input);
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Driver JDBC do MySQL nao encontrado no classpath", e);
        }
    }

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        String host = CONFIG.getProperty("db.host");
        String port = CONFIG.getProperty("db.port");
        String name = CONFIG.getProperty("db.name");
        String user = CONFIG.getProperty("db.user");
        String password = CONFIG.getProperty("db.password");
        boolean useSSL = Boolean.parseBoolean(CONFIG.getProperty("db.useSSL", "true"));

        String url = String.format(
                "jdbc:mysql://%s:%s/%s?useSSL=%s&requireSSL=%s&serverTimezone=America/Belem",
                host, port, name, useSSL, useSSL
        );

        return DriverManager.getConnection(url, user, password);
    }
}
