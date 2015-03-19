
import java.io.Serializable;

public class UseItem implements Command, Serializable{
	Player p;

	public UseItem(Player p){
		this.p = p;
	}

	public void execute(){
		p.itemActivate();
	}

}