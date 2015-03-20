//concrete Accessory Item
import java.io.Serializable;

import java.util.*;
public class HealingPotion implements Item, Serializable {
	public String getName () {
		return "Healing Potion (Accessory)";
	}
	public int uses = 3;
	public boolean isWeapon () {
		return false;
	}
	public boolean isArmor () {
		return false;
	}
	public boolean isAccessory () {
		return true;
	}
	public void use (Character c) {}
	public void remove (Character c) {}
	public void activate (Character c) {
		if (uses<=0) System.out.println ("No more potion.");
		else {
			c.hp+=(Math.min(5,c.maxhp-c.hp));
			uses--;
		}
	}
}