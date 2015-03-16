import java.awt.geom.Rectangle2D;
public class Enemy extends Character implements CloneEnemy{
	private int fov;
	private State state;
	public Enemy(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl, int fov){ // can be used for both melee and ranged, just depends on the handle
		super(atk,def,vx,vy,x,y,w,h,lvl);
		this.fov = fov;
	}

	
	public Rectangle2D attack(){
		return hitbox;
	}



	public Rectangle2D makeCopyRect(){
		Rectangle2D rect = null;
		try{
			rect = (Rectangle2D) hitbox.clone();
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