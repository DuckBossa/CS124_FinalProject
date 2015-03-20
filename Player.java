import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.*;
public class Player extends Character implements Serializable{
	public Item item;
	public int gold;
	public HashMap<Integer,Command> hm;
	public HashMap<String,String> map;
        public boolean in;
        public ArrayList<String> pressed;
        
        public Player()
        {
            pressed = new ArrayList<String>();
            x = 20;
            System.out.println("SOSOS");
        }
	public Player(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		super(atk,def,vx,vy,x,y,w,h,lvl);
		item = null;
		gold = 0;
                pressed = new ArrayList<String>();
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

	public CharacterMemento createMemento(){
		return new CharacterMemento(this);
	}
	
	public Rectangle2D attack(){
		Rectangle2D temp = new Rectangle2D.Double((int)(x - GameWindow.PLAYER_ATTACK_W/2 + w/2),(int)(y - GameWindow.PLAYER_ATTACK_H/2 + h/2),GameWindow.PLAYER_ATTACK_W,GameWindow.PLAYER_ATTACK_H);
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

	public void execute(){
            for (int i=0; i<pressed.size(); i++) {
                char cur = pressed.get(i).charAt(0);
                this.move((int)cur);
                if (cur==map.get("item").charAt(0)) {
                    pressed.remove(cur+"");
                    i--;
                }
            }
	}


}