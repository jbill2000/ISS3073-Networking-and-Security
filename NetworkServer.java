import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class NetworkServer {
    public static void main(String[] args) throws IOException {
        //vars
        int portnumber = 3900;
        Scanner scan = new Scanner(System.in);
        DataInputStream input;
        DataOutputStream output;
        ServerSocket listener;
        //more vars
        int choice = 0;
        listener = new ServerSocket(portnumber);
        Socket servsocket = listener.accept();
        System.out.println("Server Connected");
        output = new DataOutputStream(servsocket.getOutputStream());
        input = new DataInputStream(servsocket.getInputStream());
        //more vars
        boolean end = false;
        boolean validLogin = false;
        String usernameinput = "";
        String passwordinput = "";
        //reads these from file
        String username = "";
        String password = "";

        //File Scanner
        File file1 = new File("shadow.txt");
        Scanner fscan = new Scanner(file1);
        Scanner fscan2;

        //sets count to 0 for later
        int count = 0;
        //file writer creation
        FileWriter writer = new FileWriter("messages.txt", true);
        BufferedWriter buff = new BufferedWriter(writer);
        //creates an arraylist for use
        ArrayList<String> database = new ArrayList<String>();
        ArrayList<String> messages = new ArrayList<String>();


        //reads in data to arraylist
        while (fscan.hasNext()) {
            database.add(fscan.next());
            database.add(fscan.next());
        }
        //file stuff
        File file2 = new File("messages.txt");
        if (file2.createNewFile()) {
            System.out.println("File created!" + file2.getName());

        }
        //sets file scanner to messages.txt
        fscan2 = new Scanner(file2);


        //if array is empty set it to true
        boolean malempty = false;

        //write to client


        //vars
        boolean loggedin = false;
        boolean exists = true;
        boolean loggedout=false;
        FileWriter writer3 = new FileWriter("messages.txt", true);

        //while 4 is not chosen ie end is not true
        while (end == false) {
            try {
                choice = input.readInt();
                //while loop to see if any data needs reading from file
                while (fscan2.hasNext()) {
                    //user
                    messages.add(fscan2.next());
                    //message
                    messages.add(fscan2.nextLine());
                }
                if (messages.isEmpty() == true) {
                    malempty = true;
                    output.writeBoolean(malempty);
                }
                if (messages.isEmpty() == false) {
                    malempty = false;
                    output.writeBoolean(malempty);
                }
                switch (choice) {
                    case 1: {
                        usernameinput = input.readUTF();
                        passwordinput = input.readUTF();
                        //for loop to check login
                        for (int i = 0; i < database.size() - 1; i += 2) {
                            //if user and pass are good then great!
                            if (database.get(i).equalsIgnoreCase(usernameinput) && database.get(i + 1).equalsIgnoreCase(passwordinput)) {
                                validLogin = true;
                                loggedin = true;
                                output.writeBoolean(validLogin);
                                exists = true;
                                break;
                            }
                            //if pwd is wrong tell client
                            if (database.get(i).equalsIgnoreCase(usernameinput) && !database.get(i + 1).equalsIgnoreCase(passwordinput)) {

                                String invalidpwd = "Invalid pwd";
                                validLogin = false;
                                output.writeBoolean(validLogin);
                                output.writeUTF(invalidpwd);
                                exists = true;
                                break;
                            }
                            //if usrname is wrong till client
                            if (!database.get(i).equalsIgnoreCase(usernameinput) && database.contains(passwordinput)) {
                                String invalidusr = "Invalid user";
                                validLogin = false;
                                output.writeBoolean(validLogin);
                                output.writeUTF(invalidusr);
                                exists = true;
                                break;
                            }
                            //if the name does not exist tell client
                            if (!database.contains(usernameinput)) {
                                validLogin = false;
                                output.writeBoolean(validLogin);
                                output.writeUTF("User does not exist in database");
                                exists = false;
                                break;
                            }
                        }
                        //if the arraylist is not empty it will count the occurences of a user
                        if (malempty == false && loggedin == true) {
                            count = 0;
                            for (int i = 0; i < messages.size(); i++) {
                                if (messages.get(i).equals(usernameinput)) {
                                    count++;
                                }
                            }
                            //writes count
                            output.writeInt(count);
                            //counts messages
                            int mcount = 1;
                            //this will display the msg,count the msg and delete them after
                            for (int i = 0; i < messages.size() - 1; i++) {
                                if (messages.get(i).equals(usernameinput)) {
                                    output.writeInt(mcount);
                                    output.writeUTF(messages.get(i + 1));
                                    messages.remove(messages.get(i + 1));
                                    mcount++;
                                }
                            }
                            //this deletes the username after reading
                            for (int i = 0; i < messages.size(); i++) {
                                if (messages.contains(usernameinput)) {
                                    messages.remove(usernameinput);
                                }
                            }
                            //overwrites the file so its fresh
                            FileWriter writer2 = new FileWriter("messages.txt");
                            writer2.write("");
                            writer2.close();
                        }
                        break;
                    }
                    case 2: {
                        //if username input is nothing
                        if (loggedin == false) {
                            output.writeBoolean(loggedin);
                            output.writeUTF("There is not a user logged in.");
                        }
                        //if the user does not exist
                        else if (!database.contains(usernameinput)) {
                            loggedin = false;
                            output.writeBoolean(loggedin);
                        }
                        //if user is logged in already
                        else if (loggedin == true) {
                            usernameinput = "";
                            passwordinput = "";
                            output.writeBoolean(loggedin);
                            output.writeUTF("Logging out ");
                            loggedin = false;
                        }
                        break;
                    }
                    case 3: {
                        boolean test = input.readBoolean();
                        String messagereciever = input.readUTF();
                        if (!database.contains(messagereciever)) {
                            exists = false;
                        } else {
                            exists = true;
                        }
                        if(exists == false) {
                            output.writeBoolean(exists);
                        } else {
                            output.writeBoolean(exists);
                        }
                        //if there is a user logged in do this, if not do nothing
                        if (test == true && exists == true) {

                            String incomingm = input.readUTF();
                            buff.write(messagereciever + " ");
                            buff.write(incomingm + "\n");
                            messages.add(messagereciever);
                            messages.add(incomingm);
                            buff.flush();
                        }
                        if(test==false)
                        {
                            //do nothing
                        }
                        break;
                    }
                    case 4: {
                        //will write the previous messages back from array list
                        if (malempty != true) {
                            for (int i = 0; i < messages.size() - 1; i++) {
                                writer3.append(messages.get(i) + messages.get(i + 1) + "\n");
                            }
                        }
                        writer3.close();
                        buff.close();
                        loggedout=true;
                        end = true;
                        break;
                    }
                }
            } catch (InputMismatchException ie) {
                System.out.println("Please input a valid menu option");
            }
            catch(EOFException eof)
            {
                end=true;
            }
        }
    }
}

