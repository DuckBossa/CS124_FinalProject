
import java.io.Serializable;

public class MoveUp implements Command, Serializable{
	Player p;

	public MoveUp(Player p){
		this.p = p;
	}
	
	public void execute(){
		p.move(Character.Movement.UP.getCode());
	}

}