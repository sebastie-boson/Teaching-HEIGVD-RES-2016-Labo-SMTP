package config;

import model.mail.Mail;
import model.mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by sebbos on 15.04.2016.
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
    private static final String EXTENSION_FILE = "UTF-8";
    // separator for the different mails
    private static final String MAILS_SEPARATOR = "==";
    // minimal victims for a group
    // accessible outside the class
    public static final int VICTIMS_MINIMAL_NUMBER = 3;

    /**
     * constructeur of the class ConfigurationManager
     */
    public ConfigurationManager() {
        victimsList = new ArrayList<Person>();
        mailsList = new ArrayList<Mail>();
    }

    @Override
    public void loadPropertiesFromFile(String fileName) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(FILES_DIRECTORY + fileName);

            // load a properties file
            prop.load(input);

            // get the property value and stock it
            smtpServerAddress = prop.getProperty("smtpServerAddress");
            smtpServerPort = Integer.valueOf(prop.getProperty("smtpServerPort"));
            groupsNumber = Integer.valueOf(prop.getProperty("groupsNumber"));
            witnessToCC = new Person(prop.getProperty("witnessToCC"));

            // test if number of groups is correct
            if (groupsNumber < 1) {
                System.out.println("The number of group is incorrect !");

                // we must change the number of group
                System.exit(-1);
            }

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (input != null){
                try {
                    input.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void loadVictimsListFromFile(String fileName) {
        BufferedReader bufferedReader = null;
        String fileLine;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FILES_DIRECTORY + fileName), EXTENSION_FILE));

            while ((fileLine = bufferedReader.readLine()) != null)   {
                victimsList.add(new Person(fileLine));
            }

            // test if number of victims is correct
            // we must have three victims or more
            if (victimsList.size() < VICTIMS_MINIMAL_NUMBER) {
                System.out.println("The number of victims is incorrect !");

                // we must add victims in the list
                System.exit(-1);
            }

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void loadMailsListFromFile(String fileName) {
        BufferedReader bufferedReader = null;
        String emailContent = "";
        String fileLine;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FILES_DIRECTORY + fileName), EXTENSION_FILE));

            while ((fileLine = bufferedReader.readLine()) != null)   {
                if (!fileLine.contentEquals(MAILS_SEPARATOR)) {
                    emailContent += fileLine + "\n";
                }
                else {
                    mailsList.add(new Mail(emailContent));

                    emailContent = "";
                }
            }

            if (mailsList.size() < 1) {
                System.out.println("The number of mails is incorrect !");

                // we must add mails in the list (one at least)
                System.exit(-1);
            }

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
