import java.util.*;
public class Connectfour {

//if column is full this num will get incremented
static int count=0;
    public static void main(String [] args) {
        {
            //scanner input
            Scanner input= new Scanner(System.in);
            //2D array creation
            String[][] gamearr = new String[6][7];
            //variables
                boolean gameover=false;
                int player1=0;
                int player2=0;

                //creates gameboard
            for (int m = 0; m < 6; m++) {
                for (int n = 0; n < 7; n++) {
                    gamearr[m][n] = "." + " ";
                }
            }
            printBoard(gamearr);
            System.out.println();
            //while loop to keep the game going
            while(gameover!=true) {
                try {

                    System.out.println("Player 1: Please enter the column you wish to play in (1-7) ");
                    player1 = input.nextInt() - 1;
                    //check to see if column is full
                    Boolean check=arrayFullcheck(player1,gamearr);
                    //if column is full, let user know and ask for new input and check again
                    if(check==true)
                    {
                        //if user continues to try inputting into a full column
                        while(check==true) {

                            System.out.println("That column is full, please try again");
                            System.out.println("Player 1: Please enter the column you wish to play in (1-7) ");
                            player1 = input.nextInt() - 1;
                            check = arrayFullcheck(player1, gamearr);
                        }
                    }

                    //if the column isnt full, add a 1 to the column.
                    if(check==false){
                        for (int m = 6; m > 0; m--) {

                            if (gamearr[m - 1][player1] == "." + " ") {
                                gamearr[m - 1][player1] = "1" + " ";
                                break;
                            }
                        }
                    }

                    //prints the gameboard
                    printBoard(gamearr);

                    //checks if player1 wins vertically
                    for (int i = 0; i < 3; i++) {
                        //vertical check
                        if (gamearr[i][player1] == "1" + " " && gamearr[i + 1][player1] == "1" + " " && gamearr[i + 2][player1] == "1" + " " && gamearr[i + 3][player1] == "1" + " ") {
                            gameover = true;
                            System.out.println("\nCongrats Player 1!");
                        }

                    }
                    //Horizontal check for victory
                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < 3; j++) {

                            if (gamearr[i][j] == "1" + " " && gamearr[i][j + 1] == "1" + " " && gamearr[i][j + 2] == "1" + " " && gamearr[i][j + 3] == "1" + " ") {
                                gameover = true;
                                System.out.println("\nCongrats Player 1!");
                            }
                        }
                    }

                    //diagonal check for victory
                    for (int i = 3; i < 6; i++) {
                        for (int j = 0; j < 3; j++) {


                            if (gamearr[i][j] == "1" + " " && gamearr[i - 1][j + 1] == "1" + " " && gamearr[i - 2][j + 2] == "1" + " " && gamearr[i - 3][j + 3] == "1" + " ") {
                                gameover = true;
                                System.out.println("\nCongrats Player 1!");
                            }

                        }
                    }
                    //Second diagonal check for other direction
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (gamearr[i][j] == "1" + " " && gamearr[i + 1][j + 1] == "1" + " " && gamearr[i + 2][j + 2] == "1" + " " && gamearr[i + 3][j + 3] == "1" + " ") {
                                gameover = true;
                                System.out.println("\nCongrats Player 1!");
                            }
                        }
                    }
                    //accounts for a tie
                    if(count==7)
                    {
                        gameover=true;
                        System.out.println("\nBoth players tied!");
                    }
                    //if player 1 doesnt win, player 2 goes
                    if (gameover != true) {

                        System.out.println("Player 2: Please enter the column you wish to play in (1-7) ");
                        player2 = input.nextInt() - 1;
                        check=arrayFullcheck(player2,gamearr);
                        //if a column player 2 trys to play is full then tell them they cant play in that column.
                        if(check==true)
                        {
                            //if player 2 continues inputting into the wrong column.
                            while(check==true) {


                                System.out.println("That column is full, please try again");
                                System.out.println("Player 2: Please enter the column you wish to play in (1-7) ");
                                player2 = input.nextInt() - 1;
                                check = arrayFullcheck(player2, gamearr);
                            }
                        }
                        if(check==false) {

                            for (int m = 6; m > 0; m--) {

                                if (gamearr[m - 1][player2] == "." + " ") {
                                    gamearr[m - 1][player2] = "2" + " ";
                                    break;
                                } else {

                                }

                            }
                        }

                        //prints the gameboard
                        printBoard(gamearr);
                        //checks if player1 wins vertically
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 6; j++) {
                                //vertical check
                                if (gamearr[i][j] == "2" + " " && gamearr[i + 1][j] == "2" + " " && gamearr[i + 2][j] == "2" + " " && gamearr[i + 3][j] == "2" + " ") {
                                    gameover = true;
                                    System.out.println("\nCongrats Player 2!");
                                }
                            }

                        }
                        //Horizontal check for victory
                        for (int i = 0; i < 6; i++) {
                            for (int j = 0; j < 3; j++) {
                                if (gamearr[i][j] == "2" + " " && gamearr[i][j + 1] == "2" + " " && gamearr[i][j + 2] == "2" + " " && gamearr[i][j + 3] == "2" + " ") {
                                    gameover = true;
                                    System.out.println("\nCongrats Player 2!");
                                }
                            }
                        }
                        //diagonal check for victory
                        for (int i = 3; i < 6; i++) {
                            for (int j = 0; j < 3; j++) {
                                if (gamearr[i][j] == "2" + " " && gamearr[i - 1][j + 1] == "2" + " " && gamearr[i - 2][j + 2] == "2" + " " && gamearr[i - 3][j + 3] == "2" + " ") {
                                    gameover = true;
                                    System.out.println("\nCongrats Player 2!");
                                }

                            }
                        }
                        //Second diagonal check for other direction
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                if (gamearr[i][j] == "2" + " " && gamearr[i + 1][j + 1] == "2" + " " && gamearr[i + 2][j + 2] == "2" + " " && gamearr[i + 3][j + 3] == "2" + " ") {
                                    gameover = true;
                                    System.out.println("\nCongrats Player 2!");
                                }
                            }
                        }
                        //accounts for a tie
                        if(count==7)
                        {
                            gameover=true;
                            System.out.println("\nBoth players tied!");
                        }
                    }
                }catch(ArrayIndexOutOfBoundsException aindex)
                {
                    System.out.println("input was not a valid column. Please try again");
                }
                catch(InputMismatchException ie)
                {
                    System.out.println("Input was not a number, Please try again.");
                }
            }
        }
    }
    public static boolean arrayFullcheck(int col,String [][] gameboard)
    {
        if (gameboard[0][col]!="."+" ") {
            count++;
            return true;
        }
        return false;
    }
    public static void printBoard(String [][] gameboard)
    {
        for(int i=0; i<6; i++){
            for(int j=0; j<7; j++){
                System.out.print(gameboard[i][j]);
            }
            System.out.println("");
        }
        for(int j=0; j<13; j++)
        {
            System.out.print("-");
        }
    }
    }

