// Copyright 2020 Ty Bayn
// Last Edited Aug. 11, 2020
// RapidTower (Laser) Class

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

// RapidTower class
public class RapidTower extends TowerDefenseObject{
	
	// Class Members, including number of bullets and delay
	private ArrayList<Bullet> mg1;
	private Boolean placed;
	private Boolean valid;
	private BufferedImage cur;
	private BufferedImage invalid;
	private int fireRadius;
	private int maxNumBullets = 100;
	private int bulletDelay;
	private int bulletDelayInc = 1;
	private BufferedImage radiusRing;
	private BufferedImage fullRing;
	private BufferedImage fadeRing;
	private BufferedImage bulletImage;
	private AudioInputStream sound;
	private static Clip bullet;
	private int curAud;

	// RapidTower constructor
	public RapidTower(int posx, int posy, int imageW, int imageH, BufferedImage img, BufferedImage invalidImg, BufferedImage rangeImg, BufferedImage rangeTImg, BufferedImage bulletImg) throws IOException
	{
		// Super
		super(posx, posy, img, imageW, imageH);

		// Set members
		mg1 = new ArrayList<Bullet>();
		placed = false;
		invalid = invalidImg;
		cur = img;
		fireRadius = 200;
		bulletDelay = 0;
		fullRing = rangeImg;
		fadeRing = rangeTImg;
		radiusRing = fullRing;
		bulletImage = bulletImg;

		// Load the sound effect sound clip
		try{
			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/wave.wav"));
			bullet = AudioSystem.getClip();
			bullet.open(sound);
			bullet.start();
			bullet.setFramePosition(0);
			bullet.stop();
		} catch (Exception ex){
			System.err.println("Unable to read audio file...");
			System.err.println(ex);
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
	public void drawBullet(Graphics g, int b){
			mg1.get(b).drawImage(g);
	}
	
	//Got to return the bullet to check the position
	public Bullet getBullet(int pos)
	{
		return mg1.get(pos);
	}

	//Get number of current bullets
	public int getNumBullets(){
		return mg1.size();
	}
	
	// Destroy this towers bullet
	public void destroyBullet(int pos)
	{
		mg1.remove(pos);
	}

	// Destroy all this towers bullet
	public void destroyAllBullets(){
		mg1.clear();
	}
	
	// Fire function to create and fire bullet
	public void fire(int enX, int enY)
	{
		// Check if delay and num bullets allows for a new bullet
		if(bulletDelay % bulletDelayInc == 0 && mg1.size() < maxNumBullets){

			//Do math here to try and adjust velocity to hit the target
			double vel = 15;
			double dx = (enX + 4) - this.posx;
			double dy = enY - this.posy;
			double dd = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
			double ratio = vel / dd;
			int velx = (int)Math.round(dx * ratio);
			int vely = (int)Math.round(dy * ratio);

			// Create new bullet
			mg1.add(new Bullet(this.posx + 18, this.posy + 18, bulletImage, 25,25, velx, vely, 3));

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
		bulletDelay = (bulletDelay + 1) % bulletDelayInc;
	}
}