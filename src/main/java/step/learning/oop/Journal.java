package step.learning.oop;

public class Journal extends  Literature implements Copiable, Periodic {
    private int Number;

    public Journal(String title, int number) {
        super.setTitle(title);
        this.setNumber(number);
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    @Override
    public String getCard() {
        return String.format("Journal: %s No. %d",super.getTitle(),this.getNumber());
    }

    @Override
    public String gryPeriod() {
        return "Monthly";
    }
}
