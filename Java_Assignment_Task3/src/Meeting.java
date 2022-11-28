import java.util.Random;

public class Meeting {
    public static final int offspring = 2;

    public static void meet(Fish fish1, Fish fish2){
        //System.out.println("Meeting started");

        String desc = "";

        // 1) if two male fish meet they kill each other
        if(fish1.getGender() && fish2.getGender()){
            desc = "Two Males Met...";
            fish1.die();
            fish2.die();
        }
        // 2) if two females meet one dies randomly
        else if(!fish1.getGender() && !fish2.getGender()) {
            desc = "Two Females Met...";
            Random r = new Random();
            if(r.nextBoolean()){
                fish1.die();
            }
            else{
                fish2.die();
            }
        }
        // if neither, then one is true and one is false
        else{
            desc = "One Male and One Female Met...";
            // 3) if a male and female fish meet they spawn new fish of random gender
            for (int i = 0; i < offspring; i++) {
                Pond.fish.add(new Fish());
            }
        }

        System.out.println(desc + " population is: " + Fish.population);
    }
}
