import java.util.*;
import java.io.*;
import java.net.*;
public class NetworkServer {

    public static void main(String[] args) throws IOException {
        //variables
        int portnumber= 3900;
        Scanner scan= new Scanner(System.in);
        DataInputStream input;
        DataOutputStream output;
        ServerSocket listener;
         int num1=0;
         int num2=0;
        int result=0;
        boolean check=false;
        char incoming='a';
            //initalization for listener socket and data streams as well as printing server was connected
            listener = new ServerSocket(portnumber);
            Socket servsocket= listener.accept();
            System.out.println("Server Connected");
            output= new DataOutputStream(servsocket.getOutputStream());
            input= new DataInputStream(servsocket.getInputStream());
            //reads in the operator from the client
            incoming = input.readChar();
            //tells user to enter two numbers
             System.out.println("Please enter two numbers followed by a space");
             //keeps looping until the numbers are valid
            do {
                try {
                        check=true;
                    num1 = scan.nextInt();
                    num2 = scan.nextInt();
                        //accounts for divide by 0 errors
                    if (incoming == '/' && num2 == 0) {
                        while (num2 == 0) {
                            System.out.println("Can't Divide by 0. Please input two new numbers");
                            num1 = scan.nextInt();
                            num2 = scan.nextInt();
                        }

                    }
                    //catches anything other than a number
                } catch (InputMismatchException ie) {
                    System.out.println("Input was not a number");
                    check=false;
                    scan.nextLine();
                }

            }while(check!=true);
                //switch case to figure out which operation to do with the operator the user passed in
                switch(incoming) {
                    case '+': {
                        result = (num1 + num2);
                        break;
                    }
                    case '-': {
                        result = (num1 - num2);
                        break;
                    }
                    case '*': {
                        result = (num1 * num2);
                        break;
                    }
                    case '/': {
                        result = (num1) / (num2);
                        break;
                    }
                }
                //writes the result to the client for printing
                output.writeInt(result);
                //prints the result on server side.
                System.out.println("The Result is "+result);







    }

}
