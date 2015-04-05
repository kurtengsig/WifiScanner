import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;


public class TabPanel extends JPanel{
	NetworkDiscoveryView frame;
	ArrayList<JButton> buttons;
	public TabPanel(NetworkDiscoveryView n, ArrayList<String> titles){
		frame = n;
		buttons = new ArrayList<JButton>();

		if(titles != null){
			GridLayout l = new GridLayout(0, titles.size());
			this.setLayout(l);
			this.setBackground(Color.LIGHT_GRAY);
			
			for(String s: titles){
				JButton j = new JButton(s);
				j.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent a) {
						frame.notifyTabChange(((JButton)a.getSource()).getText());
					}
				});
				j.setBackground(Color.LIGHT_GRAY);
				j.setForeground(Color.BLACK);
				buttons.add(j);
				add(j);
			}
		}
		
	}
	public Dimension getPreferredSize(){
		return new Dimension(getWidth(), 40);
	}
	public void updateTabs(List<AP> list){
		for(int i=0; i<buttons.size(); i++){
			this.remove(buttons.get(i));
		}
		
		GridLayout l = new GridLayout(0, list.size());
		this.setLayout(l);
		this.setBackground(Color.LIGHT_GRAY);
		
		for(AP s: list){
			JButton j = new JButton(s.ESSID);
			j.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent a) {
					frame.notifyTabChange(((JButton)a.getSource()).getText());
				}
			});
			j.setBackground(Color.LIGHT_GRAY);
			j.setForeground(Color.BLACK);
			add(j);
		}
	}
}
