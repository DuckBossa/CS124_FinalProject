import java.awt.geom.Rectangle2D;
public class Enemy extends Character implements CloneEnemy{
	public int fov;
	public Rectangle2D fov_rect;
	public State state;
	public boolean enemyDetect;
	public Enemy(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl, int fov){ // can be used for both melee and ranged, just depends on the handle
		super(atk,def,vx,vy,x,y,w,h,lvl);
		this.fov = fov;
		fov_rect = new Rectangle2D.Double(x-fov/2, y-fov/2,fov,fov);
		enemyDetect = false;
	}

	
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

}