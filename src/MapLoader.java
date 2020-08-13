// Copyright 2020 Ty Bayn
// Last Edited Aug. 11, 2020
// Graphics and Audio Loader for Tower Defense Game

import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

// MapLoader class, extends JPanel
@SuppressWarnings({ "serial", "unused" })
public class MapLoader extends JPanel {

	//Instance variables
	Bullet b1;
	ArrayList<Tower> t1;
	ArrayList<RapidTower> rt1;
	ArrayList<Enemy> e1;
	protected int[][] move; 
	private int r;
	private int c; 
	private int lives = 20;
	private int bossLives = 20;
	private int money = 50;
	private int level = 1;
	private boolean active = false;
	private Random rand = new Random();
	private boolean firstOver = true;

	//Load Images
	private BufferedImage background;
	private BufferedImage en1Img;
	private BufferedImage en2Img;
	private BufferedImage en3Img;
	private BufferedImage bossImg;
	private BufferedImage tow1Img;
	private BufferedImage tow2Img;
	private BufferedImage tow3Img;
	private BufferedImage tow4Img;
	private BufferedImage tow5Img;
	private BufferedImage towInvalid;
	private BufferedImage bul1Img;
	private BufferedImage bul2Img;
	private BufferedImage bul3Img;
	private BufferedImage bul4Img;
	private BufferedImage bul5Img;
	private BufferedImage range1Img;
	private BufferedImage range2Img;
	private BufferedImage range3Img;
	private BufferedImage range4Img;
	private BufferedImage rangeT1Img;
	private BufferedImage rangeT2Img;
	private BufferedImage rangeT3Img;
	private BufferedImage rangeT4Img;

	//Load Audio
	private AudioInputStream sound;
	private Clip levelBackground;
	private Clip bossBackground;
	private Clip selectTower;
	private Clip placeTower;
	private Clip enemyIn;
	private Clip passLevel;
	private Clip cancelSnd;
	private Clip resetSnd;
	private Clip removeSnd;
	private Clip hoverSnd;
	private Clip moveSnd;
	
