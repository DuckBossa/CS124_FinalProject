//Character Memento class

import java.awt.geom.*;
import java.io.*;
import java.util.*;
public class CharacterMemento implements Serializable {
	private int atk;
	private int def;
	private int hp;
	private int vx,vy;
	private int x,y,w,h;
	private int lvl;
	private int exp;
	private int gold;
	private HashMap<String,String> map;
	private Rectangle2D hitbox;
	private Item item;
	//arg is Character User that will be saved
	public CharacterMemento (Player c){
		setState(c);
	}
	private void setState (Player c) {
		map = new HashMap<String, String>();
		map.put("up", c.map.get("up"));
		map.put("down", c.map.get("down"));
		map.put("left", c.map.get("left"));
		map.put("right", c.map.get("right"));
		map.put("attack", c.map.get("attack"));
		map.put("item", c.map.get("item"));
		atk = c.atk;
		def = c.def;
		vx = c.vx;
		vy = c.vy;
		x = c.x;
		y = c.y;
		w = c.w;
		h = c.h;
		lvl = c.lvl;
		hp = c.hp;
		gold = c.gold;
		exp = c.exp;
	}

	//arg is Character User that will be loaded with stats of this previously saved Memento
	public void getState (Player c) {
		c.atk = atk;
		c.def = def;
		c.vx = vx;
		c.vy = vy;
		c.x = x;
		c.y = y;
		c.w = w;
		c.h = h;
		c.lvl = lvl;
		c.hp = hp;
		c.hitbox = new Rectangle2D.Double(x,y,w,h);
		c.map.put("up", map.get("up"));
		c.map.put("down", map.get("down"));
		c.map.put("left", map.get("left"));
		c.map.put("right", map.get("right"));
		c.map.put("attack", map.get("attack"));
		c.map.put("item", map.get("item"));
		c.hm.clear();
		c.hm.put((int)map.get("up").charAt(0), new MoveUp(c));
		c.hm.put((int)map.get("down").charAt(0), new MoveDown(c));
		c.hm.put((int)map.get("left").charAt(0), new MoveLeft(c));
		c.hm.put((int)map.get("right").charAt(0), new MoveRight(c));
		c.hm.put((int)map.get("attack").charAt(0), new Attack(c));
		c.hm.put((int)map.get("item").charAt(0), new UseItem(c));
		c.hm.put((int)'.',new DoNothing());										

	}
}
