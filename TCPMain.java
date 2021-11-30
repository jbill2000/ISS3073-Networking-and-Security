import java.util.*;

public class TCPMain
{
   private static boolean canRecieve=false;
   public static boolean getAccept(){return canRecieve;}


   public static void main(String[] args)
   {
      float bitErrorPercent = .1f;
      float delayChance = .1f;
      
      int maxDelay = 5;
   
      //determining if enough arguments have been given
      try
      {
         bitErrorPercent = Float.parseFloat(args[0]);
         maxDelay = Integer.parseInt(args[1]);
         delayChance = Float.parseFloat(args[2]);
      }
      catch(Exception e)
      {
         System.out.println("USAGE: java TCPMain [chance there is a single bit error in a segment] [the max delay if there is a delay on a segment] [chance there is a delay on a segment]");
         System.out.println("EXAMPLE java TCPMain .1 5 .3");
         return;
      }
   
   
      //setting up different layer objects
      TCPL4 send = new TCPL4();
      TCPL4 recieve = new TCPL4();
      TCPL5 L5S = new TCPL5();
      TCPL5 L5R = new TCPL5();
      TCPL3 layer3 = new TCPL3(bitErrorPercent,maxDelay,delayChance);
      
      canRecieve=false;
      
      int messages = 0;
      int M_AMOUNT=10;
      while(layer3.messagesLeft() || messages < M_AMOUNT)
      {

         //if sending a new data peice
         if(messages < M_AMOUNT)
         {
            int m = (new Random()).nextInt(100000000);
            
            canRecieve = true;
            int [] d = L5S.sendData(m, L5R, send, layer3);
            
            layer3.takeMessage(d,L5R);
            
            L5R.markR(m); //for comparison purposes at the end    
            canRecieve = false;
         }
         
         System.out.println("[to help with debugging] Number of messages in transit in layer 3: "+layer3.getMessagesLeft());
      
         
         //reduce the counter on the messages
         layer3.advanceMessages();
         
         //get messages at the destination
         ArrayList<int [] > resultSend = layer3.getTransportedMessages(L5S);
         ArrayList<int [] > resultsRec = layer3.getTransportedMessages(L5R);
         
         canRecieve = true;
         
         //recieve messages to L4 and get any responeses
         ArrayList<int [] > toRespondSend =  send.receive(resultSend,L5S,true);
         ArrayList<int [] > toRespondRec =  recieve.receive(resultsRec, L5R,false);
         
         //send down to layer 3
         if(toRespondSend != null)
         {
            for(int i=0;i<toRespondSend.size();i++)
            {
               layer3.takeMessage(toRespondSend.get(i),L5R);
            }  
         }
         
         if(toRespondRec != null)
         {
            for(int i=0;i<toRespondRec.size();i++)
            {
               layer3.takeMessage(toRespondRec.get(i),L5S);
            } 
         } 
         
         canRecieve = false;
         
      
         messages+=1;
      }
      L5R.printMessages();
   }
}