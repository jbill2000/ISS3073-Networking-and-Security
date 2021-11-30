import java.util.*;
import java.io.*;
import java.net.*;
public class NetworkClient {
    public static void main(String[] args) throws IOException  {
        //Try catch to catch there
        try{
            //variables
        int portnumber = 3900;
        DataInputStream input;
        boolean valid= true;
        DataOutputStream output;
        Socket theClient;
        char theOperator;
                //scanner and socket creation
                Scanner scan= new Scanner(System.in);
                theClient = new Socket("127.0.0.1", portnumber);
                    //print to print the client connected
                   System.out.println("Client Connected!");
                   //input and output stream initialization
                   output = new DataOutputStream(theClient.getOutputStream());
                   input = new DataInputStream(theClient.getInputStream());
                   //asks for an operator and reads it in
                   System.out.println("Please enter an operator. Either + - * or /");
                   String operator = scan.next();
                   //if operator is invalid keep looping
                   if (!operator.equals("+") && !operator.equals("-") && !operator.equals("*") && !operator.equals("/")) {
                       valid = false;
                       while (valid == false) {

                            //keep reading an operator till its valid
                           System.out.println("Operator wasn't valid. Please enter a new operator");
                           operator = scan.next();
                           //Checks if operator is valid and quits the loop
                           if (operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/")) {
                               System.out.println("Valid Operator. Thank you");
                               valid = true;
                           }


                       }

                   }
                   //sends the operator to the server
                   output.writeChars(operator);
                   //Now client will receive results from server

                   int result = input.readInt();
                   //prints the result
                   System.out.println("The Result is " + result);
                   //tells client to start server first
               }catch(ConnectException ce)
               {
                   System.out.println("Connection Refused. Please Start the server then the client.");
               }








        }


    }
