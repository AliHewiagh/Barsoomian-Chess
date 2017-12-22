
public class Helper {//This class is to verify if the selected stone belongs to the current player or not
    public int position;
    public Helper() {

    }
    public boolean bVerifySelectedStone(int iSelectedStoneOwner) {
        if (iSelectedStoneOwner == ChessPlayer.iCurrentPlayer) { 
            return true;
        } else {
            return false;
        }
    }
}
