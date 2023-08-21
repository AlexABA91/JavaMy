package step.learning.db.dao;

import step.learning.db.dto.Record;

import java.sql.*;
import java.util.*;

public class RecordDao {
    private final Random random;
    private final Connection connection;
    public Record getBuId(UUID id){
        if(id == null){
        return null;
        }
        String sql = String.format("SELECT * FROM jpu121_randoms where id='%s'"
                ,id.toString());
        try (Statement statement = this.connection.createStatement()) {
            ResultSet res =  statement.executeQuery(sql);
            res.next();
            return new Record(res);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return  null;
        }
    }
    public List<Record> getAll(){
        String sql = "Select * FROM jpu121_randoms"; // ; в конце sql команды не нужна
        try (Statement statement = this.connection.createStatement()){
            ResultSet res =  statement.executeQuery(sql); // ADO ~ SQLDataReader
            // ResultSer res = объект для трансфера дынны, что есть результатом запроса
            // Особенностью БД - робота с большими данными, что означает отсутствие одного результата
            // и получения данных ряд-зарядом (цитирование)

            // res.next() Получение нового row (если есть true иначе false)

            List<Record> ret = new ArrayList<>();
            while (res.next()){
               ret.add( new Record(res));
            }
            res.close();
            return ret;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    public RecordDao(Random random, Connection connection) {
        this.random = random;
        this.connection = connection;
    }
    public boolean insertPreparedRecords(int rowCount){
        // Подготовленные запросы можно принимать временными сохранеными процедурами
        // (скомпилированные запросы, которые сохраняются в базе данных со стороны СУБД)
        // Идея - запрос компилируется и скомпилированный код остояться в СУБД пока соединение остается открытым
        // соединение. В это время можно повторить запрос, в том числе и с другими параметрами
        // (за что их и называют параметризованными запросами)
        String sql =  "INSERT INTO jpu121_randoms(`id`,`val_int`,`val_str`,`val_float`) VALUES (UUID(),?,?,?)";
        // Место для вариативных данных заменяются знаками ?
        try(PreparedStatement prep = this.connection.prepareStatement(sql))
        {
            /*На втором этапе (после подготовки - создание временной процедуры)
            происходит заполнение параметров. Желательно использовать конкретные типы данных
            seterov (избегать обобщения setObject)
            *  */
            for (int i = 0; i < rowCount; i++) {

                prep.setInt(1, random.nextInt()); //!! подстановка на место пегого ?
                prep.setString(2, random.nextInt() + " ");
                prep.setDouble(3, random.nextDouble());

                /*Третий этап - выполнение
                 * */
                prep.execute();
            }
            return true;
        }catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public void createRandomRecord(){
        String sql = String.format(Locale.US,"INSERT" +
                        " INTO jpu121_randoms(`id`,`val_int`,`val_str`,`val_float`) " +
                        "VALUES ('UUID()',%d,'%s',%e)"
                , random.nextInt(), random.nextInt(), random.nextDouble());
        System.out.println(sql);
        try(Statement statement = this.connection.createStatement()){
            statement.executeUpdate( sql );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public boolean create(Record record){
        String sql = "INSERT INTO jpu121_randoms(`id`,`val_int`,`val_str`,`val_float`) VALUES (?,?,?,?)";

        try (PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1,record.getId() == null? UUID.randomUUID().toString(): record.getId().toString());
            prep.setInt(2,record.getIntVal());
            prep.setString(3,record.getValStr());
            prep.setDouble(4,record.getValFloat());
            return prep.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public boolean ensureCreated(){
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
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public int  getCount(){
        try (PreparedStatement prep = this.connection.prepareStatement("select Count(id) from jpu121_randoms")){
            ResultSet res =  prep.executeQuery();
            res.next();
           return res.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }
    public boolean update(Record record){
        String sql = String.format("UPDATE jpu121_randoms SET val_int = ?, val_str = ?, val_float = ? WHERE id ='%s' ",record.getId().toString());
        try (PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setInt(1,record.getIntVal());
            prep.setString(2,record.getValStr());
            prep.setDouble(3,record.getValFloat());
            prep.execute();
            return true;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    };
    public boolean delete(Record record){
        String sql = String.format("DELETE FROM jpu121_randoms WHERE id = '%s'",record.getId().toString());
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;       }
    }
    public boolean deleteBuId(UUID id){
        String sql = String.format("DELETE FROM jpu121_randoms WHERE id = '%s'",id.toString());
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;       }
    }
}
