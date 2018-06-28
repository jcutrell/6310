import java.util.Random;


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
  float patience = 0.5;
  ArrayList knowledge = new ArrayList();
  ArrayList uArray = new ArrayList();
  void addMem(int[] item){
    if (memory.size() == 20){
      memory.remove(0);
    }
    memory.add(item);
  }
  
  void investigate(ArrayList ub, ArrayList cb){
    knowledge.clear();
    uArray.clear();
    knowledge.addAll(ub);
    knowledge.addAll(cb);
    uArray.addAll(ub);
  }
  void decideXYZ(int[] block, int[] newBlock, boolean recursed){
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
  void makeMove(){
      
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
