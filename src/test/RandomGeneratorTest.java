package test;

import core.AccessType;
import core.RandomGenerator;

/**
 * Created by Tomáš Honner on 10.3.2016.
 */
public class RandomGeneratorTest {

    public static void main(String[] args)
    {
        RandomGenerator rand = new RandomGenerator(AccessType.RANDOM_NO_REPEATS, 20, 20);
        rand.randomInit();
        for (int i = 0; i<20; i++)
        {
            System.out.println(rand.getRandom());
        }
    }

}
