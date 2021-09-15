import java.util.*;
import java.io.*;
import java.net.*;
public class NetworkClient {
    public static void main(String[] args) throws IOException  {

        int portnumber = 3900;
        DataInputStream input;
        boolean valid= true;
        DataOutputStream output;
        Socket theClient;
        char theOperator;
                Scanner scan= new Scanner(System.in);
                theClient = new Socket("127.0.0.1", portnumber);
                System.out.println("Client Connected!");
                output= new DataOutputStream(theClient.getOutputStream());
                input= new DataInputStream(theClient.getInputStream());
                System.out.println("Please enter an operator. Either + - * or /");
                String operator=scan.next();
                if(!operator.equals("+") && !operator.equals("-") && !operator.equals("*") && !operator.equals("/"))
                {
                    valid=false;
                    while(valid==false) {


                        System.out.println("Operator wasn't valid. Please enter a new operator");
                        operator=scan.next();
                        //theOperator=operator.charAt(0);
                        if(operator.equals("+")||operator.equals("-")||operator.equals("*")||operator.equals("/"))
                        {
                            System.out.println("Valid Operator. Thank you");
                            valid=true;
                        }


                    }

                }
                output.writeChars(operator);
                //Now client will receive results from server
                int result= input.read();
                System.out.println("The Result is "+result);









        }


    }
