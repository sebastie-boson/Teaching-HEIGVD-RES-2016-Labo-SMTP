package smtp;

import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;

/**
 * Created by sebbos on 16.04.2016.
 */
public interface ISmtpClient {
    void sendMail(Group group, Person witnessToCC, Mail mail);
}
