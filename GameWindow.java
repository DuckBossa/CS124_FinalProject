import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferStrategy;
public class GameWindow{
	public static final int START_HP = 20;
	public static final int FPS = 60;
	public static final int START_PlAYER_X = 20;
	public static final int START_PLAYER_Y = 20;
	public static final int PLAYER_IMG_WIDTH = 32;
	public static final int PLAYER_IMG_HEIGHT = 48;
	public static final int ENEMY_IMG_WIDTH = 32;
	public static final int ENEMY_IMG_HEIGHT = 48;
	public static final int ARROW_W = 40;
	public static final int ARROW_H = 15;
	public static final int PLAYER_NUM_FRAMES = 4;
	public static final int ENEMY_NUM_FRAMES = 4;
	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 720;
	public static final int NUM_ENEMY_MELEE = 5;
	public static final int NUM_ENEM_RANGED = 5;
	public static final int FOV_MELEE = 150;
	public static final int FOV_RANGED = 300;

	private boolean playerAlive;

	private ArrayList<Enemy> enemy;
	private ArrayList<Arrow> arrow;
	private Player player;
	public static Image img_player;
	private Image img_enemy_melee;
	private Image img_enemy_ranged;
	private Image img_arrow;
	private Image img_bg;
	private MainFrame mf;
	private boolean[] keys;
	private int key;
	private EnemyFactory ef;
	private Random random;
	private int recharge;
	private ArrayList<Integer> buildme;
	public GameWindow()throws IOException{
		buildme = new ArrayList<Integer>();
		key = (int) '.';
		random = new Random();
		img_player = (ImageIO.read(new FileInputStream("img/player.png")));
		img_enemy_melee = (ImageIO.read(new FileInputStream("img/enemy_melee.png")));
		img_enemy_ranged = (ImageIO.read(new FileInputStream("img/enemy_ranged.png")));
		keys = new boolean[5];
		playerAlive = true;
		recharge = 0;
		enemy = new ArrayList<Enemy>();
		arrow = new ArrayList<Arrow>();
		player = new Player(100,2,5,5,START_PlAYER_X,START_PLAYER_Y,PLAYER_IMG_WIDTH,PLAYER_IMG_HEIGHT,1);
		ef = new EnemyFactory(new Enemy(1,2,3,3,100,100,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT,1,0),player,arrow);
		init();
		//public Character(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		mf = new MainFrame(WINDOW_WIDTH,WINDOW_HEIGHT,enemy,player,arrow,buildme);
	}

	public void init(){
		for(int i = 1; i <= NUM_ENEMY_MELEE; i++){
			enemy.add(ef.createMeleeEnemy());
		}
		for(int i = 1; i <= NUM_ENEM_RANGED; i++){
			enemy.add(ef.createRangedEnemy());
		}
	}

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
	}



	public void playerAnimate(){
		if(player.attacking){
			player.move(Player.Movement.ATTACK.getCode());
			if(player.seq == 3){
				for(int i = 0 ; i < enemy.size(); i++){
					Enemy temp = enemy.get(i);
					if(temp.collide(player.attack())){
						temp.takeDamage(player.atk);
						if(!temp.isAlive()){
							player.gainExp(1);
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
	}


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
	

	public void collide(){
		resolveWalls(player);
		for(int i = 0; i < enemy.size(); i++){
			resolveWalls(enemy.get(i));
		}
	}

	public void render(){
		mf.update();
	}


	public void addEnemies(){
		if(enemy.size() < NUM_ENEM_RANGED + NUM_ENEMY_MELEE){
			if(--recharge <= 0){
				recharge = 80;
				enemy.add(ef.createMeleeEnemy());
				enemy.add(ef.createRangedEnemy());
			}

		}
	}

	public void loop(){
			addEnemies();
			playerAnimate();
			animate();
			collide();
			render();

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
		ArrayList<Integer> buildme;
		public MainFrame(int w, int h, ArrayList<Enemy> enemy, Character player, ArrayList<Arrow> arrow, ArrayList<Integer> buildme){
			this.buildme = buildme;
			setTitle("Final_Project");
			setSize(w,h);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			gc = new GameCanvas(enemy,player,arrow);
			gc.setFocusable(false);
			setFocusable(true);
			add(gc);
			addKeyListener(new Keyboard(keys));
			setVisible(true);
		}

		public void update(){
			gc.repaint();
		}


		class Keyboard implements KeyListener{
			boolean[] keys;
			public Keyboard(boolean[] keys){
				this.keys = keys;
			}
			public void keyTyped(KeyEvent e){
				if(e.getKeyChar() == 'g'){
					player.itemActivate();
				}
			}
			public void keyPressed(KeyEvent e){
				if(player.hm.containsKey((int) e.getKeyChar())){
					key = (int) e.getKeyChar();
				}
				/*
				switch(e.getKeyChar()){
					case 'w'://move up
						keys[0] = true;
						break;
					case 'a'://move left
						keys[1] = true;
						break; 
					case 's'://move down
						keys[2] = true;
						break;
					case 'd'://move right
						keys[3] = true;
						break;
					case ' ':
						keys[4] = true;
						break;
				}*/

			}
			public void keyReleased(KeyEvent e){

				key = (int) '.';
				/*
				switch(e.getKeyChar()){
					case 'w'://move up
						keys[0] = false;
						break;
					case 'a'://move left
						keys[1] = false;
						break; 
					case 's'://move down
						keys[2] = false;
						break;
					case 'd'://move right
						keys[3] = false;
						break;
					case ' ':
						keys[4] = false;
						break;
					case 'g':
						break;
				}*/
			}
		}

	}

	class GameCanvas extends Canvas{
		ArrayList<Enemy> enemy;
		ArrayList<Arrow> arrow;
		Character player;
		public GameCanvas(ArrayList<Enemy> enemy, Character player, ArrayList<Arrow> arrow){
			this.player = player;
			this.enemy = enemy;
			this.arrow = arrow;
			setBackground(Color.WHITE);
		}

		void drawSpriteFrame(Image source, Graphics2D g2d, int x, int y,
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

		    g2d.drawImage(source, x, y, x+width, y+height, //for the 
		                  frameX, frameY, frameX+width, frameY+height,this);
		}

		public void paint(Graphics g){
			BufferStrategy bs = getBufferStrategy();
			if(bs == null){
				createBufferStrategy(2);
				return;
			}
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRect(0,0,getWidth(),getHeight());			
			if(player.attacking && player.seq == 3){
				g2.setColor(Color.GREEN);
				g2.draw(player.attack());
			};
			
			for(int i = 0; i < enemy.size(); i++){
				Enemy e = enemy.get(i);
				if(e.isRanged()){
					drawSpriteFrame(img_enemy_ranged,g2,e.x,e.y,e.face,e.seq,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT);
				}
				else{
					drawSpriteFrame(img_enemy_melee,g2,e.x,e.y,e.face,e.seq,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT);	
				}
			}
			g2.setColor(Color.ORANGE);
			for(int i = 0; i < arrow.size(); i++){
				Arrow a = arrow.get(i);
				g2.draw(a.hitbox);
			}
			drawSpriteFrame(img_player,g2,player.x,player.y,player.face,player.seq,PLAYER_IMG_WIDTH,PLAYER_IMG_HEIGHT);
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

	public static void main(String[] args)throws IOException{
		GameWindow start = new GameWindow();
		start.run();
	}



}