	public MapLoader(){
		
		// Allow image to be loaded in to the jar if needed
		try{
			background = ImageIO.read(this.getClass().getResource("/res/images/digiFloor.png"));
			en1Img = ImageIO.read(this.getClass().getResource("/res/images/digiEnemy.png"));
			en2Img = ImageIO.read(this.getClass().getResource("/res/images/digiEnemy2.png"));
			en3Img = ImageIO.read(this.getClass().getResource("/res/images/digiEnemy3.png"));
			bossImg = ImageIO.read(this.getClass().getResource("/res/images/digiEnemyBoss.png"));
			tow1Img = ImageIO.read(this.getClass().getResource("/res/images/digiTower.png"));
			tow2Img = ImageIO.read(this.getClass().getResource("/res/images/digiTower2.png"));
			tow3Img = ImageIO.read(this.getClass().getResource("/res/images/digiTower3.png"));
			tow4Img = ImageIO.read(this.getClass().getResource("/res/images/digiTower4.png"));
			tow5Img = ImageIO.read(this.getClass().getResource("/res/images/digiTower5.png"));
			towInvalid = ImageIO.read(this.getClass().getResource("/res/images/invalidTower.png"));
			bul1Img = ImageIO.read(this.getClass().getResource("/res/images/digiBullet.png"));
			bul2Img = ImageIO.read(this.getClass().getResource("/res/images/digiBullet2.png"));
			bul3Img = ImageIO.read(this.getClass().getResource("/res/images/digiBullet3.png"));
			bul4Img = ImageIO.read(this.getClass().getResource("/res/images/digiBullet4.png"));
			bul5Img = ImageIO.read(this.getClass().getResource("/res/images/digiBullet5.png"));
			range1Img = ImageIO.read(this.getClass().getResource("/res/images/digiRange.png"));
			range2Img = ImageIO.read(this.getClass().getResource("/res/images/digiRange2.png"));
			range3Img = ImageIO.read(this.getClass().getResource("/res/images/digiRange3.png"));
			range4Img = ImageIO.read(this.getClass().getResource("/res/images/digiRange4.png"));
			rangeT1Img = ImageIO.read(this.getClass().getResource("/res/images/digiRangeT.png"));
			rangeT2Img = ImageIO.read(this.getClass().getResource("/res/images/digiRangeT2.png"));
			rangeT3Img = ImageIO.read(this.getClass().getResource("/res/images/digiRangeT3.png"));
			rangeT4Img = ImageIO.read(this.getClass().getResource("/res/images/digiRangeT4.png"));

		} catch (Exception ex){
			System.err.println("Unable to read image file...");
		}

		// Allow audio to be loaded in to the jar if needed
		try{
			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/wave.wav"));
			levelBackground  = AudioSystem.getClip();
			levelBackground.open(sound);
			levelBackground.loop(Clip.LOOP_CONTINUOUSLY);
			levelBackground.setFramePosition(0);
			levelBackground.stop();

			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/boss.wav"));
			bossBackground = AudioSystem.getClip();
			bossBackground.open(sound);
			bossBackground.loop(Clip.LOOP_CONTINUOUSLY);
			bossBackground.setFramePosition(0);
			bossBackground.stop();

			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/selectTower.wav"));
			selectTower = AudioSystem.getClip();
			selectTower.open(sound);
			selectTower.start();
			selectTower.setFramePosition(0);
			selectTower.stop();

			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/placeTower.wav"));
			placeTower = AudioSystem.getClip();
			placeTower.open(sound);
			placeTower.start();
			placeTower.setFramePosition(0);
			placeTower.stop();

			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/enemyIn.wav"));
			enemyIn = AudioSystem.getClip();
			enemyIn.open(sound);
			enemyIn.start();
			enemyIn.setFramePosition(0);
			enemyIn.stop();

			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/levelComplete.wav"));
			passLevel = AudioSystem.getClip();
			passLevel.open(sound);
			passLevel.start();
			passLevel.setFramePosition(0);
			passLevel.stop();

			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/cancel.wav"));
			cancelSnd = AudioSystem.getClip();
			cancelSnd.open(sound);
			cancelSnd.start();
			cancelSnd.setFramePosition(0);
			cancelSnd.stop();

			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/reset.wav"));
			resetSnd = AudioSystem.getClip();
			resetSnd.open(sound);
			resetSnd.start();
			resetSnd.setFramePosition(0);
			resetSnd.stop();

			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/removeTower.wav"));
			removeSnd = AudioSystem.getClip();
			removeSnd.open(sound);
			removeSnd.start();
			removeSnd.setFramePosition(0);
			removeSnd.stop();

			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/hover.wav"));
			hoverSnd = AudioSystem.getClip();
			hoverSnd.open(sound);
			hoverSnd.start();
			hoverSnd.setFramePosition(0);
			hoverSnd.stop();

			sound = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/audio/moveTower.wav"));
			moveSnd = AudioSystem.getClip();
			moveSnd.open(sound);
			moveSnd.start();
			moveSnd.setFramePosition(0);
			moveSnd.stop();
		} catch (Exception ex){
			System.err.println("Unable to read audio file...");
		}

		// Create the gamearea
		GameArea gameArea = new GameArea(); 
		gameArea.setBackground(background);
		if (gameArea != null){
			this.add(gameArea);
		}
		
		// Create arraylists for towers and enemies
		t1 = new ArrayList<Tower>();
		rt1 = new ArrayList<RapidTower>();
		e1 = new ArrayList<Enemy>();

		// Show the display
		this.setVisible(true);

		// Start background music
		try{
			Thread.sleep(500);
		}
		catch(Exception ex){
			// Om nom nom
		}
		playBackgroundAudio();
	}
	
