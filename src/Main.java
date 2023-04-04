import java.util. *;


/**
 *
 * @author Adam Whaley and 
 */




public class Main {


    public static void main(String[] args) {


        Scanner scnr = new Scanner(System. in);
        PetStore ps = new PetStore("Insert Petstore name here"); // Give your store a name!
        System.out.println("**** Welcome to " + ps.getStoreName() + "****");
        while (true) {


            System.out.println("Please select from one of the following menu otions");
            System.out.println("\t1. Buy a new pet");
            System.out.println("\t2. Register a new member");
            System.out.println("\t3. Start adoption drive (add new pets)");
            System.out.println("\t4. Check current inventory");
            System.out.println("\t5. Register new pet to Owner profile");
            System.out.println("\t6. Exit");


            int choice1 = scnr.nextInt();


            switch (choice1) {
                case 1:
                    System.out.println("-----------------------------------");
                    purchase(ps, scnr, new ArrayList<Dog>());
                    break;
                case 2:
                    System.out.println("-----------------------------------");
                    registerNewMember(ps, scnr);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    System.out.println("Thanks for visiting!");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }


    private static void purchase(PetStore petStore, Scanner scnr, ArrayList<Dog> cart) {
        System.out.println("What type of pet are you here to purchase?");
        System.out.println("\t1. Dogs");
        //System.out.println("\t2. Cats"); System.out.println("\t3. Exotic Pets");


        int petTypeChoice = scnr.nextInt();


        // display inventory menu
        int itemNum = 1;
        if (petTypeChoice == 1) {
            ArrayList<Dog> inventory = petStore.getAvailableDogs();
            if (!inventory.isEmpty()) {


                System.out.println("Which of the following dogs would you like to purchase?:");


                for (Dog pet : inventory) {
                    System.out.println(
                            "\t" + itemNum + ". $" + pet.getPrice() + " - " + pet.getBreed() + "(" + pet.getName() +
                                    ")"
                    );
                    itemNum++;
                }
                // get user selection for product to add arraylist (cart)
                int choice = scnr.nextInt();
                if (choice <= inventory.size()) {
                    cart.add(inventory.get(choice - 1));
                    //update inventory for this item
                    petStore.removePet("dog", choice - 1);
                    //cart summary
                    System.out.println(
                            "You have " + cart.size() + " items in your cart. Are you done shopping?"
                    );
                    System.out.println("\t1. Yes");
                    System.out.println("\t2. No");


                    int doneShopping = scnr.nextInt();
                    if (doneShopping == 1) {
                        //System.out.println("TO DO - CHEKOUT ");
                        checkout(petStore, scnr, cart);
                    } else if (doneShopping == 2) { // more shopping
                        purchase(petStore, scnr, cart); // recursively call purchase(...) until done
                    } else {
                        System.out.println("Invalid Choice.");
                    }
                } else {
                    System.out.println("Please enter a valid product number. Try again");
                    purchase(petStore, scnr, cart);
                }
            } else {
                System.out.println("Sorry! we currently have no dogs available.");
            }


        }
    }


    private static void checkout(PetStore petStore, Scanner scnr, ArrayList<Dog> cart) {
        // calculate total
        double total = 0;
        for (Dog pet : cart) {
            total += pet.getPrice();
        }
        System.out.println(
                "Your total comes to " + total + ". \nPlease select which member is making this" +
                        " purchase."
        );


        // list members and option to register
        int num = 1;
        for (Member member : petStore.getMemberList()) {
            System.out.println(num + ". " + member.getName());
            num++;
        }
        for (PremiumMember member : petStore.getPremiumMemberList()) {
            System.out.println(num + ". " + member.getName());
            num++;
        }
        System.out.println(num + ". Register a new Member.");


        System.out.println(""); // space line
        int memberSelect = scnr.nextInt();
        Member purchaser = null;
        PremiumMember premiumPurchaser = null;


        if (memberSelect > petStore.getMemberList().size() + petStore.getPremiumMemberList().size() + 1) { // invalid selection
            System.out.println("Invalid Selection");
            checkout(petStore, scnr, cart); // recursive call if valid user input


        } else { // valid selection
            if (memberSelect == (petStore.getMemberList().size() + petStore.getPremiumMemberList().size()) + 1) { // adding a new member
                boolean premium = registerNewMember(petStore, scnr);
                if (premium) {
                    premiumPurchaser = petStore.getPremiumMemberList().get(
                            petStore.getPremiumMemberList().size() - 1
                    );
                } else {
                    purchaser = petStore.getMemberList().get(petStore.getMemberList().size() - 1);
                }
            } else if (memberSelect <= (petStore.getMemberList().size())) {
                purchaser = petStore.getMemberList().get(memberSelect - 1);
            } else { // existing premium member
                premiumPurchaser = petStore.getPremiumMemberList().get(
                        memberSelect - petStore.getMemberList().size() - 1
                );
            }


            // check if premium member and fees are due
            if (purchaser == null && premiumPurchaser != null) {
                if( !premiumPurchaser.isDuesPaid()){
                    System.out.println(
                            "Premium Membership dues unpaid, $5 will be added to purchase total to cover du" +
                                    "es."
                    );
                    total += 5;
                }
                premiumPurchaser.setDuesPaid(true);
                // update amount of purchases for this member
                premiumPurchaser.setAmountSpent(total);
                // done
                System.out.println("Your purchase total was: " + total);
                System.out.println(
                        "Congrats on your purchase " + premiumPurchaser.getName()
                );


            } else {
                // update amount of purchases for this member
                purchaser.setAmountSpent(total);
                System.out.println("Your purchase total was: " + total);
                System.out.println("Congrats on your purchase " + purchaser.getName());


            }
        }
    }


    private static boolean registerNewMember(PetStore petStore, Scanner scnr) {
        // prompt user to select membership type
        System.out.println("Let's get you registered as a new Member!");
        System.out.println( "Would you like to register as a free-tier or premium member?");
        System.out.println("\t1. Free-tier");
        System.out.println("\t2. Premium");


        // user selection
        int choice1 = scnr.nextInt();
        // if user selects a wrong choice 
        if (choice1 > 2 || choice1 < 1) {
            System.out.println("Invalid choice.");
            registerNewMember(petStore, scnr); // try again


        } else {
            // prompt user for name 
            System.out.println("Please enter your name:");
            scnr.nextLine();
            String name = scnr.nextLine();
            System.out.println("Welcome to our membership program! Thank you for registering.");
            if (choice1 == 1) { // regular membership
                petStore.addNewMember(name, false);
                return false;
            } else { // user entered 2 - premimum membership
                petStore.addNewMember(name, true);
                return true;
            }
        }
        return false;
    }


}




