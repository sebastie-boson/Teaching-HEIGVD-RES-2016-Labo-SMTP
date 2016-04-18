package model.prank;

import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;
import smtp.SmtpClient;

/**
 * Class that represents a prank that would be send to the specified persons.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class Prank {
    private SmtpClient smtpClient;
    private Group prankGroup;
    private Person witnessToCC;
    private Mail mailToSend;

    /**
     * constructor of the class Prank
     *
     * @param smtpClient object that contains the informations for the connection with a smpt server
     * @param prankGroup group of persons for the prank
     * @param witnessToCC witness of the prank
     * @param mailToSend object Mail with its subject and content that would be send
     */
    public Prank(SmtpClient smtpClient, Group prankGroup, Person witnessToCC, Mail mailToSend) {
        this.smtpClient = smtpClient;
        this.prankGroup = prankGroup;
        this.witnessToCC = witnessToCC;
        this.mailToSend = mailToSend;
    }

    /**
     * launch the send of the prank
     */
    public void send() {
        // the first person of the group is the sender
        // the other persons are the receivers
        smtpClient.sendMail(prankGroup.removePersonAt(0), witnessToCC, prankGroup, mailToSend);
    }
}
