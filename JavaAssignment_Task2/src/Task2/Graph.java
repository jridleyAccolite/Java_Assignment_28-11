package Task2;

public class Graph {

    public static void main(String[] args) {
        Graph graph = new Graph();
        int result = graph.calculateAverageDistanceBetweenTwoPoints("A", "C");
    }

    private int[][] g;  // store DAG as matrix
    private static int totalPathDistance = 0;
    private static int numberOfPaths = 0;

    public Graph(){
        // init graph
        g = new int[5][5];
        g[0] = new int[] {0,12,13,11,8};
        g[1] = new int[] {0,0,3,0,0};
        g[2] = new int[] {0,0,0,0,0};
        g[3] = new int[] {0,0,0,0,7};
        g[4] = new int[] {0,0,4,0,0};
    }

    public int calculateAverageDistanceBetweenTwoPoints(String x, String y){

        System.out.println("Finding Paths Between Points " + x + " and " + y + "...");

        totalPathDistance = 0;
        numberOfPaths = 0;

        int point1 = Character.getNumericValue(x.toUpperCase().charAt(0)) - 10;
        int point2 = Character.getNumericValue(y.toUpperCase().charAt(0)) - 10;

        countPaths(point1, point2, 0);  // recursive path search

        System.out.println(numberOfPaths + " paths found");
        int average = totalPathDistance / numberOfPaths;
        System.out.println("Average path length between points " + x + " and " + y + " is " + average);
        return average;
    }

    public void countPaths(int current, int target, int total){
        if (current == target){
            System.out.println("Path of length " + total + " found");
            totalPathDistance += total;
            numberOfPaths++;
        }

        for (int i = 0; i < 5; i++) {
            if(g[current][i] > 0){
                //System.out.println("step of " + g[current][i]);
                countPaths(i, target, (total + g[current][i]));
            }
        }
    }
}
