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

//The Casino class is the main class for this application. It contains functions
//for getting the user's bet information, pull information, etc.
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
            //Save the winnings in the static member of TripleString
            //If saveWinnings returns true, then the winnings were saved,
            //otherwise, the array is full.
            if(TripleString.saveWinnings(winnings) == false) {
                System.out.println("The maximum number of pulls has been reached.");
                break;
            }
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
    /*  String randString()
        In: Nothing
        Out: A String object containing one of four possible strings.
        Description: This function generates a random number to produce one of four possible
        strings based on the probability specified in the instructions for this assignment.
     */
    private static String randString(){
        double rand = Math.round(Math.random()*23); //0-23 provides 24 possible values.
        //24 is evenly divisible by 1/2, 1/4, and 1/8, which are the probabilities specified
        //in the assignment instructions.
        if(rand >= 0 && rand < 12) //50% chance for BAR
            return "BAR";
        else if(rand >= 12 && rand < 18) //25% chance for cherries
            return "cherries";
        else if(rand >= 18 && rand < 21) //12.5% chance for space
            return "space";
        else //12.5% chance for 7
            return "7";

    }
    /*  int getPayMultiplier(TripleString)
        In: A TripleString object containing information from the user's pull.
        Out: An integer representing the multiplier associated with the input pull.
        Description: This functions calculates the pay multiplier according to the instructions
        of this assignment.
     */
    private static int getPayMultiplier(TripleString thePull){
        int multiplier = 0;
        if(thePull.getString1().equals("cherries")){ //If string1 is cherries, set the multiplier to 5.
            multiplier = 5;
            if(thePull.getString2().equals("cherries")) { //If string 2 is cherries, set it to 15.
                multiplier *= 3;
                if (thePull.getString3().equals("cherries"))
                    multiplier *= 2; //If string 3 is cherries, set it to 30.
            }
        }
        //BAR BAR BAR gets 50.
        else if(thePull.getString1().equals("BAR") && thePull.getString2().equals("BAR") && thePull.getString3().equals("BAR"))
            multiplier = 50;
            //7 7 7 gets 100.
        else if(thePull.getString1().equals("7") && thePull.getString2().equals("7") && thePull.getString3().equals("7"))
            multiplier = 100;
        return multiplier;
    }
}
//TripleString holds three string objects, each representing one of the reels on a slot machine.
//It also contains static members that track how many times the machine has been pulled, and
//each of the user's winnings.
class TripleString{
    private String string1, string2, string3; //Three reels.
    public static final short MAX_LEN = 20, MAX_PULLS=40; //Constants
    private static int numPulls = 0; //The current number of times the user has pulled.
    private static int[] pullWinnings = new int[MAX_PULLS]; //Each of the winnings associated with each pull.
    /*  TripleString()
        In: Nothing
        Out: Nothing
        Description: A Constructor which initializes each of the object's
        strings to an empty string.
     */
    public TripleString(){
        this.string1 = this.string2 = this.string3 = "";
    }
    /*  boolean validString(String)
        In: A String to be tested for validity.
        Out: True if the string is valid, false if otherwise.
        Description: This function tests to see if the String input is a valid string by checking
        The length of the string against the MAX_LEN constant. If the string is too long, this
        function will return false.
     */
    private boolean validString(String str){
        if(str != null && str.length() <= MAX_LEN)
            return true;
        else
            return false;
    }
    /*  String toString()
        In: Nothing
        Out: A concatenated string containing each of the object's three strings separated by spaces.
        Description:  This function returns a concatenated string containing each of the object's three
        strings separated by spaces.
     */
    public String toString(){
        return this.string1 + "    " + this.string2 + "    " + this.string3;
    }
    /*  void saveWinnings(int)
        In: An int containing the amount of money the user won for a single pull.
        Out: A boolean value, true if the winnings were saved, false if the array is full.
        Description:  This function saves the winnings gained from the last pull to the static
        array. It also increases the number of pulls stored by 1.
     */
    public static boolean saveWinnings(int winnings){
        if(TripleString.numPulls != TripleString.MAX_PULLS) {
            TripleString.pullWinnings[TripleString.numPulls] = winnings;
            TripleString.numPulls++;
            return true;
        }
        return false;
    }
    /*  boolean setString1(String)
        In: A String object for string1 to be set to.
        Out: A boolean value, true if the string is valid, false if otherwise.
        Description:  This function checks to see if the input string is valid, and if so
        sets string1 to the input string. If the input string is not valid, it returns false.
     */
    public boolean setString1(String str){
        if(!this.validString(str))
            return false;
        this.string1 = str;
        return true;
    }
    /*  boolean setString2(String)
        In: A String object for string1 to be set to.
        Out: A boolean value, true if the string is valid, false if otherwise.
        Description:  This function checks to see if the input string is valid, and if so
        sets string2 to the input string. If the input string is not valid, it returns false.
     */
    public boolean setString2(String str){
        if(!this.validString(str))
            return false;
        this.string2 = str;
        return true;
    }
    /*  boolean setString3(String)
        In: A String object for string1 to be set to.
        Out: A boolean value, true if the string is valid, false if otherwise.
        Description:  This function checks to see if the input string is valid, and if so
        sets string3 to the input string. If the input string is not valid, it returns false.
     */
    public boolean setString3(String str){
        if(!this.validString(str))
            return false;
        this.string3 = str;
        return true;
    }
    /*  String getString1()
        In: Nothing
        Out: The value of the object's string1 member.
        Description:  A basic accessor function.
     */
    public String getString1(){
        return this.string1;
    }
    /*  String getString2()
        In: Nothing
        Out: The value of the object's string2 member.
        Description:  A basic accessor function.
     */
    public String getString2(){
        return this.string2;
    }
    /*  String getString3()
        In: Nothing
        Out: The value of the object's string3 member.
        Description:  A basic accessor function.
     */
    public String getString3(){
        return this.string3;
    }
    /*  void displayWinnings()
        In: Nothing
        Out: Nothing
        Description:  This function displays the information held in the class' static array.
        It also calculates the user's total winnings.
     */
    public void displayWinnings(){
        System.out.println("Your inidividual winnings were:");
        int totalWinnings = 0;
        for(int i = 0; i < TripleString.numPulls; i++) { //Iterate through each of the array values.
            System.out.print(TripleString.pullWinnings[i] + " ");
            totalWinnings += TripleString.pullWinnings[i]; //Add the current value to totalWinnings
        }
        System.out.println("\nYour total winnings were: $" + totalWinnings);
    }
}