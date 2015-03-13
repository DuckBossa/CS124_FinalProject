import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.image.BufferStrategy;
public class GameWindow{
	public static final int START_HP = 20;
	public static final int START_PlAYER_X = 100;
	public static final int START_PLAY_Y = 100;
	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 480;
	public static final int NUM_ENEMY = 5;
	public static final int FOV = 100;

	private boolean playerAlive;

	private ArrayList<Enemy> enemy;
	private ArrayList<Arrow> arrows;
	private Character player;
	private Image img_player;
	private Image img_enemy;
	private Image img_arrow;
	private Image img_bg;
	private MainFrame mf;
	public GameWindow(){
		playerAlive = true;
		enemy = new ArrayList<Enemy>();
		arrows = new ArrayList<Arrow>();
		//public Character(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		player = new Player(1,2,3,4,50,50,80,80,1);
		mf = new MainFrame(WINDOW_WIDTH,WINDOW_HEIGHT,enemy,player);
	}


	public void animate(){
		for(Enemy e: enemy)
			e.handle();
		for(Arrow a: arrows)
			a.move();
	}

	public void playerAnimate(){

	}

	public void collide(){

	}

	public void render(){

	}

	public void loop(){
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

		public MainFrame(int w, int h, ArrayList<Enemy> enemy, Character player){
			setTitle("CS124_Lab04");
			setSize(w,h);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			gc = new GameCanvas(enemy,player);
			add(gc);
			setVisible(true);
		}

		public void update(){
			gc.repaint();
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

	}



}