import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;

import java.awt.image.BufferStrategy;
public class GameWindow implements Runnable{
	public static final int START_HP = 20;
        public static final int START_PlAYER_X = 20;
	public static final int START_PLAYER_Y = 20;
        public static final int NUM_ENEMY_MELEE = 5;
	public static final int NUM_ENEM_RANGED = 5;
	public static final int FOV_MELEE = 150; 
	public static final int FOV_RANGED = 300;
       	private EnemyFactory ef; // server
	private Random random; // server
	private int recharge; // server yung recharge
	public static final int FPS = 60; //
	public static final int PLAYER_IMG_WIDTH = 32; //
	public static final int PLAYER_IMG_HEIGHT = 48; //
	public static final int ENEMY_IMG_WIDTH = 32; //
	public static final int ENEMY_IMG_HEIGHT = 48; //
	public static final int PLAYER_ATTACK_W = 192; //
	public static final int PLAYER_ATTACK_H = 192; //
	public static final int ATTACK_FRAMES = 18; //
	public static final int ATTACK_COL = 5; //
	public static final int ATTACK_ROW = 4; //
	public static final int ARROW_W = 60; //
	public static final int ARROW_H = 15; //
	public static final int PLAYER_NUM_FRAMES = 4; //
	public static final int ENEMY_NUM_FRAMES = 4; //
	public static final int WINDOW_WIDTH = 959; //
	public static final int WINDOW_HEIGHT = 623; //


	private ArrayList<Enemy> enemy;
	private ArrayList<Arrow> arrow;
	private ArrayList<Player> players;
	private String img_player;
	private String img_enemy_melee;
	private String img_enemy_ranged;
	private String img_arrow;
	private String img_attack;
	private String img_bg;
	private MainFrame mf;
	public CharacterMemento savedGame;
	//private int key;
	//private EnemyFactory ef; // server
	//private Random random; // server
	private int id; // server yung recharge
	private FlyweightFactory ff;
        private ServerInt serv;
	public Shop shop;
	public Settings keyset;
	public JLabel curItem, curLevel, curGold, curHP, curAttack, curDef;
	public GameWindow(ServerInt x, int y)throws IOException{
		savedGame = null;
                serv = x;
                id = y;
		//key = (int) '.';
		random = new Random();
		img_player = "img/player.png";
		img_enemy_melee = "img/enemy_melee.png";
		img_enemy_ranged = "img/enemy_ranged.png";
		img_arrow = "img/arrow.png";
		img_attack = "img/player_attack.png";
		img_bg = "img/map.png";
		ff = new FlyweightFactory();
		recharge = 0;
                try{
		enemy = serv.getAllEnemies();
		arrow = serv.getAllArrows();
                players = serv.getAllCharacters();
                }catch (Exception e){}
		//ef = new EnemyFactory(new Enemy(1,2,3,3,100,100,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT,1,0),player,arrow);
		//init();
		//public Character(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		mf = new MainFrame(WINDOW_WIDTH,WINDOW_HEIGHT,enemy,players,arrow,ff, id);
                shop = new Shop(mf.gc.ff, serv, id);
		keyset = new Settings(serv, id);
		shop.setVisible(false);
		keyset.setVisible(false);
                Thread t = new Thread(this);
                t.start();
	}
 
	public void render(){
		mf.update();
	}

// thread until run
	public void loop(){
						Player temp;
                        try{
                        temp = serv.getMyPlayer(id);
                        players = serv.getAllCharacters();
                        enemy = serv.getAllEnemies();
                        arrow = serv.getAllArrows();
                        mf.passUpdates(enemy, arrow, players);
                         curLevel.setText("Level: " + temp.lvl);
                            curItem.setText("Item: <empty>");
                            if (serv.getMyPlayer(id).item!=null)
                                    curItem.setText("Item: " + temp.item.getName());
                            curHP.setText("Health: " + temp.hp);
                            curAttack.setText("Attack: " + temp.atk);
                            curDef.setText("Defense: " + temp.def);
                            curGold.setText("Gold: " + temp.gold);
                        if(!temp.isAlive()){
                        	serv.kill(id);
                        	mf.dispose();
                        	JFrame x = new JFrame();
                        	x.setSize(50,50);
                        	x.add(new JLabel("You're dead"));
                        	x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        	x.setVisible(true);

                        }
                        } catch(Exception e)
                        {}
			render();
                          
			mf.revalidate();

	}

