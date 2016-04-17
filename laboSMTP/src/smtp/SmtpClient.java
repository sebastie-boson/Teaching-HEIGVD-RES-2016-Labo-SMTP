package smtp;

import config.ConfigurationManager;
import model.mail.Group;
import model.mail.Mail;
import model.mail.Person;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by sebbos on 15.04.2016.
 */
public class SmtpClient implements ISmtpClient {
    private ConfigurationManager configManager;


    public SmtpClient(ConfigurationManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public void sendMail(Group group, Person witnessToCC, Mail mail) {
        Socket clientSocket = null;
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;

        final int BUFFER_SIZE = 500;

        try {
            clientSocket = new Socket(configManager.getSmtpServerAddress(), configManager.getSmtpServerPort());
            bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());

            ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int newBytes;

            // read the response of the server
           /* while ((newBytes = is.read(buffer)) != -1) {
                responseBuffer.write(buffer, 0, newBytes);
                System.out.println("Response : " + responseBuffer);
            }*/

            newBytes = bufferedInputStream.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // initiation
            bufferedOutputStream.write("ehlo res\r\n".getBytes());
            bufferedOutputStream.flush();

            newBytes = bufferedInputStream.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // mail from
            bufferedOutputStream.write(("MAIL FROM: " + fromPerson.getMailAddress() + "\r\n").getBytes());
            bufferedOutputStream.flush();

            newBytes = bufferedInputStream.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // mail to
            for (int i = 0; i < toPersons.size(); i++) {
                bufferedOutputStream.write(("RCPT TO: " + toPersons.get(i).getMailAddress() + "\r\n").getBytes());
                bufferedOutputStream.flush();

                newBytes = bufferedInputStream.read(buffer);
                responseBuffer.write(buffer, 0, newBytes);
                System.out.println(responseBuffer);
            }

            // data
            bufferedOutputStream.write("DATA\r\n".getBytes());
            bufferedOutputStream.flush();

            newBytes = bufferedInputStream.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // content of mail
            // . for terminate
            bufferedOutputStream.write((mail.getContent() + "\r\n.\r\n").getBytes());
            bufferedOutputStream.flush();

            newBytes = bufferedInputStream.read(buffer);
            responseBuffer.write(buffer, 0, newBytes);
            System.out.println(responseBuffer);

            // quit
            bufferedOutputStream.write("quit\r\n".getBytes());
            bufferedOutputStream.flush();

            newBytes = bufferedInputStream.read(buffer);
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
            else if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
