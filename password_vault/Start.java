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
public class Start extends Frame  implements ActionListener {
    Frame f ;
    Label lb1;
    TextField tf1;
    Button B1;
    public static String SecKey;
    public Start()
    {
      
        f = new Frame("PasswordVault");
        lb1 = new Label("SecretKey");
        tf1 = new TextField();
        B1 = new Button("EnterVault");
        lb1.setBounds(50,50,100,30);
        tf1.setBounds(150, 50, 150, 30);
        B1.setBounds(150, 110, 150, 40);
        f.add(tf1);
        f.add(lb1);
        f.add(B1);
        B1.addActionListener(this);
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
          if(e.getSource()==B1)
          {
              //System.out.println("fghfghf");
              PasswordVault obj = new PasswordVault();
              SecKey = tf1.getText();
              f.setVisible(false);
          }
    }
    public static void main(String [] arg)
    {
        Start obj = new Start();
    }
}
