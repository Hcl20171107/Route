package com.picture.DrawRoute;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Painting extends JPanel{
	@Override protected void paintComponent (Graphics g) { 
		super.paintComponent(g); 
		System.out.println("component"); 
		} 
	@Override public void paint (Graphics g) { 
		super.paint(g); System.out.println("paint"); 
		} 
	public static void main(String [] ars){ 
		JFrame frame = new JFrame("Route");
		Painting painting = new Painting(); 
		frame.add(painting); 
		frame.setSize(600,600); 
		frame.setLocationRelativeTo(null); 
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
		frame.setVisible(true); 
		JFrame frame1 = new JFrame("paintTest"); 
		Painting painting1 = new Painting(); 
		frame1.add(painting1); 
		frame1.setSize(400,400); 
		frame1.setLocationRelativeTo(null); 
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame1.setVisible(true);

		} 
	
		
	
	}
	
