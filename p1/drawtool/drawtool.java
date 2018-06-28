import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class drawtool extends PApplet {

PFont myFont;
Boolean brushOn = false;
Boolean orbOn = true;
Boolean orbDown = false;
Boolean brushDown = false;
Boolean sliderOn = false;
Boolean hideControls = false;
ArrayList orbList = new ArrayList();
ArrayList allStrokes = new ArrayList();
float x, y, old_x, old_y;
float easing = 0.12f;
float orbColor = 255;
float orbColorRatio = 70 - (orbColor / 255 * 70);
Timer fonttimer;

public void setup() {
  size(1200,650);
  background(200);
  smooth();
//  String[] fontList = PFont.list();
//  println(fontList);
  myFont = createFont("Futura-Light", 30);
  textFont(myFont);
  fonttimer = new Timer(3000);
  fonttimer.start();
}
public void draw() {
  drawBG();
  fill(100);
  if(!fonttimer.isFinished()){
    text("Press spacebar to hide/show tools", 275, 50);
  }
  buildColorPicker();
  buildConnections();
  drawStrokes();
  drawOrbs();
}



public void mousePressed(){
  // Instrument selector
  if (mouseX < 110 && mouseX > 10 && mouseY < 60 && mouseY > 10){
    orbOn = true;
    brushOn = false;
  } else if (mouseX < 210 && mouseX > 110 && mouseY < 60 && mouseY > 10){
    brushOn = true;
    orbOn = false;
  } else if (mouseX > orbColorRatio+10 && mouseX < orbColorRatio+30 && mouseY < 80 && mouseY > 60){
    sliderOn = true;
  } else if (orbOn){
    Orb newOrb = new Orb(mouseX, mouseY, 100, orbColor);
    orbList.add(newOrb);
    orbDown = true;
  } else if (brushOn){
      brushDown = true;
  }
}

public void mouseReleased(){
  // clear tools
  sliderOn = false;
  orbDown = false;
  brushDown = false;
}
public void mouseDragged(){
  if (orbDown){
    // get the current dragging orb
    Orb activeOrb = (Orb)orbList.get(orbList.size() -1);
    activeOrb.xPos = mouseX;
    activeOrb.yPos = mouseY;
    activeOrb.update(orbColor);
  } else if (brushDown){
      float targetX = mouseX;
      float targetY = mouseY;
      x += (targetX - x) * easing;
      y += (targetY - y) * easing;
      BStroke bstroke = new BStroke(old_x, old_y, x, y);
      allStrokes.add(bstroke);
      old_x = x;
      old_y = y;
  } else if (sliderOn){
    // if mouseX = 10, orbColor = 255; if mouseX = 80, orbColor = 0
    if(mouseX > 10 && mouseX < 80){
      orbColor = 255 - (((mouseX - 10.0f) / 70) * 255);
    }
  }
}

// custom functions
public void buildColorPicker(){
  if (!hideControls){
    fill(150);
  quad(10,60, 10,80, 100,80, 100,60);
  fill(orbColor);
  quad(10,80, 10,100, 100,100, 100,80);
  // slider handle
  orbColorRatio = 70 - (orbColor / 255 * 70);
  fill(orbColor);
  quad(orbColorRatio+10, 60, orbColorRatio+10, 80, orbColorRatio+30, 80, orbColorRatio+30, 60);
  }
}

public void keyPressed(){
  if (keyCode==32){
    hideControls = !hideControls;
  }
}

public void buildConnections(){
  // calculate distance from strokes to center of circle minus
  // radius of the circle
  for(int i=allStrokes.size(); i>0; i--){
    BStroke cStroke = (BStroke)allStrokes.get(i-1);
    for (int i2 = orbList.size(); i2>0; i2--){
      Orb aOrb = (Orb)orbList.get(i2-1);
      float rd = aOrb.diam / 2;
      float xD = min(abs(aOrb.xPos - cStroke.old_x), abs(aOrb.xPos - cStroke.x));
      float yD = min(abs(aOrb.yPos - cStroke.old_y), abs(aOrb.yPos - cStroke.y));
      float tD = sqrt((xD*xD) + (yD*yD));
      if (tD < (100 + rd + (255 - aOrb.gColor))){
        stroke(0);
        strokeWeight(0.01f * rd);
        line(aOrb.xPos, aOrb.yPos, cStroke.x, cStroke.y);
      }
    }
  }
}

public void drawBG(){
  background(200);
  fill(0);
  if (!hideControls){
    quad(10,10,100,10,100,60,10,60);
    quad(110,10,210,10,210,60,110,60);
    if (brushOn){
      fill(70, 70, 70);
      quad(110,10,210,10,210,60,110,60);
      fill(0);
      quad(10,10,100,10,100,60,10,60);
    } else if (orbOn) {
      fill(70, 70, 70);
      quad(10,10,100,10,100,60,10,60);
      fill(0);
      quad(110,10,210,10,210,60,110,60);
    }
    smooth();
    fill(255);
    text("ORB", 26, 46);
    text("BRUSH", 116, 46);
  }
}

public void drawStrokes(){
  for(int i=allStrokes.size(); i>0; i--){
    BStroke drawstroke = (BStroke)allStrokes.get(i-1);
    fill(50);
    smooth();
    line(drawstroke.old_x, drawstroke.old_y, drawstroke.x, drawstroke.y);
  }
}
public void drawOrbs(){
  for(int i=orbList.size(); i>0; i--){
    Orb draworb = (Orb)orbList.get(i-1);
    fill(draworb.gColor);
    smooth();
    ellipse(draworb.xPos, draworb.yPos, draworb.diam, draworb.diam);
  }
}
class BStroke
{
  float old_x, old_y, x, y;
  BStroke(float p_old_x, float p_old_y, float p_x, float p_y){
    this.old_x = p_old_x;
    this.old_y = p_old_y;
    this.x = p_x;
    this.y = p_y;
    stroke(0);
    strokeWeight(2);
    line(this.old_x,this.old_y,this.x,this.y);
  }
}
class Orb
{
  int xPos,yPos;
  int diam;
  float gColor;
  Orb(int x, int y, int diamInput, float orbColor){
    this.xPos = x;
    this.yPos = y;
    this.gColor = orbColor;
    this.diam = 10;
    this.show();
  }
  public void update(float orbColor){
    this.xPos = xPos;
    this.yPos = yPos;
    this.diam = this.diam + 1;
    this.show();
  }
  public void show(){
    drawBG();
    fill(this.gColor);
    ellipse(this.xPos, this.yPos, this.diam, this.diam);
  }
}
// Learning Processing
// Daniel Shiffman
// http://www.learningprocessing.com

// Example 10-5: Object-oriented timer

class Timer {
 
  int savedTime; // When Timer started
  int totalTime; // How long Timer should last
  
  Timer(int tempTotalTime) {
    totalTime = tempTotalTime;
  }
  
  // Starting the timer
  public void start() {
    // When the timer starts it stores the current time in milliseconds.
    savedTime = millis(); 
  }
  
  // The function isFinished() returns true if 5,000 ms have passed. 
  // The work of the timer is farmed out to this method.
  public boolean isFinished() { 
    // Check how much time has passed
    int passedTime = millis()- savedTime;
    if (passedTime > totalTime) {
      return true;
    } else {
      return false;
    }
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "drawtool" });
  }
}
