// Copyright 2020 Ty Bayn
// Last Edited Aug. 11, 2020
// StationaryObject Class

import java.awt.image.BufferedImage;
import java.awt.Graphics;

// StationaryObject is an object on a graphics panel that does not move
public class StationaryObject
{
	// Member methods
	protected int posx; 
	protected int posy;
	protected BufferedImage bi; 
	protected int imageW;
	protected int imageH;

	// StationaryObject constructor
	StationaryObject(int posx, int posy, BufferedImage bi, int imageW, int imageH)
	{
		this.posx = posx;
		this.posy = posy; 
		this.bi= bi;
		this.imageW = imageW; 
		this.imageH = imageH;
	}
	
	// Get the x-position
	public int getposx()
	{
		return posx;
	}
	
	// Set the x-position
	public void setposx(int _posx)
	{
		posx = _posx;
	}
	
	// Get the y-position
	public int getposy()
	{
		return posy;
	}
	
	// Set the y-position
	public void setposy(int _posy)
	{
		posy = _posy;
	}
	
	// Draw the image on the graphics object
	public void drawImage(Graphics g)
	{
		g.drawImage(bi,posx, posy,imageW,imageH,null);
	}

	
}