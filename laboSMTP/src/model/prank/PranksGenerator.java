package model.prank;

import config.ConfigurationManager;
import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;
import smtp.SmtpClient;

import java.io.IOException;
import java.util.*;

/**
 * Class used to generate pranks with the specified victims and number of groups (of victims) and to send these pranks.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class PranksGenerator {
    private int groupsNumber;
    private int victimsNumber;
    private int victimsByGroupe;

    private ConfigurationManager configManager;
    private SmtpClient smtpClient;
    private Person witnessToCC;
    private List<Person> victimsList;
    private List<Group> groupsList;
    private List<Mail> mailsList;
    private List<Prank> pranksList;
    // to generate a random number to choice a mail from the list
    private Random generator;

    /**
     * constructor of the class PranksGenerator
     *
     * @throws IOException
     */
    public PranksGenerator() throws IOException {
        configManager = new ConfigurationManager();

        // we load the content of the needed files
        configManager.loadPropertiesFromFile("config.properties");
        configManager.loadVictimsListFromFile("victims.utf8");
        configManager.loadMailsListFromFile("mails.utf8");

        // we create the SmtpClient object from the ConfigurationManager object
        smtpClient = new SmtpClient(configManager);

        groupsNumber = configManager.getGroupsNumber();
        victimsNumber = configManager.getVictimsList().size();
        // number of victims by group
        victimsByGroupe = victimsNumber / groupsNumber;

        // check if number of groups and victims are coherent
        if (groupsNumber > 1 && victimsNumber < groupsNumber * ConfigurationManager.VICTIMS_MINIMUM_NUMBER) {
            throw new IOException("The number of victims is incorrect");
        }

        witnessToCC = configManager.getWitnessToCC();
        victimsList = configManager.getVictimsList();
        groupsList = new ArrayList<Group>();
        mailsList = configManager.getMailsList();
        pranksList = new ArrayList<Prank>();
        generator = new Random();
    }

    /**
     * launch this campaign of pranks
     */
    public void launch() {
        // first, generate groups

        // shuffle the list of victims
        Collections.shuffle(victimsList);

        // we create a group and add victims to it
        for (int i = 0; i < groupsNumber; i++) {
            groupsList.add(new Group());

            for (int j = 0; j < victimsByGroupe; j++) {
                groupsList.get(i).addPerson(victimsList.remove(0));
            }
        }

        // add the last victims at the last group (if necessary)
        while (victimsList.size() != 0) {
            groupsList.get(groupsList.size() - 1).addPerson(victimsList.remove(0));
        }

        // then, generate pranks
        sendPranks();
    }

    /**
     * generate and send pranks
     */
    private void sendPranks() {
        Mail mailToSend;

        // shuffle the list of mails
        Collections.shuffle(mailsList);

        for (int i = 0; i < groupsNumber; i++) {
            // choice a random mail in the list
            mailToSend = mailsList.get(generator.nextInt(mailsList.size()));

            pranksList.add(new Prank(smtpClient, groupsList.get(i), witnessToCC, mailToSend));
            pranksList.get(i).send();
        }
    }
}
