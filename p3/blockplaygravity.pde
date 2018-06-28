import peasy.test.*;
import peasy.org.apache.commons.math.*;
import peasy.*;
import peasy.org.apache.commons.math.geometry.*;

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

void setup() {
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
  background(0.5);
  stroke(20, 40);
  p.ratioLine = loadStrings("http://labs.jonathancutrell.com/cdtweets.php?s=" + buff);
}

void draw(){
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

void keyPressed(){
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


void mousePressed() {
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
