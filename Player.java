import java.awt.geom.Rectangle2D;
public class Player extends Character{
	
	public Player(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl){
		super(atk,def,vx,vy,x,y,w,h,lvl);
	}
	
	public Rectangle2D attack(){
		// check where character is facing
		// get the rectangle that is the same height and width as the character but 
		return hitbox;
	}

}