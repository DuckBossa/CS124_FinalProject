import java.awt.geom.Rectangle2D;
import java.util.*;
public class Player extends Character{
	public Item item;
	public int gold;
	public HashMap<Integer,Command> hm;
	public HashMap<String,String> map;
	public Player(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		super(atk,def,vx,vy,x,y,w,h,lvl);
		item = null;
		gold = 0;
		hm = new HashMap<Integer,Command>();
		map = new HashMap<String,String>();
		hm.put((int)'.',new DoNothing());
		hm.put((int) 'w', new MoveUp(this));
		hm.put((int) 'a', new MoveLeft(this));
		hm.put((int) 's', new MoveDown(this));
		hm.put((int) 'd', new MoveRight(this));
		hm.put((int) ' ', new Attack(this));
		hm.put((int) 'g', new UseItem(this));

		map.put("up","w");
		map.put("down","s");
		map.put("left","a");
		map.put("right","d");
		map.put("attack"," ");
		map.put("item","g");

	}
	
	public Rectangle2D attack(){
		Rectangle2D temp =  null;
		// check where character is facing
		// get the rectangle that is the same height and width as the character but 
		int hitx = 0;
		int hity = 0;
		switch(face){
			case 0: //up
				hitx = x; 
				hity = y - h;
				break;
			case 1: //left
				hitx = x - h;
				hity = y + w/3;
				break;
			case 2: //down
				hitx = x;
				hity = y + h;			
				break;
			case 3: //right
				hitx = x + w;
				hity = y + w/3;			
				break;
		}
		if(face%2 == 0){
			temp = new Rectangle2D.Double(hitx,hity,w,h);
		}
		else{
			temp = new Rectangle2D.Double(hitx,hity,h,w);
		}
		return temp;
	}

	public void setItem(Item i){
		if(item == null){
			item = i;
		}
		else{
			item.remove(this);
			item = i;
		}
		item.use(this);
	}

	public void itemActivate(){
		if(item != null){
			item.activate(this);
		}
	}

	public void execute(int command){
		if(hm.containsKey(command)){
			Command exec = hm.get(command);
			exec.execute();
		}
	}


}