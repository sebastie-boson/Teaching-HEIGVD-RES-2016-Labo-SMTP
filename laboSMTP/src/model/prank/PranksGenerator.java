package model.prank;

import config.ConfigurationManager;
import model.mail.Person;
import smtp.SmtpClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sebbos on 16.04.2016.
 */
public class PranksGenerator {

    public void launch() {
        int groupsNumber;
        int victimsNumber;

        ConfigurationManager configManager;
        SmtpClient smtpClient;
        List<Prank> pranksList;

        configManager = new ConfigurationManager();

        configManager.loadPropertiesFromFile("config.properties");
        configManager.loadVictimsListFromFile("victims.utf8");
        configManager.loadMailsListFromFile("mails.utf8");

        smtpClient = new SmtpClient(configManager);

        groupsNumber = configManager.getGroupsNumber();
        victimsNumber = configManager.getVictimsList().size();

        // test if number of groups and victims are coherent
        if (groupsNumber > 1 && victimsNumber < groupsNumber * ConfigurationManager.VICTIMS_MINIMAL_NUMBER) {
            System.out.println("The number of victims is incorrect !");

            // we must add victims in the list
            System.exit(-1);
        }

        pranksList = new ArrayList<Prank>();

        for (int i = 0; i < groupsNumber; i++) {
            pranksList.add(new Prank(victimsNumber / groupsNumber));
        }
    }
}
