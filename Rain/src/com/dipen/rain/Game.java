package com.dipen.rain;

public class Game implements Runnable{

	/**
	 * @param args
	 */
	public static int width = 300;
	public static int height = width * 16 /9;
	public static int scale = 3;
	
	private Thread thread;
	private boolean running = false;
	
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
		while(running){
			
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub		

	}

}
