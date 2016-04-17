import model.prank.PranksGenerator;

public class MailRobot {

    public static void main(String[] args) {
        PranksGenerator pranksGenerator = null;

        try {
            pranksGenerator = new PranksGenerator();
        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());

            System.exit(-1);
        }

        pranksGenerator.launch();
    }
}
