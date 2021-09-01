/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.password_vault;

/**
 *
 * @author rohan
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class UpdateCred extends Frame implements ActionListener {
 public static Frame Fup;
 MenuItem i1,I2,i3,i4;
 Menu menu;
 Button B,U;
 static Choice C;
 static Label lb1,lb2,lb3;
 static TextField l1,l2,l3;
 static int count=-1,flag=-1;
static JSONObject Credentials = new JSONObject();
 public UpdateCred()
 {
     Fup= new Frame("Password Vault");
        MenuBar m1 = new MenuBar();
        menu = new Menu("Options");
        i1 = new MenuItem("View_Saved");
        I2 = new MenuItem("New_Credentials");
        i3 = new MenuItem("Secret_Key");
        i4 = new MenuItem("Update_Credentials");
        B = new Button("GET");
        U = new Button("Update");
        lb1 = new Label("WEBSITE  :");
        lb2 = new Label("USER NAME:");
        lb3 = new Label("PASSWORD :");
        l1 = new TextField();
        l2 = new TextField();
        l3 = new TextField();
        I2.addActionListener(this);
        i3.addActionListener(this);
        menu.add(i4);
        menu.add(i3);
        menu.add(i1);
        menu.add(I2);
        m1.add(menu);
        Fup.setMenuBar(m1);
        C= new Choice();
        AddItem();
        C.add("select From");
        C.setBounds(100,100, 155,75);
        lb1.setBounds(70, 150, 100, 50);
        lb2.setBounds(70, 200, 100, 50);
        lb3.setBounds(70, 250, 100, 50);
        B.setBounds(270,100 , 75, 25);
        U.setBounds(300,300,75,25);
        B.addActionListener(this);
        U.addActionListener(this);
        Fup.add(C);
        Fup.add(U);
        Fup.add(B);
        Fup.setSize(400,400);
        Fup.setLayout(null);  
        Fup.setVisible(true);
        Fup.add(lb1);
        Fup.add(lb2);
        Fup.add(lb3); 
         Fup.setResizable(false);
         Fup.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
            Fup.dispose();
         }
        });
 }
    @Override
     public void actionPerformed(ActionEvent e) {
         if(e.getSource()==B)
         {
                 String x =C.getItem(C.getSelectedIndex());
             get_Credentials(x);
         }
         else if (e.getSource()==U)
         {
            Update();
            System.out.println();
         }
         else if(e.getSource()==I2)
         {
             PasswordVault obj = new PasswordVault();
             Fup.setVisible(false);
         }
         else if(e.getSource()==i3)
         {
             Start obj = new Start();
             Fup.setVisible(false);
         }
         else if(e.getSource()==i1)
         {
             SavedCred obj = new SavedCred();
             Fup.setVisible(false);
         }
     }
         

     private static void AddItem()
     {
         JSONParser jsonParser = new JSONParser();
            try(FileReader jfile = new FileReader(PasswordVault.Path))
            {
              Object obj = jsonParser.parse(jfile);
              JSONArray arr_obj = (JSONArray) obj;
             
              arr_obj.forEach( emp -> parseWebsites((JSONObject) emp ) );
              
            }
            catch(Exception ex)
            {
                System.out.print(ex.getMessage());
            }
                     
     }
     private static void parseWebsites(JSONObject credentials)
     {
         JSONObject credobj = (JSONObject) credentials.get("Cred");
         C.add(credobj.get("Website").toString());
     }
     private static void get_Credentials(String val)
     {
         
         JSONParser jsonParser = new JSONParser();
            try(FileReader jfile = new FileReader(PasswordVault.Path))
            {
              
              Object obj = jsonParser.parse(jfile);
              JSONArray arr_obj = (JSONArray) obj;
              arr_obj.forEach( emp -> parsePass((JSONObject) emp,val ) );
            }
            catch(Exception ex)
            {
                System.out.print(ex.getMessage());
            }
     }
   
      private static void parsePass(JSONObject credentials,String x)
     {
         
         JSONObject credobj = (JSONObject) credentials.get("Cred");
         ++count;
         if(x.equals(credobj.get("Website").toString())&& Encrypt_Decrypt.decrypt(credobj.get("Password").toString())!=null)
         {
         flag=count;
         l1.setText(credobj.get("Website").toString()); 
         l2.setText(credobj.get("UserName").toString());
         l3.setText(Encrypt_Decrypt.decrypt(credobj.get("Password").toString()));
         l1.setBounds(170, 150, 100, 50);
         l2.setBounds(170, 200, 200, 50);
         l3.setBounds(170, 250, 200, 50);
         Fup.add(l1);
         Fup.add(l2);
         Fup.add(l3);
         }
         else
         {
         l1.setText("WRONG KEY"); 
         l2.setText("WRONG KEY");
         l3.setText("WRONG KEY");
         l1.setBounds(170, 150, 100, 50);
         l2.setBounds(170, 200, 200, 50);
         l3.setBounds(170, 250, 200, 50);
         Fup.add(l1);
         Fup.add(l2);
         Fup.add(l3);
         }
     }
      private static void Update()
      {
        String website = l1.getText();
        String username = l2.getText();
        String password = Encrypt_Decrypt.encrypt(l3.getText());
        JSONObject Passwords = new JSONObject();
      
      Passwords.put("Website",website);
      Passwords.put("UserName",username);
      Passwords.put("Password",password);
           JSONParser jsonParser = new JSONParser();
            try(FileReader jfile = new FileReader(PasswordVault.Path))
            {
                File f1 = new File(PasswordVault.Path);
              if(flag!=-1)
              {
              Object obj = jsonParser.parse(jfile);
              JSONArray arr_obj = (JSONArray) obj;
              arr_obj.remove(flag);
              Credentials.put("Cred", Passwords);
              arr_obj.add(flag,Credentials);
              FileWriter fileWritter = new FileWriter(f1);
               fileWritter.write(arr_obj.toJSONString());
               fileWritter.close();
              }
              else
              {
                  System.out.println("error");
              }
            }
            catch(Exception ex)
            {
                System.out.print(ex.getMessage());
            }
      }
}
