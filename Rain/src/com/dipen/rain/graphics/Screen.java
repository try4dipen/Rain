package com.dipen.rain.graphics;

public class Screen {
	
	private static int width,height;
	private int[] pixels;
	
	public Screen(int width, int height){
		this.width=width;
		this.height=height;
		pixels = new int[width*height];
	}

}
