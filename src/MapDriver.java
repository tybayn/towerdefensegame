// Copyright 2020 Ty Bayn
// Last Edited Aug. 11, 2020
// Driver Program for Tower Defense Game

import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;
import javax.imageio.ImageIO;

// Map Driver Class
public class MapDriver extends JFrame{

	// Method Members
	protected int money = 50; 
	private int x;
	private int y;
	private int lives = 20;
	private int level = 1;
	private int numEnemies = 0;
	private boolean removeMode = false;
	private boolean moveMode = false;
	private boolean isPlacing = false;
	private boolean kill = false;
	
	private int t1 = 10, t2 = 50, t3 = 300, t4 = 750, t5 = 1500;
	
	// Constructor for MapDriver
	public MapDriver() {

		// Set the title of the game
		super("Ty Bayn Tower Defense");

		x = 0;
		y = 0;

		// Create panel objects
		getContentPane().setLayout(null);
		JPanel map = new MapLoader();
		JButton startButton = new JButton("Start");
		JButton resetButton = new JButton("Reset");
		JButton endButton = new JButton("Exit");
		JButton cancelButton = new JButton("Cancel");
		JButton removeButton = new JButton("Remove");
		JButton moveButton = new JButton("Move ($25)");
		JButton btnTowers = new JButton("T-1: $" + t1);
		JButton btnTowersLvl2 = new JButton("T-2: $" + t2);
		JButton btnTowersLvl3 = new JButton("T-3: $" + t3);
		JButton btnTowersLvl4 = new JButton("T-4: $" + t4);
		JButton btnTowersLvl5 = new JButton("T-5: $" + t5);
		JLabel towerLbl = new JLabel("Towers");
		JLabel moneyLbl = new JLabel("Money");
		JLabel moneyNumLbl = new JLabel("$" + money);
		JLabel levelLbl = new JLabel("Level");
		JLabel levelNumLbl = new JLabel("" + level);
		JLabel enemyLbl = new JLabel("Enemies");
		JLabel enemyNumLbl = new JLabel("0/0");
		JPanel control = new JPanel();
		JPanel controlTowers = new JPanel();
		JLabel livesLbl = new JLabel("Lives");
		JLabel livesNumLbl = new JLabel("" + lives);
		JLabel infoLbl = new JLabel("<html><div WIDTH=95>The goal is simple, defend your base (the right side) from the enemies (the left side). Pick a tower then click on the map to place it. Then click Start to start that wave. Have fun!<br/><br/><br/><center>Copyright 2020<br/>Ty Bayn</center></div></html>");
		
		// Start new thread to run the game
		new Thread() {
		  	public void run() {

				// Keep GUI updated while the lives > 0
				do{

				  	// Update values
					lives = ((MapLoader)map).getLives();
					if(lives < 0)
						livesNumLbl.setText("Game Over");
					else
						livesNumLbl.setText("" + lives);
					money = ((MapLoader)map).getMoney();
					moneyNumLbl.setText("$" + money);
					level = ((MapLoader)map).getLevel();
					levelNumLbl.setText("" + level);
					numEnemies = ((MapLoader)map).getNumEnemies();
					if(level % 10 == 0)
						enemyNumLbl.setText(numEnemies + "/1 (Boss)");
					else
						enemyNumLbl.setText(numEnemies + "/" + (10 + ((level - 1) * 2)));
					
					// Make certain elements inactive while running level
					if(((MapLoader)map).isActive() || removeMode || moveMode || isPlacing){
						startButton.setEnabled(false);
						btnTowers.setEnabled(false);
						btnTowersLvl2.setEnabled(false);
						btnTowersLvl3.setEnabled(false);
						btnTowersLvl4.setEnabled(false);
						btnTowersLvl5.setEnabled(false);
						removeButton.setEnabled(false);
						moveButton.setEnabled(false);
						cancelButton.setEnabled(true);
					}
					else{
						startButton.setEnabled(true);
						btnTowers.setEnabled(money >= t1 ? true:false);
						btnTowersLvl2.setEnabled(money >= t2 && level >= 10 ? true:false);
						btnTowersLvl3.setEnabled(money >= t3 && level >= 20 ? true:false);
						btnTowersLvl4.setEnabled(money >= t4 && level >= 30 ? true:false);
						btnTowersLvl5.setEnabled(money >= t5 && level >= 40 ? true:false);
						removeButton.setEnabled(true);
						if(money >= 25)
							moveButton.setEnabled(true);
						else
							moveButton.setEnabled(false);
						cancelButton.setEnabled(false);
					}
					
					// Unlock items at Level 10
					if(level >= 10)
						btnTowersLvl2.setText("T-2: $" + t2);
					else
						btnTowersLvl2.setText("Locked");

					// Unlock items at Level 15
					if(level >= 20)
						btnTowersLvl3.setText("T-3: $" + t3);
					else
						btnTowersLvl3.setText("Locked");
					
					// Unlock items at Level 20
					if(level >= 30)
						btnTowersLvl4.setText("T-4: $" + t4);
					else
						btnTowersLvl4.setText("Locked");

					// Unlock items at Level 25
					if(level >= 40)
						btnTowersLvl5.setText("L-1: $" + t5);
					else
						btnTowersLvl5.setText("Locked");

			  	}while(!kill);
		  	}
		}.start();

		// Left Side Menu
		control.setBounds(10, 15, 115, 600);
		getContentPane().add(control);
		control.setLayout(null);
		
		// Right Side Menu
		controlTowers.setBounds(740, 15, 200, 600);
		getContentPane().add(controlTowers);
		controlTowers.setLayout(null);
		
		// Add mouse click listener for placing towers
		map.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(removeMode){
					((MapLoader)map).removeTower(arg0.getX(),arg0.getY(),t1,t2,t3,t4,t5);
					removeMode = false;
				}
				else if (moveMode){
					((MapLoader)map).moveTower(arg0.getX(),arg0.getY());
					moveMode = false;
				}
				else{
					isPlacing = !((MapLoader)map).placeTower(arg0.getX(),arg0.getY());
				}
			}
		});
		
		// Add mouse motion listener to check for valid placement
		map.addMouseMotionListener(new MouseAdapter(){
			@Override
			public void mouseMoved(MouseEvent arg0) {
				((MapLoader)map).followTower(arg0.getX(),arg0.getY());
			}
		});
		
		// The gameplay canvas
		map.setBounds(130, 0, 600, 600);
		getContentPane().add(map);
		map.setLayout(new GridLayout(1, 0, 0, 0));
		
		// Add start button event
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
		
				if(lives >= 0)
				{
					((MapLoader)map).start();
				}
			}
		});
		
		// Add reset button event
		resetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				removeMode = false;
				moveMode = false;
				isPlacing = false;
				((MapLoader)map).reset();
			}
		});
		
		// Add end button event
		endButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				kill = true;
				System.exit(0);
			}
		});
		
		// Add remove button event
		removeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(lives >= 0)
				{
					removeMode = true;
				}
			}
		});

		// Add move button event
		moveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(lives >= 0 && money >= 25)
				{
					moveMode = true;
				}
			}
		});

		// Add cancel button event
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(lives >= 0)
				{
					moveMode = false;
					removeMode = false;
					isPlacing = false;
					((MapLoader)map).cancelTower(t1,t2,t3,t4,t5);
				}
			}
		});
		
		// Tower 1 Button Event
		btnTowers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(money >= t1 && lives >=0){
					((MapLoader)map).createTower(0,t1);
					isPlacing = true;
				}
			}
		});
		
		// Tower 2 Button Event
		btnTowersLvl2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(money >= t2 && lives >=0){
					((MapLoader)map).createTower(1,t2);
					isPlacing = true;
				}
			}
		});
		
		// Tower 3 Button Event
		btnTowersLvl3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(money >= t3 && lives >=0){
					((MapLoader)map).createTower(2,t3);
					isPlacing = true;
				}
			}
		});
		
		// Tower 4 Button Event
		btnTowersLvl4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(money >= t4 && lives >=0){
					((MapLoader)map).createTower(3,t4);
					isPlacing = true;
				}
			}
		});

		// Tower 5 Button Event
		btnTowersLvl5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(money >= t5 && lives >=0){
					((MapLoader)map).createTower(4,t5);
					isPlacing = true;
				}
			}
		});
		
		// Set Left menu button locations
		startButton.setBounds(0, 465, 110, 30);
		control.add(startButton);
		resetButton.setBounds(0, 505, 110, 30);
		control.add(resetButton);
		endButton.setBounds(0, 545, 110, 30);
		control.add(endButton);
		
		// Set Right menu button locations
		moveButton.setBounds(0, 465, 110, 30);
		removeButton.setBounds(0, 505, 110, 30);
		cancelButton.setBounds(0, 545, 110, 30);
		controlTowers.add(moveButton);
		controlTowers.add(removeButton);
		controlTowers.add(cancelButton);
		
		btnTowers.setBounds(0, 35, 110, 80); 
		try {
			btnTowers.setIcon(new ImageIcon(((MapLoader)map).getTowerImg(0).getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH )));
			btnTowers.setHorizontalTextPosition(JButton.CENTER);
			btnTowers.setVerticalTextPosition(JButton.TOP);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		controlTowers.add(btnTowers);
		
		btnTowersLvl2.setBounds(0, 120, 110, 80); 
		try {
			btnTowersLvl2.setIcon(new ImageIcon(((MapLoader)map).getTowerImg(1).getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH )));
			btnTowersLvl2.setHorizontalTextPosition(JButton.CENTER);
			btnTowersLvl2.setVerticalTextPosition(JButton.TOP);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		controlTowers.add(btnTowersLvl2);
		
		btnTowersLvl3.setBounds(0, 205, 110, 80); 
		try {
			btnTowersLvl3.setIcon(new ImageIcon(((MapLoader)map).getTowerImg(2).getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH )));
			btnTowersLvl3.setHorizontalTextPosition(JButton.CENTER);
			btnTowersLvl3.setVerticalTextPosition(JButton.TOP);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		controlTowers.add(btnTowersLvl3);
		
		btnTowersLvl4.setBounds(0, 290, 110, 80); 
		try {
			btnTowersLvl4.setIcon(new ImageIcon(((MapLoader)map).getTowerImg(3).getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH )));
			btnTowersLvl4.setHorizontalTextPosition(JButton.CENTER);
			btnTowersLvl4.setVerticalTextPosition(JButton.TOP);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		controlTowers.add(btnTowersLvl4);

		btnTowersLvl5.setBounds(0, 375, 110, 80); 
		try {
			btnTowersLvl5.setIcon(new ImageIcon(((MapLoader)map).getTowerImg(4).getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH )));
			btnTowersLvl5.setHorizontalTextPosition(JButton.CENTER);
			btnTowersLvl5.setVerticalTextPosition(JButton.TOP);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		controlTowers.add(btnTowersLvl5);
		
		// Level label
		Font font = new Font("Lucida", Font.BOLD,24);
		levelLbl.setFont(font);
		levelLbl.setHorizontalAlignment(JLabel.CENTER);
		font = new Font("Lucida", Font.BOLD,40);
		levelNumLbl.setFont(font);
		levelNumLbl.setHorizontalAlignment(JLabel.CENTER);
		levelLbl.setBounds(5, 0, 95, 30);
		levelNumLbl.setBounds(5, 25, 95, 50);
		control.add(levelLbl);
		control.add(levelNumLbl);
		font = new Font("Lucida", Font.BOLD,14);
		
		// Lives label
		livesLbl.setHorizontalAlignment(JLabel.CENTER);
		livesLbl.setBounds(5, 80, 95, 15);
		livesLbl.setFont(font);
		control.add(livesLbl);
		livesNumLbl.setHorizontalAlignment(JLabel.CENTER);
		livesNumLbl.setBounds(5, 95, 95, 15);
		control.add(livesNumLbl);
		
		// Money label
		moneyLbl.setHorizontalAlignment(JLabel.CENTER);
		moneyLbl.setBounds(5, 120, 95, 15);
		moneyLbl.setFont(font);
		control.add(moneyLbl);	
		moneyNumLbl.setHorizontalAlignment(JLabel.CENTER);
		moneyNumLbl.setBounds(5, 135, 95, 15);
		control.add(moneyNumLbl);
		
		// Enemy count label
		enemyLbl.setHorizontalAlignment(JLabel.CENTER);
		enemyLbl.setBounds(5, 160, 95, 15);
		enemyLbl.setFont(font);
		control.add(enemyLbl);
		enemyNumLbl.setHorizontalAlignment(JLabel.CENTER);
		enemyNumLbl.setBounds(5, 175, 95, 15);
		control.add(enemyNumLbl);

		//Add info label
		infoLbl.setHorizontalAlignment(JLabel.CENTER);
		infoLbl.setBounds(5, 200, 95, 250);
		font = new Font("Lucida", Font.BOLD,10);
		infoLbl.setFont(font);
		control.add(infoLbl);

		// Tower label
		towerLbl.setHorizontalAlignment(JLabel.CENTER);
		towerLbl.setBounds(5, 0, 95, 30);
		font = new Font("Lucida", Font.BOLD,22);
		towerLbl.setFont(font);
		controlTowers.add(towerLbl);
	}
	
	
	// Main function, start point
	public static void main(String[] args) {
		
		MapDriver m = new MapDriver();
		m.setSize(860, 635);
		m.setResizable(false);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setVisible(true);
	}
}
