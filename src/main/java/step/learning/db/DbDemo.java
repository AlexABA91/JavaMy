package step.learning.db;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbDemo {

    private String url;
    private String user;
    private String password;
    private com.mysql.cj.jdbc.Driver mySqlDriver;
    private java.sql.Connection connection;

    public void run() {
        System.out.println("-----------Database Demo-------------");
        JSONObject conf = config();
        JSONObject dbCong = conf.getJSONObject("DataProviders").getJSONObject("PlanetScale");
        this.url = dbCong.getString("url");
        this.user = dbCong.getString("user");
        this.password = dbCong.getString("password");

        if((connection = this.connect()) == null) return;

        System.out.println("Connection ok");
        ensureCreated();

        this.disconnect();

    }
    private void ensureCreated(){
        String sql = "CREATE TABLE IF NOT EXISTS jpu121_randoms (" +
                "`id`        CHAR(36)     PRIMARY KEY," +
                "`val_int`   INT," +
                "`val_str`   VARCHAR(256)," +
                "`val_float` FLOAT" +
                ")";
        System.out.print("ensureCreate: ");
        try(Statement statement = this.connection.createStatement()){
            // ADO.NET :  SQLCommand
            statement.executeUpdate( sql );
            System.out.println("OK");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    private JSONObject config() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("appsetings.json"))) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return new JSONObject(sb.toString());
    }

    private java.sql.Connection connect() {
        // регистрируем драйвер
        //а) через Class.forName("com.mysql.cj.jdbc.Driver");
        // Б) через прямое создание драйвера
        try {
            this.mySqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver((mySqlDriver));
            //подключаемся и имеем зарегистрированный драйвер
            return DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private void disconnect() {
        try {
            if (connection != null)
                connection.close();
            if (mySqlDriver != null)
                DriverManager.deregisterDriver(mySqlDriver);
        } catch (SQLException ignored) {
        }
    }
}
/* Работа с базой данных. JDBC.
 *  1 Конфигурация - работа с вариативным JSON
 * - подключаем org.Json
 * -детали работы - в методах config() и run()
 * 2 Коннектор (драйвер подключения)
 * - на Maven ищем соответсвующий коннектор драйвер (MySQL) для JDBC
 *3 работа с базой данных
 *  - подключение
 *
 * Настройка IDE
 * DataBase (tool-window) - + - DataSource - MySql -
 * option: URL Only
 * Вводим данные с конфигурации
 * Test Connection - Apply - ok
 * */