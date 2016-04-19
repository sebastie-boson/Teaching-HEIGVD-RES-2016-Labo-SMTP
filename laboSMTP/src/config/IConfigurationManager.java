package config;

import model.mail.Mail;
import model.mail.Person;

import java.io.IOException;
import java.util.List;

/**
 * Interface used to collect data for the connection with a SMTP server. It is also used to collect data for the list
 * of victims and the list of mails.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public interface IConfigurationManager {
    /**
     * load properties for the connection with a SMTP server from the name of the specified properties file
     *
     * @param fileName the name of the properties file
     * @throws IOException
     */
    void loadPropertiesFromFile(String fileName) throws IOException;

    /**
     * load list of victims from the specified name file
     *
     * @param fileName the name of the file that contains victims
     * @throws IOException
     */
    void loadVictimsListFromFile(String fileName) throws IOException;
    /**
     * load list of mails from the specified name file
     *
     * @param fileName the name of the file that contains mails
     * @throws IOException
     */
    void loadMailsListFromFile(String fileName) throws IOException;
    /**
     * get the address of the SMTP server
     *
     * @return address of the SMTP server
     */
    String getSmtpServerAddress();

    /**
     * get the port of the SMTP server
     *
     * @return port number
     */
    int getSmtpServerPort();

    /**
     * get the number of groups of victims
     *
     * @return number of groups
     */
    int getGroupsNumber();

    /**
     * get the person to send mail as witness
     *
     * @return person to CC
     */
    Person getWitnessToCC();

    /**
     * get the list of victims
     *
     * @return list of persons (victims)
     */
    List<Person> getVictimsList();

    /**
     * get the list of mails
     *
     * @return list of mails
     */
    List<Mail> getMailsList();
}
