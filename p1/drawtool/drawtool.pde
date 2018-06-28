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
float easing = 0.12;
float orbColor = 255;
float orbColorRatio = 70 - (orbColor / 255 * 70);
Timer fonttimer;

void setup() {
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
void draw() {
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



void mousePressed(){
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

void mouseReleased(){
  // clear tools
  sliderOn = false;
  orbDown = false;
  brushDown = false;
}
void mouseDragged(){
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
      orbColor = 255 - (((mouseX - 10.0) / 70) * 255);
    }
  }
}

// custom functions
void buildColorPicker(){
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

void keyPressed(){
  if (keyCode==32){
    hideControls = !hideControls;
  }
}

void buildConnections(){
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
        strokeWeight(0.01 * rd);
        line(aOrb.xPos, aOrb.yPos, cStroke.x, cStroke.y);
      }
    }
  }
}

void drawBG(){
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

void drawStrokes(){
  for(int i=allStrokes.size(); i>0; i--){
    BStroke drawstroke = (BStroke)allStrokes.get(i-1);
    fill(50);
    smooth();
    line(drawstroke.old_x, drawstroke.old_y, drawstroke.x, drawstroke.y);
  }
}
void drawOrbs(){
  for(int i=orbList.size(); i>0; i--){
    Orb draworb = (Orb)orbList.get(i-1);
    fill(draworb.gColor);
    smooth();
    ellipse(draworb.xPos, draworb.yPos, draworb.diam, draworb.diam);
  }
}
