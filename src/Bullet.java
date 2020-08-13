// Copyright 2020 Ty Bayn
// Last Edited Aug. 8, 2020
// Bullet object

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Color;

// Bullet Object
public class Bullet extends MovingObject
{
	// Damage Value
	protected int damage = 1; 

	// Bullet constructor
	public Bullet(int _posx, int _posy, BufferedImage _bi, int _imageW, int _imageH, int _vx, int _vy, int _damage)
	{
		super(_posx, _posy, _bi, _imageW, _imageH, _vx, _vy);
		damage = _damage;
	}
	
	// Get damage value of bullet
	public int getDamage()
	{
		return damage; 
	}
}