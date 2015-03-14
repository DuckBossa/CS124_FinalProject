public class Enemy extends Character implements CloneEnemy{
	private int fov;
	private State state;
	public Enemy(int atk, int def, int vx, int vy, int x, int y,int w, int h, int lvl, int fov){ // can be used for both melee and ranged, just depends on the handle
		super(atk,def,vx,vy,x,y,w,h,lvl);
		this.fov = fov;
	}

	public void move(int dir){
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