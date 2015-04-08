import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
/*
 * Need to deal with:
 * Host name (ESSID)
 * Channel
 * Encryption
 * Frequency
 * Quality
 * Signal level
 * Bit rates
 * Mode
 * 
 * |------------------------|
 * | t|a|b|s|				| JPanel
 * |------------------------|
 * | Current tab details	|
 * |						|
 * | . . . .                | JPanel
 * |------------------------|
 * |						| JPanel
 * |________________________|
 */
public class NetworkDiscoveryView extends JFrame{
	
	List<AP> accessPoints;
	TabPanel 	  tp;
	DetailsPanel dp;
	ComparisonPanel cp;
	
	
	public NetworkDiscoveryView(){
		ArrayList<String> test = new ArrayList<String>();
		ArrayList<Integer> testChannels = new ArrayList<Integer>();

		test.add("CU Wireless");
		test.add("eduroam");
		test.add("Goat House");
		test.add("Hidden Network");
		
		BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		this.setLayout(layout);
		
		tp = new TabPanel(this, null);
		dp = new DetailsPanel(this);
		cp = new ComparisonPanel(this, testChannels, accessPoints);
		
		this.add(tp);
		this.add(dp);
		this.add(cp);
		
		this.setResizable(false);
		this.setSize(1000, 600);
		this.setVisible(true);
		this.pack();
		//dp.update("Example","4","Yes","2.4","18/23","High","10 Mb/s","Beacon");
	}
	public void notifyTabChange(String tab){
		//Called when one of the buttons are pressed to show info on a different access point
		System.out.println(tab);
		AP temp = null;
		for(int i=0; i<accessPoints.size(); i++){
			if(accessPoints.get(i).ESSID.equalsIgnoreCase(tab)){
				temp = accessPoints.get(i);
			}
		}
		if(temp != null){
			dp.update(temp.ESSID, ""+temp.channel, temp.encryption, temp.quality, temp.strength+" dbm", temp.bitrates, temp.mode, temp.wps);
		}
	}
	
	
	public void updateScreen(List<AP> list){
		accessPoints = list;
		ArrayList<Integer> channelList = new ArrayList<Integer>();
		
		System.out.println("Calling update");
		Collections.sort(list,Collections.reverseOrder());
		int index = list.size();
		if(index > 8)
			index = 8;
		ArrayList<AP> tabList = new ArrayList<AP>();
		for(int i=0; i<index; i++){
			tabList.add(list.get(i));
			channelList.add(list.get(i).channel);
		}
		if (index > 1){
			AP temp = list.get(0);
			dp.update(temp.ESSID, ""+temp.channel, temp.encryption, temp.quality, temp.strength+" dbm", temp.bitrates, temp.mode, temp.wps);
			channelList.add(10);
			cp.update(channelList, accessPoints);
			tp.updateTabs(tabList);
		}
	}
}
