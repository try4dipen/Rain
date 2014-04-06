package com.dipen.rain.graphics;

import java.util.Random;

public class Screen {
	
	private static int width,height;
	public int[] pixels;
	public final int MAP_SIZE=8;
	public final int MAP_SIZE_MASK=MAP_SIZE-1;
	public int[] tiles = new int[MAP_SIZE*MAP_SIZE];
	
	private Random random = new Random();
	
	int time=0;
	int counter=0;
	
	public Screen(int width, int height){
		this.width=width;
		this.height=height;
		pixels = new int[width*height];
		
		for(int i=0; i<MAP_SIZE*MAP_SIZE; i++){
			tiles[i] = random.nextInt(0xffffff);
		}
		
	}
	
	public void clear(){
		for(int i=0; i<pixels.length;i++){
			pixels[i]=0;
		}
	}
	
	public void render(int xoffset,int yoffset){
		for(int y=0; y<height; y++){
			int yy =y+yoffset;
			if(y<0 || y>=height)break;
			for(int x=0; x<width; x++){
				if(x<0 || x>=width)break;
				int xx = x+xoffset;
				int tileindex = ((xx>>4)&MAP_SIZE_MASK) + ((yy>>4)&MAP_SIZE_MASK)*MAP_SIZE;
				pixels[x+(y*width)] = tiles[tileindex];
			}
		}
	}

}
