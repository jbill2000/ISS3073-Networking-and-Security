import java.util.*;

public class TCPL5
{

   private ArrayList<Integer> expected = new ArrayList<Integer>();
   private ArrayList<Integer> recieved = new ArrayList<Integer>();

   public int [] sendData(int data, TCPL5 destination, TCPL4 send, TCPL3 L3)
   {
      return  send.send(data);
   }
   
   public void markR(int data)
   {
      //can only call if at valid part of code.
      if(!TCPMain.getAccept())
      {
         return;
      }
      expected.add(data);
   }
   
   public void giveMessage(int data)
   {
      //can only call if at valid part of code. 
      if(!TCPMain.getAccept())
      {
         return;
      }
   
      recieved.add(data);
   }
   
   public void printMessages()
   {
      System.out.println("Messages expected to be received by the other party:");
      for(int i=0; i<expected.size(); i++)
      {
         System.out.println(expected.get(i));
      }
      System.out.println("Messages actually sent up to layer 5 (yes, order matters):");
      for(int i=0; i<recieved.size(); i++)
      {
         System.out.println(recieved.get(i));
      }
   }
}
