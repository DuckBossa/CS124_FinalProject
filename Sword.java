//Concrete Weapon Item
import java.io.Serializable;

public class Sword implements Item, Serializable {
	public String getName () {
		return "Sword (Weapon)";
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
		c.atk += 3;
		c.atk += 3;
	}
	public void remove (Character c) {
		c.atk -= 3;
		c.atk -= 3;
	}
	public void activate (Character c) {}
}