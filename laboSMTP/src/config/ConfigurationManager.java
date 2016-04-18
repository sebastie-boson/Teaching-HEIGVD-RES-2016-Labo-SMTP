package config;

import model.mail.Mail;
import model.mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Implementation of the IConfigurationManager interface to collect data from a configuration file config.properties and
 * from two other files victims.utf8 and mails.utf8.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class ConfigurationManager implements IConfigurationManager {
    private String smtpServerAddress;
    private int smtpServerPort;
    private int groupsNumber;

    private Person witnessToCC;
    private List<Person> victimsList;
    private List<Mail> mailsList;

    // indicates where is located the directory who contains the files for the application
    private static final String FILES_DIRECTORY = "./config/";
    // indicates file extension
    // accessible outside the class
    public static final String EXTENSION_FILE = "UTF-8";
    // separator for the different mails
    private static final String MAILS_SEPARATOR = "==";
    // minimum number of groups
    private static final int GROUPS_MINIMUM_NUMBER = 1;
    // minimum number of victims for a group
    // accessible outside the class
    public static final int VICTIMS_MINIMUM_NUMBER = 3;
    // minimum number of mails
    private static final int  MAILS_MINIMUM_NUMBER = 1;

    /**
     * constructor of the class ConfigurationManager
     */
    public ConfigurationManager() {
        victimsList = new ArrayList<Person>();
        mailsList = new ArrayList<Mail>();
    }

    @Override
    public void loadPropertiesFromFile(String fileName) throws IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream(FILES_DIRECTORY + fileName);

        // load a properties file
        prop.load(input);

        // get the property value and store it
        smtpServerAddress = prop.getProperty("smtpServerAddress");
        smtpServerPort = Integer.valueOf(prop.getProperty("smtpServerPort"));
        groupsNumber = Integer.valueOf(prop.getProperty("groupsNumber"));
        witnessToCC = new Person(prop.getProperty("witnessToCC"));

        // check if number of groups is correct
        if (groupsNumber < GROUPS_MINIMUM_NUMBER) {
            throw new IOException("The number of groups is incorrect !");
        }

        // close input
        input.close();
    }

    @Override
    public void loadVictimsListFromFile(String fileName) throws IOException {
        String fileLine;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FILES_DIRECTORY + fileName), EXTENSION_FILE));

        while ((fileLine = bufferedReader.readLine()) != null) {
            // we create a new Person for each victim
            victimsList.add(new Person(fileLine));
        }

        // check if number of victims is correct
        // we must have three victims or more
        if (victimsList.size() < VICTIMS_MINIMUM_NUMBER) {
            throw new IOException("The number of victims is incorrect !");
        }

        // close buffer
        bufferedReader.close();
    }

    @Override
    public void loadMailsListFromFile(String fileName) throws IOException {
        String emailContent = "";
        String fileLine;
        int indexLoop = 0;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FILES_DIRECTORY + fileName), EXTENSION_FILE));

        while ((fileLine = bufferedReader.readLine()) != null) {
            if (fileLine.startsWith("Subject: ")) {
                // we create a new mail
                mailsList.add(new Mail());

                // only the content of the subject is added to the Mail object
                mailsList.get(indexLoop).setSubject(fileLine.replace("Subject: ", ""));
            }
            else if (!fileLine.contentEquals(MAILS_SEPARATOR)) {
                // we store each line of the mail
                emailContent += fileLine + "\r\n";
            }
            else {
                // when we finished to read a mail, we put its content in the Mail object
                mailsList.get(indexLoop).setContent(emailContent);
                // clear for the next mail
                emailContent = "";

                // for the next mail
                indexLoop++;
            }
        }

        // check if we have the minimum number of mails required
        if (mailsList.size() < MAILS_MINIMUM_NUMBER) {
            throw new IOException("The number of mails is incorrect !");
        }

        // close buffer
        bufferedReader.close();
    }

    @Override
    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    @Override
    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    @Override
    public int getGroupsNumber() {
        return groupsNumber;
    }

    @Override
    public Person getWitnessToCC() {
        return witnessToCC;
    }

    @Override
    public List<Person> getVictimsList() {
        // for the encapsulation
        return new LinkedList<>(victimsList);
    }

    @Override
    public List<Mail> getMailsList() {
        // for the encapsulation
        return new ArrayList<>(mailsList);
    }
}
