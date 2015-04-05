import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AP implements Comparable<AP>{
	
	String address;
	String ESSID;
	String quality;
	String frequency;
	int channel;
	int strength;
	
	
	
	public AP(){
		
	}
	@Override
	public int compareTo(AP other) {
		if(this.strength > other.strength){
			return 1;
		}
		else{
			return -1;
		}
	}
	
	
};