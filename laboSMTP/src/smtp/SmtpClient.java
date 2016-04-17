package smtp;

import config.ConfigurationManager;
import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;

import java.io.*;
import java.net.Socket;
import java.nio.charset.CharsetEncoder;

/**
 * Created by sebbos on 15.04.2016.
 */
public class SmtpClient implements ISmtpClient {
    private ConfigurationManager configManager;


    public SmtpClient(ConfigurationManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public void sendMail(Person fromPerson, Person witnessToCC, Group toPersons, Mail mail) {
        int indexBoucle;

        Socket clientSocket = null;
        OutputStream os = null;
        InputStream is = null;

        final int BUFFER_SIZE = 500;

        try {
            clientSocket = new Socket(configManager.getSmtpServerAddress(), configManager.getSmtpServerPort());
            os = clientSocket.getOutputStream();
            is = clientSocket.getInputStream();

            ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int newBytes;

            // read the response of the server
           /* while ((newBytes = is.read(buffer)) != -1) {
                responseBuffer.write(buffer, 0, newBytes);
                System.out.println("Response : " + responseBuffer);
            }*/

            newBytes = is.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // initiation
            os.write(("EHLO res\r\n").getBytes());
            os.flush();

            newBytes = is.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // mail from
            os.write(("MAIL FROM: " + fromPerson.getMailAddress() + "\r\n").getBytes());
            os.flush();

            newBytes = is.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // witness mail to
            os.write(("RCPT TO: " + witnessToCC.getMailAddress() + "\r\n").getBytes());
            os.flush();

            newBytes = is.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // mail to
            for (int i = 0; i < toPersons.getGroupSize(); i++) {
                os.write(("RCPT TO: " + toPersons.getPersonAt(i).getMailAddress() + "\r\n").getBytes());
                os.flush();

                newBytes = is.read(buffer);
                responseBuffer.write(buffer, 0, newBytes);
                System.out.println(responseBuffer);
            }

            // data
            os.write("DATA\r\n".getBytes());
            os.flush();

            newBytes = is.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // add from person
            os.write(("From: " + fromPerson.getMailAddress() + "\r\n").getBytes());

            // add to persons
            os.write(("To: ").getBytes());

            for (indexBoucle = 0; indexBoucle < toPersons.getGroupSize() - 1; indexBoucle++) {
                os.write((toPersons.getPersonAt(indexBoucle).getMailAddress() + ", ").getBytes());
            }

            os.write((toPersons.getPersonAt(indexBoucle).getMailAddress() + "\r\n").getBytes());

            // add cc person
            os.write(("Cc: " + witnessToCC.getMailAddress() + "\r\n").getBytes());

            // content of mail
            // . for terminate
            os.write((mail.getContent() + "\r\n.\r\n").getBytes());
            os.flush();

            newBytes = is.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // quit
            os.write("quit\r\n".getBytes());
            os.flush();

            newBytes = is.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

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
            else if (os != null) {
                try {
                    os.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