	// Clear and reset game
	public void reset(){
		lives = 20;
		money = 50;
		level = 1;
		active = false;
		t1.clear();
		rt1.clear();
		e1.clear();
		playAudio(resetSnd);
	}

	// Load and play an audio clip
	private void playAudio(Clip clip){

		new Thread() {
			public void run() {
				try{
					clip.start();
					clip.setFramePosition(0);
				} catch (Exception ex){
					System.err.println("Audio files not found!");
				}
			}
		}.start();
	}

	// Stop all music and play background music
	public void playBackgroundAudio(){
		try{
			bossBackground.stop();
			levelBackground.stop();
			bossBackground.stop();
			levelBackground.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception ex){
			System.err.println("Audio files not found!");
		}
	}

	// Stop all music and play boss music
	public void playBossAudio(){
		try{
			levelBackground.stop();
			bossBackground.stop();
			levelBackground.stop();
			bossBackground.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception ex){
			System.err.println("Audio files not found!");
		}
	}

	// Return preloaded tower image
	public BufferedImage getTowerImg(int style){
		if(style == 0)
			return tow1Img;
		else if(style == 1)
			return tow2Img;
		else if(style == 2)
			return tow3Img;
		else if(style == 3)
			return tow4Img;
		else if(style == 4)
			return tow5Img;
		else
			return towInvalid;
	}
	
	// Create a new tower obeject
	public void createTower(int style, int cost)
	{
		// Create a tower based on requested style
		try {
			if(style == 0)
				t1.add (new Tower(5,5, 60, 60, style, tow1Img, towInvalid, range1Img, rangeT1Img, bul1Img));
			else if(style == 1)
				t1.add (new Tower(5,5, 60, 60, style, tow2Img, towInvalid, range2Img, rangeT2Img, bul2Img));
			else if(style == 2)
				t1.add (new Tower(5,5, 60, 60, style, tow3Img, towInvalid, range3Img, rangeT3Img, bul3Img));
			else if(style == 3)
				t1.add (new Tower(5,5, 60, 60, style, tow4Img, towInvalid, range4Img, rangeT4Img, bul4Img));
			else if(style == 4){
				try{
					rt1.add (new RapidTower(5,5, 60, 60, tow5Img, towInvalid, range3Img, rangeT3Img, bul5Img));
				} catch (Exception ex){
					System.err.println("Unable to read audio file...");
					System.err.println(ex);
				}
			}
			else
				t1.add (new Tower(5,5, 60, 60, style, tow1Img, towInvalid, range1Img, rangeT1Img, bul1Img));

			// Subtract cost of tower
			money -= cost;

			// Play select tower audio sound
			playAudio(selectTower);
		}
		catch (IOException e)
		{
			System.out.println("Unable to generate tower due to IO exception");
		}
	}
	
	//Place the tower
	public boolean placeTower(int x, int y){
		boolean legal = true;
		int radius = 60;

		// Loop through to find the unplaced tower
		for(int t = 0; t < t1.size(); t++)
		{
			if(!t1.get(t).isPlaced()){

				// Check if tower is in a legal position
				for(int c = 0; c < t1.size(); c++)
				{
					if(c != t && Math.sqrt((double)Math.pow(x - t1.get(c).getposx() - 30,2) + (double)Math.pow(y - t1.get(c).getposy()-30,2)) < radius)
						legal = false;
					
					if(y < 300 + 70 && y > 300 - 70)
						legal = false;
				}
				
				// Place tower if legal
				if(legal){
					t1.get(t).place(x-30,y-30);
					playAudio(placeTower);
				}
			}	
		}

		// Loop through to find the unplaced rapid tower
		for(int t = 0; t < rt1.size(); t++)
		{
			if(!rt1.get(t).isPlaced()){

				// Check if rapid tower is in a legal position
				for(int c = 0; c < rt1.size(); c++)
				{
					if(c != t && Math.sqrt((double)Math.pow(x - rt1.get(c).getposx() - 30,2) + (double)Math.pow(y - rt1.get(c).getposy()-30,2)) < radius)
						legal = false;
					
					if(y < 300 + 70 && y > 300 - 70)
						legal = false;
				}
				
				// Place rapid tower if legal
				if(legal){
					rt1.get(t).place(x-30,y-30);
					playAudio(placeTower);
				}
			}	
		}
		
		return legal;
	}
	
