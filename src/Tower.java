// Copyright 2020 Ty Bayn
// Last Edited Aug. 11, 2020
// Tower Class

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

// Tower class
public class Tower extends TowerDefenseObject{
	
	// Class Members
	private Bullet b1;
	private Boolean placed;
	private Boolean valid;
	private int style;
	private BufferedImage cur;
	private BufferedImage invalid;
	private int fireRadius;
	private BufferedImage radiusRing;
	private BufferedImage fullRing;
	private BufferedImage fadeRing;
	private BufferedImage bulletImage;
	private Clip bullet;

	// Tower constructor
	public Tower(int posx, int posy, int imageW, int imageH, int type, BufferedImage img, BufferedImage invalidImg, BufferedImage rangeImg, BufferedImage rangeTImg, BufferedImage bulletImg) throws IOException
	{
		// Super
		super(posx, posy, img, imageW, imageH);

		// Set members
		b1 = null;
		placed = false;
		style = type;
		invalid = invalidImg;
		cur = img;
		fireRadius = 100 + (50 * type);
		fullRing = rangeImg;
		fadeRing = rangeTImg;
		radiusRing = fullRing;
		bulletImage = bulletImg;

		// Load the sound effect sound clip
		try{
			AudioInputStream sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/shot.wav"));
			bullet = AudioSystem.getClip();
			bullet.open(sound);
			bullet.start();
			bullet.setFramePosition(0);
			bullet.stop();
		} catch (Exception ex){
			System.err.println("Unable to read audio file...");
		}
	}

	// Draw both background images on the graphics object
	public void drawImage(Graphics g)
	{
		g.drawImage(bi, posx, posy, imageW, imageH, null);
		g.drawImage(radiusRing, posx - fireRadius + (imageW / 2), posy - fireRadius + (imageH / 2), fireRadius * 2, fireRadius * 2, null);
	}
	
	// Set tower position to mouse
	public void setPosition(int corX, int corY, boolean valid){
		posx = corX;
		posy = corY;
		
		// Check if faded radius image is needed
		if(valid)
			this.setImage(cur);
		else
			this.setImage(invalid);
	}

	// Focus to determine if mouse is hovered
	public void focus(boolean f){
		if(f)
			radiusRing = fullRing;
		else
			radiusRing = fadeRing;
	}
	
	// Place the tower on graphics canvas
	public void place(int corX, int corY){
		placed = true;
		posx = corX;
		posy = corY;
		radiusRing = fadeRing;
	}

	// Rip the tower off the canvas and attach to mouse
	public void rip(){
		placed = false;
		radiusRing = fullRing;
	}

	// Get the radius tha the tower can see
	public int getRadius(){
		return fireRadius;
	}
	
	// Check if the tower is placed on the canvas
	public boolean isPlaced(){
		return placed;
	}
	
	//Place the tower on the map at creation
	public void drawBullet(Graphics g){
		b1.drawImage(g);
	}
	
	//Got to return the bullet to check the position
	public Bullet getBullet()
	{
		return b1;
	}
	
	// Destroy this towers bullet
	public void destroyBullet()
	{
		b1 = null;
	}
	
	// Return the tier/level of the tower
	public int getType()
	{
		return style;
	}
	
	// Fire function to create and fire bullet
	public void fire(int enX, int enY)
	{
		//Do math here to try and adjust velocity to hit the target
		double vel = style == 0 ? 5 : style == 1 ? 10 : style == 2 ? 20 : 38;
		double dx = (enX + (10  - (3 * style))) - this.posx;
		double dy = enY - this.posy;
		double dd = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
		double ratio = vel / dd;
		int velx = (int)Math.round(dx * ratio);
		int vely = (int)Math.round(dy * ratio);

		// Adjust bullet type based on tower type
		if(style == 0)
			b1 = new Bullet(this.posx + 18, this.posy + 18, bulletImage, 25,25, velx, vely, 1);
		else if (style == 1)
			b1 = new Bullet(this.posx + 18, this.posy + 18, bulletImage, 25,25, velx, vely, 1);
		else if (style == 2)
			b1 = new Bullet(this.posx + 18, this.posy + 18, bulletImage, 25,25, velx, vely, 3);
		else 
			b1 = new Bullet(this.posx + 18, this.posy + 18, bulletImage, 25,25, velx, vely, 5);

		//Start new sound thread
		new Thread() {
			public void run() {
				try{
					bullet.start();
					bullet.setFramePosition(0);
				} catch (Exception ex){
					System.err.println("Audio files not found!");
				}
			}
		}.start();
	}
}