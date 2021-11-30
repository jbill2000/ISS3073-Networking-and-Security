import java.util.*;
public class Parity {


    public static void main(String [] args)
    {
        //Scanner and vars
        Scanner scan= new Scanner(System.in);
        int columns=0;
        int rows=0;
        //Takes in num of columns and rows
        System.out.println("Enter the amount of columns and rows");
        columns=scan.nextInt();
        rows=scan.nextInt();
        //Creates the 2d array
        int [] [] parity= new int [rows] [columns];
        //Fills the array
        System.out.println("Please enter your data");
        for(int i=0; i< rows; i++){
            for(int j=0; j<columns; j++)
            {
                parity[i][j]=scan.nextInt();
            }
        }


        System.out.println("Please enter in thr row parity data");
        //Creates an array of row parity data
        int [] rowparity= new int[rows];

        for(int i=0; i<rows; i++)
        {
            rowparity[i]=scan.nextInt();
        }

        System.out.println("Please enter in the column parity data");
        //Creates an array of column parity data
        int [] columnparity= new int[columns];
        for(int i=0; i<columns; i++)
        {
            columnparity[i]=scan.nextInt();
        }
        System.out.println("Enter the bottom right most bit");
        int parityofparitybits= scan.nextInt();

        //Counter vars + boolean to indicate an error
        int rowcounter=0;
        int colcounter=0;

        int colparitycalc=0;
        int rowparitycalc=0;
        boolean error=false;
        //parity stuff

        //Calculates an error in the rows if there is one

        //Loops through row parity array and adds the number of ones,
        for(int i=0; i<rows; i++)
        {
            if(rowparity[i]==1) {

                rowparitycalc++;
            }

        }
        //If number of ones is odd then there is an error.
        if (rowparitycalc % 2 == 1)
        {
            System.out.println("There is an error in the row parity bits!");
            error = true;
        }
        //Nested Loop to go through 2D Array.
        for(int i=0; i<rows; i++)
        {
            for(int j=0; j<columns; j++)
            {
                //If there is a 1 in the row add 1 to the counter
                if(parity[i][j]==1)
                {
                    rowcounter++;
                }
            }
            //If there is a 1 in the parity bit for that row then its good.
            if (rowparity[i] == 1)
            {
                rowcounter++;


            }
            //Adds 1 if the bottom right bit is a 1
            if(parityofparitybits==1)
            {
                rowcounter++;
            }
            //If the 1's in an particular row, the row parity and parity of parity's modulus is odd then there is an error in that row.
            if(rowcounter % 2 ==1)
            {
                System.out.println("There is an error in row "+i);
                error=true;
                rowcounter=0;
            }


        }
        //Loops through the column parity array and adds one to the counter
        for(int i=0; i<columns; i++)
        {
            if(columnparity[i]==1) {

                colparitycalc++;
            }

        }
        //If there is an error in the column parity array
        if (colparitycalc % 2 == 1)
        {
            System.out.println("There is an error in the col parity bits!");
            error = true;
        }

        //Column parity stuff

        //Goes through columns instead of rows
        for(int i=0; i<columns; i++)
        {
            for(int j=0; j<rows; j++)
            {
                //If there is a 1 in the column add 1 to the counter
                if(parity[j][i]==1)
                {
                    colcounter++;
                }
            }
            //If there is a 1 in the parity bit for that column then add it to the counter
            if (columnparity[i] == 1)
            {
                colcounter++;

            }
            //If parity of parity is 1 it gets added to the overall counter
            if(parityofparitybits==1)
            {
                colcounter++;
            }
            //If number of ones in col, col parity array and bottom right parity bit is odd then there is an error in that particular column.
            if(colcounter % 2 ==1)
            {
                System.out.println("There is an error in column "+i);
                error=true;
                colcounter=0;
            }


        }
        //If no error then no error found.
        if(error==false)
        {
            System.out.println("No error found");
        }
    }

}
