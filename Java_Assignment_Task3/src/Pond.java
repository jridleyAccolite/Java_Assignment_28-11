import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pond implements Runnable {

    public ExecutorService meetings;
    private static final int poolSize = 5;
    public static ArrayList<Fish> fish;

    private final int startMaleNo = 10;
    private final int startFemaleNo = 10;

    public Pond(){

        fish = new ArrayList<>();
        for (int i = 0; i < startMaleNo; i++) {
            fish.add(new Fish(true));
        }
        for (int i = 0; i < startFemaleNo; i++) {
            fish.add(new Fish(false));
        }
    }

    private void startSimulation() {
        System.out.println("Simulation beginning... population is: " + Fish.population);
        meetings = Executors.newFixedThreadPool(poolSize);
        Random r = new Random();

        // pick a random fish and check whether it is alive and not busy
        // if so, submit to threadpool
        // if population is 0, shutdown meetings
        while(true){

            if(Fish.population <= 0){
                System.out.println("All fish are dead... ending simulation");
                meetings.shutdownNow();
                break;
            }

            int fishIndex = r.nextInt(fish.size());
            Fish chosenFish = fish.get(fishIndex);

            while(chosenFish.isDead()) {
                fishIndex = r.nextInt(fish.size());
                chosenFish = fish.get(fishIndex);

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    //terminate
                    meetings.shutdown();
                    return;
                }
            }

            meetings.submit(chosenFish);
        }
    }

    @Override
    public void run() {
        startSimulation();
    }
}
