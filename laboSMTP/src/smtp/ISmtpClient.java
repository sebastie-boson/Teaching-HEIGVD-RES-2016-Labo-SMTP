package smtp;

import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;

/**
 * Interface used to send the mail to the specified persons with the specified smtp configuration (server).
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public interface ISmtpClient {
    /**
     * send the mail to the witnessToCC person and to the toPersons (mail address)
     * the mail will appear to be send by the from person (mail address)
     *
     * @param fromPerson from person for the mail
     * @param witnessToCC witnessToCC person
     * @param toPersons toPersons persons
     * @param mail the mail with its subject and content
     */
    void sendMail(Person fromPerson, Person witnessToCC, Group toPersons, Mail mail);
}
