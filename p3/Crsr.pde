class Crsr {
  int x, y, z;
  Crsr(int thisx, int thisy, int thisz){
    x = thisx;
    y = thisy;
    z = thisz;
  };
  void update(int newx, int newy, int newz){
    x = newx;
    y = newy;
    z = newz;
  }
}
