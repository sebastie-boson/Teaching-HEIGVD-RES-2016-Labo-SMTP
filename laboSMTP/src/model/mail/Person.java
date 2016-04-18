package model.mail;

/**
 * Class that represents a person with a mail address.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class Person {
    private String mailAddress;

    /**
     * constructor of the class Person
     *
     * @param mailAddress mail address of the person
     */
    public Person(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * get the mail address of the person
     *
     * @return the mail address
     */
    public String getMailAddress() {
        return mailAddress;
    }
}
