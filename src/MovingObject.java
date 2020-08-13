// Copyright 2020 Ty Bayn
// Last Edited Aug. 11, 2020
// MovingObject Class

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;

// Moving object is a moving stationaryObject
public class MovingObject extends StationaryObject
{
	
	//Member methods for velocities
	protected int vx = 0;
	protected int vy = 0;

	// MovingObject constructor
	public MovingObject(int posx, int posy, BufferedImage bi, int imageW, int imageH, int vx, int vy)
	{
		// Super
		super(posx, posy, bi,  imageW, imageH); 

		// Set velocities 
		this.vx=vx;
		this.vy=vy;
	}
	
	// Get the x-velocity
	public int getvx()
	{
		return vx;
	}
	
	// Set the x-velocity
	public void setvx(int _vx)
	{
		vx = _vx;
	}
	
	// Draw the object on the graphics object
	public void drawImage(Graphics g)
	{
		// Auto move the object
		this.posx += this.vx;
		this.posy += this.vy;
		g.drawImage(bi,posx, posy,imageW,imageH, null);
	}
}