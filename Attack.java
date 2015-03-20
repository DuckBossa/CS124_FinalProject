
import java.io.Serializable;


/**
 *
 * @author J.Lo
 */
public class Attack implements Command ,Serializable{
	Player p;
	
	public Attack(Player p){
		this.p = p;
	}

	public void execute(){
		p.move(Character.Movement.ATTACK.getCode());
	}

}