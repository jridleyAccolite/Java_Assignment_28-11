public class Simulation {
    public static void main(String[] args) {
        Thread pond = new Thread(new Pond());
        pond.start();

        while(Fish.population != 0){
            // wait
        }
        pond.interrupt();
    }
}
