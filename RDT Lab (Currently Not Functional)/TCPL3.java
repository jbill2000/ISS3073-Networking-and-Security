import java.util.*;

public class TCPL3
{

   private float bitrate;
   private int delayrate;
   private float delayChance;

   private ArrayList<int []> messagesInQueue = new ArrayList<int[]>();
   private ArrayList<Integer> deliverDelays = new ArrayList<Integer>();
   private ArrayList<Object> deliverPeople = new ArrayList<Object>();

   

   public TCPL3(float bitin, int maxDelay, float delayChance)
   {

      bitrate = bitin;
      delayrate = maxDelay;
      this.delayChance = delayChance;
   }
   
   Random rand = new Random();
   //put new messages into layer 3 and below
   public void takeMessage(int [] messageOriginal, Object reciever)
   {
      int [] message = new int[messageOriginal.length];
      for(int i=0;i<message.length;i++)
      {
         message[i] = messageOriginal[i];
      }
   
      float f;
      f = rand.nextFloat();
      
      //if flip a bit
      if(f < bitrate)
      { 
         //this randomly selects a bit and flips it. 
         int index = rand.nextInt(message.length);
         int bit = rand.nextInt(32);
         int mask = 0x1 << bit; //create a one bit mask and then shift by bit
         message[index] = message[index] ^ mask;  //^ is the XOR operator
      }
      
      
      messagesInQueue.add(message);
      
      //if delay
      if(rand.nextFloat() < delayChance)
         deliverDelays.add(rand.nextInt(delayrate)+1);
      else
         deliverDelays.add(1);
      deliverPeople.add(reciever);
      
      
   }
   
   //each time, reduce message times by 1
   public void advanceMessages()
   {
      for(int i=0;i<deliverDelays.size();i++)
      {
         deliverDelays.set(i, deliverDelays.get(i) -1);
      }
   }
   
   //get all messages that are at a particular destination for them.
   public ArrayList<int []> getTransportedMessages(Object reciever)
   {
      ArrayList<int []> messages = new ArrayList<int []>();
   
      for(int i=0;i<deliverDelays.size();i++)
      {
         if(deliverDelays.get(i)<=0 && deliverPeople.get(i) == reciever)
         {
            int [] message = messagesInQueue.get(i);
            int [] m = new int[message.length];
            for(int j=0;j<message.length;j++)
            {
               m[j] = message[j];
            }
         
            messages.add(m);
            
            deliverDelays.remove(i);
            deliverPeople.remove(i);
            messagesInQueue.remove(i);
            i--;
         }
         

      }
      
      return messages;  
   }
   
   public boolean messagesLeft()
   {
      return messagesInQueue.size() > 0;
   }
   
   public int getMessagesLeft()
   {
      return messagesInQueue.size();
   }
}