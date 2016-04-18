import model.prank.PranksGenerator;

import java.io.IOException;

/**
 * Principal class of the program that generate pranks with the specified victims (mail address).
 *
 * @author Mathieu Urstein and Sébastien Boson
 */
public class MailRobot {
    /**
     * entry point for the program
     *
     * @param args args of the program
     */
    public static void main(String[] args) {
        PranksGenerator pranksGenerator = null;

        // try catch for eventual exceptions
        try {
            pranksGenerator = new PranksGenerator();
        }
        catch (IOException ex) {
            ex.printStackTrace();

            // quit the program
            System.exit(-1);
        }

        pranksGenerator.launch();
    }
}
