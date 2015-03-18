public class MoveUp implements Command{
	Player p;

	public MoveUp(Player p){
		this.p = p;
	}
	
	public void execute(){
		p.move(Character.Movement.UP.getCode());
	}

}