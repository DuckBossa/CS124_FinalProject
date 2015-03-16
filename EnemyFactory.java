import java.util.Random;
public class EnemyFactory{
	private Random random;
	private Enemy e;
	public EnemyFactory(Enemy e){
		random = new Random();
		this.e = e;
	}


	public Enemy createRangedEnemy(){
		Enemy temp = e.makeCopy();
		int low = 100;
		int high = 500;
		int ranx = random.nextInt(high-low) + low;
		int rany = random.nextInt(high-low) + low;
		temp.x = ranx;
		temp.y = rany;
		temp.fov_rect = e.makeCopyRect(e.fov_rect);
		temp.hitbox = e.makeCopyRect(e.hitbox);
		temp.setState(new EnemyPatrolState(temp));
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
		temp.hitbox = e.makeCopyRect(e.hitbox);
		temp.setState(new EnemyPatrolState(temp));
		temp.updateRectangle();
		return temp;
	}

}