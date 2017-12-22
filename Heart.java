import java.io.Serializable;

public class Heart extends Pieces implements Serializable{
    public boolean bCheckForValidMovemement(int stonetype, int base, int target){
        System.out.println("stonetype=" + stonetype);
        if (stonetype == 1 || stonetype == 5) {
            System.out.println("type=" + stonetype + ", target=" + target + ", base=" + base);
            int diff = target - base;
            diff = Math.abs(diff);
            if(base == getPosition()){
               setPosition(target);
            }
            if(diff == 1|| diff == 5)
                return true;
            if(diff == 6 || diff ==4 )
                return true;
        } 
    return false;
    }
}
