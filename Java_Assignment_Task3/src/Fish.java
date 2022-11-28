import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fish implements Runnable{
    public static final int offspring = 2;
    private Boolean isMeeting = false;
    private Boolean gender;
    private Boolean isDead = false;
    public Lock lock = new ReentrantLock();
    //private Fish partner;

    public static int population;


    public Fish(Boolean gender){
        this.gender = gender;
        population++;
    }
    public Fish(){
        // creates a Fish with a random gender
        Random r = new Random();
        this.gender = r.nextBoolean();
        population++;
    }

    public Boolean isBusy() {
        return isMeeting;
    }

    public void setBusy(Boolean meeting) {
        isMeeting = meeting;
    }

    /*public void meetWithPartner(){
        //System.out.println("Meeting started");

        String desc = "";

        prepareForMeeting();

        // 1) if two male fish meet they kill each other
        if(this.getGender() && this.partner.getGender()){
            desc = "Two Males Met...";
            this.die();
            this.partner.die();
        }
        // 2) if two females meet one dies randomly
        else if(!this.getGender() && !partner.getGender()) {
            desc = "Two Females Met...";
            Random r = new Random();
            if(r.nextBoolean()){
                this.die();
            }
            else{
                this.partner.die();
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

        endMeeting();

        System.out.println(desc + " population is: " + Fish.population);
    }*/

    @Override
    public void run() {
        //System.out.println("Fish run called");

        // pick a fish to meet with
        Fish partner = pickPartner();

        if(partner != null){
            // meet with that fish
            Meeting.meet(this, partner);
        }
        this.setBusy(false);

        partner.setBusy(false);
    }


    public Fish pickPartner(){
        //System.out.println("Picking partner...");
        // pick a fish to meet with
        Random r = new Random();
        int partnerIndex = r.nextInt(Pond.fish.size());
        Fish partner = Pond.fish.get(partnerIndex);

        // N.B. this fish has already been set to busy so it cannot select itself as a partner
        while(partner.isDead() || partner.isBusy()){
            // if partner is dead or busy, find new partner
            partnerIndex = r.nextInt(Pond.fish.size());
            partner = Pond.fish.get(partnerIndex);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // if interrupted terminate thread
                return null;
            }
        }

        partner.lock.lock();
        partner.setBusy(true);
        partner.lock.unlock();

        return partner;
    }

    public Boolean getGender(){
        return this.gender;
    }

    public Boolean isDead() {
        return this.isDead;
    }

    public synchronized void die() {
        this.isDead = true;
        population--;
    }
}
