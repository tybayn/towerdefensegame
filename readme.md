# Tower Defense Game

This is a GUI based Tower Defense game written in Java using the Java openJDK. 

This project is generally assigned in a more simple form as a University Assignment. It was never formally assigned to me, but I decided to work on and complete this software project in my own time to further my education on GUI and Java. 

## Structure
To ensure the software works and compiles correctly, be sure to maintain the following file structure for the project:

```bash
src
|  MapDriver.java
|  Tower.java
|  ...
|
└──res
   |
   └──audio
   |  |  boss.wav 
   |  |  cancel.wav
   |  |  ...
   |   
   └──images
      |  digiBullet.png
      |  digiBullet2.png
      |  ...
```

## Compilation
To develop this I used openJDK command line compiler. To compile the project to a stand-alone (non-executable) .jar file, make sure there is a 'manifest.txt' file in the src/ directory that contains:

```bash
Main-Class: MapDriver
```

Once that file is in place, I recommend running the following from the same src/ directory:

```bash
javac MapDriver.java
jar cmvf manifest.txt TowerDefenseGame.jar *.class res/*
```

## Running the Software
Once the software has been compiled down to a .jar file, you can run it from the command line by typing:

```bash
java -jar TowerDefenseGame.jar
```

## Final Notes
This repo is posted without a license.    
Audio is under a (Creative Commons Zero, CC0) License from [kenney.nl](https://www.kenney.nl/assets?q=audio)  
All other sources and graphics were created by Ty Bayn.  
Copyright 2020 Ty Bayn  
Date Created: Apr. 15, 2019  
Last Edit: Aug. 12, 2020