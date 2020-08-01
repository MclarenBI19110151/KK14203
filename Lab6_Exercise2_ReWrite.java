import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;

class Cake{
   String name;
   String[] topping;
   String[] toppingOrder;
   double priceSmall;
   double priceMedium;
   double priceBig;
   double totalPrice;
   int size;
   int quantity;
   
   Cake(String name){this.name=name;}
   
   public void setCake(String[] t, double ps, double pm, double pb){
      topping = t;
      priceSmall = ps;
      priceMedium = pm;
      priceBig = pb;      
   }
   
   public void cakeOrder(String[] to, int q, int s){
      toppingOrder = to;
      quantity = q;
      size = s;
   }
   
   public double getSizePrice(){
      double sprice=0.0;
      if(size==1) sprice = priceSmall;
      else if(size==2) sprice = priceMedium;
      else if(size==3) sprice = priceBig;
      
      return sprice;
   }
   
   public double getTotalPrice(){
      totalPrice = getSizePrice();
      totalPrice *= quantity;
      totalPrice += (toppingOrder.length*10);
      return totalPrice;  
   }
   
   public int getQuantity(){
      return quantity;  
   }
   
   public String getSize(){
      String size_str="";
      if(size==1) size_str="Small";
      else if (size==2) size_str="Medium";
      else if (size==3) size_str="Large";
      return size_str;
   }
      public String printOrder_GUI(){
      String txt = "\n\nCake Order detail:\n";
      txt += "-------------------------------------------\n";
      txt += "Topping Order: \n";
      for(int i=0; i<toppingOrder.length; i++){
         txt += "(" + (i+1) + ") " + toppingOrder[i] + " \n";
      } 
      txt += "\nSize        : " + getSize() + "\n";
      txt += "Quantity : " + getQuantity() + "\n";
      txt += "-------------------------------------------\n";
      txt += "Total Price: RM" + getTotalPrice() + "\n"; 
      txt += "-------------------------------------------\n"; 
      return txt;
   }      
}


class FormPanel extends JPanel implements ActionListener{
   JButton btnOrder;
   JButton btnAdd;
   JLabel jclabel;
   JTextArea jt1;
   JTextArea jt2;
   JComboBox jcb;
   JLabel jcomp2;
   static Cake cake;
   static ArrayList<String> order;
   
   
   String filePath = "data.txt";

   public FormPanel() {
      String[] jcbItems = {"[Select]"};
      jclabel = new JLabel ("Cake Menu");
      btnOrder = new JButton ("Order");
      btnAdd = new JButton ("Add Topping");
      jt1 = new JTextArea (5, 5);
      jt2 = new JTextArea (5, 5);
      JScrollPane jt1_sp = new JScrollPane(jt1);
      JScrollPane jt2_sp = new JScrollPane(jt2);
      jcb = new JComboBox (jcbItems);
      jcomp2 = new JLabel ("Topping Selection");
      
      btnAdd.addActionListener(this);
      btnOrder.addActionListener(this);
   
     
      setPreferredSize (new Dimension (825, 725));
      setLayout (null);
   
     
      add (btnAdd);
      add (btnOrder);
      add (jt1_sp);
      add (jt2_sp);
      add (jcb);
      add (jcomp2);
      add (jclabel);
   
      
    
      btnAdd.setBounds (360, 320, 120, 30);
      btnOrder.setBounds(360, 360, 120, 30);
      jt2_sp.setBounds (25, 35, 790, 285);
      jt1_sp.setBounds (25, 400, 785, 285);
      jcb.setBounds (180, 320, 165, 30);
      jcomp2.setBounds (55, 320, 155, 30);
      jclabel.setBounds  (25, 10, 100, 25);
   }
   
   public void DisplayMenu(){
   
      String[] topping = {"Lava Chocolate", "Cream Cheese", "Butter Cream", "Fondan"};
      String text = ("-----------------------------\n");
      text += "         Cake Menu\n";
      text += "-----------------------------\n";
      text += "Generic Cake with available toppings:\n";
      for(int i=0; i<topping.length; i++){
         text += "\n" + (i+1) + ")  " + topping[i] + "\n";
      } 
      text += "\nPrice:\n";
      text += "[1] Small : RM45.0 \n";
      text += "[2] Medium: RM65.0 \n";
      text += "[3] Big   : RM80.0 \n";
      jt2.append(text);
   }
   
   public void actionPerformed(ActionEvent ae){
      String command = ae.getActionCommand(); 
      
      if(command.equals("Add Topping")){
         order.add(jcb.getSelectedItem().toString());      
         jt1.append(jcb.getSelectedItem().toString() + "\n");   
      }
      else if(command.equals("Order")){
        
         String str_order[] = new String[order.size()]; 
      
       
         for (int j = 0; j < order.size(); j++) { 
           
            str_order[j] = order.get(j); 
         }
        
      
         cake.cakeOrder(str_order, 1, 2);
         String txt = cake.printOrder_GUI();
         jt1.append(txt);
         saveData();
      }    
   }
   
 
   public void saveData(){
      File file = new File(filePath);
      FileWriter fr = null;
      BufferedWriter br = null;
      PrintWriter pr = null;
      
      String String = "";
      String str_order[] = new String[order.size()];
      for(int i=0; i < order.size(); i++){
         str_order[i] = order.get(i);
         String += str_order[i] + ", ";
      }
      String += "Size: " + cake.getSize();
      String += ", Quantity: " + (String.valueOf(cake.quantity));
      String += ", Total Price: " + (String.valueOf(cake.getTotalPrice()));
      
      
     
      try {
      
         fr = new FileWriter(file, true);
         br = new BufferedWriter(fr);
         pr = new PrintWriter(br);
         pr.println(String);
      } catch (IOException e) {			
         jt1.setText(e.toString());
      } finally {
         try {
            pr.close();
            br.close();
            fr.close();
         } catch (IOException e) {
            jt1.setText(e.toString());
         }
      }
   }
   
   public void updateCB(String[] topping){
      for(int i=0; i<topping.length; i++){
         jcb.addItem(topping[i]);
      }      
   }

}



public class Lab6_Exercise2_ReWrite{
   public static void main (String[] args) {
      FormPanel fgenericcake = new FormPanel();
      fgenericcake.cake = new Cake("Generic Cake");
      fgenericcake.order = new ArrayList<String>();
      
      
      JFrame frame = new JFrame ("MyPanel");
      String[] topping = {"Lava Chocolate", "Cream Cheese", "Butter Cream", "Fondant"};
      fgenericcake.cake.setCake(topping, 45.00, 65.00, 80.00); 
      fgenericcake.DisplayMenu();
      
      fgenericcake.updateCB(topping);
      
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add (fgenericcake);
      frame.pack();
      frame.setVisible (true);
   }
}

