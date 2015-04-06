import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;



public class DetailsPanel extends JPanel{
	private String name = "";
	private String channel = "";
	private String encrypted = "";
	private String freq = "";
	private String qual = "";
	private String siglev = "";
	private String bitrate = "";
	private String mode = "";
	private int    labelOffsetX = 30;
	private int    labelOffsetY = 20;
	private int    labelWidth	= labelOffsetX + 95;
	public DetailsPanel(NetworkDiscoveryView n){
		this.setBackground(Color.BLACK);
	}
	public Dimension getPreferredSize(){
		return new Dimension(getWidth(), 360);
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.green);
		g.drawString("Name (ESSID):", labelOffsetX, 20+labelOffsetY);  g.drawString(name, labelWidth, 20+labelOffsetY);
		g.drawString("Channel:", labelOffsetX, 40+labelOffsetY);       g.drawString(channel, labelWidth, 40+labelOffsetY);
		g.drawString("Encrypted:", labelOffsetX, 60+labelOffsetY);     g.drawString(encrypted, labelWidth, 60+labelOffsetY);
		g.drawString("Frequency (Hz):", labelOffsetX, 80+labelOffsetY);g.drawString(freq, labelWidth, 80+labelOffsetY);
		g.drawString("Quality:", labelOffsetX, 100+labelOffsetY);      g.drawString(qual, labelWidth, 100+labelOffsetY);
		g.drawString("Signal Level:", labelOffsetX, 120+labelOffsetY); g.drawString(siglev, labelWidth, 120+labelOffsetY);
		g.drawString("Bit Rate:", labelOffsetX, 140+labelOffsetY);     g.drawString(bitrate, labelWidth, 140+labelOffsetY);
		g.drawString("Mode:", labelOffsetX, 160+labelOffsetY);         g.drawString(mode, labelWidth, 160+labelOffsetY);
	}
	public void update(String n, String chan, String e, String q, String sig, String br, String m){
		name = n;
		channel = chan;
		encrypted = e;
		freq = toFrequency(Integer.parseInt(chan));
		qual = q;
		siglev = sig;
		bitrate = br;
		mode = m;
		this.repaint();
	}
	
	public String toFrequency(int channel){
		return ("2.4"+((channel-1)*5+12));
	}
}
