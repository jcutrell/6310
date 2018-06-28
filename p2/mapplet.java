import processing.core.*; 
import processing.xml.*; 

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

public class mapplet extends PApplet {



WorldMap wmap;
//LatLngs lls = new LatLngs();
ArrayList tweets1 = new ArrayList();
ArrayList tweets2 = new ArrayList();
ArrayList tweets3 = new ArrayList();
Random generator = new Random();
PFont myFont;
PFont myFont2;
Point currentCity = null;
String currentTweet = "";
ArrayList tweetMaster = new ArrayList();
float[][] theArray = {{10.3873369f, -75.5196631f},{53.34155999999999f, -6.257346999999982f},{53.543564f, -113.490452f},{50.9406645f, 6.959911499999976f},{40.8400969f, 14.251635699999952f},{4.784821000000001f, 7.005452999999989f},{19.0330488f, 73.02966249999997f},{31.631321f, -8.012477999999987f},{11.0168445f, 76.95583209999995f},{23.3440997f, 85.30956200000003f},{14.6133333f, -90.53527780000002f},{52.4829614f, -1.8935920000000124f},{10.7904833f, 78.70467250000002f},{34.810488f, 117.32372499999997f},{32.128818f, 112.77195899999992f},{10.3873369f, -75.5196631f},{33.718151f, 73.06054700000004f},{1.0456264f, 104.03045350000002f},{-27.4709331f, 153.02350239999998f},{-22.8270994f, -43.054379400000016f},{30.7333148f, 76.7794179f},{-5.45f, 105.26666669999997f},{29.868336f, 121.54399000000001f},{26.2567287f, 73.01704419999999f},{46.59019000000001f, 125.10463700000003f},{12.1363889f, -86.2513889f},{29.031673f, 111.69849699999997f},{-32.9507408f, -60.66650010000001f},{56.0012512f, 92.8855896f},{32.625478f, 116.99993300000006f},{9.9252007f, 78.11977539999998f},{26.147129f, 91.73555110000007f},{35.6072668f, 140.10629070000005f},{36.179852f, 44.01610900000003f},{37.3393857f, -121.89495549999998f},{47.921378f, 106.90553999999997f},{6.3625f, 2.4255000000000564f},{-4.350073f, 15.307732299999998f},{42.331427f, -83.0457538f},{26.89384f, 112.57190000000003f},{40.84231f, 111.74884700000007f},{26.224447f, 78.17871100000002f},{11.664325f, 78.14601419999997f},{-9.6662515f, -35.735098300000004f},{16.5061743f, 80.6480153f},{12.303534f, 76.64611000000002f},{51.533269f, 46.03454899999997f},{30.894348f, 120.08682299999998f},{22.8166667f, 89.54999999999995f},{10.0636111f, -69.33472219999999f},{31.7311292f, -106.46256240000002f},{-0.95373f, 100.35199699999998f},{3.043061f, 101.44065709999995f},{51.6600188f, 39.19741049999993f},{37.8666667f, 32.48333330000003f},{11.8333333f, 13.149999999999977f},{8.487495f, 76.948623f},{35.7666667f, -5.7999999999999545f},{37.5034138f, 126.76603090000003f},{-16.3988222f, -71.5368861f},{11.08192f, 7.715956000000006f},{15.4461002f, 74.99868019999997f},{21.481217f, 109.11989900000003f},{42.870022f, 74.58788299999992f},{24.479836f, 118.08942000000002f},{14.065f, 100.64611109999998f},{7.8941667f, -72.50388889999999f},{28.55386f, 112.35518000000002f},{42.1892407f, 14.541722199999981f},{6.137777799999999f, 1.2124999999999773f},{-22.7868246f, -43.313106300000015f},{47.8388f, 35.13956699999994f},{34.308159f, 47.057320000000004f},{30.209459f, 67.01817299999993f},{-16.49901f, -68.14624800000001f},{-7.981894f, 112.62650299999996f},{27.702871f, 85.31824400000005f},{31.340527f, 119.82336299999997f},{7.190708f, 125.45534099999998f},{36.706691f, 119.16192999999998f},{-4.05f, 39.66666669999995f},{13.512668f, 2.112516000000028f},{22.270715f, 113.57672600000001f},{31.3260152f, 75.57618290000005f},{30.3321838f, -81.65565099999998f},{19.27883f, 72.87879199999998f},{39.7683765f, -86.15804229999998f},{35.5500643f, 118.82892979999997f},{34.573262f, 135.48299799999995f},{51.7592485f, 19.45598330000007f},{27.908337f, 116.77543500000002f},{36.88414f, 30.70562999999993f},{10.3156992f, 123.88543660000005f},{25.684139f, -100.25099899999998f},{-16.5166667f, -68.16666670000001f},{7.688089f, -5.030654000000027f},{43.6932364f, 5.437460399999964f},{35.6969444f, -0.6330556000000342f},{59.33278809999999f, 18.064488100000062f},{20.2960587f, 85.82453980000003f},{25.1695114f, 75.85398980000002f},{34.7084139f, 36.7073944f},{36.81881f, 10.165960000000041f},{-22.7597631f, -43.45155420000003f},{50.06465009999999f, 19.94497990000002f},{-5.794478499999999f, -35.21095309999998f},{37.950197f, 58.380223f},{23.1416985f, 120.25127279999992f},{27.89381f, 78.06813799999998f},{-23.689584f, -46.5647381f},{56.9465363f, 24.104850299999953f},{0.5333333f, 101.45000000000005f},{12.104797f, 15.044505999999956f},{28.6352778f, -106.08888890000003f},{20.97f, -89.62f},{37.8043637f, -122.2711137f},{28.3670355f, 79.43043809999995f},{28.8315925f, 78.77827639999998f},{49.839683f, 24.029717000000005f},{-8.112f, -79.02879999999999f},{16.863794f, -99.88161400000001f},{52.3702157f, 4.895167899999933f},{39.9611755f, -82.99879420000002f},{28.390409f, 36.57320000000004f},{21.158964f, -86.84593699999999f},{-33.93264f, 25.569950000000063f},{27.622865f, 113.85467599999993f},{14.0833f, -87.2167f},{8.491655999999999f, 4.544642000000067f},{39.4702393f, -0.37680490000002465f},{-5.0892123f, -42.801627499999995f},{4.361698f, 18.55597499999999f},{41.09512f, 121.12700399999994f},{53.5355606f, 49.40956870000002f},{35.05171199999999f, 118.34770700000001f},{30.041822f, 112.424664f},{39.9117431f, 127.54528849999997f},{7.8941667f, -72.50388889999999f},{21.270702f, 110.35938699999997f},{-20.4435053f, -54.64775909999997f},{40.824418f, 114.88754300000005f},{22.1564699f, -100.98554089999999f},{37.97918f, 23.716646999999966f},{18.084061f, -15.978420000000028f},{-1.950106f, 30.058768999999984f},{37.3218778f, 126.83088480000004f},{-20.17219f, 28.581159999999954f},{19.2938306f, 73.06583890000002f},{21.8817964f, -102.29127010000002f},{38.873891f, 115.46480599999995f},{41.78376f, 129.78359999999998f},{44.988458f, 126.04971499999999f},{9.9333333f, 8.883333300000004f},{8.372155f, -62.64386000000002f},{38.2526647f, -85.75845570000001f},{17.992731f, -76.79200900000001f},{32.8031004f, 130.70789109999998f},{31.768318f, 35.21371099999999f},{33.955845f, 116.79826500000001f},{44.55165299999999f, 129.63316899999995f},{19.538988f, -99.19328100000001f},{45.814912f, 15.97851449999996f},{52.9399159f, -73.5491361f},{32.39421f, 119.41296599999998f},{-23.5316928f, -46.789923199999976f},{30.267153f, -97.74306079999997f},{5.1166667f, 7.366666699999996f},{-15.7861111f, 35.00583329999995f},{4.61175f, 101.11350600000003f},{43.243603f, -79.88907499999999f},{19.4207517f, -98.94895710000003f},{37.38263999999999f, -5.9962950999999975f},{38.11564f, 13.361405900000022f},{35.55f, 45.43333329999996f},{32.1937314f, 112.10535849999997f},{35.303004f, 113.92679999999996f},{26.75f, 83.3666667f},{21.2513844f, 81.6296413f},{49.8997541f, -97.1374937f},{36.617085f, 101.77820799999995f},{47.910483f, 33.39178300000003f},{21.27066f, 40.41706799999997f},{20.5930556f, -100.39222219999999f},{32.1166667f, 20.06666670000004f},{25.4333333f, -100.98333330000003f},{4.8142778f, -75.69455829999998f},{-23.655314f, -46.5320878f},{47.02685899999999f, 28.84155099999998f},{54.3214798f, 48.38565040000003f},{-13.9833333f, 33.78333329999998f},{45.0421487f, 38.980640300000005f},{37.2410864f, 127.17755369999998f},{6.9270786f, 79.86124300000006f},{-8.65629f, 115.22209900000007f},{11.558831f, 104.91744500000004f},{6.687119f, -1.6219690000000355f},{24.3666667f, 88.60000000000002f},{35.1495343f, -90.0489801f},{-7.11532f, -34.86105120000002f},{36.9910113f, 127.92594970000005f},{50.1114451f, 8.680615399999965f},{37.91441f, 40.23062900000002f},{51.1078852f, 17.03853760000004f},{56.8426093f, 53.22444919999998f},{-0.5021829999999999f, 117.15380099999993f},{34.6551456f, 133.9195019f},{36.8f, 34.633333300000004f},{19.700593f, -101.186421f},{35.5714621f, 139.3731765f},{-28.2538694f, -68.71549749999997f},{30.199652f, 115.03852000000006f},{36.6424341f, 127.48903189999999f},{29.0891857f, -110.96132990000001f},{26.7077972f, 88.42748180000001f},{-4.77867f, 11.863640000000032f},{31.035423f, 112.19926499999997f},{35.414864f, 116.58725900000002f},{40.127344f, 124.38491299999998f},{35.215893f, 113.24182300000007f},{27.994267f, 120.69936699999994f},{8.732999999999999f, 77.70000000000005f},{21.183008f, 81.36186199999997f},{20.6333333f, -103.31666669999998f},{20.030765f, 110.32887299999993f},{30.425541f, 48.18905999999993f},{24.8041667f, -107.43111110000001f},{41.6562873f, -0.8765379000000166f},{-21.25286f, -50.645839000000024f},{46.799923f, 130.31891700000006f},{55.86562739999999f, -4.257222700000057f},{32.6455704f, -115.4453421f},{35.46883f, 44.39098000000001f},{39.2903848f, -76.61218930000001f},{23.68721f, 86.973343f},{57.6301004f, 39.86563109999997f},{8.4542363f, 124.63189769999997f},{47.349935f, 130.29810099999997f},{37.3942527f, 126.95682090000003f},{9.9457876f, 76.275443f},{53.3563385f, 83.76164990000007f},{20.930679f, 77.75871299999994f},{34.596544f, 119.22128199999997f},{55.86562739999999f, -4.257222700000057f},{34.1547f, 113.47251600000004f},{29.580237f, 105.05835200000001f},{14.5176184f, 121.05086449999999f},{32.725409f, -97.32084959999997f},{35.2270869f, -80.84312669999997f},{42.257817f, 118.88685599999997f},{-34.76118230000001f, -58.43024760000003f},{34.7108344f, 137.72612579999998f},{51.92421599999999f, 4.481775999999968f},{-33.615768f, -70.56964900000003f},{43.1306915f, 131.92382799999996f},{41.269493f, 123.17283799999996f}};


// for typing at dialog
int leftmargin = 10;
int rightmargin = 20;
Boolean box1 = false;
Boolean box2 = false;
String buff1 = "";
String buff2 = "";
Boolean dialog = true;

// Master Colors
int[] k1 = {24, 128, 200};
int[] k2 = {110, 300, 100};
int[] k3 = {245, 128, 10};

// toggle display of tweets
int Obama;
int Romney;
int both;

// Get JSON from Twitter
public void getTwitterData(String url, ArrayList tweets, int kuler[], int origin)
{
  XMLElement xml;
  xml = new XMLElement(this, url);
  wmap = new WorldMap();
  int numTweets = xml.getChildCount();
  for (int i = 0; i<numTweets; i++){
    XMLElement kid = xml.getChild(i);
    XMLElement txtNode = kid.getChild("text");
    XMLElement userNode = kid.getChild("user");
    XMLElement screenNameNode = userNode.getChild("screen_name");
    int cityInt = generator.nextInt(255);
    float lat = ((float)theArray[cityInt][0]);
    float lng = ((float)theArray[cityInt][1]);
    ArrayList tempArr = new ArrayList();
    tempArr.add(wmap.getPoint(lat, lng));
    tempArr.add(origin);
    tempArr.add(txtNode.getContent());
    tempArr.add(kuler);
    tempArr.add(screenNameNode.getContent());
    tweets.add(tempArr);
  }
}

public void buildMasterList(ArrayList twts){
    for (int i=0; i<twts.size(); i++){
    tweetMaster.add((ArrayList)twts.get(i));
    }
}

public void makeKey(String term1, String term2){
  if(Obama == 1){
    fill((int)k1[0], (int)k1[1], (int)k1[2]);
  } else {
    fill(80);
  }
  rect((width/6) * 2 -20, 6, 16, 16);
  
  if(Romney==1){
    fill((int)k2[0], (int)k2[1], (int)k2[2]);
  } else {
    fill(80);
  }
  rect((width/6) * 3 -20, 6, 16, 16);
  if(both==1){
    fill((int)k3[0], (int)k3[1], (int)k3[2]);
  } else {
    fill(80);
  }
  rect((width/6) * 4 -20, 6, 16, 16);
  fill(230);
  text(term1, (width/6) * 2, 20);
  text(term2, (width/6) * 3, 20);
  text(term1 + " + " + term2, (width/6) * 4, 20);
}
public void setup() {
  // Uncomment the following two lines to see the available fonts 
//  String[] fontList = PFont.list();
//  println(fontList);
  myFont = createFont("Helvetica-Bold", 12);
  myFont2 = createFont("Helvetica-Bold", 38);
  Obama = 1;
  Romney = 1;
  both = 1;
  textFont(myFont);
  size(1000, 500);
  ellipseMode(CENTER);
  fill(24, 128, 200);
  getTwitterData("twitteroutput/twitter.xml", tweets1, k1, 1);
  getTwitterData("twitteroutput/twitter2.xml", tweets2, k2, 2);
  getTwitterData("twitteroutput/twitter3.xml", tweets3,  k3, 3);
  buildMasterList(tweets1);
  buildMasterList(tweets2);
  buildMasterList(tweets3);
  smooth();
}

public void handleMouse(ArrayList tweetMaster) {
  for(int i=0; i<tweetMaster.size(); i++){
    ArrayList tweet = (ArrayList)tweetMaster.get(i);
    Point city = (Point)tweet.get(0);
    int origin =  (Integer)tweet.get(1);
    int k[] = (int[])tweet.get(3);
    if(((origin == 1) && Obama != 1) || ((origin == 2) && Romney != 1) || ((origin == 3) && both != 1)){ } else {
      fill((int)k[0], (int)k[1], (int)k[2]);
      if (
          ( mouseX > (city.x -4)
            && mouseX < (city.x +4)
            && mouseY > (city.y -4)
            && mouseY < (city.y + 4)
           ) && currentCity == null)
           {
            currentCity = city;
            ellipse(currentCity.x, currentCity.y, 14, 14);
            currentTweet = "@" + (String)tweet.get(4) + "\n" + (String)tweet.get(2);
            } else {
            ellipse(city.x, city.y, 9, 9);
          }
      }
    }
      if(currentCity != null){
        fill(10);
        stroke(45);
        int xPos=mouseX, yPos=mouseY, tweetY=mouseY+10, tweetX = mouseX+10;
        if(mouseX > (width/2)){
          xPos = mouseX - 250;
          tweetX = xPos + 10;
        }
        if(mouseY > (height/2)){
          yPos = mouseY - 110;
          tweetY = yPos + 10;
        }
        rect(xPos, yPos, 240, 100);
        fill(200);
        noStroke();
        text(currentTweet, tweetX, tweetY, 220, 80);
        currentCity = null;
      }
  }

public void draw() {
  noStroke();
  if(!dialog){
    wmap.drawBackground();
    handleMouse(tweetMaster);
    textAlign(LEFT);
    textFont(myFont);
    makeKey("Obama", "Romney");
  } else {
    background(20);
    textFont(myFont2);
    fill(180);
    if(box1){
      fill(100);
    }
    rect(200, height / 2 - 160, width -400, 80);
    fill(180);
    if(box2){
      fill(100);
    }
    rect(200, height / 2 - 40, width -400, 80);
    fill(180);
    rect(200, height / 2 + 80, width -400, 80);
    fill(100);
    textAlign(LEFT);
    text("Search Term One", 200, height / 2 - 160);
    text("Search Term Two", 200, height / 2 - 40);
    textAlign(CENTER);
    text("GO", width/2, height / 2 + 140);
    fill(20);
    textAlign(CENTER);
    if(buff1 != ""){
      text(buff1, width/2, height / 2 - 100);
    }
     if(buff2 != "") {
      text(buff2, width/2, height / 2 +20);
    }
  }
}



//key down event
public void keyPressed()
{
  char k;
  k = (char)key;
  println("pressed: " + (int)k);
  switch(k) {
  case 8:
    if (buff1.length()>0) {
      buff1 = buff1.substring(0, buff1.length() - 1);
    }
    if (buff2.length()>0) {
      buff2 = buff2.substring(0, buff2.length() - 1);
    }
    break;
  case 13:  // Avoid special keys
  case 10:
  case 65535:
  case 127:
  case 27:
    break;
  default:
    if(box1){
      buff1=buff1+k;
    } else if (box2){
      buff2=buff2+k;
    }
    break;
  }
}

public void toggleItem(int item){
  item = item * -1;
  println(item);
}

public void mousePressed(){
//    rect(200, height / 2 - 160, width -400, 80);
//    rect(200, height / 2 - 40, width -400, 80);
//    rect(200, height / 2 + 80, width -400, 80);
  if(dialog){
  if(mouseX > 200 && mouseX < (200 + (width-400))){
    if((mouseY > height / 2 - 160) && (mouseY < height/2 - 80)){
      box1 = true;
      box2 = false;
    } else if((mouseY > height / 2 - 40) && (mouseY < height/2 + 40)){
      box2 = true;
      box1 = false;
    } else if((mouseY > height / 2 + 80) && (mouseY < height/2 + 160)){
      dialog = false;
      box1 = false;
      box2 = false;
    }
  }
  }
//  rect((width/6) * 2 -20, 6, 16, 16);
//  rect((width/6) * 3 -20, 6, 16, 16);
//  rect((width/6) * 4 -20, 6, 16, 16);
  if (!dialog) {
    if (mouseY > 6 && mouseY < 20){
      if ((mouseX > (width/6) *2 - 20) && (mouseX < (width/6) *2 - 4)){
        Obama = Obama * -1;
      } else if ((mouseX > (width/6) *3 - 20) && (mouseX < (width/6) *3 - 4)){
        Romney = Romney * -1;
      } else if ((mouseX > (width/6) *4 - 20) && (mouseX < (width/6) *4 - 4)){
        both = both * -1;
      }
    }
  }
}
class WorldMap {
  
  int x, y, w, h;
  PImage raster;
  
  WorldMap() {
    this(0, 0, width, height);
  }
  
  WorldMap(int x, int y, int w, int h) {
    if (h >= w/2) {
      this.w = w;
      this.h = w/2;
      this.x = x;
      this.y = (h - this.h)/2;
    } else {
      this.h = h;
      this.w = 2*h;
      this.x = (w - this.w)/2;
      this.y = y;
    }
    raster = loadImage("world_longlatwgs84.png");
  }
  
  public void drawBackground() {
    image(raster, x, y, w, h);
  }
  
  public Point getPoint(float phi, float lambda) {
    return new Point(
      x + ((180+lambda)/360)*w,
      y + h - ((90+phi)/180)*h
    );
  }
  
}

class Point extends Point2D {
  Point(float x, float y) { super(x, y); }
}

class Point2D {
  float x, y;
  Point2D(float x, float y) {
    this.x = x; this.y = y;
  }
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "mapplet" });
  }
}
