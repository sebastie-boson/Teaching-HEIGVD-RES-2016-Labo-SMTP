package model.mail;

/**
 * Created by sebbos on 16.04.2016.
 */
public class Person {
    private String mailAddress;

    public Person(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getMailAddress() {
        return mailAddress;
    }
}
