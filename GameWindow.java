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

	private boolean playerAlive;

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
		playerAlive = true;
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
                shop = new Shop(mf.gc.ff, serv.getMyPlayer(id), serv, id);
		keyset = new Settings(serv.getMyPlayer(id), serv, id);
		shop.setVisible(false);
		keyset.setVisible(false);
                Thread t = new Thread(this);
                t.start();
	}
        // server
        /*
	public void init(){
		for(int i = 1; i <= NUM_ENEMY_MELEE; i++){
			enemy.add(ef.createMeleeEnemy());
		}
		for(int i = 1; i <= NUM_ENEM_RANGED; i++){
			enemy.add(ef.createRangedEnemy());
		} 
	}
        // server
	public void animate(){
		for(int i = 0; i < enemy.size(); i++){
			Enemy e = enemy.get(i);
			e.handle();
			e.updateRectangle();
		}
		for(int i = 0; i < arrow.size(); i++){
			Arrow a = arrow.get(i);
			a.move();
			if(a.motionLife()){
				if(player.collide(a.hitbox)){
					player.takeDamage(a.dmg);
					arrow.remove(i--);
				}
			}
			else{
				arrow.remove(i--);
			}
		}
	}*/


        /*/server
	public void playerAnimate(){
            for(int i)
		if(player.attacking){
			player.move(Player.Movement.ATTACK.getCode());
			if(player.seq == 14){
				for(int i = 0 ; i < enemy.size(); i++){
					Enemy temp = enemy.get(i);
					if(temp.collide(player.attack())){
						temp.takeDamage(player.atk);
						if(!temp.isAlive()){
							player.gainExp(temp.lvl);
							player.gold += random.nextInt(temp.lvl) + 1;
							enemy.remove(i);
							i--;
						}
					}
				}
			}
		}
		else{
			player.execute(key);						
		}
	}*/
            /*
        //server
	public void resolveWalls(Character a){
		if(a.x < 0){
			a.x = 0;
		}
		if(a.y < 0){
			a.y = 0;
		}
		if(a.x + PLAYER_IMG_WIDTH > mf.gc.getWidth()){
			a.x = mf.gc.getWidth() - PLAYER_IMG_WIDTH; 
		}
		if(a.y + PLAYER_IMG_HEIGHT > mf.gc.getHeight()){
			a.y = mf.gc.getHeight() - PLAYER_IMG_HEIGHT;
		}
		a.updateRectangle();
	}
	
        // server
	public void collide(){
		resolveWalls(player);
		for(int i = 0; i < enemy.size(); i++){
			resolveWalls(enemy.get(i));
		}
	}
*/
	public void render(){
		mf.update();
	}



// thread until run
	public void loop(){
			//addEnemies();
			//playerAnimate();
			//animate();
			//collide();
                        try{
                        players = serv.getAllCharacters();
                        enemy = serv.getAllEnemies();
                        arrow = serv.getAllArrows();
                        mf.passUpdates(enemy, arrow, players);
                        } catch(Exception e)
                        {}
			render();
                        try {
                            curLevel.setText("Level: " + serv.getMyPlayer(id).lvl);
                            curItem.setText("Item: <empty>");
                            if (serv.getMyPlayer(id).item!=null)
                                    curItem.setText("Item: " + serv.getMyPlayer(id).item.getName());
                            curHP.setText("Health: " + serv.getMyPlayer(id).hp);
                            curAttack.setText("Attack: " + serv.getMyPlayer(id).atk);
                            curDef.setText("Defense: " + serv.getMyPlayer(id).def);
                            curGold.setText("Gold: " + serv.getMyPlayer(id).gold);
                        } catch (Exception e) {}
			mf.revalidate();

	}

	public void run(){
		while(true){
			try{
				Thread.sleep( 1000/FPS );
				loop();
                                //System.out.println("CHUCHU");
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

			statusBar.add(shopButton);
			statusBar.add(setButton);
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
                                serv.logOut(ID);
                            } catch(Exception x)
                            {

                            }
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
                        System.out.println(players.size());
			BufferStrategy bs = getBufferStrategy();
			if(bs == null){
				createBufferStrategy(2);
				return;
			}
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(ff.getImage(img_bg),0,0,null);
                        for(int i = 0; i < players.size(); i ++){
                            Player player = players.get(i);
                            System.out.println("Player at "+player.x+" "+player.y);
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