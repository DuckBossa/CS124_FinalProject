public class EnemyFactory{
	
	public Enemy getClone(Enemy e){
		return e.makeCopy();
	}

}