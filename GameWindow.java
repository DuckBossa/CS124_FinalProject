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
	public static final int PLAYER_NUM_FRAMES = 4;
	public static final int ENEMY_NUM_FRAMES = 4;
	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 720;
	public static final int NUM_ENEMY = 6;
	public static final int FOV = 150;

	private boolean playerAlive;

	private ArrayList<Enemy> enemy;
	private ArrayList<Arrow> arrows;
	private Player player;
	public static Image img_player;
	private Image img_enemy;
	private Image img_arrow;
	private Image img_bg;
	private MainFrame mf;
	private boolean[] keys;
	private EnemyFactory ef;
	public GameWindow()throws IOException{
		ef = new EnemyFactory(new Enemy(1,2,3,3,100,100,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT,1,FOV));
		img_player = (ImageIO.read(new FileInputStream("img/player.png")));
		img_enemy = (ImageIO.read(new FileInputStream("img/enemy_melee.png")));
		keys = new boolean[5];
		playerAlive = true;
		enemy = new ArrayList<Enemy>();
		arrows = new ArrayList<Arrow>();
		init();
		//public Character(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		player = new Player(100,2,3,4,START_PlAYER_X,START_PLAYER_Y,PLAYER_IMG_WIDTH,PLAYER_IMG_HEIGHT,1);
		mf = new MainFrame(WINDOW_WIDTH,WINDOW_HEIGHT,enemy,player,keys);
	}

	public void init(){
		for(int i = 1; i <= NUM_ENEMY; i++){
			enemy.add(ef.createMeleeEnemy());
		}
	}

	public void animate(){
		enemyAttackMode();
		for(int i = 0; i < enemy.size(); i++){
			Enemy e = enemy.get(i);
			e.handle();
			e.updateRectangle();
		}
		for(Arrow a: arrows)
			a.move();
	}



	public void playerAnimate(){
		if(player.attacking){
			player.move(Player.Movement.UP.getCode());
			if(player.seq == 3){
				for(int i = 0 ; i < enemy.size(); i++){
					Enemy temp = enemy.get(i);
					if(temp.collide(player.attack())){
						temp.takeDamage(player.atk);
						if(!temp.isAlive()){
							player.gainExp(1);
							enemy.remove(i);
							i--;
						}
					}
				}
			}
		}
		else{
			for(int i = 0 ; i < keys.length; i++){
				if(keys[i]){
					player.move(i);
				}
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

	public void loop(){
			playerAnimate();
			animate();
			collide();
			render();
	}

	public void enemyAttackMode(){
		for(int i = 0; i < enemy.size(); i++){
			if(player.collide(enemy.get(i).fov_rect)){
				if(!enemy.get(i).enemyDetect){
					enemy.get(i).setState(new EnemyAttackState(enemy.get(i),player));
				}
			}
		}
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
		
		public MainFrame(int w, int h, ArrayList<Enemy> enemy, Character player, boolean[] keys){
			setTitle("Final_Project");
			setSize(w,h);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			gc = new GameCanvas(enemy,player);
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
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
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
					case 'g':
						break;
				}

			}
			public void keyReleased(KeyEvent e){
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
				}
			}
		}

	}

	class GameCanvas extends Canvas{
		ArrayList<Enemy> enemy;
		Character player;
		public GameCanvas(ArrayList<Enemy> enemy, Character player){
			this.player = player;
			this.enemy = enemy;
			setBackground(Color.WHITE);//make this an image
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
			g2.setColor(Color.BLUE);
			g2.draw(player.hitbox);
			if(player.attacking && player.seq == 3){
				g2.setColor(Color.GREEN);
				g2.draw(player.attack());
			};
			
			for(int i = 0; i < enemy.size(); i++){
				Enemy e = enemy.get(i);
				g2.setColor(Color.RED);
				g2.draw(e.hitbox);
				g2.setColor(Color.BLACK);
				g2.draw(e.fov_rect);
				drawSpriteFrame(img_enemy,g2,e.x,e.y,e.face,e.seq,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT);
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