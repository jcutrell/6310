class BlockArray {
  boolean isUsers;
  ArrayList blocks;
  BlockArray(boolean u){
    isUsers = u;
    blocks = new ArrayList();
  }
  void addBlock(int x, int y, int z, Player p){
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
  void deleteBlock(int x, int y, int z){
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
