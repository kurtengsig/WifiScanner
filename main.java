
public class main {

	public static void main(String[] args) {
		WIFIScan w = new WIFIScan();
		w.scan("wlan2");
		NetworkDiscoveryView v = new NetworkDiscoveryView();
		v.updateScreen(w.getAPs());
	}

}
