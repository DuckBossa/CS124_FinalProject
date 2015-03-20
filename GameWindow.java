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
	public static final int PLAYER_ATTACK_W = 192;
	public static final int PLAYER_ATTACK_H = 192;
	public static final int ATTACK_FRAMES = 18;
	public static final int ATTACK_COL = 5;
	public static final int ATTACK_ROW = 4;
	public static final int ARROW_W = 60;
	public static final int ARROW_H = 15;
	public static final int PLAYER_NUM_FRAMES = 4;
	public static final int ENEMY_NUM_FRAMES = 4;
	public static final int WINDOW_WIDTH = 959;
	public static final int WINDOW_HEIGHT = 623;
	public static final int NUM_ENEMY_MELEE = 5;
	public static final int NUM_ENEM_RANGED = 5;
	public static final int FOV_MELEE = 150;
	public static final int FOV_RANGED = 300;

	private boolean playerAlive;

	private ArrayList<Enemy> enemy;
	private ArrayList<Arrow> arrow;
	public Player player;
	private String img_player;
	private String img_enemy_melee;
	private String img_enemy_ranged;
	private String img_arrow;
	private String img_attack;
	private String img_bg;
	private MainFrame mf;
	private int key;
	private EnemyFactory ef;
	private Random random;
	private int recharge;
	public FlyweightFactory ff;
	private ArrayList<String> pressed;
	public Shop shop;
	public Settings keyset;
	public JLabel curItem, curLevel, curGold, curHP, curAttack, curDef;
	public GameWindow()throws IOException{
		pressed = new ArrayList<String>();
		key = (int) '.';
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
		enemy = new ArrayList<Enemy>();
		arrow = new ArrayList<Arrow>();
		player = new Player(100,2,5,5,START_PlAYER_X,START_PLAYER_Y,PLAYER_IMG_WIDTH,PLAYER_IMG_HEIGHT,1);
		ef = new EnemyFactory(new Enemy(1,2,3,3,100,100,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT,1,0),player,arrow);
		init();
		//public Character(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		mf = new MainFrame(WINDOW_WIDTH,WINDOW_HEIGHT,enemy,player,arrow,ff);
		shop = new Shop(mf.gc.ff, mf.gc.player);
		keyset = new Settings(mf.gc.player);
		shop.setVisible(false);
		keyset.setVisible(false);
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
			if (pressed.isEmpty()) player.execute((int)'.');
			else {
				for (int i=0; i<pressed.size(); i++) {
					char cur = pressed.get(i).charAt(0);
					player.execute((int)cur);
					if (cur==player.map.get("item").charAt(0)) {
						pressed.remove(cur+"");
						i--;
					}
				}
			}
			//player.execute(key);						
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
			curLevel.setText("Level: " + player.lvl);
			curItem.setText("Item: <empty>");
			if (player.item!=null)
				curItem.setText("Item: " + player.item.getName());
			curHP.setText("Health: " + player.hp);
			curAttack.setText("Attack: " + player.atk);
			curDef.setText("Defense: " + player.def);
			curGold.setText("Gold: " + player.gold);
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
		public MainFrame(int w, int h, ArrayList<Enemy> enemy, Player player, ArrayList<Arrow> arrow,FlyweightFactory ff){
			setTitle("Final_Project");
			setSize(w,h);
			setResizable(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			gc = new GameCanvas(enemy,player,arrow,ff);
			gc.setFocusable(false);
			setFocusable(true);
			setLayout(new BorderLayout());
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

			curLevel = new JLabel ("Level: " + player.lvl);
			curItem = new JLabel ("Item: <empty>");
			if (player.item!=null)
				curItem = new JLabel ("Item: " + player.item.getName());
			curHP = new JLabel ("Health: " + player.hp);
			curAttack = new JLabel ("Attack: " + player.atk);
			curDef = new JLabel ("Defense: " + player.def);
			curGold = new JLabel ("Gold: " + player.gold);

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
		}

		public void update(){
			gc.repaint();
		}


		class Keyboard implements KeyListener{
			public Keyboard(){
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if(player.hm.containsKey((int) e.getKeyChar())){
					pressed.add(e.getKeyChar()+"");
					//key = (int) e.getKeyChar();
				}
			}
			public void keyReleased(KeyEvent e){
				if (pressed.contains(e.getKeyChar()+""))
					pressed.remove(e.getKeyChar()+"");
				//key = (int) '.';
			}
		}

	}

	class GameCanvas extends Canvas{
		ArrayList<Enemy> enemy;
		ArrayList<Arrow> arrow;
		FlyweightFactory ff;
		Player player;
		public GameCanvas(ArrayList<Enemy> enemy, Player player, ArrayList<Arrow> arrow, FlyweightFactory ff){
			this.ff = ff;
			this.player = player;
			this.enemy = enemy;
			this.arrow = arrow;
			setBackground(Color.WHITE);
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
			if(player.attacking){
				drawAttackFrame(ff.getImage(img_attack),g2,player.x - PLAYER_ATTACK_W/2 + player.w/2 ,player.y - PLAYER_ATTACK_H/2 + player.h/2 ,ATTACK_COL,PLAYER_ATTACK_W,PLAYER_ATTACK_H,player.seq);
			};
			
			for(int i = 0; i < enemy.size(); i++){
				Enemy e = enemy.get(i);
				if(e.isRanged()){
					drawSpriteFrame(ff.getImage(img_enemy_ranged),g2,e.x,e.y,e.face,e.seq,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT);
				}
				else{
					drawSpriteFrame(ff.getImage(img_enemy_melee),g2,e.x,e.y,e.face,e.seq,ENEMY_IMG_WIDTH,ENEMY_IMG_HEIGHT);	
				}
			}
			g2.setColor(Color.ORANGE);
			for(int i = 0; i < arrow.size(); i++){
				Arrow a = arrow.get(i);
				g2.drawImage(ff.getImage(img_arrow),a.x,a.y,Color.LIGHT_GRAY,null);
			}
			drawSpriteFrame(ff.getImage(img_player),g2,player.x,player.y,player.face,player.seq,PLAYER_IMG_WIDTH,PLAYER_IMG_HEIGHT);
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
