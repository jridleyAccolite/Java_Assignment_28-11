package Task1;

public class TwoPoints {
    public static void main(String[] args) {
        /* Find the point of interception of two points x and y that are moving around a
        clock face with 12 points, given their start points. (x moves twice as fast as y)
        */
        System.out.println(computePointOfInterception(2, 4));
    }

    public static int computePointOfInterception(int x, int y){
        while(x != y){
            x+=2;
            x %= 12;    // wrap number around the clock faces limited 12 points

            y++;
            y %= 12;
        }
        return x;   // point of interception
    }
}


