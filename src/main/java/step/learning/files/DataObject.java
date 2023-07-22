package step.learning.files;

/**
 * Для исследования полей
 */
public class DataObject {
    public String getField() {
        return field1;
    }

    public DataObject setField(String field) {
        this.field1 = field;
        return this;
    }

    private String field1;

    public String getField2() {
        return field2;
    }

    public DataObject setField2(String field2) {
        this.field2 = field2;
        return this;
    }

    private String field2;

    @Override
    public String toString() {
        return String.format("field1 = '%s',field2 = '%s'",field1,field2);
    }
}
