package step.learning.oop;

public class Hologram extends Literature implements Expo{
    public String getType() {
        return type;
    }

    public Hologram(String title,String type) {
        super.setTitle(title);
        this.setType(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    @Override
    public String getCard() {
        return String.format("Hologram: '%s' %s",super.getTitle(),this.getType() );
    }
}
