import java.util.Scanner;
import java.lang.Math;
public class Casino {
    public static void main(String[] args){
        int bet = 1;
        TripleString thePull;
        int winnings = 0;
        while(true) {
            bet = getBet();
            if(bet == 0)
                break;
            thePull = pull();
            winnings = bet * getPayMultiplier(thePull);
            display(thePull, winnings);
        }
    }
    static int getBet(){
        Scanner keyboardIn = new Scanner(System.in);
        int bet = 0;
        do{
            System.out.println("Enter a bet between 1 and 100. Enter 0 to quit.\nBet: ");
            bet = keyboardIn.nextInt();
            if(bet < 0 || bet > 100)
                System.out.println("Your bet is outside of the allowable range.");
        } while(bet < 0 || bet > 100);
        return bet;
    }
    public static void display(TripleString thePull, int winnings){
        System.out.println("You pulled....");
        System.out.println(thePull.toString());
        if(winnings > 0)
            System.out.println("You won $" + winnings + "!!!");
        else
            System.out.println("You lost... Better luck next time...");
    }
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
    private static int numPulls;
    private static int[] pullWinnings;
    public TripleString(){
        this.string1 = this.string2 = this.string3 = "";
        this.pullWinnings = new int[MAX_PULLS];
        this.numPulls = 0;
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
    public void saveWinnings(int winnings){
        this.pullWinnings[this.numPulls] = winnings;
        this.numPulls++;
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
        for(int i = 0; i < numPulls; i++)
            System.out.println("Pull " + (i + 1) + " : " + this.pullWinnings[i]);
    }
}