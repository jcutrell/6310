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
