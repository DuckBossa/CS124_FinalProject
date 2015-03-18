import java.awt.geom.Rectangle2D;
public class Arrow{	
	public int x,y,w,h;
	public double vx,vy;
	public int life;
	public int dmg;
	public Rectangle2D hitbox;

	public Arrow(int x, int y, double vx, double vy, int dmg, int w, int h){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.dmg = dmg;
		this.h = h;
		this.w = w;
		life = 60;
		hitbox = new Rectangle2D.Double(x,y,w,h);
	}

	public void move(){
		x += vx;
		y += vy;
		--life;
		hitbox.setRect(x,y,w,h);
	}

	public boolean motionLife(){
		return (life) >= 0;
	}

}