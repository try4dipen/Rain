package com.dipen.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.dipen.rain.graphics.Screen;
import com.dipen.rain.input.Keyboard;

public class Game extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Author : Dipen Routaray
	 * Version : 1
	 */
	public static int height = 300;
	public static int width = height * 16 /9;
	public static int scale = 1;
	int x=0,y=0;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	
	private Screen screen;
	private Keyboard key;
	private BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	/*
	 * Constructor
	 */
	public Game(){
		Dimension size = new Dimension(width*scale,height*scale);
		setPreferredSize(size);
		screen = new Screen(width,height);
		frame = new JFrame();
		key = new Keyboard();
		
		addKeyListener(key);
	}
	
	public synchronized void start(){
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	public synchronized void stop(){
		running = false;
		try{
			thread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	public void run(){
		long lasttime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lasttime)/ns;
			//System.out.println("delta = "+delta);
			lasttime = now;
			while(delta>=1){
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer+=1000;
				frame.setTitle(updates + " ups, " + frames + "frames");
				updates = 0;
				frames = 0;
			}
		}
	}

	private void update() {
		key.update();
		if(key.up)y--;
		if(key.down)y++;
		if(key.left)x--;
		if(key.right)x++;
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3); // triple buffering
			return;
		}
		screen.clear();
		screen.render(x,y);
		for(int i=0;i<pixels.length;i++){
			pixels[i]=screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);		//g.setColor(new Color(140,30,100));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image,0,0,getWidth(),getHeight(),null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args){
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Rain");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
}
