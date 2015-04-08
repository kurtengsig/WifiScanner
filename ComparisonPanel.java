import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class ComparisonPanel extends JPanel{
	int height = 260;
	ArrayList<Integer> points;
	List<AP> aps;
	public ComparisonPanel(NetworkDiscoveryView n, ArrayList<Integer> p, List<AP> a){
		points = p;
		aps = a;
	}
	public Dimension getPreferredSize(){
		return new Dimension(900, height);
	}
	
	public void update(ArrayList<Integer> p, List<AP> a){
		points = p;
		aps = a;
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, 900, height);
		g.setColor(Color.black);
		g.drawLine(0, 0, 900, 0);
		drawGridLines(g, 10, 8);
		plotPoints(g, 11, 8);
		drawAxis(g, "Channels In Use", "2.4 GHz Channels", "Frequency of Use");
	}
	public void drawAxis(Graphics g, String title, String xAxis, String yAxis){
		Graphics2D g2 = (Graphics2D)g;
		
		//Draws the x and y axis
		g2.drawLine(40, 40, 40, height-40);
		g2.drawLine(40, height-40, 880-40, height-40);
		
		//Draws the title
		g2.drawString(title, 400, 30);
		
		//Labels the x axis
		g2.drawString(xAxis, 400, height-10);
		
		//Rotates the context to write the label for the y axis
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.PI);
		transform.rotate(Math.PI/2);
		g2.setTransform(transform);
		g2.drawString(yAxis, -570, (height/2)-115);
		transform.rotate(Math.PI/2); // returns the context to normal
	}
	public void drawGridLines(Graphics g, int x, int y){
		int xIncrement = (800)/x;
		int yIncrement = (height-80)/y;
		g.setColor(Color.LIGHT_GRAY);
		for(int i=0; i<y; i++){
			g.drawLine(40, 40+(i*yIncrement), 840, 40+(i*yIncrement));
		}
		for(int i=0; i<x; i++){
			g.drawLine(40+xIncrement+(i*xIncrement), 40, 40+xIncrement+(i*xIncrement), height-40);
		}
		g.setColor(Color.black);
	}
	public void plotPoints(Graphics g, int x, int y){
		int xIncrement    = (800)/(x-1);
		int yIncrement    = (height-80)/y;
		int currentYCount = 0;
		int currentX      = 0;
		ArrayList<PlottedPoints> plotted = new ArrayList<PlottedPoints>();
		g.setColor(Color.black);
		int suggestion = getSuggestedChannel();
		for(int j=0; j<x; j++){
			for(int i=0; i<points.size(); i++){
				if(points.get(i) == currentX+1){
					currentYCount+=1;
				}
			}
			plotted.add(new PlottedPoints(currentX, currentYCount));
			//System.out.println("Plotting for points: "+currentX+","+currentYCount);
			g.fillOval(40-3+(xIncrement*currentX), (height-(37+ (yIncrement*currentYCount))-10), 6, 6);
			if(currentX == suggestion){
				g.setColor(Color.BLUE);
				g.drawOval(40-5+(xIncrement*currentX), (height-(39+ (yIncrement*currentYCount))-10), 10, 10);
				g.drawOval(40-6+(xIncrement*currentX), (height-(40+ (yIncrement*currentYCount))-10), 12, 12);
				g.drawOval(40-7+(xIncrement*currentX), (height-(41+ (yIncrement*currentYCount))-10), 14, 14);
				
				g.setColor(Color.BLACK);
			}
			currentX +=1;
			currentYCount = 0;
		}
		PlottedPoints prevPoint = plotted.get(0);
		for(int i=1; i<plotted.size(); i++){
			g.drawString(""+i, 36+(xIncrement*prevPoint.x), height - 25);
			g.drawLine(40+(xIncrement*prevPoint.x), (height-(37+ (yIncrement*prevPoint.y+3))-4), 
					40+(xIncrement*plotted.get(i).x), (height-(37+ (yIncrement*plotted.get(i).y+3)))-4);
			prevPoint = plotted.get(i);
		}
		g.drawString(""+x, 36+(xIncrement*(x-1)), height - 25);
		g.setColor(Color.black);
	}
	public int getChannelScore(int channel){
		//ArrayList<AP> aps = new ArrayList<AP>();
		if(aps == null){
			return -1;
		}
		int start = 0;
		int end = 11;
		int score = 0;
		if(channel-6 > start){
			start = channel-6;
		}
		if(channel+6 < end){
			end = channel+6;
		}
		for(int i=start; i<=end; i++){
			for(int j=0; j<aps.size(); j++){
				if(aps.get(j).channel == i){
					String qual = aps.get(j).quality;
					int q = Integer.parseInt(qual.split("/")[0]);
					if(q > 50){
						score += 10;
					}
					else if(q > 30){
						score += 5;
					}
					else{
						score +=3;
					}
				}
			}
		}
		if(channel == 1 || channel == 6 || channel == 11){
			if(channel - 20 < 0)
				score = 0;
			else
				score = score -20;
		}
		return score;
	}
	public int getSuggestedChannel(){
		int[] channels = new int[11];
		for(int i=1; i<12; i++){
			channels[i-1] = getChannelScore(i);
		}
		int minChannel =1;
		for(int i=0; i<11; i++){
			if(channels[i] < channels[minChannel])
				minChannel = i;
		}
		return minChannel;
	}
}

