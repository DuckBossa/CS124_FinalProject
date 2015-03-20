//concrete Armor Item

import java.io.Serializable;

public class Dress implements Item, Serializable {
	public String getName () {
		return "Dress (Armor)";
	}
	public boolean isWeapon () {
		return false;
	}
	public boolean isArmor () {
		return true;
	}
	public boolean isAccessory () {
		return false;
	}
	public void use (Character c) {
		c.def += 10;
	}
	public void remove (Character c) {
		c.def -= 10;
	}
	public void activate (Character c) {}
}