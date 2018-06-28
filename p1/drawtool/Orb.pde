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
  void update(float orbColor){
    this.xPos = xPos;
    this.yPos = yPos;
    this.diam = this.diam + 1;
    this.show();
  }
  void show(){
    drawBG();
    fill(this.gColor);
    ellipse(this.xPos, this.yPos, this.diam, this.diam);
  }
}
