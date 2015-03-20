
import java.io.Serializable;

//concrete Weapon Item

public class Axe implements Item, Serializable {
	public String getName () {
		return "Axe (Weapon)";
	}
	public boolean isWeapon () {
		return true;
	}
	public boolean isArmor () {
		return false;
	}
	public boolean isAccessory () {
		return false;
	}
	public void use (Character c) {
		c.atk += 10;
	}
	public void remove (Character c) {
		c.atk -= 10;
	}
	public void activate (Character c) {}
}