	//Remove the tower
	public void removeTower(int x, int y, int t, int t2, int t3, int t4, int t5){
		int radius = 30;
		int type = 0, moneyBack = 0;

		// Find tower closest to mouse and delete
		for(int c = 0; c < t1.size(); c++)
		{
			if(Math.sqrt((double)Math.pow(x - t1.get(c).getposx() - 30,2) + (double)Math.pow(y - t1.get(c).getposy()-30,2)) < radius){
				type = t1.get(c).getType();
				moneyBack = (type == 0 ? t : type == 1 ? t2 : type == 2 ? t3 : t4);
				money += (moneyBack/2);
				t1.remove(c);
				playAudio(removeSnd);
				break;
			}
		}

		// Find rapid tower closest to mouse and delete
		for(int c = 0; c < rt1.size(); c++)
		{
			if(Math.sqrt((double)Math.pow(x - rt1.get(c).getposx() - 30,2) + (double)Math.pow(y - rt1.get(c).getposy()-30,2)) < radius){
				money += (t5/2);
				rt1.remove(c);
				playAudio(removeSnd);
				break;
			}
		}
	}

	//Move the tower
	public void moveTower(int x, int y){
		int radius = 30;

		// Find tower closest to mouse and rip it off the canvas
		for(int c = 0; c < t1.size(); c++)
		{
			if(Math.sqrt((double)Math.pow(x - t1.get(c).getposx() - 30,2) + (double)Math.pow(y - t1.get(c).getposy()-30,2)) < radius){
				money -= 25;
				t1.get(c).rip();
				playAudio(moveSnd);
				break;
			}
		}

		// Find rapid tower closest to mouse and rip it off the canvas
		for(int c = 0; c < rt1.size(); c++)
		{
			if(Math.sqrt((double)Math.pow(x - rt1.get(c).getposx() - 30,2) + (double)Math.pow(y - rt1.get(c).getposy()-30,2)) < radius){
				money -= 25;
				rt1.get(c).rip();
				playAudio(moveSnd);
				break;
			}
		}
	}
	
	//Move tower to mouse
	public void followTower(int x, int y){
		boolean legal = true;
		int radius = 60;

		// Loop through to find unplaced tower and move to mouse
		boolean curOver = false;
		for(int t = 0; t < t1.size(); t++)
		{
			if(!t1.get(t).isPlaced()){
				for(int c = 0; c < t1.size(); c++)
				{
					if(c != t && Math.sqrt((double)Math.pow(x - t1.get(c).getposx() - 30,2) + (double)Math.pow(y - t1.get(c).getposy()-30,2)) < radius)
						legal = false;
					
					if(y < 300 + 70 && y > 300 - 70)
						legal = false;
				}

				t1.get(t).setPosition(x-30,y-30,legal);
			}

			// Highlight tower
			if(Math.sqrt(Math.pow(Math.abs(t1.get(t).getposx() + 30 - x),2) + Math.pow(Math.abs(t1.get(t).getposy() + 30 - y),2)) < radius / 2){
				t1.get(t).focus(true);
				if(t1.get(t).isPlaced() && firstOver){
					playAudio(hoverSnd);
					firstOver = false;
				}
				curOver = true;
			}
			else{
				t1.get(t).focus(false);
			}
		}

		// Loop through to find unplaced rapid tower and move to mouse
		for(int t = 0; t < rt1.size(); t++)
		{
			if(!rt1.get(t).isPlaced()){
				for(int c = 0; c < rt1.size(); c++)
				{
					if(c != t && Math.sqrt((double)Math.pow(x - rt1.get(c).getposx() - 30,2) + (double)Math.pow(y - rt1.get(c).getposy()-30,2)) < radius)
						legal = false;
					
					if(y < 300 + 70 && y > 300 - 70)
						legal = false;
				}

				rt1.get(t).setPosition(x-30,y-30,legal);
			}

			// Highlight rapid tower
			if(Math.sqrt(Math.pow(Math.abs(rt1.get(t).getposx() + 30 - x),2) + Math.pow(Math.abs(rt1.get(t).getposy() + 30 - y),2)) < radius / 2){
				rt1.get(t).focus(true);
				if(rt1.get(t).isPlaced() && firstOver){
					playAudio(hoverSnd);
					firstOver = false;
				}
				curOver = true;
			}
			else{
				rt1.get(t).focus(false);
				firstOver = true;
			}
		}
		if(!curOver)
			firstOver = true;
	}

