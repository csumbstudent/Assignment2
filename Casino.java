/*Assignment 2, Casino

  Team:
  Christopher Rendall
  Caroline Lancaster
  Daniel Kushner

  Description:
  This Casino application promps the user for a bet, randomly generates strings to represent
  slot machine reels, and checks the multiplier associated with the outcome. It then displays
  whether the user won or lost. If the user enters a bet of 0, then the game ends and displays
  each of the user's winnings, as well as the total amount of money that they won.

 */
import java.util.Scanner; //For reading keyboard input.
import java.lang.Math; //For random.

//The Casino class is the main class for this application.
public class Casino {
    //Program entry point main():
    public static void main(String[] args){
        int bet = 0; //Initialize bet.
        //Initialize the TripleString object.
        TripleString thePull = new TripleString();
        //Initialize a variable to  hold the winngings from each iteration.
        int winnings = 0;

        //This is the main game loop. It runs while true, and breaks if the user enters a bet of 0.
        while(true) {
            bet = getBet(); //Read a valid bet in from the user.
            //End the loop if the user enters 0.
            if(bet == 0)
                break;
            thePull = pull(); //Get a random pull.
            //Calculate the winnings based on the pull.
            winnings = bet * getPayMultiplier(thePull);
            TripleString.saveWinnings(winnings); //Save the winnings in the static member of TripleString
            display(thePull, winnings); //Display whether the user won or lost this round.
        }
        //Print some information for the user once they decide to quit playing.
        System.out.println("Thank you for playing the casino!");
        thePull.displayWinnings();
    }

    /*  int getBet()
        In: Nothing
        Out: A valid integer bet value input by the user.
        Description: A static function to retrieve a bet from the user. This uses keyborad input to get the bet.
        It then checks to make sure the bet is valid, and will not return an integer until a valid bet is entered.
     */
    static int getBet(){
        Scanner keyboardIn = new Scanner(System.in);
        int bet = -1; //Set bet to any non-zero value.
        do{
            //This loop will run until a valid bet is entered.
            //Prompt the user for a bet.
            System.out.println("Enter a bet between 1 and 100. Enter 0 to quit.\nBet: ");
            if(keyboardIn.hasNextInt()) //Wait for the user to enter a bet, and then check if the user has entered an integer.
                bet = keyboardIn.nextInt(); //If they have entered an integer, then set bet to that integer.
            else{
                //If the user did not enter an integer, then clear the input buffer.
                keyboardIn.next(); //Clear the input buffer.
                bet = -1; //Set bet to an integer value outside of the allowed range.
                continue; //Start at the beginning of this loop.
            }
            //If the loop makes it this far, the user has entered an integer. Check to see if it is within the allowable bet range.
            if (bet < 0 || bet > 100)
                System.out.println("Your bet is outside of the allowable range.");
        } while(bet < 0 || bet > 100); //Continue until a valid bet is entered.
        return bet; //Return the valid bet to the caller.
    }

    /*  void display(TripleString, int)
        In:
        1. A TripleString object that contains information about a pull
        2. The amount won by the user on the pull
        Out: Nothing
        Description: A static function to display the user's pull results and the amount of money that they won (if they won).
    */
    public static void display(TripleString thePull, int winnings){
        System.out.println("You pulled....");
        System.out.println(thePull.toString());
        if(winnings > 0)
            System.out.println("You won $" + winnings + "!!!\n");
        else
            System.out.println("You lost... Better luck next time...\n");
    }
    /*  TripleString pull()
        In: Nothing
        Out: A TripleString object containing three of the strings that can appear on a reel.
        Description: This function creates a new TripleString object and sets its string members
        randomly.
     */
    public static TripleString pull(){
        TripleString triple = new TripleString();
        triple.setString1(randString());
        triple.setString2(randString());
        triple.setString3(randString());
        return triple;
    }
    private static String randString(){
        double rand = Math.round(Math.random()*23);
        if(rand >= 0 && rand < 12)
            return "BAR";
        else if(rand >= 12 && rand < 18)
            return "cherries";
        else if(rand >= 18 && rand < 21)
            return "space";
        else
            return "7";

    }
    private static int getPayMultiplier(TripleString thePull){
        int multiplier = 0;
        if(thePull.getString1().equals("cherries")){
            multiplier = 5;
            if(thePull.getString2().equals("cherries")) {
                multiplier *= 3;
                if (thePull.getString3().equals("cherries"))
                    multiplier *= 2;
            }
        }
        else if(thePull.getString1().equals("BAR") && thePull.getString2().equals("BAR") && thePull.getString3().equals("BAR"))
            multiplier = 50;
        else if(thePull.getString1().equals("7") && thePull.getString2().equals("7") && thePull.getString3().equals("7"))
            multiplier = 100;
        return multiplier;
    }
}

class TripleString{
    private String string1, string2, string3;
    public static final short MAX_LEN = 20, MAX_PULLS=40;
    private static int numPulls = 0;
    private static int[] pullWinnings = new int[MAX_PULLS];
    public TripleString(){
        this.string1 = this.string2 = this.string3 = "";
    }
    private boolean validString(String str){
        if(str != null && str.length() <= MAX_LEN)
            return true;
        else
            return false;
    }
    public String toString(){
        return this.string1 + "    " + this.string2 + "    " + this.string3;
    }
    public static void saveWinnings(int winnings){
        TripleString.pullWinnings[TripleString.numPulls] = winnings;
        TripleString.numPulls++;
    }
    public boolean setString1(String str){
        if(!this.validString(str))
            return false;
        this.string1 = str;
        return true;
    }
    public boolean setString2(String str){
        if(!this.validString(str))
            return false;
        this.string2 = str;
        return true;
    }
    public boolean setString3(String str){
        if(!this.validString(str))
            return false;
        this.string3 = str;
        return true;
    }
    public String getString1(){
        return this.string1;
    }
    public String getString2(){
        return this.string2;
    }
    public String getString3(){
        return this.string3;
    }
    public void displayWinnings(){
        System.out.println("Your inidividual winnings were:");
        int totalWinnings = 0;
        for(int i = 0; i < TripleString.numPulls; i++) {
            System.out.print(TripleString.pullWinnings[i] + " ");
            totalWinnings += TripleString.pullWinnings[i];
        }
        System.out.println("\nYour total winnings were: $" + totalWinnings);
    }
}