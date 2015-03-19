
import java.io.Serializable;


/**
 *
 * @author J.Lo
 */
public class MoveLeft implements Command , Serializable{
	Player p;

	public MoveLeft(Player p){
		this.p = p;
	}
	
	public void execute(){
		p.move(Character.Movement.LEFT.getCode());
	}

}