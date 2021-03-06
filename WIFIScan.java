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
		File f = new File("ex1.txt");
		if (f.exists()){
			try {
				s = new Scanner(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
		String cmd =  "ifconfig "+wlan+" down; iw "+wlan+" set type managed; ifconfig "+wlan+" up;iwlist scan" ;
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
		}
		
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
					//System.out.println(parts[0]);
					temp.quality = parts[0];
					
					parts = string.split("Signal level=");
					parts = parts[1].split(" ");
					//System.out.println(parts[0]);
					temp.strength =  Integer.parseInt(parts[0]);
					break;
				}

				if (string.indexOf("Encryption key") == 20){
					parts = string.split("Encryption key:");
					//System.out.println(parts[1]);
					if (parts[1].indexOf("on")==0){
						temp.encryptionOn = true;
						temp.encryption = "WEP";
					} else {
						temp.encryptionOn = false;
						temp.encryption = "None";
					}
					break;
				}
				
				if (string.indexOf("IE: WPA Version 1") == 20){
					temp.encryption = "WPA";
					break;
				}
			
				if (string.indexOf("IE: IEEE 802.11i/WPA2 Version 1") == 20){
					temp.encryption = "WPA2";
					break;
				}
				
				if (string.indexOf("Mode:") == 20){
					parts = string.split("Mode:");
					temp.mode = parts[1];
				}
				
				if (string.indexOf("Bit Rates:") == 20){
					parts = string.split("Bit Rates:");
					temp.bitrates = temp.bitrates+parts[1]+"; ";
				}
				
				Mode:
				
				break;
			}			
		}
		
		s.close();
		
		f = new File("wps1.txt");
		if (wlan.equals("example")){
			try {
				s = new Scanner(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String cmd =  "ifconfig "+wlan+" down; iw "+wlan+" set type monitor; ifconfig "+wlan+" up;./wps -i "+wlan ;
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
		}
		
		while(s.hasNextLine())
		{
			string = s.nextLine();
			parts = string.split("\\s+");		
			System.out.println(string);
			
			for (AP ap:APs){
				if (ap.ESSID.equals(parts[1])){
					//System.out.println(parts[3]);
					if (parts[0].equals("0.0"))
						ap.wps = "No";
					else 
						ap.wps = "Yes";
				}
			}
		}
		s.close();
		
		return numAP;
	}
	
	
}
