import processing.core.*; 
import processing.xml.*; 

import peasy.test.*; 
import peasy.org.apache.commons.math.*; 
import peasy.*; 
import peasy.org.apache.commons.math.geometry.*; 
import java.util.Random; 

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

public class blockplaygravity extends PApplet {






PeasyCam cam;
PImage bg;
float[] camPos;
boolean isUsersTurn = true;
Crsr crsr = new Crsr(0,0,0);
BlockArray ublocks = new BlockArray(true);
Player p = new Player(0, ublocks); // Creates a computer, tells it a person to watch

String buff = "";
String currSearchText ="";
int leftmargin = 10;
int rightmargin = 20;
Boolean searching = false;

int boxSize = 20;
int margin = boxSize/2;
int containerSize = 200;

String data;

// implied from Player: Random ran = new Random();

public void setup() {
  size(1200, 800, P3D);
  // camera
  cam = new PeasyCam(this, 100);
  cam.setMinimumDistance(350);
  cam.setMaximumDistance(750);
  cam.rotateX(PI);
  cam.setResetOnDoubleClick(false);
  
  // gui
  textFont(createFont("Helvetica Neue", 14));
  //default stuff
  bg = loadImage("bg.jpg");
  background(0.5f);
  stroke(20, 40);
  p.ratioLine = loadStrings("http://labs.jonathancutrell.com/cdtweets.php?s=" + buff);
}

public void draw(){
// draw background
background(bg);
//draw interface
//draw outer box

// ---------- LIGHTS/SPECULAR
pointLight(255,255,220, -100, 0, 0);
// Create a light at the camera position
camPos = cam.getPosition();
pointLight(200,255,255, camPos[0],camPos[1],camPos[2]);
specular(255,255,240);
// ------- END LIGHTS/SPECULAR

// ------- CONTAINER BOX
fill(240, 30);
box(200, 200, 200);
// ------- END CONTAINER BOX



// ------- DRAW BLOCKS
//draw players blocks
  for (int i = ublocks.blocks.size() - 1; i >= 0; i--){
    int[] block = (int[])ublocks.blocks.get(i);
    pushMatrix();
    translate(-100 + margin + boxSize * block[0], -100 + margin + boxSize * block[1], -100 + margin + boxSize * block[2]);
    fill(255, 30, 40, 80);
    box(20, 20, 20);
    popMatrix();
  }
//draw computer's blocks
  for (int i = p.cblocks.blocks.size() - 1; i >= 0; i--){
    int[] block = (int[])p.cblocks.blocks.get(i);
    pushMatrix();
    translate(-100 + margin + boxSize * block[0], -100 + margin + boxSize * block[1], -100 + margin + boxSize * block[2]);
    fill(40, 30, 255, 80);
    box(20, 20, 20);
    popMatrix();
  }
//draw and blink cursor block
  if((frameCount / 15) % 2 == 0){
    pushMatrix();
    translate(-100 + margin + boxSize * crsr.x, -100 + margin + boxSize * crsr.y, -100 + margin + boxSize * crsr.z);
    fill(255, 255, 0, 50);
    box(20, 20, 20);
    popMatrix();
  }
// ------- END DRAW BLOCKS

// ------- COMPUTER TURN LOOP
  if(frameCount / (float)(ran.nextInt(20) + (p.patience * 100)) % 2 == 0){
        p.investigate(ublocks.blocks, p.cblocks.blocks);
        p.makeMove();
  }
// ------- END COMPUTER TURN LOOP
cam.beginHUD();
fill(100,100,110);
rect(0,0, 200, 50);
if (searching){
  fill(160,160,170);
} else {
  fill(130,130,140);
}
rect(10,10, 180, 30);
fill(80);
if (buff.length() == 0 && !searching){
  text("Search", 14, 38);
} else {
  text(buff, 14, 38);
  text(currSearchText, 14, 80);
}
cam.endHUD();
}

public void keyPressed(){
  println(keyCode);
// if enter key was pressed
  // if cursor is in valid spot / if is players turn
    // place block at cursor spot
 // else if cursor is in invalid spot
   //return false (notify of error)
 // else if isn't players turn
   //return false (notify of error)
  if (!searching){
    if (keyCode == 10 || keyCode == 32|| keyCode == 8){
    //enter and delete key respectively
    if (isUsersTurn){
      if (keyCode == 10 || keyCode == 32){
        ublocks.addBlock(crsr.x, crsr.y, crsr.z, p);
      } else if (keyCode == 8){
        ublocks.deleteBlock(crsr.x, crsr.y, crsr.z);
        p.addMem(new int[] {crsr.x, crsr.y, crsr.z, 0});
      }
    }
    //check keyCode; set cursor. Logic for keeping cursor in bounds is here too
    } else if(keyCode == 87 || keyCode == 38){
    // up key
      if (crsr.y != 9){ crsr.y++; }
    } else if (keyCode == 83 || keyCode == 40){
    // down key
      if (crsr.y != 0){ crsr.y--; }
    } else if (keyCode == 65 || keyCode == 37){
    // left key
      if (crsr.x != 0){ crsr.x--; }
    } else if (keyCode == 68 || keyCode == 39){
    // right key
      if (crsr.x != 9){ crsr.x++; }
    } else if (keyCode == 69){
      // ' key
      if (crsr.z != 9){ crsr.z++; }
    } else if (keyCode == 81){
      // / key
      if (crsr.z != 0){ crsr.z--; }
    } else if (keyCode == 82){
      p.cblocks.blocks.clear();
      ublocks.blocks.clear();
    }
  } else {
  // searching
    if (keyCode == 10){
      p.ratioLine = loadStrings("http://labs.jonathancutrell.com/cdtweets.php?s=" + buff);
      p.creator = Float.valueOf(p.ratioLine[0]).floatValue();
      currSearchText = "Using search term \"" + buff + "\" with a creativity ratio of " + p.creator * 100 + "%";
      println(p.creator);
    }
    char k;
    k = (char)key;
    switch(k) {
    case 8:
      if (buff.length()>0) {
        buff = buff.substring(0, buff.length() - 1);
      }
      break;
    case 13:  // Avoid special keys
    case 10:
    case 65535:
    case 127:
    case 27:
      break;
    default:
      if (textWidth(buff+k)+leftmargin < width-rightmargin) {
        buff=buff+k;
      }
      break;
    }
  }
}


public void mousePressed() {
  // Double Click resets camera
  if (mouseEvent.getClickCount()==2){
    cam.reset(0);
    cam.setMinimumDistance(350);
    cam.setMaximumDistance(750);
    cam.rotateX(PI);
  }
  if (mouseX > 10 && mouseX < 190 && mouseY > 10 && mouseY < 40){
    searching = true;
  } else {
    searching = false;
  }
}
class BlockArray {
  boolean isUsers;
  ArrayList blocks;
  BlockArray(boolean u){
    isUsers = u;
    blocks = new ArrayList();
  }
  public void addBlock(int x, int y, int z, Player p){
    boolean oob = false;
    int[] block = new int[] { x, y, z };
    println("added a block.");
      ArrayList newList = new ArrayList();
      newList.addAll(blocks);
      newList.addAll(p.cblocks.blocks);
      boolean taken = false;
      boolean xyOk = false;
      int maxY = 0;
      boolean placeAbove = false;
      boolean gravity = false;
      
      // loop through all blocks
      for (int i = newList.size() - 1; i >= 0; i--){
        // check to see if a block already exists in cursor location
        int[] check = (int[])newList.get(i);
        if (x == check[0] && y == check[1] && z == check[2]){
          taken = true; // position already occupied by a block
        }
      }
      // Check to make sure the block is in bounds
      if(x>9 || x<0 || y>9 || y<0 || z>9 || z<0){
        oob = true;
      }
    
    if (taken || oob){
    } else if (blocks.size() == 0 || !taken){
      blocks.add(block);
      p.addMem(new int[] {block[0], block[1], block[2], 1});
    }
  }
  public void deleteBlock(int x, int y, int z){
    ArrayList newList = new ArrayList();
    newList.addAll(blocks);
    newList.addAll(p.cblocks.blocks);
    
    boolean taken = false;
    for (int it = blocks.size()-1; it>=0; it--){
      int[] b = (int[])blocks.get(it);
      if (b[0] == x && b[1] == y && b[2] == z){
        taken = true;
      }
    }
    boolean deletable = false;
    int place = 0;
    if (taken){
      // hold place for deleting
      // loop through all blocks
      for (int i = blocks.size() - 1; i >= 0; i--){
        // for each block, grab the coordinates
        int[] b = (int[])blocks.get(i);
        // if deleted block coordinates match current looped block
        if (x == b[0] && y == b[1] && z == b[2]){
          // hold on to the place for deletion
          place = i;
          deletable = true;
        }
      }
    }
    
    if (deletable){
      println("deletable");
      blocks.remove(place);
    } else {
      println("not deletable");
    }
  }
}
class Crsr {
  int x, y, z;
  Crsr(int thisx, int thisy, int thisz){
    x = thisx;
    y = thisy;
    z = thisz;
  };
  public void update(int newx, int newy, int newz){
    x = newx;
    y = newy;
    z = newz;
  }
}



Random ran = new Random();
int[] cm = new int[5];
class Player {
  int turns;
  int[] currMove;
  BlockArray cblocks;
  BlockArray userarray;
  String[] ratioLine = {"0.5"};
  Player(int t, BlockArray watch){
    turns = t;
    currMove = cm;
    cblocks = new BlockArray(false);
    userarray = watch;
  }
  
