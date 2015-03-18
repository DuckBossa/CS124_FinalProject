public class Attack implements Command{
	Player p;
	
	public Attack(Player p){
		this.p = p;
	}

	public void execute(){
		p.move(Character.Movement.ATTACK.getCode());
	}

}