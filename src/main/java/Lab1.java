import java.lang.Math;

public class Lab1 {
    public static void main(String args[]){
        int a = squareRoot(64);
    }

    public static int squareRoot (int x) {
        if(x < 0) {
            return (int) -1e8;
        }else {
            return (int) Math.sqrt(x);
        }
    }
}