	//Cancel building tower
	public void cancelTower(int t, int t2, int t3, int t4, int t5){
		int type = 0, moneyBack = 0;

		// Loop through and find the unplaced tower, delete it
		for(int c = 0; c < t1.size(); c++)
		{
			if(!t1.get(c).isPlaced()){
				type = t1.get(c).getType();
				moneyBack = (type == 0 ? t : type == 1 ? t2 : type == 2 ? t3 : t4);
				money += moneyBack;
				t1.remove(c);
				playAudio(cancelSnd);
				break;
			}
		}

		// Loop through and find the unplaced rapid tower, delete it
		for(int c = 0; c < rt1.size(); c++)
		{
			if(!rt1.get(c).isPlaced()){
				money += t5;
				rt1.remove(c);
				playAudio(cancelSnd);
				break;
			}
		}
	}
	
	// Get lives left
	public int getLives(){
		return lives;
	}
	
	// Get current money amount
	public int getMoney(){
		return money;
	}
	
	// Get current level
	public int getLevel(){
		return level;
	}
	
	// Get current number of enemies
	public int getNumEnemies(){
		return e1.size();
	}
	
	// Return if the current wave is running
	public boolean isActive(){
		return active;
	}
	
	// Start the next enemy wave
	public void start()
	{
		int lv1spd = 1, lv2spd = 2, lv3spd = 3, bossspd = 0;
		int lv1hp = 2, lv2hp = 3, lv3hp = 4, bosshp = 150;

		try {
			// Check for boss level
			if(level % 10 == 0){
				e1.add(new Enemy(0 - (3 * (60)), 261, bossImg, 80, 80, (bossspd + 1), 0, (level / 10) * bosshp, false));
			}

			else{
				// Standard level
				int num = 10 + ((level - 1) * 2);

				// Loop through and create all enemies
				for(int i = 0; i < num; i++)
				{
					if(level <= 5){
						e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
					}
					else if(level <= 10){
						if(i < (num / 10) * 9)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
					}
					else if(level <= 15){
						if(i < (num / 10) * 8)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 9)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 20){
						if(i < (num / 10) * 7)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 9)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 25){
						if(i < (num / 10) * 6)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 8)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 30){
						if(i < (num / 10) * 5)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 8)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 35){
						if(i < (num / 10) * 4)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 7)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 40){
						if(i < (num / 10) * 3)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 7)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 45){
						if(i < (num / 10) * 2)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 6)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 50){
						if(i < (num / 10) * 1)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 6)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 55){
						e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
					}
					else if(level <= 60){
						if(i < (num / 10) * 9)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
					}
					else if(level <= 65){
						if(i < (num / 10) * 8)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 9)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 70){
						if(i < (num / 10) * 7)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 9)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 75){
						if(i < (num / 10) * 6)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 8)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 80){
						if(i < (num / 10) * 5)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 8)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 85){
						if(i < (num / 10) * 4)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 7)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 90){
						if(i < (num / 10) * 3)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 7)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 95){
						if(i < (num / 10) * 2)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 6)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 100){
						if(i < (num / 10) * 1)
							e1.add(new Enemy(0 - (i * (60)), 281, en1Img, 40, 40, (rand.nextInt(2) + lv1spd) + (level / 50), 0, (level / 5) * lv1hp, false));
						else if(i < (num / 10) * 6)
							e1.add(new Enemy(0 - (i * (60)), 281, en2Img, 40, 40, (rand.nextInt(2) + lv2spd) + (level / 50), 0, (level / 5) * lv2hp, false));
						else
							e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else if(level <= 110){
						e1.add(new Enemy(0 - (i * (60)), 281, en3Img, 40, 40, (rand.nextInt(2) + lv3spd) + (level / 50), 0, (level / 5) * lv3hp, false));
					}
					else{
						e1.add(new Enemy(0 - (3 * (60)), 261, bossImg, 80, 80, (bossspd + 1) + (level / 10), 0, (level / 5) * bosshp, false));
					}
				}
			}
			active = true;
		}
		catch (Exception e)
		{
			System.out.println("Unable to generate enemies due to IO exception");
		}
	}

	// Paint the screen, draw all objects
	public void paint(Graphics g){
		super.paint(g);
		try{
		
			//bullet and enemy position
			int bx, by, ex, ey;
			int minDist = 1000, minPos = -1, dist = 0;

			//Loop through towers to print
			for(int t = 0; t < t1.size(); t++)
			{
				t1.get(t).drawImage(g);
				
				if(active){
					if (t1.get(t).getBullet() != null)
					{
						if(t1.get(t).getBullet().getposx() > 600
						   || t1.get(t).getBullet().getposx() < 0
						   || t1.get(t).getBullet().getposy() > 600
						   || t1.get(t).getBullet().getposy() < 0)
							t1.get(t).destroyBullet();
							
						else
							t1.get(t).drawBullet(g);
					}
					else{
						for(int e = 0; e < e1.size(); e++){
							if(e1.get(e).getposx() >= 0){
								if( t1.get(t).getposx() > e1.get(e).getposx())
									dist = Math.abs(t1.get(t).getposx() - e1.get(e).getposx());
								else
									dist = Math.abs(e1.get(e).getposx() - t1.get(t).getposx());
								if( dist < minDist){
									minDist = dist;
									minPos = e;
								}
							}
						}
						
						if(minPos != -1){
							if (Math.sqrt(Math.pow(Math.abs(t1.get(t).getposx() - e1.get(minPos).getposx()),2) + Math.pow(Math.abs(t1.get(t).getposy() - e1.get(minPos).getposy()),2)) <= t1.get(t).getRadius()){
								t1.get(t).fire(e1.get(minPos).getposx(),e1.get(minPos).getposy());
							}
						}
					}
				}
			}

			//Loop through rapid towers to print
			ArrayList<Integer> deadBullets;
			for(int t = 0; t < rt1.size(); t++)
			{
				rt1.get(t).drawImage(g);
				
				if(active){
					deadBullets = new ArrayList<Integer>();
					for(int rb = 0; rb < rt1.get(t).getNumBullets(); rb++){

						// Handle already made bullets
						if(rt1.get(t).getBullet(rb).getposx() > 600
						|| rt1.get(t).getBullet(rb).getposx() < 0
						|| rt1.get(t).getBullet(rb).getposy() > 600
						|| rt1.get(t).getBullet(rb).getposy() < 0)
							deadBullets.add(rb);
							
						else
							rt1.get(t).drawBullet(g,rb);
					}
					Collections.reverse(deadBullets);
					for(int d : deadBullets)
						rt1.get(t).destroyBullet(d);

					//Create New Bullet
					for(int e = 0; e < e1.size(); e++){
						if(e1.get(e).getposx() >= 0){
							if( rt1.get(t).getposx() > e1.get(e).getposx())
								dist = Math.abs(rt1.get(t).getposx() - e1.get(e).getposx());
							else
								dist = Math.abs(e1.get(e).getposx() - rt1.get(t).getposx());
							if( dist < minDist){
								minDist = dist;
								minPos = e;
							}
						}
					}

					if(minPos != -1){
						if (Math.sqrt(Math.pow(Math.abs(rt1.get(t).getposx() - e1.get(minPos).getposx()),2) + Math.pow(Math.abs(rt1.get(t).getposy() - e1.get(minPos).getposy()),2)) <= rt1.get(t).getRadius()){
							rt1.get(t).fire(e1.get(minPos).getposx(),e1.get(minPos).getposy());
						}
					}
				}
			}
			
			//Loop through enemies to print
			for(int i = 0; i < e1.size(); i++)
			{	
					e1.get(i).drawImage(g);
					if(e1.get(i).getposx() > 600)
					{
						e1.remove(i);
						this.playAudio(enemyIn);
						if(level % 10 == 0)
							lives = -1;
						else
							lives--;
					}
			}
				
			//Nest loop to do comparisons between bullets and enemies
			for(int t = 0; t < t1.size(); t++){
				if (t1.get(t).getBullet() != null){
					for(int e = 0; e < e1.size(); e++){
						
						bx = t1.get(t).getBullet().getposx();
						by = t1.get(t).getBullet().getposy();
						ex = e1.get(e).getposx();
						ey = e1.get(e).getposy();
						
						//check bullet and enemy position
						if (((bx+12) >= ((ex+20) - 20) && (bx+12) <= ((ex+20) + 20)) && ((by+12) >= ((ey+20) - 20) && (by+12) <= ((ey+20) + 20)))
						{
							e1.get(e).hit(t1.get(t).getBullet().getDamage());
							t1.get(t).destroyBullet();	
							break;
						}
					}
				}
			}

			//Nest loop to do comparisons between rapid bullets and enemies
			for(int t = 0; t < rt1.size(); t++){
				deadBullets = new ArrayList<Integer>();
				for(int rb = 0; rb < rt1.get(t).getNumBullets(); rb++){
					for(int e = 0; e < e1.size(); e++){
						
						bx = rt1.get(t).getBullet(rb).getposx();
						by = rt1.get(t).getBullet(rb).getposy();
						ex = e1.get(e).getposx();
						ey = e1.get(e).getposy();
						
						//check bullet and enemy position
						if (((bx+12) >= ((ex+20) - 20) && (bx+12) <= ((ex+20) + 20)) && ((by+12) >= ((ey+20) - 20) && (by+12) <= ((ey+20) + 20)))
						{
							e1.get(e).hit(rt1.get(t).getBullet(rb).getDamage());
							break;
						}
					}
				}
			}
			
			//Remove dead enemies
			for(int e = 0; e < e1.size(); e++){
				if(e1.get(e).isDead()){
					if(level % 10 == 0)
						money += (level / 10) * 200;
					else
						money += 2;
					e1.remove(e);
				}
			}	
			
			//Check if level is over
			if(active){
				if(e1.size() <= 0){
					level++;
					active = false;
					if (level % 10 == 0)
						playBossAudio();
					else if(level % 10 == 1)
						playBackgroundAudio();

					for(int t = 0; t < t1.size(); t++)
						t1.get(t).destroyBullet();
					for(int t = 0; t < rt1.size(); t++)
						rt1.get(t).destroyAllBullets();

					if(lives >= 0)
						playAudio(passLevel);
				}
			}
			Thread.sleep(7);
			
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
		Toolkit.getDefaultToolkit().sync();
	}
}


// GameArea (canvas) for gameplay
@SuppressWarnings("serial")
class GameArea extends JPanel{

	protected BufferedImage background;
  
	// GameArea constructor
	public GameArea(){

		// super
		super();
	}

	// Set the background image
	public void setBackground(BufferedImage back){
		background = back;
	}

	// Draw the background on the panel
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, null);
	}
}
