package smtp;

import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;

import java.util.List;

/**
 * Created by sebbos on 16.04.2016.
 */
public interface ISmtpClient {
    void sendMail(Person fromPerson, Person witnessToCC, Group toPersons, Mail mail);
}
