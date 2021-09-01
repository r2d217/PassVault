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
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class SavedCred extends Frame implements ActionListener {
public static Frame fSave;
 MenuItem i1,I2,i3,i4;
 Menu menu;
 Button B;
 static Choice C;
 static Label l1,l2,l3,lb1,lb2,lb3;
    public SavedCred() {
        fSave= new Frame("Password Vault");
        MenuBar m1 = new MenuBar();
        menu = new Menu("Options");
        i1 = new MenuItem("View_Saved");
        I2 = new MenuItem("New_Credentials");
        i4 = new MenuItem("Update_Credentials");
        i3 = new MenuItem("Secret_Key");
        B = new Button("Show");
        lb1 = new Label("WEBSITE  :");
        lb2 = new Label("USER NAME:");
        lb3 = new Label("PASSWORD :");
        l1 = new Label("");
        l2 = new Label("");
        l3 = new Label("");
        I2.addActionListener(this);
        i3.addActionListener(this);
        i4.addActionListener(this);
        menu.add(i3);
        menu.add(i1);
        menu.add(I2);
        menu.add(i4);
        m1.add(menu);
        fSave.setMenuBar(m1);
        C= new Choice();
        AddItem();
        C.add("select From");
        C.setBounds(100,100, 155,75);
        lb1.setBounds(70, 150, 100, 50);
        lb2.setBounds(70, 200, 100, 50);
        lb3.setBounds(70, 250, 100, 50);
        B.setBounds(270,100 , 75, 25);
        B.addActionListener(this);
        fSave.add(C);
        fSave.add(B);
        fSave.setSize(400,400);
        fSave.setLayout(null);  
        fSave.setVisible(true);
        fSave.add(lb1);
        fSave.add(lb2);
        fSave.add(lb3); 
         fSave.setResizable(false);
         fSave.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
            fSave.dispose();
         }
        });
    }
@Override
     public void actionPerformed(ActionEvent e) {
         if(e.getSource()==I2)
         {
             PasswordVault obj = new PasswordVault();
             fSave.setVisible(false);
         }
         else if(e.getSource()==B)
         {
            String x =C.getItem(C.getSelectedIndex());
             get_Credentials(x);
         }
         else if(e.getSource()==i3)
         {
             Start obj = new Start();
             fSave.setVisible(false);
         }
         else if(e.getSource()==i4)
        {
            UpdateCred obj = new UpdateCred();
            fSave.setVisible(false);
        }
     }
    /* public static void main(String [] arg)
     {
         SavedCred obj = new SavedCred();
     }
     */
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
         
         if(x.equals(credobj.get("Website").toString()))
         {
         l1.setText(credobj.get("Website").toString()); 
         l2.setText(credobj.get("UserName").toString());
         l3.setText(Encrypt_Decrypt.decrypt(credobj.get("Password").toString()));
         l1.setBounds(170, 150, 100, 50);
         l2.setBounds(170, 200, 200, 50);
         l3.setBounds(170, 250, 200, 50);
         fSave.add(l1);
         fSave.add(l2);
         fSave.add(l3);
         }
         
     }
}
