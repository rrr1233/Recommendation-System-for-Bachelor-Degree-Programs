
class Validator {
    

    /**
     * Checks if the given password is valid. The password must meet the
     * following criteria: - Length must be at least 8 characters - Must not be
     * the same as the email - Must contain at least 2 lowercase letters, 2
     * uppercase letters, and 2 numbers or special characters
     *
     * @param password the password to validate
     * @param email the associated email for additional validation
     * @return true if the password is valid, false otherwise
     *
     */
    public boolean isPasswordValid(String password, String email) {
        int lower = 0, upper = 0, ch = 0, i = 0, test;
        if (password.length() < 8 && password.length() > 30) // To check length
        {
            return false;
        } else if (email.equalsIgnoreCase(password)) {
            return false;
        } else {
            while (password.length() > i) {
                test = password.charAt(i);
                if (test >= 97 && test <= 122) {
                    lower++; // Count number of lowercase letters
                } else if (test >= 65 && test <= 90) {
                    upper++; // Count number of uppercase letters
                } else {
                    ch++; // Count numbers and special characters
                }
                i++;
            }

            if (lower < 2 || upper < 2 || ch < 2) // Check if password follows the policies
            {
                return false;
            }

            return true;
        }
    }

    /**
     * Checks if the given email is in correct format. The email must have a
     * minimum length of 5 characters, contain an '@' symbol, and have a valid
     * domain name.
     *
     * @param email the email to check
     * @return true if the email is in correct format, false otherwise
     */
    public boolean isEmailCorrect(String email) {
        // Email validation criteria
        int minLength = 5, maxLength = 20;
        int atIndex = email.indexOf('@');
        int dotIndex = email.lastIndexOf('.');

        return email.length() >= minLength && email.length() <= maxLength && atIndex > 0 && dotIndex > atIndex && dotIndex < email.length() - 1;
    }

    /**
     * Checks if the given email belongs to an admin.
     * @param email the email to check
     * @return true if the email belongs to an admin, false otherwise
     */
    public boolean isAdmin(String email) {
        String adminEmail = "Admin@gmail.com";
        return adminEmail.equalsIgnoreCase(email);
    }


    //to check if input was number or not
    public static boolean isValidSalary(String value) {
        try {
            Double.parseDouble(value);
            if (Double.parseDouble(value) <= 0)
            return false;
            
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
        
    }
     public static boolean isInt(String value) {
        try {
            int x = Integer.parseInt(value);
            if(x!=1&&x!=2&&x!=3) return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
        
    }

    //to check if the programming interest on the scale
    public static boolean isValidScaleInput(String input) {
        String lowerCaseInput = input.toLowerCase();
        if (lowerCaseInput.equals("low")
                || lowerCaseInput.equals("medium")
                || lowerCaseInput.equals("high")
                || lowerCaseInput.equals("very high")) {
            return true;
        } else {
            return false;
        }
    }

    //to check if the GPA on range
    public static boolean isValidGPA(String value) {
          try {
           double gpa = Double.parseDouble(value);
           
            if ( gpa >= 0.1 && gpa <= 5)
            return true;
            
            return false;

        } catch (NumberFormatException e) {
            return false;
        }
    }

}
