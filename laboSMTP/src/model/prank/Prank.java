package model.prank;

import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;
import smtp.SmtpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebbos on 16.04.2016.
 */
public class Prank {
    private SmtpClient smtpClient;
    private Group prankGroup;
    private Person witnessToCC;
    private Mail mailToSend;

    public Prank(SmtpClient smtpClient, Group prankGroup, Person witnessToCC, Mail mailToSend) {
        this.smtpClient = smtpClient;
        this.prankGroup = prankGroup;
        this.witnessToCC = witnessToCC;
        this.mailToSend = mailToSend;
    }

    public void send() {
        // the first person of the group is the sender
        // the other persons are the receivers
        smtpClient.sendMail(prankGroup.removePersonAt(0), witnessToCC, prankGroup, mailToSend);
    }
}
