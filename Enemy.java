import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Random;
public class Enemy extends Character implements CloneEnemy, Serializable{
	public int fov;
	public Rectangle2D fov_rect;
	public State state;
	public boolean enemyDetect;
	public boolean isRanged;
	public Random random;
	public Enemy(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl, int fov){
		super(atk,def,vx,vy,x,y,w,h,lvl);
		random = new Random();
		this.fov = fov;
		fov_rect = new Rectangle2D.Double(x-fov/2, y-fov/2,fov,fov);
		enemyDetect = false;
		isRanged = false;
	}
        
        public Enemy(){}
	
	public Rectangle2D attack(){
		return hitbox;
	}


	public void updateRectangle(){
		super.updateRectangle();
		fov_rect.setRect(x - (fov/2) + (w/2) ,y - (fov/2) + (h/2),fov,fov);
	}

	public Rectangle2D makeCopyRect(Rectangle2D copy){
		Rectangle2D rect = null;
		try{
			rect = (Rectangle2D) copy.clone();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return rect;
	}

	public void handle(){
		state.handle();
	}

	public void setState(State state){
		this.state = state;
	}

	public boolean isRanged(){
		return isRanged;
	}

	public Enemy makeCopy(){
		Enemy cloned = null;
		try{
			cloned = (Enemy) super.clone();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return cloned;
	}

	public boolean roll(){
		boolean val = random.nextInt(16) == 0;
		return val;
	}


}