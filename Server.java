import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Random;

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
    ArrayList<Integer> keys;
    ArrayList<Enemy> enemies;
    ArrayList<Arrow> arrow;
    public static final int PLAYER_IMG_WIDTH = 32; //
    public static final int PLAYER_IMG_HEIGHT = 48; //
    	public static final int FPS = 60; 
    
    	public static final int START_HP = 20;
        public static final int START_PlAYER_X = 20;
	public static final int START_PLAYER_Y = 20;
	public static final int FOV_MELEE = 150; 
	public static final int FOV_RANGED = 300;
       	private EnemyFactory ef; // server
	private Random random; // server
	private int recharge;
    
    public Server() throws RemoteException{
        //characters = new HashMap<Integer, Characters>();
        //enemies = new ArrayList<Characters>();
        enemies = new ArrayList<Enemy>();
        arrow = new ArrayList<Arrow>();
        characters = new HashMap<Integer, Player>();
        keys = new ArrayList<Integer>();
        random = new Random();
        ef = new EnemyFactory(new Enemy(1,2,3,3,100,100,PLAYER_IMG_WIDTH,PLAYER_IMG_HEIGHT,1,0),getAllCharacters(),arrow);
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
           
            IP = new JLabel("I.P. Address : "+ ip);
           
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
                                    spawnRate+=2;
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
                                    spawnRate-=2;
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
            System.out.println("bound");
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
        numEn.setText("Number of Enemies : "+ enemies.size());
        
        
        max.setText("Max Monsters : "+maxNum);
        
        spawn.setText("Spawn Rate : "+spawnRate+"%");
    }
    
    public void appLog(String text){
        log.setText(log.getText()+"\n"+text);
    }
    
    public int logIn(Player thisGuy) throws RemoteException{
        //characters.put(curID, thisGuy); 
        loggedIn++;
        
        characters.put( curID, thisGuy);
        keys.add(curID);
        appLog("A Client has connected to the server! ID assigned: "+curID);
        refreshData();
        //System.out.println(thisGuy.hitbox == null);
        //System.out.println(thisGuy.x+" "+thisGuy.y);
        thisGuy.in = true;
        return curID++;
    }
    
    public Player logOut(int i) throws RemoteException{
        loggedIn--;
        keys.remove(((Integer) i));
        refreshData();
        appLog("Client#"+i+" has disconnected.");
        Player temp = characters.remove(i);
        temp.in = false;
        return temp;
    }
    
   
    public ArrayList<Player> getAllCharacters() throws RemoteException{
        // get All thigns in HashMap thats a Character or store keys?
        // iffy with this implementaion VV because I'm not sure what to think just yet until test is done
        ArrayList<Player> pls = new ArrayList<Player>();
        for(int i = 0; i < keys.size(); i++)
        {
            pls.add(characters.get(keys.get(i)));
        }
        return pls;
    } 
    
    public ArrayList<Enemy> getAllEnemies() throws RemoteException{
        return enemies;
    }
    
    public ArrayList<Arrow> getAllArrows() throws RemoteException{
        return arrow;
    }
    
    public Player getMyPlayer(int ID) throws RemoteException{
        return characters.get(ID);
    }
    
    public void refreshPlayer(int ID, Player p) throws RemoteException{
        characters.put(ID, p);
    }
    
    public void doCommand(int ID, int key, int pressed) throws RemoteException{
        Player temp = characters.get(ID);
        appLog("Key was pressed by client#"+ID+" "+(char)key);
        if (pressed==1) {
            if(temp.hm.containsKey(key)){
                temp.pressed.add((char) key +"");
            }
        }
        else {
            if (temp.pressed.contains((char)key+""))
                temp.pressed.remove((char) key+"");
        }
        /*
        if(temp.hm.containsKey(key))
        {
            if(!temp.attacking)
            {
                
                temp.execute(key);
            }
            temp.key = key;
        }*/
        appLog(temp.x+" , "+temp.y);
    }
    
    
    public void init(){
		for(int i = 1; i <= maxNum/2; i++){
			enemies.add(ef.createMeleeEnemy());
		}
		for(int i = 1; i <= maxNum/2; i++){
			enemies.add(ef.createRangedEnemy());
		} 
	}
        // server
	public void animate() {
            System.out.println(keys.size());
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
                        try{
			e.handle(getAllCharacters());
                        } catch(RemoteException re){}
			e.updateRectangle();
		}
		for(int i = 0; i < arrow.size(); i++){
			Arrow a = arrow.get(i);
			a.move();
			if(a.motionLife()){
                            for(int j = 0; j < characters.size(); j++){
                                Player player = characters.get(j);
				if(player.collide(a.hitbox)){
					player.takeDamage(a.dmg);
					arrow.remove(i);
                                        i--;
				}
                            }
			}
			else{
				arrow.remove(i);
                                i--;
			}
		}
	}
    // game stuff
    
    	public void playerAnimate(){
            
            for(int i = 0; i < keys.size(); i++){
                
                Player player = characters.get(keys.get(i));
		if(player.attacking){
			player.move(Player.Movement.ATTACK.getCode());
			if(player.seq == 14){
				for(int j = 0 ; j < enemies.size(); j++){
					Enemy temp = enemies.get(j);
					if(temp.collide(player.attack())){
						temp.takeDamage(player.atk);
                                                System.out.println("I'm here "+ enemies.size());
						if(!temp.isAlive()){
							player.gainExp(temp.lvl);
							player.gold += random.nextInt(temp.lvl) + 1;
							enemies.remove(j);
                                                     
							j--;
						}
					}
				}
			}
		} else
                {
                    player.execute();
                }
            }
	}
        
        	public void resolveWalls(Character a){
		if(a.x < 0){
			a.x = 0;
		}
		if(a.y < 0){
			a.y = 0;
		}
		if(a.x + PLAYER_IMG_WIDTH > 959){
			a.x = 959 - PLAYER_IMG_WIDTH; 
		}
		if(a.y + PLAYER_IMG_HEIGHT > 623){
			a.y = 623 - PLAYER_IMG_HEIGHT;
		}
		a.updateRectangle();
	}
	
        // server
	public void collide(){
                for(int i = 0; i < keys.size(); i++)
                {
                    Player player = characters.get(keys.get(i));
                    resolveWalls(player);
                }
		for(int i = 0; i < enemies.size(); i++){
			resolveWalls(enemies.get(i));
		}
	}
        
        	public void addEnemies(){
		if(enemies.size() < maxNum){
			if(--recharge <= 0){
				recharge = 80;
				enemies.add(ef.createMeleeEnemy());
				enemies.add(ef.createRangedEnemy());
			}

		}
	}
    
    class Running implements Runnable{

        @Override
        public void run() {
            while(true)
            {
                //this animates
                animate();
                playerAnimate();
               collide();
                addEnemies();
                refreshData();
      
                try{
                    Thread.sleep(1000/FPS);
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
