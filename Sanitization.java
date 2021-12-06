import java.util.*;
import java.lang.*;
public class Sanitization {
    public static void main(String [] args)
    {
        //creation of scanner
        Scanner scan= new Scanner(System.in);
        //Asks user for input
        System.out.println("Please enter a Username and Password combination");
        String username=scan.next();
        String password= scan.next();

        //Substring and char creation for checks
        String goodUser="";
        String goodPassword="";
        char usercheck='a';
        char passcheck='a';
        //Checks if user is good
        for(int i=0; i<username.length(); i++) {
            //sets the char to be a val in the original string
            usercheck = username.charAt(i);
            //if input is good, add it to a new string
            if (Character.isUpperCase(usercheck) == true || usercheck == ';') {
                goodUser += usercheck;
            } else {
                //invalid input
            }

        }
        //Checks password
        for(int i=0; i<password.length(); i++)
        {
            //pulls a char to check
            passcheck=password.charAt(i);
            //if its a good char, add it to a new string
            if(Character.isUpperCase(passcheck)==true || passcheck==';')
            {
                goodPassword+=passcheck;
            }
            else
            {
                //bad password input
            }
        }
        //print out valid inputs
        System.out.println("Username: "+goodUser);
        System.out.println("Password: "+goodPassword);
    }
}
