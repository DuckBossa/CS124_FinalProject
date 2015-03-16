import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author J.Lo
 */
public class Server extends UnicastRemoteObject implements ServerInt{
    int loggedIn = 0; // Number of people logged in
    int curID = 0; // ID of clients assigned always incrementing even if they quit
    JFrame UI;
    JTextArea log;
    JLabel head, head1, head2, spawn, max, IP, numEn, numLog;
    JPanel gen, manip, generalData, manipItself, spawnR, maxN;
    JButton spawnPlus, spawnMin, maxPlus, maxMin;
    int spawnRate = 50; // in percent
    int maxNum = 10; // in pieces
    //HashMap<Integer, Characters> characters;
    //ArrayList<Characters> enemies;
    
    public Server() throws RemoteException{
        //characters = new HashMap<Integer, Characters>();
        //enemies = new ArrayList<Characters>();
        JFrame x = new JFrame("IP ADDRESS FOR REFERENCE");
        x.setLayout(new BorderLayout());
        JPanel buttons = new JPanel();
        JTextField IPs = new JTextField("(optional) Enter your I.P. Address for reference");
        JButton ok = new JButton("OK");
        JButton ex = new JButton("CANCEL");
        ok.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae)
                    {
                        setUpFrame(IPs.getText());
                        x.dispose();
                    }
        });
        ex.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae)
                    {
                        System.exit(0);
                    }
        });
        buttons.add(ok);
        buttons.add(ex);
        x.add(IPs, BorderLayout.PAGE_START);
        x.add(buttons, BorderLayout.PAGE_END);
        x.setSize(350,80);
        x.setVisible(true);
        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String args[])
    {
        try{
        Server x = new Server();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void setUpFrame(String ip)
    {
        UI = new JFrame("Server Data");
        UI.setSize(400,500);
        UI.setResizable(false);
        UI.setLayout(new GridLayout(2,1));
        
        generalData = new JPanel();
        generalData.setLayout(new GridLayout(2,1));
        gen = new JPanel();
            gen.setLayout(new GridLayout(4,1));
            head = new JLabel("General Data");
            try{
            IP = new JLabel("I.P. Address : "+ ip);
            } catch(Exception e)
            {}
            numEn = new JLabel();
            numLog = new JLabel();
            gen.add(head);
            gen.add(IP);
            gen.add(numLog);
            gen.add(numEn);
            generalData.add(gen);
        manip = new JPanel();
        manip.setLayout(new BorderLayout());
        head1 = new JLabel("Manipulate Game: ");
        manip.add(head1, BorderLayout.PAGE_START);
        manipItself = new JPanel();
            manipItself.setLayout(new GridLayout(2,1));
            spawnR = new JPanel();
                spawnR.setLayout(new GridLayout(1,3));
                spawnPlus = new JButton("+ Spawn Rate");
                spawnMin = new JButton("- Spawn Rate");
                spawnPlus.addActionListener(
                        new ActionListener(){
                            
                            public void actionPerformed(ActionEvent ae)
                            {
                                if(spawnRate < 70){
                                    spawnRate++;
                                    refreshData();
                                }
                            }
                            
                        }
                );
                spawnMin.addActionListener(
                        new ActionListener(){
                            
                            public void actionPerformed(ActionEvent ae)
                            {
                                if(spawnRate > 10){
                                    spawnRate--;
                                    refreshData();
                                }
                            }
                            
                        }
                );
                spawn = new JLabel();
                spawnR.add(spawn);
                spawnR.add(spawnPlus);
                spawnR.add(spawnMin);
            maxN = new JPanel();
                maxN.setLayout(new GridLayout(1,3));
                maxPlus = new JButton("+ Max Monsters");
                maxMin = new JButton("- Max Monsters");
                maxPlus.addActionListener(
                        new ActionListener(){
                            
                            public void actionPerformed(ActionEvent ae)
                            {
                                if(maxNum < 20){
                                    maxNum++;
                                    refreshData();
                                }
                            }
                            
                        }
                );
                maxMin.addActionListener(
                        new ActionListener(){
                            
                            public void actionPerformed(ActionEvent ae)
                            {
                                if(maxNum > 10){
                                maxNum--;
                                refreshData();
                                }
                            }
                            
                        }
                );
                max = new JLabel();
                maxN.add(max);
                maxN.add(maxPlus);
                maxN.add(maxMin);
            manipItself.add(maxN);
            manipItself.add(spawnR);
            generalData.add(manipItself);
        UI.add(generalData);
        
        log = new JTextArea("Attempting to initialize the server...");
        log.setEditable(false);
        UI.add(log);
        refreshData();
        
        UI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UI.setVisible(true);
        
        try{
            Naming.rebind("GameServer", this);
        }catch (Exception e){
            System.out.println("error");
        }
        
        appLog("Server Initialized! \nWELCOME");
    }
    
    public void refreshData(){
        numLog.setText("Number of Clients : "+ loggedIn); 
        numEn.setText("Number of Enemies : "+ /*enemies.size()*/ 10);
        
        
        max.setText("Max Monsters : "+maxNum);
        
        spawn.setText("Spawn Rate : "+spawnRate+"%");
    }
    
    public void appLog(String text){
        log.setText(log.getText()+"\n"+text);
    }
    
    
    
    public int logIn(/*Character thisGuy*/){
        //characters.put(curID, thisGuy); 
        loggedIn++;
        return curID++;
    }
    
    /*
    
    public void moveChar(int ID, int x, int y)
    {
        //change character stuff;
    }
    
    public ArrayList<Characters> getAllCharacters(){
        // get All thigns in HashMap thats a Character or store keys?
    }
    
    public ArrayList<Characters> getAllEnemies(){
        return enemies;
    }*/
    
}
