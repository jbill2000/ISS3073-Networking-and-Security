import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.*;
import java.io.*;
public class NetworkClient {
    public static void main(String [] args) throws IOException
    {

        //server stuff
        int portnumber = 3900;
        DataInputStream input;
        DataOutputStream output;
        Socket theClient;
        Scanner scan= new Scanner(System.in);
        theClient = new Socket("127.0.0.1", portnumber);
        //print to print the client connected
        System.out.println("Client Connected!");
        //input and output stream initialization
        output = new DataOutputStream(theClient.getOutputStream());
        input = new DataInputStream(theClient.getInputStream());

        //var initalization
        int choice=0;
        String usernameinput="";
        String passwordinput="";
        String validusername="";
        String validpassword="";
        //file writer
        FileWriter writer= new FileWriter("messages.txt",true);
        //bool vars
        boolean end=false;
        boolean valid=false;
        boolean loggedin=false;
        //bool vars
        boolean exists=false;
        //This will keep the client going
        while(end==false) {
        try
        {
            //menu
            System.out.println("What would you like to do?");
            System.out.println("1: Login");
            System.out.println("2: Logout");
            System.out.println("3: Send message");
            System.out.println("4: Turn off server");
            //menu choice and writes it to server
            choice = scan.nextInt();
            output.writeInt(choice);
            boolean marray=input.readBoolean();
            switch (choice) {
                case 1: {
                    if (loggedin == true) {
                        System.out.println("User already logged in");
                        usernameinput="";
                        passwordinput="";
                        break;
                    }
                    System.out.println("Please enter the username and password");
                    //takes in user and pass and writes it
                    usernameinput = scan.next();
                    passwordinput = scan.next();
                    output.writeUTF(usernameinput);
                    output.writeUTF(passwordinput);
                    //will see if its valid or not
                    valid = input.readBoolean();
                    //if good login then great
                    if (valid == true && loggedin == false) {
                        System.out.println("Valid login!");
                        validusername = usernameinput;
                        validpassword = passwordinput;
                        loggedin = true;
                    }
                    //depending on the error this will print a multitude of things
                    else if (valid == false && loggedin == false) {
                        System.out.println(input.readUTF());
                    }
                    //if user is logged in already
                    //message stuff
                    if (marray == false && loggedin==true) {
                        //reads in counter ie how many messages are in for that user
                        int counter = input.readInt();
                        System.out.println("You have " + counter + " unread messages");
                        //vars
                        int mcount;
                        String curmessage;
                        //for loop to read messages and display them
                        for (int i = 0; i < counter; i++) {
                            mcount = input.readInt();
                            curmessage = input.readUTF();
                            System.out.println("Message: " + mcount + ": " + curmessage);
                        }
                    }
                    if(marray==true && loggedin==true)
                    {
                        System.out.println("You have 0 unread messages");
                    }
                    break;
                }
                case 2: {
                    loggedin = input.readBoolean();
                    if (loggedin==true) {
                        String m = input.readUTF();
                        System.out.println(m + validusername);
                        validusername = "";
                        validpassword = "";
                        usernameinput = "";
                        passwordinput = "";
                        loggedin = false;
                    } else {
                        System.out.println(input.readUTF());
                    }
                    break;
                }
                case 3: {
                    if (loggedin == true) {
                        output.writeBoolean(loggedin);
                        System.out.println("Who do you want to message?");
                        String messagereciever = scan.next();
                        output.writeUTF(messagereciever);
                        exists=input.readBoolean();
                            System.out.println("What is your message? (single line)");
                            scan.nextLine();
                            String message = scan.nextLine();
                        if(exists==true) {
                            output.writeUTF(message);
                            System.out.println("Message saved for " + messagereciever);
                        }
                        else if(exists==false)
                        {
                            System.out.println("Intended recipient does not exist in the database");
                        }
                    } else {
                        System.out.println("There is not a user logged in, so you cannot message");
                        loggedin = false;
                        output.writeBoolean(loggedin);
                    }
                    break;
                }
                case 4: {
                    System.out.println("Server Shutting Down!");
                    System.out.println("Exiting!");
                    end = true;
                    break;
                }
            }
        }catch(ConnectException ce)
            {
                System.out.println("Please start server first then client");
            }
        catch(InputMismatchException ie)
        {
            System.out.println("Please input a valid menu option");
        }
        }
    }
}
