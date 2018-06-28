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
  
  void drawBackground() {
    image(raster, x, y, w, h);
  }
  
  Point getPoint(float phi, float lambda) {
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
