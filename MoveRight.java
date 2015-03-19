
import java.io.Serializable;

public class MoveRight implements Command, Serializable{
	Player p;

	public MoveRight(Player p){
		this.p = p;
	}
	
	public void execute(){
		p.move(Character.Movement.RIGHT.getCode());
	}

}