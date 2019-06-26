package com.picture.DrawRoute;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.picture.DrawRoute.InformationDialog;

public class ShowMap {
static InformationDialog dialog = null;
	
	public static void main(String args[]){
		JFrame frame = new JFrame();  
        JPanel panel = new JPanel(); 
        JButton button = new JButton("µÿÕº");
        button.setSize(200, 30);
        button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(null == dialog){
					dialog = new InformationDialog();
				}
				dialog.setVisible(true);
				dialog.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.out.println("∞Îæ∂£∫" + dialog.getRadiusStr());
						System.out.println("æ≠Œ≥∂»£∫" + dialog.getLocationStr());
					}
				});

			}
        });
        panel.setLayout(new GridLayout()); 
        panel.add(button);
        frame.setTitle("µÿÕº≤‚ ‘");
        frame.setLocation(400, 300);
        frame.add(panel);  
        frame.setSize(400,400);  
        frame.setVisible(true);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
