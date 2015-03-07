//Character Memento class

import java.awt.geom.*;
import java.io.*;
public class CharacterMemento implements Serializable {
	private int atk;
	private int def;
	private int hp;
	private int vx,vy;
	private int x,y,w,h;
	private int lvl;
	private Rectangle2D self;

	//arg is Character User that will be saved
	public CharacterMemento (Character c){
		setState(c);
	}
	private void setState (Character c) {
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
		self = c.self;
	}

	//arg is Character User that will be loaded with stats of this previously saved Memento
	public void getState (Character c) {
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
		c.self = self;
	}
}
