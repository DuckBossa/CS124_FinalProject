//This code should belong to Caretaker class

//Assuming we have three game slots

import java.io.*;
import java.util.*;

public class SerializationCode {

	//this arrayList should only contain three objects (the three versions na pwedeng i-overwrite and save)
	ArrayList<CharacterMemento> savedGames = new ArrayList<CharacterMemento>();

	//this should be done at the beginning of the program, para may malagay sa main menu
	//if there are less than three objects, it means you can start a new game dun sa remaining slots
	public void readSavedGames () {
		try{
			FileInputStream fin = new FileInputStream("savedgames.out");
			ObjectInputStream ois = new ObjectInputStream(fin);
			CharacterMemento cm = (CharacterMemento) ois.readObject();
			while (cm!=null) {
				savedGames.add(cm);
				cm = (CharacterMemento) ois.readObject();
			}
			ois.close();
			System.out.println("got saved games");
		}catch(Exception ex){} 
	}

	//this should be done before the game is closed or exited so that all saved games are safely outputted into the files and will be read when the game is run again
	//this is different from just saving a game... saving a game only means you create a memento and replace the memento in the particular index of the arraylist
	public void outputSavedGames () {
		try {
			FileOutputStream fout = new FileOutputStream("savedgames.out");
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			for (int i=0; i<savedGames.size(); i++)
				oos.writeObject(savedGames.get(i));
			oos.close();
			System.out.println("saved games");
		} catch (Exception ex) {}
	}

	/*
	NOTES:
	- CharacterMemento should be different from the Character interface and the concrete Character User
	- Character User is the originator
	*/

}
