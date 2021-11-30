//Lab worked on by Sara, Audrey and Jeremy.
import java.util.*;

public class TCPL4
{
    public TCPL4()
    {

    }

    //send is effectively implemented as UDT right now. It just passes the message down to layer 3 with nothing extra.
        //Variables
    int [] DA;
    int NakorAck=-1;
    ArrayList<Integer> possibly = new ArrayList<Integer>();
    int seqNum = 0;
    ArrayList<int []> delaybuffer= new ArrayList<int []>();
    boolean isSorted=true;
    int prevseqnum=-1;


    //message is the message that sender is going to send
    public int[] send(int message)
    {
        //you can have an array with more than 1 int for things like times / seq nums / etc.
        int [] dataArray = new int[4];

        dataArray[0] = message;   //First message
        dataArray[1] = message;   //Checksum
        dataArray[2]= NakorAck; //nak or ack
        dataArray[3]=seqNum;   //sequence num

        possibly.add(message); //add message to store it
        seqNum++;

        System.out.println("\nThis is the message: "+message+" I am calling send\n");

        return dataArray;
    }

    //receive is effectively implemented as UDT right now. It just passes the messages it gets up to layer 5 with nothing extra.

    ///receive returns an arraylist of messages that it will send at the conclusion of the send call. The TCPL5 object is there only so you may send the messages up to layer 5. The boolean isSender indicates if the "this" object is the original sender or not.
    public ArrayList<int []> receive(ArrayList<int [] >dataArray, TCPL5 reciever, boolean isSender) //isSender is for the purpose of letting you know if this is the sender of reciever
    {
        //Responses ArrayList
        ArrayList<int []> responses = new ArrayList<int []>();


        for(int i=0;i<dataArray.size();i++)
        {

            DA = dataArray.get(i);  //Gets the information
            //If seqnum is the next seqnum to be sent
            //Fills the Delaybuffer
            if(DA[3]!=prevseqnum+1)
            {
                delaybuffer.add(DA);
                //while loop that should stop when the buffer is sorted.
                while(isSorted!=true)
                {
                    for(int k=0; k< delaybuffer.size()-1;k++)
                    {
                        //Bubble sort storage vars
                        int [] first= delaybuffer.get(k);
                        int [] second= delaybuffer.get(k+1);
                        //Bubble sort
                        if(first[3]>second[3])
                        {
                            delaybuffer.set(k,second);
                            delaybuffer.set(k+1,first);
                            isSorted=true;
                        }
                    }
                }
            }
            //if sender is true then do this
            if (isSender == true)
            {
                //sets an Ack
                if (DA[0] == possibly.get(seqNum-1))
                {
                    DA[2] = 1;  //Supposed ot set it to an ack
                }

            }
            //If ACK
            if (DA[2] == 1)
            {
                reciever.giveMessage(DA[0]);
                prevseqnum=DA[3];

            }
            //If Checksum and message do not match, then NAK
            if (DA[0] != DA[1])
            {
                DA[2] = 0;   //NACK
                responses.add(DA);

            }
            else  //If the checksum and messages are fine
            {
                DA[2] = 1;   //ACK
                reciever.giveMessage(DA[0]);
                //send the data up to layer 5. Since the udt messages are 1 length, the data is at 0
            }
            //IF not ACk and seqnum's dont match, get a msg from the buffer thats sorted
            if(DA[2]!=1 && DA[3]!=prevseqnum+1)
            {
                for(int u=0; u< delaybuffer.size(); u++)
                {
                    int [] msgholder= delaybuffer.get(u);
                    if(msgholder[3]== prevseqnum+1)
                    {
                        reciever.giveMessage(msgholder[0]);
                        prevseqnum=msgholder[3];
                    }
                }
            }
        }
        //If sender is false
        if (isSender == false)   //If it is not from the original sender
        {
            try
            {
                //If there is an ACK, return null
                if (DA[2] == 1)
                {
                    return null;
                }
                else if(DA[2] == 0)   //Otherwise, we return the message with NACK in hopes it gets uncorrupted, don't work though
                {
                        return responses;
                }
            }
            catch (NullPointerException e)   //If DA is null, we say okay then
            {
                return null;
            }
        }
        return null; //you can return an arraylist of messages you want to send back. You know, acks, nacks, and whatnot
    }
}