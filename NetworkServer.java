import java.nio.channels.ScatteringByteChannel;
import java.util.*;
import java.io.*;
import java.net.*;
public class NetworkServer {

    public static void main(String[] args) throws IOException {

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
            listener = new ServerSocket(portnumber);
            Socket servsocket= listener.accept();
            System.out.println("Server Connected");
            output= new DataOutputStream(servsocket.getOutputStream());
            input= new DataInputStream(servsocket.getInputStream());
            incoming = input.readChar();

             System.out.println("Please enter two numbers followed by a space");
            do {
                try {
                        check=true;
                    num1 = scan.nextInt();
                    num2 = scan.nextInt();

                    if (incoming == '/' && num2 == 0) {
                        while (num2 == 0) {
                            System.out.println("Can't Divide by 0. Please input two new numbers");
                            num1 = scan.nextInt();
                            num2 = scan.nextInt();
                        }

                    }

                } catch (InputMismatchException ie) {
                    System.out.println("Input was not a number");
                    check=false;
                    scan.nextLine();
                }
            }while(check!=true);

                switch (incoming) {
                    case '+': {
                        result = num1 + num2;
                        break;
                    }
                    case '-': {
                        result = num2 - num1;
                        break;
                    }
                    case '*': {
                        result = num1 * num2;
                        break;
                    }
                    case '/': {
                        result = (num1) / (num2);
                        break;
                    }
                }
                output.write(result);
                System.out.println("The Result is "+result);







    }

}
