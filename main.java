
public class main {

	public static void main(String[] args) {
		WIFIScan w = new WIFIScan();
		if (args.length < 1){
			System.out.println("INTERFACE REQUIRED");
		} else {
			w.scan("wlan2");
			NetworkDiscoveryView v = new NetworkDiscoveryView();
			v.updateScreen(w.getAPs());
		}
	}

}
