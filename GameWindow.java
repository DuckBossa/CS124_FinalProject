import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.image.BufferStrategy;
public class GameWindow{
	public static final int START_HP = 20;
	public static final int START_PlAYER_X = 20;
	public static final int START_PLAYER_Y = 20;
	public static final int PLAYER_IMG_WIDTH = 20;
	public static final int PLAYER_IMG_HEIGHT = 20;
	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 480;
	public static final int NUM_ENEMY = 5;
	public static final int FOV = 100;

	private boolean playerAlive;

	private ArrayList<Enemy> enemy;
	private Enemy dummy;
	private ArrayList<Arrow> arrows;
	private Character player;
	private Image img_player;
	private Image img_enemy;
	private Image img_arrow;
	private Image img_bg;
	private MainFrame mf;
	private boolean[] keys;
	private EnemyFactory ef;
	public GameWindow(){
		ef = new EnemyFactory();
		dummy = new Enemy(1,2,3,3,100,100,60,60,1,FOV);
		keys = new boolean[5];
		playerAlive = true;
		enemy = new ArrayList<Enemy>();
		arrows = new ArrayList<Arrow>();
		init();
		//public Character(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		player = new Player(1,2,3,4,START_PlAYER_X,START_PLAYER_Y,PLAYER_IMG_WIDTH,PLAYER_IMG_HEIGHT,1);
		mf = new MainFrame(WINDOW_WIDTH,WINDOW_HEIGHT,enemy,player,keys);
	}

	public void init(){
		//spawn the enemies using the dummy
		Enemy x = ef.getClone(dummy);
		Enemy y = ef.getClone(dummy);
		System.out.println(System.identityHashCode(System.identityHashCode(x)));
		System.out.println(System.identityHashCode(System.identityHashCode(y)));
		System.out.println(System.identityHashCode(System.identityHashCode(dummy)));
	}

	public void animate(){
		for(Enemy e: enemy)
			e.handle();
		for(Arrow a: arrows)
			a.move();
	}

	public void createRangedEnemy(){

	}

	public void createMeleeEnemy(){

	}

	public void playerAnimate(){
		for(int i = 0 ; i < keys.length; i++){
			if(keys[i]){
				player.move(i);
			}
		}
	}

	public void collide(){

	}

	public void render(){

	}

	public void loop(){
			playerAnimate();
			animate();
			collide();
			render();
	}

	public void run(){
		//show frame, run everything;
		while(playerAlive){
			try{
				Thread.sleep(16);
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
			setTitle("CS124_Lab04");
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

		public void paint(Graphics g){
			BufferStrategy bs = getBufferStrategy();
			if(bs == null){
				createBufferStrategy(2);
				return;
			}
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.LIGHT_GRAY);
			g2.draw(player.hitbox);
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

	public static void main(String[] args){
		GameWindow start = new GameWindow();
		start.run();
	}



}