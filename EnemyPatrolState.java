import java.util.Random;
public class EnemyPatrolState implements State{
	Enemy e;
	Random random;
	double proba;
	double probb;
	double probc;
	int currMove; 
	public EnemyPatrolState(Enemy e){
		random = new Random();
		this.e = e;
		proba =  0.5;
		probb = 1.0;
		probc = 1.0;
		currMove = random.nextInt()%4;
	}

	public void handle(){
		if(roll(1)){
			int ranMove = Math.abs(random.nextInt()%4);
			e.move(ranMove);
			System.out.println("leSwitch! to : " + ranMove);
		}
		else{
			e.move(currMove);
		}
	}

	public boolean roll(int x){
		switch(x){
			case 0:
				return random.nextDouble() > proba;
			case 1:
				boolean temp = random.nextDouble() > probc;
				if(temp) probc = 1.0;
				else probc*= 0.99999;
				return temp;
			case 2:
				boolean temp2 = random.nextDouble() > probb;
				if(temp2) probb = 1.0;
				else probb*= 0.99999;
				return temp2;
			default:
				return false;
		}
	}

}