package model.prank;

import config.ConfigurationManager;
import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;
import smtp.SmtpClient;

import java.util.*;

/**
 * Created by sebbos on 16.04.2016.
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
    private Random generator;

    public PranksGenerator() throws IllegalArgumentException {
        configManager = new ConfigurationManager();

        configManager.loadPropertiesFromFile("config.properties");
        configManager.loadVictimsListFromFile("victims.utf8");
        configManager.loadMailsListFromFile("mails.utf8");

        smtpClient = new SmtpClient(configManager);

        groupsNumber = configManager.getGroupsNumber();
        victimsNumber = configManager.getVictimsList().size();
        victimsByGroupe = victimsNumber / groupsNumber;

        // test if number of groups and victims are coherent
        if (groupsNumber > 1 && victimsNumber < groupsNumber * ConfigurationManager.VICTIMS_MINIMAL_NUMBER) {
            throw new IllegalArgumentException("The number of victims is incorrect");
        }

        witnessToCC = configManager.getWitnessToCC();
        victimsList = configManager.getVictimsList();
        groupsList = new ArrayList<Group>();
        mailsList = configManager.getMailsList();
        pranksList = new ArrayList<Prank>();
        generator = new Random();
    }

    public void launch() {
        // first, generate groups

        // shuffle the list of victims
        Collections.shuffle(victimsList);

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
