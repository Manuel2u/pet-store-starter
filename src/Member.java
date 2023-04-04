import java.util.ArrayList;




/**
 *
 * @author Adam Whaley and
 */
public class Member {

    private String name;
    private int memberID;
    private boolean newsletterSubscribed;
    private ArrayList<Dog> dogsOwned = new ArrayList();
    private ArrayList<Cat> catsOwned = new ArrayList();
    private ArrayList<ExoticPet> exoticPetsOwned = new ArrayList();
    private double amountSpent = 0;


    public Member(String name, int memberID, boolean newsletterSubscribed) {
        this.name = name;
        this.memberID = memberID;
        this.newsletterSubscribed = newsletterSubscribed;
    }


    public String getName() {
        return name;
    }


    public int getMemberID() {
        return memberID;
    }


    public boolean isNewsletterSubscribed() {
        return newsletterSubscribed;
    }


    public ArrayList<Dog> getDogsOwned() {
        return dogsOwned;
    }


    public ArrayList<Cat> getCatsOwned() {
        return catsOwned;
    }


    public ArrayList<ExoticPet> getExoticPetsOwned() {
        return exoticPetsOwned;
    }


    public double getAmountSpent() {
        return amountSpent;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }


    public void setNewsletterSubscribed(boolean newsletterSubscribed) {
        this.newsletterSubscribed = newsletterSubscribed;
    }


    public void setAmountSpent(double amountSpent) {
        this.amountSpent = this.amountSpent + amountSpent;
    }

    public void addDog(Dog dog) {
        dogsOwned.add(dog);
    }
    public void addCat(Cat cat) {
        catsOwned.add(cat);
    }
    public void addExoticPet(ExoticPet exoticPet) {
        exoticPetsOwned.add(exoticPet);
    }
}


