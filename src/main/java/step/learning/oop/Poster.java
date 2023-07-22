package step.learning.oop;

import com.sun.javafx.binding.StringFormatter;

public class Poster extends Literature implements Expo,Copiable {
    public Poster(String title, String customer) {
        super.setTitle(title);
        this.setCustomer(customer);
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    private String customer;

    @Override
    public String getCard() {
        return String.format("Poster: %s Customer: %s", super.getTitle(),this.getCustomer() );
    }
}
