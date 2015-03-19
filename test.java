
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author J.Lo
 */
public class test {
    HashMap<Integer, dummy> dm = new HashMap<Integer, dummy>();

	public static void main(String args[])
	{
		test a = new test();
		a.putshit();
		dummy temp = a.dm.get(9);
		System.out.println(temp.getDummy());
		temp.dummyChange(11);
		dummy temp2 = a.dm.get(9);
		System.out.println(temp2.getDummy());
		
	}

	public void putshit()
	{
		dm.put(0, new dummy(1));
		dm.put(9, new dummy(10));
	}

	class dummy{
		
		private int lolo;
		
		public dummy(int i)
		{
			lolo = i;
		}
		public int getDummy()
		{
			return lolo;
		}
		public void dummyChange(int x)
		{
			lolo = x;
		}
		
	}

}
