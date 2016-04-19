package smtp;

import config.ConfigurationManager;
import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;

import java.io.*;
import java.net.Socket;

/**
 * Implementation of the ISmtpClient interface to send mails for a prank campaign.
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class SmtpClient implements ISmtpClient {
    private ConfigurationManager configManager;

    /**
     * constructor of the class SmtpClient
     *
     * @param configManager ConfigurationManager object for the connection with the smpt server
     */
    public SmtpClient(ConfigurationManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public void sendMail(Person fromPerson, Person witnessToCC, Group toPersons, Mail mail) {
        int indexBoucle;
        String line;

        Socket clientSocket = null;
        PrintWriter writer = null;
        BufferedReader bufferedReader = null;

        try {
            clientSocket = new Socket(configManager.getSmtpServerAddress(), configManager.getSmtpServerPort());

            // true for flush automatically when use printf method
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), ConfigurationManager.EXTENSION_FILE), true);
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), ConfigurationManager.EXTENSION_FILE));

            // first response of the server
            line = bufferedReader.readLine();
            System.out.println(line + "\n");

            // initiation
            writer.printf("EHLO res\r\n");

            // read response
            line = bufferedReader.readLine();
            System.out.println(line);

            // error with the SMTP server
            if (!line.startsWith("250")) {
                throw new IOException("SMTP error: " + line);
            }

            while (line.startsWith("250-")) {
                line = bufferedReader.readLine();
                System.out.println(line);
            }

            // mail from
            writer.write("MAIL FROM: ");
            writer.write(fromPerson.getMailAddress());
            writer.write("\r\n");
            writer.flush();

            // read response
            line = bufferedReader.readLine();
            System.out.println("\n" + line + "\n");

            // witness mail to
            writer.write("RCPT TO: ");
            writer.write(witnessToCC.getMailAddress());
            writer.write("\r\n");
            writer.flush();

            // read response
            line = bufferedReader.readLine();
            System.out.println(line + "\n");

            // mail to
            for (int i = 0; i < toPersons.getGroupSize(); i++) {
                writer.write("RCPT TO: ");
                writer.write(toPersons.getPersonAt(i).getMailAddress());
                writer.write("\r\n");
                writer.flush();

                // read response
                line = bufferedReader.readLine();
                System.out.println(line + "\n");
            }

            // data
            writer.printf("DATA\r\n");

            // read response
            line = bufferedReader.readLine();
            System.out.println(line + "\n");

            // specify the content type and the charset
            writer.write("Content-Type: text/plain; charset=\"" + ConfigurationManager.EXTENSION_FILE + "\"\r\n");

            // add from person
            writer.write("From: " + fromPerson.getMailAddress() + "\r\n");

            // add to persons
            writer.write("To: ");

            for (indexBoucle = 0; indexBoucle < toPersons.getGroupSize() - 1; indexBoucle++) {
                writer.write(toPersons.getPersonAt(indexBoucle).getMailAddress() + ", ");
            }

            writer.write(toPersons.getPersonAt(indexBoucle).getMailAddress() + "\r\n");

            // add cc person
            writer.write("Cc: " + witnessToCC.getMailAddress() + "\r\n");

            // add mail subject in utf-8 format (header)
            writer.write("Subject: =?" + ConfigurationManager.EXTENSION_FILE + "?Q?" + mail.getSubject() + "?=\r\n");

            // content of mail
            writer.write(mail.getContent());
            // . for terminate
            writer.write(".\r\n");
            writer.flush();

            // read response
            line = bufferedReader.readLine();
            System.out.println(line + "\n");

            // quit
            writer.printf("QUIT\r\n");

            // read response
            line = bufferedReader.readLine();
            System.out.println(line);

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (clientSocket != null){
                try {
                    clientSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (writer != null) {
                writer.close();
            }
            else if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
