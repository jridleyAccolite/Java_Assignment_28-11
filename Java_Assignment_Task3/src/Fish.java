import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fish implements Runnable{
    private Boolean gender;
    private Boolean isDead = false;
    public Lock lock = new ReentrantLock();

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

    @Override
    public void run() {
        // pick a fish to meet with
        Fish partner = pickPartner();

        if(partner != null){
            // meet with that fish
            Meeting.meet(this, partner);
            this.lock.unlock();
            partner.lock.unlock();
        }
    }


    public Fish pickPartner(){
        // pick a fish to meet with
        Random r = new Random();
        int partnerIndex = r.nextInt(Pond.fish.size());
        Fish partner = Pond.fish.get(partnerIndex);

        while(partner.isDead() || partner == this){
            // if partner is dead or the same as current fish instance, find new partner
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
        this.lock.lock();   // N.B. only lock self after successfully locking a partner

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
        // limits population to 0 to mimic real world
        if(population <= 0){
            population = 0;
        }
    }
}
