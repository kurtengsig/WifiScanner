import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WIFIScan{
	
	int numAP;
	List<AP> APs;
	Scanner s;
	
	//public static void main(String[] args) throws FileNotFoundException{
	//	WIFIScan w = new WIFIScan();	
	//	w.scan("wlan2");
	//}
	
	public WIFIScan(){
		
	}
	
	public List<AP> getAPs(){
		return APs;
	}
		
	public int scan(String wlan){
		
		String cmd =  "iwlist " + wlan + " scan" ;
		Process p;
		try {
			p = Runtime.getRuntime().exec(cmd);
			InputStream output = p.getInputStream();
			s = new Scanner(output);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/*
		try {
			s = new Scanner(new File("ex.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		AP temp = null;
		APs = new ArrayList<AP>();
		numAP = 0;
		
		String string,x;
		String[] parts;
				
		while(s.hasNextLine())
		{
			x = null;
			string = s.nextLine();
			if (string.indexOf("Cell") == 10){
				temp = new AP();
				APs.add(temp);
				numAP++;
			}
			while(temp != null){
				if (string.indexOf("Address") == 20){
					parts = string.split("Address: ");
					//System.out.println(parts[1]);
					temp.address = parts[1];
					break;
				}
				
				if (string.indexOf("Channel") == 20){
					parts = string.split("Channel:");
					//System.out.println(parts[1]);
					temp.channel =  Integer.parseInt(parts[1]);
					break;
				}
				
				if (string.indexOf("ESSID") == 20){
					parts = string.split("\"");
					//System.out.println(parts[1]);
					temp.ESSID = parts[1];
					break;
					
				}
				if (string.indexOf("Quality") == 20){
					parts = string.split("Quality=");
					parts = parts[1].split(" ");
					System.out.println(parts[0]);
					temp.quality = parts[0];
					
					parts = string.split("Signal level=");
					parts = parts[1].split(" ");
					//System.out.println(parts[0]);
					temp.strength =  Integer.parseInt(parts[0]);
					
					break;
				}			
				break;
			}			
		}
		
		
		
		return numAP;
	}
	
	
}