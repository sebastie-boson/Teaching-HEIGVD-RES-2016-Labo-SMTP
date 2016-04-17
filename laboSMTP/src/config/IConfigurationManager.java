package config;

import model.mail.Mail;
import model.mail.Person;

import java.util.List;

/**
 * Created by sebbos on 16.04.2016.
 */
public interface IConfigurationManager {
    /**
     * load properties for the smtp server from the name of the specified properties file
     * @param fileName the name of the properties file
     */
    void loadPropertiesFromFile(String fileName);

    /**
     * load list of victims from the specified name file
     * @param fileName the name of the file that contains victims
     */
    void loadVictimsListFromFile(String fileName);
    /**
     * load list of mails from the specified name file
     * @param fileName the name of the file that contains mails
     */
    void loadMailsListFromFile(String fileName);
    /**
     * get the address of the smtp server
     * @return address of the smtp server
     */
    String getSmtpServerAddress();

    /**
     * get the port of the smtp server
     * @return port number
     */
    int getSmtpServerPort();

    /**
     * get the number of groups of victims
     * @return number of groups
     */
    int getGroupsNumber();

    /**
     * get the person to send mail as witnesses
     * @return person to CC
     */
    Person getWitnessToCC();

    /**
     * get the list of victims
     * @return list of persons (victims)
     */
    List<Person> getVictimsList();

    /**
     * get the list of mails
     * @return list of mails
     */
    List<Mail> getMailsList();
}
