import java.awt.geom.Rectangle2D;
public class Arrow{	
	private int x,y,vx,vy;
	private int life;
	private Rectangle2D self;
	public Arrow(int x, int y, int vx, int vy){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		life = 10;
	}

	public void move(){
		x += vx;
		y += vy;
	}

	public boolean motionLife(){
		return (--life) >= 0;
	}

}