

import java.io.Serializable;


public class Cross extends Pieces implements Serializable{
    public boolean bCheckForValidMovemement(int stonetype, int base, int target){
        System.out.println("stonetype=" + stonetype);
        if (stonetype == 2 || stonetype == 6) {
            System.out.println("target=" + target + ", base=" + base);
            int diff = target - base;
            diff = Math.abs(diff);
            if(base == getPosition()){
               setPosition(target);
            }
            if(diff == 6 || diff == 12 || diff == 18 || diff == 24 ) { 
                  return true;
            }else if (diff == 4 || diff == 8 || diff == 12 || diff == 16){
                return true;
            }
        }
        return false;
    }    
}
