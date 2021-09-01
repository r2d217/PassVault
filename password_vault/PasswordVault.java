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
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class PasswordVault extends Frame implements ActionListener {
   static File[] root_Dir=File.listRoots();
public static String Path =root_Dir[1]+"output.json";
   TextField tw,tu,tp;
   Button save,reset;
   Menu menu;
   MenuItem i1,i2,i3,i4;
   Frame f;
   Choice C = new Choice();
   JSONObject Credentials = new JSONObject();
   JSONArray Cred_array = new JSONArray();
   public PasswordVault()  
    {  
        f= new Frame("Password Vault");  
        MenuBar m1 = new MenuBar();
        menu = new Menu("Options");
        i1 = new MenuItem("View_Saved");
        i2 = new MenuItem("New_Credentials");
        i3 = new MenuItem("Secret_Key");
        i4 = new MenuItem("Update_Credentials");
        menu.add(i3);
        menu.add(i1);
        menu.add(i2);
        menu.add(i4);
        i4.addActionListener(this);
        i3.addActionListener(this);
        reset = new Button("reset");
        reset.setBounds(85, 200, 100, 30);
        save = new Button("save");
        save.setBounds(195, 200, 100, 30);
        Label lb1,lb2,lb3;
        lb1 = new Label("Website");
        lb1.setBounds(50,50,100,30);
        lb2 = new Label("Username");
        lb2.setBounds(50,100,100,30);
        lb3 = new Label("Password");       
        lb3.setBounds(50,150, 100,30);
        tw= new TextField();
        tw.setBounds(150, 50, 200, 30);
        tu = new TextField();
        tu.setBounds(150, 100, 200, 30);
        tp = new TextField();
        tp.setBounds(150, 150, 200, 30);
        tp.setEchoChar('*');
        f.add(reset);f.add(save);
        save.addActionListener(this);reset.addActionListener(this);
        f.add(tw);
        f.add(tu);
        f.add(tp);
        f.add(lb1);
        f.add(lb2);
        f.add(lb3);
        m1.add(menu);
        i1.addActionListener(this);
        f.setMenuBar(m1);
        f.setSize(400,400);  
        f.setLayout(null);  
         f.setResizable(false);
        f.setVisible(true);
         f.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
            f.dispose();
         }
        });
    }  
    public void actionPerformed(ActionEvent e) {
        String website = tw.getText();
        String username = tu.getText();
        String password = Encrypt_Decrypt.encrypt(tp.getText());
        if(e.getSource()==i1)
        {
         SavedCred obj = new SavedCred();
         f.setVisible(false);            
        }
        else if(e.getSource()==save)
        {
            //System.out.println(website);
            create_json(website,username,password);
        }
        else if(e.getSource()==i3)
         {
             Start obj = new Start();
             f.setVisible(false);
         }
        else if(e.getSource()==i4)
        {
            UpdateCred obj = new UpdateCred();
            f.setVisible(false);
        }
        else
        {
            tw.setText("");
            tu.setText("");
            tp.setText("");
        }
    
    }
  public void create_json(String w ,String u , String p)
  {
      JSONObject Passwords = new JSONObject();
      //Inserting key-value pairs into the json object
      Passwords.put("Website",w);
      Passwords.put("UserName",u);
      Passwords.put("Password",p);
      
       try {
           File f1 = new File(Path);
           if(f1.exists())
           {
                JSONParser jsonParser = new JSONParser();
               try(FileReader jfile = new FileReader(Path))
               {
               Object obj = jsonParser.parse(jfile);
              JSONArray arr_obj = (JSONArray) obj;
              Credentials.put("Cred", Passwords);
               arr_obj.add(Credentials);
               FileWriter fileWritter = new FileWriter(f1);
               fileWritter.write(arr_obj.toJSONString());
               fileWritter.close();
                //System.out.println(arr_obj.toJSONString());
               }
               catch(Exception ex)
               {
                 // System.out.println("uch gadbad hai"+ ex.getMessage());
               }
           }   
           else
           {
         FileWriter file = new FileWriter(Path);
         Credentials.put("Cred", Passwords);
         Cred_array.add(0,Credentials);
         file.write(Cred_array.toJSONString());
         file.close();
           }
      }catch (IOException e) {
         // TODO Auto-generated catch block 
         //e.printStackTrace();
      }
     // System.out.println("JSON file created: "+Passwords);
  }

   
}
