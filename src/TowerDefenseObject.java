// Copyright 2020 Ty Bayn
// Last Edited Aug. 11, 2020
// Tower Class

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Color;

// TowerDefenseObject: Base class for Types of Towers
public class TowerDefenseObject
{
	// Class Members for positions and images
	protected int posx; 
	protected int posy;
	protected BufferedImage bi; 
	protected int imageW;
	protected int imageH;
	
	// TowerDefenseObject constructor
	public TowerDefenseObject(int posx, int posy, BufferedImage bi, int imageW, int imageH)
	{
		// Set Members
		this.posx = posx;
		this.posy = posy; 
		this.bi= bi;
		this.imageW = imageW; 
		this.imageH = imageH;
	}
	
	// Get x-position
	public int getposx()
	{
		return posx;
	}
	
	// Get y-position
	public int getposy()
	{
		return posy;
	}
	
	// Set x-position
	public void setposx(int _posx)
	{
		posx = _posx;
	}
	
	// Set Backgrounf Image of object
	public void setImage(BufferedImage bi){
		this.bi= bi;
	}
}