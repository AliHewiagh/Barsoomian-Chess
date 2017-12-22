

import java.io.Serializable;


public class Arrow extends Pieces implements Serializable{
    public boolean bCheckForValidMovemement(int stonetype, int base, int target){
        System.out.println("stonetype=" + stonetype);
        if (stonetype == 3 || stonetype == 7){
            System.out.println("target=" + target + ", base=" + base);
            int diff = target - base;
            if(base == 1 || base == 2 || base == 3){
                 if(diff == 5 || diff == 10){
                     return true;
                 }
            }
            if(base == getPosition()){
                setPosition(target);
            }
            if(diff == -5 || diff == -10){
                return true;
            }
        }
        return false;
    }
}
