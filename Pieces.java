

import java.io.Serializable;


public abstract class Pieces implements Serializable
{
    private int piecePosition; //current position of the piece
    private boolean pieceStatus = true; //Status of the piece (dead/alive)
    public abstract boolean bCheckForValidMovemement(int stonetype, int base, int target); //check if the movement made by the user this function will be overridden by the subclasses
    int getPosition(){ // get piece position
        return piecePosition;
    }
    void setPosition(int pos){//set pice position
        piecePosition = pos;
    }
    boolean getStatus(){//get current status
        return pieceStatus;
    }
    void setStatus(boolean stat){// set current status
        pieceStatus = stat;
    }
}
