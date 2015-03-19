//Factory for flyweight
//Flyweight object is BufferedImage

import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

class FlyweightFactory {
	private HashMap<String, BufferedImage> pool;
	public FlyweightFactory() {
		pool = new HashMap<String, BufferedImage>();
	}
	public BufferedImage getImage(String filename){
		if (!pool.containsKey(filename)) {
			try {
				BufferedImage img = ImageIO.read(new File(filename));
				pool.put(filename, img);
			} catch (IOException e) {}
		}
		return pool.get(filename);
	}
}