	public void run(){
		while(true){
			try{
				Thread.sleep( 1000/FPS );
				loop();
                               
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}

	}

	class MainFrame extends JFrame{
		GameCanvas gc;
                int ID;
		public MainFrame(int w, int h, ArrayList<Enemy> enemy, ArrayList<Player> player, ArrayList<Arrow> arrow,FlyweightFactory ff, int x){
			setTitle("Final_Project");
			setSize(w,h);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			gc = new GameCanvas(enemy,player,arrow,ff);
			gc.setFocusable(false);
			setFocusable(true);
			add(gc, BorderLayout.CENTER);

			JPanel statusBar = new JPanel (new GridLayout(1,0));
			JButton shopButton = new JButton("Shop");
			shopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					shop.setVisible(true);
				}
			});
			shopButton.setFocusable(false);

			JButton setButton = new JButton ("Settings");
			setButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					keyset.setVisible(true);
				}
			});
			setButton.setFocusable(false);
                        
                        try {
                            curLevel = new JLabel ("Level: " + serv.getMyPlayer(id).lvl);
                            curItem = new JLabel ("Item: <empty>");
                            if (serv.getMyPlayer(id).item!=null)
                                    curItem = new JLabel ("Item: " + serv.getMyPlayer(id).item.getName());
                            curHP = new JLabel ("Health: " + serv.getMyPlayer(id).hp);
                            curAttack = new JLabel ("Attack: " + serv.getMyPlayer(id).atk);
                            curDef = new JLabel ("Defense: " + serv.getMyPlayer(id).def);
                            curGold = new JLabel ("Gold: " + serv.getMyPlayer(id).gold);
                        } catch (Exception e) {}
						
			JButton saveButton = new JButton ("Save");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try{savedGame = serv.getMyPlayer(id).createMemento();}catch(Exception e){}
				}
			});
			saveButton.setFocusable(false);

			statusBar.add(shopButton);
			statusBar.add(setButton);
			statusBar.add(saveButton);
			statusBar.add(curGold);
			statusBar.add(curLevel);
			statusBar.add(curHP);
			statusBar.add(curAttack);
			statusBar.add(curDef);
			statusBar.add(curItem);
			add(statusBar, BorderLayout.SOUTH);

			addKeyListener(new Keyboard());
			setVisible(true);
                        ID = x;
                        addWindowListener(new WindowListener(){

                        @Override
                        public void windowOpened(WindowEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            try{
									Player temp = serv.logOut(ID);
									if(temp.isAlive()){
									savedGame = temp.createMemento();
									FileOutputStream fout = new FileOutputStream("savedgames.out");
									ObjectOutputStream oos = new ObjectOutputStream(fout);   
									oos.writeObject(savedGame);
									oos.close();
									System.out.println("saved game");
									}
								} catch (Exception ex) {}
													}
                      
                        

                        @Override
                        public void windowClosed(WindowEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public void windowIconified(WindowEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public void windowDeiconified(WindowEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public void windowActivated(WindowEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public void windowDeactivated(WindowEvent e) {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                    });
			setVisible(true);
		}
                
                public void passUpdates(ArrayList<Enemy> a, ArrayList<Arrow> b, ArrayList<Player> c)
                {
                    gc.receiveUpdates(a,b,c);
                }

		public void update(){
			gc.repaint();
		}


		class Keyboard implements KeyListener{
			public Keyboard(){
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
                                try{
                                serv.doCommand(id, (int)e.getKeyChar(), 1);
                                } catch(Exception except)
                                {
                                }
			}
			public void keyReleased(KeyEvent e){
                                try{
				serv.doCommand(id, (int)e.getKeyChar(), 0);
                                } catch(Exception except)
                                {
                                
                                }
			}
		}

	}

	class GameCanvas extends Canvas{
		ArrayList<Enemy> enemy;
		ArrayList<Arrow> arrow;
		FlyweightFactory ff;
		ArrayList<Player> players;
		public GameCanvas(ArrayList<Enemy> enemy, ArrayList<Player> players, ArrayList<Arrow> arrow, FlyweightFactory ff){
			this.ff = ff;
			this.players = players;
			this.enemy = enemy;
			this.arrow = arrow;
			setBackground(Color.WHITE);
		}
                
                public void receiveUpdates(ArrayList<Enemy> a, ArrayList<Arrow> b, ArrayList<Player> c)
                {
                    enemy = a;
                    arrow = b;
                    players = c;
                }

		public void drawAttackFrame(Image source, Graphics2D g2d, int x, int y,
									 int col, int width, int height, int seq){
			int frameX = (seq%col) * width;
			int frameY = (seq/col) * height;
			g2d.drawImage(source,x,y,x+width,y+height,
						frameX,frameY,frameX+width,frameY+height,this);
		}

		public void drawSpriteFrame(Image source, Graphics2D g2d, int x, int y,
		                     int face, int seq, int width, int height){

		    int frameX = seq*width;
		    int frameY = 0;
		    switch(face){
		    	case 0:
		    		frameY = 3*height;
		    		break;
		    	case 1:
		    		frameY = height;
		    		break;
		    	case 2:
		    		frameY = 0;
		    		break;
		    	case 3:
		    		frameY = 2*height;
		    		break;
		    }

		    g2d.drawImage(source, x, y, x+width, y+height, 
		                  frameX, frameY, frameX+width, frameY+height,this);
		}

		public void paint(Graphics g){
                        
			BufferStrategy bs = getBufferStrategy();
			if(bs == null){
				createBufferStrategy(2);
				return;
			}
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(ff.getImage(img_bg),0,0,null);
                        for(int i = 0; i < players.size(); i ++){
                            Player player = players.get(i);
              
                            if(player.attacking){
                                    drawAttackFrame(ff.getImage(img_attack),g2,player.x - PLAYER_ATTACK_W/2 + player.w/2 ,player.y - PLAYER_ATTACK_H/2 + player.h/2 ,ATTACK_COL,PLAYER_ATTACK_W,PLAYER_ATTACK_H,player.seq);
                            };
                            drawSpriteFrame(ff.getImage(img_player),g2,player.x,player.y,player.face,player.seq,PLAYER_IMG_WIDTH,PLAYER_IMG_HEIGHT);
                        }

			for(int i = 0; i < enemy.size(); i++){
				Enemy e = enemy.get(i);
				if(e.isRanged()){
					drawSpriteFrame(ff.getImage(img_enemy_ranged),g2,e.x,e.y,e.face,e.seq,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT);
				}
				else{
					drawSpriteFrame(ff.getImage(img_enemy_melee),g2,e.x,e.y,e.face,e.seq,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT);	
				}
			}

			for(int i = 0; i < arrow.size(); i++){
				Arrow a = arrow.get(i);
				g2.drawImage(ff.getImage(img_arrow),a.x,a.y,Color.LIGHT_GRAY,null);
			}

		}

		public void update(Graphics g){
			BufferStrategy bs = getBufferStrategy();
			if(bs == null){
				createBufferStrategy(2);
				return;
			}
			Graphics bg = bs.getDrawGraphics();
			paint(bg);
			bg.dispose();
			bs.show();			
		}

	}
	
}
