package step.learning.db;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.json.JSONObject;
import step.learning.db.dao.RecordDao;
import step.learning.db.dto.Record;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DbDemo {

    private String url;
    private String user;
    private String password;
    private com.mysql.cj.jdbc.Driver mySqlDriver;
    private java.sql.Connection connection;
    private final Random random = new Random();
    private RecordDao recordDao;


    public void run() {
        System.out.println("-----------Database Demo-------------");
        JSONObject conf = config();
        JSONObject dbCong = conf.getJSONObject("DataProviders").getJSONObject("PlanetScale");
        this.url = dbCong.getString("url");
        this.user = dbCong.getString("user");
        this.password = dbCong.getString("password");

        if((connection = this.connect()) == null) return;

        System.out.println("Connection ok");

        recordDao= new RecordDao(random,connection);
        if(recordDao.ensureCreated()){
            System.out.println("Ensure OK");
        }

       // RandomInitialization();
       // insertPrepared(5);
      //  showRandomCount();
      //  rowCountInSegment();

        //System.out.println(recordDao.getBuId(UUID.fromString("029dd003-fbd1-4d2f-ae46-9d757b258a7d")));
        ShowCount();
        //Record record = recordDao.getBuId(UUID.fromString("0e7f01db-4f00-415c-bb10-27c7932b9245"));
       //deleteByRecord(record);
        deleteById(UUID.fromString("11a5f875-b98a-4ea8-9378-14a9b53f218c"));
        ShowCount();
        this.disconnect();

    }
//    public void RandomInitialization(){
//        System.out.println("Rand");
//        for (int i = 0; i < 1; i++) {
//
//           String sql = String.format(Locale.US,"INSERT" +
//                                      " INTO jpu121_randoms(`id`,`val_int`,`val_str`,`val_float`) " +
//                                      "VALUES ('%s',%d,'%s',%e)"
//                                      ,UUID.randomUUID(), randInt(),randStr(),randFloat());
//            System.out.println(sql);
//            try(Statement statement = this.connection.createStatement()){
//                statement.executeUpdate( sql );
//            } catch (SQLException e) {
//                System.err.println(e.getMessage());
//            }
//
//        }
//    }
    private void  showRandomCount(){
      int cnt = recordDao.getCount();
      if(cnt == -1 )
          System.err.println("Counter error");
      else
          System.out.println("rows count:  "+cnt );
    }
    private void rowCountInSegment(){
        System.out.print("Введите мин. = ");
        Scanner in = new Scanner(System.in);
        int min = in.nextInt();
        System.out.print("Введите Max. = ");
        int max = in.nextInt();

        if (min > max){
            int temp = max;
            max = min;
            min = temp;
        }

        String sql = String.format("SELECT * FROM jpu121_randoms WHERE `val_int` > %d AND `val_int`< %d", min,max);
        String sql2 = String.format("select Count(id) from jpu121_randoms  WHERE `val_int` > %d AND `val_int`< %d", min,max);
        try (PreparedStatement prep1 = this.connection
                .prepareStatement(sql2)){
            ResultSet res1 =  prep1.executeQuery();
            res1.next();
            System.out.println("rows count:  "+ res1.getInt(1));
            res1.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

       try( PreparedStatement prep = this.connection.prepareStatement(sql)){
           ResultSet res = prep.executeQuery();
           while (res.next()){
               System.out.printf("%s %d %s %f %n",
                       res.getString(1),
                       res.getInt("val_int"),
                       res.getString(3),
                       res.getFloat("val_float")
               );
           }
           res.close();

       } catch (SQLException e) {
           System.err.println(e.getMessage());
       }
    }
    private void insertPrepared(int rowCount){
            if(recordDao.insertPreparedRecords(rowCount)){
                 System.out.println("INSET OK");
            }
    }
    private void showAllDao(){
        List<Record> res =recordDao.getAll();
        if(res== null){
            System.out.println("Error getting list");
        }else {
            for (Record item : res ) {
                System.out.println(item);
            }
        }
    }

//    public  void  ShowRandomS(){
//        String sql = "Select * FROM jpu121_randoms"; // ; в конце sql команды не нужна
//        try (Statement statement = this.connection.createStatement()){
//            ResultSet res =  statement.executeQuery(sql); // ADO ~ SQLDataReader
//                                                          // ResultSer res = объект для трансфера дынны, что есть результатом запроса
//                                                          // Особенностью БД - робота с большими данными, что означает отсутствие одного результата
//                                                          // и получения данных ряд-зарядом (цитирование)
//
//                                                // res.next() Получение нового row (если есть true иначе false)
//            while (res.next()){
//                System.out.printf("%s %d %s %f %n",
//                res.getString(1), //!!!! в JDBC отчет начинается с 1 !!!!!!
//                res.getInt("val_int"),// за именем колонки;
//                res.getString(3),
//                res.getFloat("val_float")
//                );
//            }
//            res.close();
//
//
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
//    }
    private  int randInt(){
        return (int) ((Math.random() * (9999 - 1) + 1));
    }
    private  String randStr(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;


      return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    private float randFloat(){
        float leftLimit = 1F;
        float rightLimit = 10F;
       return leftLimit + random.nextFloat() * (rightLimit - leftLimit);
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
    private void updateRecord(Record record){
        if(recordDao.update(record)){
            System.out.println("Update is OK");
        }else
            System.out.println("Error: !!!!");;
    }
    private void deleteByRecord(Record record){
        if(recordDao.delete(record)){
            System.out.println("record is deleted");
        } else {
            System.out.println("ERROR!!!");
        }
    }
    private void deleteById(UUID id){
        if(recordDao.deleteBuId(id)){
            System.out.println("record is deleted");
        } else {
            System.out.println("ERROR!!!");
        }
    }
    private void ShowCount(){
        System.out.printf("Row count : %d \n",recordDao.getCount());
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
/*        Java                                                  DB
   prepareStatement                                           proc_tmp() {
"SELECT COUNT(id) FROM jpu121_randoms"     ---------------->    return SELECT COUNT(id) FROM jpu121_randoms }
   res = prep.executeQuery()               ----------------> CALL proc_tmp() --> Iterator#123
       (res==Iterator#123)               <-- Iterator#123 --
   res.next()                              ---------------->   Iterator#123.getNext() - берется 1й рядок
                                        <-- noname: 7 ------
   res.getInt( 1 ) - 7
 */

/*DTO ,DAO
     DTO - Data Transfer Object - объект для передачи данных - структура, которою включает в себя поля или свойства
     их аксесоры, конструкторы а утилиты (toString(),toJson()). Не имеют логику. Аналог - Entity

     DAO - Data Accesses Object - объект доступа к логики - логика работы с объектами DTO. Аналог LINQ
     Например :
      USerDTO {
      private UUID ID;
      STRING NAME
      public UUID gdtID
      }

      UserDAO{
      public UserDTO getUserById(UUID Id) {...}
      }

      * */