  // history of recent moves; format: [x, y, z, delete/create]
  ArrayList memory = new ArrayList();
  float creator = Float.valueOf(ratioLine[0]).floatValue();
  float destroyer = 1 - creator;
  float obliviousness = 0;
  float awareness = 1 - obliviousness;
  float patience = 0.5f;
  ArrayList knowledge = new ArrayList();
  ArrayList uArray = new ArrayList();
  public void addMem(int[] item){
    if (memory.size() == 20){
      memory.remove(0);
    }
    memory.add(item);
  }
  
  public void investigate(ArrayList ub, ArrayList cb){
    knowledge.clear();
    uArray.clear();
    knowledge.addAll(ub);
    knowledge.addAll(cb);
    uArray.addAll(ub);
  }
  public void decideXYZ(int[] block, int[] newBlock, boolean recursed){
    boolean taken = false;
    if (!recursed){
      newBlock[0] = block[0];
      newBlock[1] = block[1];
      newBlock[2] = block[2];
    }
    int chooseXYZ = ran.nextInt(3);
    int addSub = ran.nextInt(2);
    if (block[chooseXYZ] == 9){
      newBlock[chooseXYZ]--;
    } else if (block[chooseXYZ] == 0){
      newBlock[chooseXYZ]++;
    } else {
      if(addSub == 1){
        newBlock[chooseXYZ]++;
      } else {
        newBlock[chooseXYZ]--;
      }
    }
    for (int i = knowledge.size() - 1; i >= 0; i--){
      // check to see if a block already exists in cursor location
      int[] check = (int[])knowledge.get(i);
      if (newBlock[0] == check[0] && newBlock[1] == check[1] && newBlock[2] == check[2]){
        taken = true; // position already occupied by a block
      }
    }
    if (taken){
      println("Recurse!");
      decideXYZ(block, newBlock, true);
    }
  }
  public void makeMove(){
      
      // make a decision of what to do based on "demeanor"
      // basically a ratio of different types of 4 different behaviors
      // "destructiveness" is the tendency to delete blocks
      // "awareness" is the tendency to delete a user's blocks OR copy a user's pattern
      // "creativity" is the tendency to create new blocks
      // "obliviousness" is the tendency to delete own blocks OR
      //    to create blocks with no discernible connection to user input
      // IMPORTANT:
      //  Don't forget that in the case of full destroyer, full oblivious, there still must be response from computer;
      //  Provide explanation for non-play if applicable. "The other player is just ignoring you."
  
      // Create creativity array and percentage pool
      ArrayList creativityArray = new ArrayList();
      for (int i = (int)(destroyer * 100); i > 0; i--){
        creativityArray.add(0);
      }
      for (int i2 = (int)(creator * 100); i2 > 0; i2--){
        creativityArray.add(1);
      }
      int isCreative = ((Number)creativityArray.get(ran.nextInt(creativityArray.size()))).intValue();

      
      // Create awareness array and percentage pool
      ArrayList awarenessArray = new ArrayList();
      for (int i = (int)(obliviousness * 100); i > 0; i--){
        awarenessArray.add(0);
      }
      for (int i = (int)(awareness * 100); i > 0; i--){
        awarenessArray.add(1);
      }
      int isAware = ((Number)awarenessArray.get(ran.nextInt(awarenessArray.size()))).intValue();
  
      turns++;
      cm[3] = isAware;
      cm[4] = isCreative;
      // if the user has created one block
      if (isAware==1){
        if (isCreative==1){
          println(turns + ": Aware, Creative");
          int range;
            if (uArray.size() > 0){
              if (uArray.size() == 1){
                range = 1;
              } else {
                range = uArray.size();
              }
              int[] ublock = (int[])uArray.get(ran.nextInt(range));
              int[] newBlock = new int[3];
              decideXYZ(ublock, newBlock, false);
              cblocks.addBlock(newBlock[0], newBlock[1], newBlock[2], this);
            } else {
              // if the user has yet to create any blocks
              cblocks.addBlock(ran.nextInt(9), ran.nextInt(9), ran.nextInt(9), this);
            }
          } else {
          println(turns + ": Aware, Destructive");
          int range;
          if (uArray.size() > 0){
            if (uArray.size() == 1){
            range = 1;
            } else {
              range = uArray.size();
            }
            int[] ublock = (int[])uArray.get(ran.nextInt(range));
            userarray.deleteBlock(ublock[0], ublock[1], ublock[2]);
          }
          }
      } else {
        if (isCreative==1){
          println(turns + ": Oblivious, Creative");
          cblocks.addBlock(ran.nextInt(9), ran.nextInt(9), ran.nextInt(9), this);
        } else {
          println(turns + ": Oblivious, Destructive");
          userarray.deleteBlock(ran.nextInt(9), ran.nextInt(9), ran.nextInt(9));
        }
      }
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "blockplaygravity" });
  }
}
