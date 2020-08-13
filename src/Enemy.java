// Copyright 2020 Ty Bayn
// Last Edited Aug. 8, 2020
// Enemy Class

import java.awt.Graphics;
import java.awt.image.BufferedImage;

// Enemy Class, is a moving object
public class Enemy extends MovingObject{
	
	// Method Members
	protected int hitPoints;
	private boolean dead; 
	
	// Enemy Constructor
	public Enemy(int posx, int posy, BufferedImage bi, int imageW, int imageH, int vx, int vy, int hitPoints, boolean dead)
	{
		super(posx, posy, bi, imageW, imageH, vx, vy);
		this.hitPoints = hitPoints;
		dead = false;
	}
	
	// Get the enemy hit points
	public int getHitPoints()
	{
		return hitPoints;
	}
	
	// Get enemy X position
	public int getposx()
	{
		return posx;
	}
	
	// Get enemy Y position
	public int getposy()
	{
		return posy;
	}
	
	// Take damage
	public void hit(int damage)
	{
		hitPoints -= damage;
		if(hitPoints <= 0)
			setDead();
	}
	
	// Check if enemy is dead
	public boolean isDead()
	{
		return dead; 
	}
	
	// Set enemy as dead
	public void setDead()
	{
		this.dead = true;
	}
}




