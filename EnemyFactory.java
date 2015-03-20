import java.util.Random;
import java.util.ArrayList;
public class EnemyFactory{
	private Random random;
	private ArrayList<Player> p;
	private ArrayList<Arrow> arrow;
	private Enemy e;
	public EnemyFactory(Enemy e, ArrayList<Player> p, ArrayList<Arrow> arrow){
		random = new Random();
		this.e = e;
		this.p = p;
		this.arrow = arrow;
	}


	public Enemy createRangedEnemy(){
		Enemy temp = e.makeCopy();
		int low = 100;
		int high = 500;
		int ranx = random.nextInt(high-low) + low;
		int rany = random.nextInt(high-low) + low;
		temp.x = ranx;
		temp.y = rany;
		temp.fov = GameWindow.FOV_RANGED;
		temp.fov_rect = e.makeCopyRect(e.fov_rect);
		temp.hitbox = e.makeCopyRect(e.hitbox);
		temp.setState(new EnemyRangedState(temp,arrow));
		temp.isRanged = true;
		temp.updateRectangle();
		return temp;
	}
	public Enemy createMeleeEnemy(){
		Enemy temp = e.makeCopy();
		int low = 100;
		int high = 500;
		int ranx = random.nextInt(high-low) + low;
		int rany = random.nextInt(high-low) + low;
		temp.x = ranx;
		temp.y = rany;
		temp.fov_rect = e.makeCopyRect(e.fov_rect);
		temp.fov = GameWindow.FOV_MELEE;
		temp.hitbox = e.makeCopyRect(e.hitbox);
		temp.setState(new EnemyMeleeState(temp));
		temp.isRanged = false;
		temp.updateRectangle();
		return temp;
	}

}