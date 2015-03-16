import java.util.Random;
public class EnemyPatrolState implements State{
	Enemy e;
	Random random;
	int currMove; 
	public EnemyPatrolState(Enemy e){
		random = new Random();
		this.e = e;
		currMove = random.nextInt()%4;
	}

	public void handle(){
		if(roll()){
			int ranMove = Math.abs(random.nextInt()%4);
			currMove = ranMove;
		}	
		e.move(currMove);
	}

	public boolean roll(){
		boolean val = random.nextInt(20) == 0;
		return val;
	}

}