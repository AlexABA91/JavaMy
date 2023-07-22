package step.learning.oop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Newspaper extends Literature implements Periodic{
    private Date date;
    private final static  SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public Newspaper(String title ,Date date) {
        super.setTitle(title);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getCard() {
        return String.format("Newspaper: '%s' %s",super.getTitle(),dateFormat.format(this.getDate()) );
    }

    @Override
    public String gryPeriod() {
        return "Daily";
    }
}
