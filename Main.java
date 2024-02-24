import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static void isolation() {
        String maxHeapOption = "-Xmx2687M";
        System.setProperty("java.vm.options", maxHeapOption);

        String minHeapOption = "-Xms0M";
        System.setProperty("java.vm.options", minHeapOption);

    }

    static public String reset = "\u001B[0m";
    static public String red = "\u001B[31m";
    static public String green = "\u001B[32m";
    static public String blue = "\u001B[36m";

    static Scanner scan = new Scanner(System.in);
    private static Validator test = new Validator();
    private static Database d = new Database();

    public static void main(String[] args) throws IOException, ClassNotFoundException, Exception {

        isolation();

        try {
            

            System.out.print(blue + "Welcome to recommendation system application to suggest a suitable bachelor degree program" + reset);
            System.out.println();

            // Part 1: Sign in or create an account
            System.out.print("Enter 1 for sign in, 2 for create an account: ");
            String input = scan.nextLine();

            int tries = 0;

            while (!test.isInt(input)) {

                System.out.print("Enter 1 or 2: ");
                input = scan.nextLine();

                if (tries > 6) {
                    System.out.println(red + "Too many attempts" + reset + ", try again after 15 min");
                    System.exit(0);
                }

                tries++;
            }
        
            int x = Integer.parseInt(input);

          
            System.out.println();

            switch (x) {
                case 1:
                    Signin();
                    break;
                case 2:
                    CreateanAccount();
                    break;

            }

            // Part 2: Major selection based on criteria
            Major[] majors = new Major[5];
            // Initialize majors array with data
            majors[0] = new Major("SE", 5000, 3.0f, "Low", "Software Engineer", 3.5f);
            majors[1] = new Major("CY", 7000, 3.5f, "Very high", "Cybersecurity Engineer", 4.0f);
            majors[2] = new Major("CSAI", 5000, 3.0f, "High", "Software Developer", 3.5f);
            majors[3] = new Major("CNE", 5000, 3.0f, "High", "Network Engineer", 3.5f);
            majors[4] = new Major("DS", 6000, 3.5f, "Medium", "Data Analyst/Data Scientist", 3.5f);

            System.out.print("Enter minimum acceptable industry salary: ");
            input = scan.nextLine();

            tries = 0;

            while (!test.isValidSalary(input)) {
                System.out.print(red + "Invalid salary value." + reset + " Please enter a valid value: ");
                input = scan.nextLine();

                if (tries > 6) {
                    System.out.println(red + "Too many attempts" + reset + ", try again after 15 min");
                    System.exit(0);
                }
                tries++;
            }
            double minimumSalary = Double.parseDouble(input);

            System.out.print("Enter previous GPA: ");
            input = scan.nextLine();

            tries = 0;

      
            while (!test.isValidGPA(input)) {
                System.out.print(red + "Invalid GPA value." + reset + " Please enter a positive value between 0.1 and 5: ");
                input = scan.nextLine();

                if (tries > 6) {
                    System.out.println(red + "Too many attempts" + reset + ", try again after 15 min");
                    System.exit(0);
                }
                tries++;
            }
            
            double previousGPA = Double.parseDouble(input);

            System.out.print("Enter computer programming interest (on a scale of: Low - Medium - High - Very high): ");

            tries = 0;
            String programmingInterest = scan.nextLine();

            while (!test.isValidScaleInput(programmingInterest)) {
                System.out.print(red + "Invalid\n" + reset + "Enter computer programming interest (on a scale of: Low - Medium - High - Very high): ");
                programmingInterest = scan.nextLine();

                if (tries > 6) {
                    System.out.println(red + "Too many attempts" + reset + ", try again after 15 min");
                    System.exit(0);
                }
                tries++;
            }

            for (int i = 0; i < majors.length; i++) {
                if (minimumSalary <= majors[i].getMinIndustrySalary()) {
                    majors[i].givePoint();
                }
            }

            for (int i = 0; i < majors.length; i++) {
                if (previousGPA >= majors[i].getPreviousGPA()) {
                    majors[i].givePoint();
                }
            }

            for (int i = 0; i < majors.length; i++) {
                if (programmingInterest.equalsIgnoreCase(majors[i].getProgrammingInterest())) {
                    majors[i].givePoint();
                }
            }

            int maxP = majors[0].getPoint();

            for (int i = 1; i < majors.length; i++) {
                if (maxP < majors[i].getPoint()) {
                    maxP = majors[i].getPoint();
                }
            }

            for (int i = 0; i < majors.length; i++) {
                if (maxP == majors[i].getPoint()) {
                    System.out.println("Major name: " + majors[i].getName());
                    System.out.println("Minimum Industry Salary: " + majors[i].getMinIndustrySalary());
                    System.out.println("Minimum Pre GPA: " + majors[i].getPreviousGPA());
                    System.out.println("Interest Level: " + majors[i].getProgrammingInterest());
                    System.out.println("Job Category: " + majors[i].getJobCategory());
                    System.out.println("Acceptable GPA: " + majors[i].getAcceptableGPA());
                    System.out.println("extra hours for study: " + majors[i].getPreviousGPA() +"h");
                    System.out.println();
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println(red + "There appears to be a problem with the system." + reset + " We apologize. Try running the program again :) ");
            System.exit(0);
        }
    }

    private static void Signin() throws IOException, Exception {


        // Clearing the input buffer
        int tries = 0;
        System.out.print("Enter your email: ");
        String Email = scan.nextLine();

        while (!d.isThereEmail(Email)) {
            // Prompting the user for another email or option to exit
            System.out.print(red + "There is no such email\n"+reset+"Enter another email: ");
            Email = scan.nextLine();

            if (tries > 6) {
                System.out.println(red + "Too many attempts" + reset + ", try again after 15 min");
                System.exit(0);
            }
            tries++;

        }

        System.out.print("Enter your password: ");
        String password = scan.nextLine();

        tries = 0;
        // Checking if the entered password matches the stored password hash for the email
        while (!(d.hash(password).equals(d.retrieve(Email)))) {

            // Prompting the user to enter the password again
            System.out.print(red + "Wrong password" + reset + ", Enter again: ");
            password = scan.nextLine();

            tries++;

            // Exiting the program if the user exceeds the maximum number of attempts
            if (tries > 6) {
                System.out.print(red + "Too many attempts" + reset + ", try again after 15 min");
                System.exit(0);
            }
        }

        d.log(Email);

        // Checking if the user is an admin
        if (test.isAdmin(Email)) {
            // Granting access to the admin
            d.getAccess();
            System.exit(0);
        }
    }

    private static void CreateanAccount() throws ClassNotFoundException, SQLException, IOException {


        System.out.print("Enter your email: ");
        String email = scan.nextLine();

        int tries = 0;
        // Checking if the entered email is already used
        while (d.isThereEmail(email)) {
            // Prompting the user to enter another email
            System.out.print(red + "This email already used," + reset + " enter another email: ");
            email = scan.nextLine();

            if (tries > 6) {
                System.out.println(red + "Too many attempts" + reset + ", try again after 15 min");
                System.exit(0);
            }
            tries++;
        }

        // Checking if the entered email is in a correct format
        tries = 0;
        while (!test.isEmailCorrect(email)) {
            // Prompting the user to enter a valid email
            System.out.print(red + "Invalid email" + reset + ", The email should be in this format>> " + green + "google@gmail.com" + reset
                    + "\nEnter again: ");
            email = scan.nextLine();

            if (tries > 6) {
                System.out.println(red + "Too many attempts" + reset + ", try again after 15 min");
                System.exit(0);
            }
            tries++;
        }

        System.out.print("Enter password: ");
        String password = scan.nextLine();
        System.out.println("");

        // Checking if the entered password is valid (contains specific criteria)
        tries = 0;
        while (!test.isPasswordValid(password, email)) {
            // Prompting the user to enter a valid password
            System.out.print(red + "Invalid password." + reset + " The password should be between 8 and 20 digits contain 2 uppercase letters, 2 lowercase letters, and 2 characters or numbers"
                    + "\nEnter again: ");
            password = scan.nextLine();

            if (tries > 6) {
                System.out.println(red + "Too many attempts" + reset + ", try again after 15 min");
                System.exit(0);
            }
            tries++;
        }

        // Inserting the email and password into the database
        d.insert(email, password);

        d.log(email);
    }

}
