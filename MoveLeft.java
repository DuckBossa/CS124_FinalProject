public class MoveLeft implements Command{
	Player p;

	public MoveLeft(Player p){
		this.p = p;
	}
	
	public void execute(){
		p.move(Character.Movement.LEFT.getCode());
	}

}