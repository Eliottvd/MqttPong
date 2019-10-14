/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;


public class Paddle implements Runnable{
	
	int x, y, yDirection, id;
	
	Rectangle paddle;
	
	public Paddle(int x, int y, int id){
		this.x = x;
		this.y = y;
		this.id = id;
		paddle = new Rectangle(x, y, 10, 50);
		}
		
        
	public void keyPressed(KeyEvent e) {
		switch(id) {
		default:
			System.out.println("Please enter a Valid ID in paddle contructor");
			break;
		case 1:
			if(e.getKeyCode() == KeyEvent.VK_Z)
                        {
				setYDirection(-1);
                        }
			if(e.getKeyCode() == KeyEvent.VK_S) {
				setYDirection(1);
			}
			break;
			
		case 2:
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				setYDirection(-1);
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				setYDirection(1);
			}
			break;
	}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(id) {
		default:
			System.out.println("Please enter a Valid ID in paddle contructor");
			break;
		case 2:
		if(e.getKeyCode() == e.VK_UP) {
			setYDirection(0);
		}
		if(e.getKeyCode() == e.VK_DOWN) {
			setYDirection(0);
		}
		break;
		case 1:
			
		if(e.getKeyCode() == e.VK_Z) {
			setYDirection(0);
		}
		if(e.getKeyCode() == e.VK_S) {
			setYDirection(0);
		}
		break;
	}
	}
	public void setYDirection(int yDir) {
		yDirection = yDir;
	}
	
	public void move() {
	 	paddle.y += yDirection;
	 	if (paddle.y <= 15)
	 		paddle.y = 15;
	 	if (paddle.y >= 340)
	 		paddle.y = 340;
	}
	public void draw(Graphics g) {
		switch(id) {
		default:
			System.out.println("Please enter a Valid ID in paddle contructor");
			break;
		case 1:
			g.setColor(Color.CYAN);
			g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
			break;
		case 2:
			g.setColor(Color.pink);
			g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
			break;
		}
	}
	@Override
	public void run() {
		try {
			while(true) {
				move();
				Thread.sleep(7);
			}
		} catch(Exception e) { System.err.println(e.getMessage()); }
	}


	

}