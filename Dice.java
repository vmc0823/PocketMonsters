package utilities;

import java.util.Random;

/**
 * The Dice class is a utility used for generating random numbers.
 */
public class Dice {

    /**
     * Returns a random value between min and max. Also does a small check to ensure
     * that min and max are used properly. :)
     * @param min the minimum possible value to return
     * @param max the maximum possible value to return
     * @return a random int between min and max (inclusive)
     */
    public static int roll(int min, int max){
        Random random = new Random();
        if(min > max){
            int temp = min;
            min = max;
            max = temp;
        }

        return (random.nextInt(max-Math.abs(min))+1) + min;
    }

    public static int roll(int max){
        return Dice.roll(max,1);
    }
}
