
import java.io.Serializable;


public class Star extends Pieces implements Serializable{
    public boolean bCheckForValidMovemement(int stonetype, int base, int target) {
        System.out.println("stonetype=" + stonetype);
        if (stonetype == 4 || stonetype == 8) {
           System.out.println("type=" + stonetype + ", target=" + target + ", base=" + base);
           int diff = target - base;
           diff = Math.abs(diff);
           if(base == getPosition()){
               setPosition(target);
           }
           if(diff == 5 || diff == 10 ||diff== 1 || diff == 2 ) {
               return true;
           }
           if(diff == 4 || diff == 8 || diff == 6 || diff == 12){
                 return true;
           }
       }
       return false;
  }
}
