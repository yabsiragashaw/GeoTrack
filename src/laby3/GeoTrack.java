/*This GUI program includes a map and it calculates the distance using the mouse movement as
X and Y coordinates.*/
package laby3;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;



import java.util.ArrayList;

public class GeoTrack extends MouseAdapter implements ActionListener{
	private static MyDrawPanel panel;
	public static JMenuBar panel2;
	private static JLabel xycoordinates;
	private static JLabel infoKm;
	public  JButton reset;
	
	

	public GeoTrack() {
		// Create frame and set properties
		JFrame frame = new JFrame("Mouse input example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 700);
		frame.setLocation(50, 50);
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		
		JMenuBar menuBar = new JMenuBar();
		reset = new JButton("Reset");
		menuBar.add(reset);
		reset.setForeground(Color.BLACK);
		reset.addActionListener(this);
		
		panel = new MyDrawPanel();
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		
		panel2 = new JMenuBar();
		panel2.setLayout(new GridLayout(1, 3));
		panel2.add(menuBar);
		xycoordinates = new JLabel();
		infoKm = new JLabel();
		infoKm.setForeground(Color.RED); 
		panel2.add(xycoordinates);
		panel2.add(infoKm);
		
		
		contentPane.add(panel2);
		
		//frame.setJMenuBar(panel2);
		frame.add(panel2);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	/** Event handling.
	 * 
	 * @param event Event object (created by runtime)
	 */
	public static JLabel getInfoKm() {
		return infoKm;
	}
	public void mousePressed(MouseEvent event) {
		if (event.getButton() == 1) {
			System.out.printf("(x,y) = (%d, %d)\n", event.getX(), event.getY());
			panel.addPoint(event.getX(), event.getY());
		} else if (event.getButton() == 3) {
			System.out.println("Clear panel");
			panel.clear();
		}
	}
	public void mouseMoved(MouseEvent event2) {
		System.out.println("X: " + event2.getX()+ "Y: " + event2.getY());
		
		xycoordinates.setText("Mouse coordinates: ( " + event2.getX()+ ":" + event2.getY() + " )");
		
	}
	

	public static void main(String[] args) {
		new GeoTrack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Clear panel");
		panel.clear();
		
	}
}



@SuppressWarnings("serial")
class MyDrawPanel extends JPanel {
	// Arrays holding the coordinates of the polygon's points
	private ArrayList<Integer> listCoordsX = new ArrayList<Integer>();
	private ArrayList<Integer> listCoordsY = new ArrayList<Integer>();
	static BufferedImage image;
	public GeoRoute ourRoute = new GeoRoute("red line");
	
	public void addPoint(int x, int y) {
		ourRoute.addWaypoint(new GeoPosition(x*(11.24725-8.4375)/921+8.4375, 54.5720556 + y*(53.3325-54.5720556)/691));
		GeoTrack.getInfoKm().setText(String.format("%.3f km", ourRoute.getDistance()));
		System.out.printf("%.3f km \n" , ourRoute.getDistance());
		listCoordsX.add(x);
		listCoordsY.add(y);
		repaint();
	}
	/** Create instance.
	 * 
	 * Marks the panel border with a black line.
	 */
	public MyDrawPanel() {
		
	    
		try {
		image = ImageIO.read(this.getClass().getResource("OSM_Map.png"));
		} catch (IOException e) {
		}
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	/** Set panel size.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(900,700);
	}
	
	/** Add point to polygon and repaint.
	 * 
	 * @param x x coordinate of point to add 
	 * @param y y coordinate of point to add
	 */

	
	/** Clear list and repaint.
	 */
	public void clear() {
		ourRoute.removeAllWaypoints();
		GeoTrack.getInfoKm().setText("Route Cleared");
		System.out.println("Route Deleted");
		listCoordsX.clear();
		listCoordsY.clear();
		repaint();
	}
	
	/** Repaint: Draw polygon in panel.
	 * 
	 * @param g Graphics to paint on
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);

		int numberPoints = listCoordsX.size();
		if (numberPoints > 1) {
			for (int i = 1; i < numberPoints; i++) {
				g.setColor(Color.red);
				g.drawLine(
					listCoordsX.get(i - 1), 
					listCoordsY.get(i - 1), 
					listCoordsX.get(i), 
					listCoordsY.get(i)
				);
			}
		}
	}
}



