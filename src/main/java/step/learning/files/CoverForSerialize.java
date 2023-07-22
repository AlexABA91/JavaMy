package step.learning.files;

import step.learning.oop.Literature;

public class CoverForSerialize {
    public Literature getData() {
        return Data;
    }

    public CoverForSerialize setData(Literature data) {
        Data = data;
        return this;
    }

    String Type;

    public String getType() {
        return Type;
    }

    public CoverForSerialize setType(String type) {
        Type = type;
        return this;
    }

    Literature Data;
}
