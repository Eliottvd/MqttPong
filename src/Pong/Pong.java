/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pong;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;


public class Pong extends JFrame {
	
	//screen size variables.
	int gWidth = 500;
	int gHeight = 400;
	Dimension screenSize = new Dimension(gWidth, gHeight);
	
	Image dbImage;
	Graphics dbGraphics;
	
	//ball object
	static Ball b = new Ball(250, 200);
	
	
	//constructor for window
	public Pong() {
		this.setTitle("Pong!");
		this.setSize(screenSize);
		this.setResizable(false);
		this.setVisible(true);
		this.setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new AL());
	}
	
	public static void main(String[] args) {
		Pong pg = new Pong();
		
		//create and start threads.
		Thread ball = new Thread(b);
		ball.start();
		Thread p1 = new Thread(b.p1);
		Thread p2 = new Thread(b.p2);
                ThreadSwagg ts = new ThreadSwagg(b.p1);
                b.setThreadSwagg(ts);
		p2.start();
		p1.start();
                ts.start();
		
	}
	
	@Override
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbGraphics = dbImage.getGraphics();
		draw(dbGraphics);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public void draw(Graphics g) {
		b.draw(g);
		b.p1.draw(g);
		b.p2.draw(g);
		
		g.setColor(Color.WHITE);
		g.drawString(""+b.p1score, 15, 20);
		g.drawString(""+b.p2score, 385, 20);
		
		repaint();
	}
	
	public class AL extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
                        System.out.println("key pressed !");
			b.p1.keyPressed(e);
			b.p2.keyPressed(e);
		}
		@Override
		public void keyReleased(KeyEvent e) {
                        System.out.println("key released !");
			b.p1.keyReleased(e);
			b.p2.keyReleased(e);
		}
		
	}
}