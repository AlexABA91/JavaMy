package step.learning.db.dto;

import com.sun.javafx.binding.StringFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.UUID;

/**
 * DTO for jpu121_randoms (ORM)
 * */
public class Record {
    private UUID id;
    private int intVal;
    private String valStr;
    private double valFloat;

    public Record() {
        setId(null);
    }

    public Record(ResultSet resultSet) throws SQLException {
       setId(UUID.fromString(resultSet.getString("id")));
       setIntVal(resultSet.getInt("val_int"));
       setValStr(resultSet.getString("val_str"));
       setValFloat(resultSet.getDouble("val_float"));
    }

    @Override
    public String toString() {

        return String.format(
                Locale.US,
                "{" +
                "id:'%s',valInt:%d,valStr:'%s'," +
                "valFloat:%f}",getId(),getIntVal(),
                getValStr(),getValFloat());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public String getValStr() {
        return valStr;
    }

    public void setValStr(String valStr) {
        this.valStr = valStr;
    }

    public double getValFloat() {
        return valFloat;
    }

    public void setValFloat(double valFloat) {
        this.valFloat = valFloat;
    }
}
