import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.util.HashMap;

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
    JScrollPane scroll;
    JLabel head, head1, head2, spawn, max, IP, numEn, numLog;
    JPanel gen, manip, generalData, manipItself, spawnR, maxN;
    JButton spawnPlus, spawnMin, maxPlus, maxMin;
    int spawnRate = 50; // in percent
    int maxNum = 10; // in pieces
    HashMap<Integer, Player> characters; // different hashmaps for different maps (i.e. iba sa town)
    ArrayList<Enemy> enemies;
    
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
        scroll = new JScrollPane(log);
        UI.add(scroll);
        refreshData();
        
        UI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UI.setVisible(true);
        
        try{
            Naming.rebind("GameServer", this);
        }catch (Exception e){
            System.out.println("error");
            System.exit(0);
        }
        
        appLog("Server Initialized! \nWELCOME");
        Thread t = new Thread(new Running());
        t.start();
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
    
    
    
    public int logIn(/*Character thisGuy*/) throws RemoteException{
        //characters.put(curID, thisGuy); 
        loggedIn++;
        appLog("A Client has connected to the server! ID assigned: "+curID);
        refreshData();
        return curID++;
    }
    
    public void logOut(int i) throws RemoteException{
        loggedIn--;
        refreshData();
        appLog("Client#"+i+" has disconnected.");
        //characters.remove(i);
    }
    
   
    public ArrayList<Player> getAllCharacters(){
        // get All thigns in HashMap thats a Character or store keys?
        // iffy with this implementaion VV because I'm not sure what to think just yet until test is done
        return (ArrayList<Player>)characters.values();
    } 
    
    public ArrayList<Enemy> getAllEnemies(){
        return enemies;
    }

    class Running implements Runnable{

        @Override
        public void run() {
            while(true)
            {
                //System.out.println("chuchu");
                try{
                    Thread.sleep(100);
                }catch(Exception e)
                {}
            }
        }
        
    }
    /** server runs threads **/
    /** some assumptions:
     * Running will handle everything that does not need user input, i.e. enemies
     * If needed animation chuchu for attacks that have lots of animations na walang kinalaman sa character
     * i.e. ranged laser na malayo sa character na may charging animation chuchu
     * IF necessary server will hold an attack array just say'n
     * clarify pls
     */
    
}
