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
		JFrame frame = new JFrame("paintTest");
		Painting painting = new Painting(); 
		frame.add(painting); 
		frame.setSize(300,300); 
		frame.setLocationRelativeTo(null); 
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
		frame.setVisible(true); 
		} 
	}
	
