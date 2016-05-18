package core;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tomáš Honner on 7.3.2016.
 */
public class RandomGenerator {

    /**
     * Seed for RandomGenerator constructor
     */
    private long seed;

    /**
     * Type of access (queue, random, generator)
     */
    private AccessType access;

    /**
     * Index in array with random generated numbers
     */
    private int arrayIndex;

    /**
     * Size of array samples from Dataset
     */
    private int arraySize;

    /**
     * Array list of not choosen random numbers
     */
    private ArrayList<Integer> randoms;

    /**
     * Inicialization of class Random
     */
    private Random random;

    /**
     * Default constructor of class RandomGenerator
     * @param access Type of access
     * @param seed Seed for Random class
     * @param size Size of array samples from Dataset
     */
    public RandomGenerator(AccessType access, long seed, int size)
    {
        this.access = access;
        this.seed = seed;
        arraySize = size;
    }

    /**
     * Inicialization and set up of random generator
     */
    public void randomInit()
    {
        if (access == AccessType.QUEUE)
        {
            arrayIndex = 0;
        }
        if (access == AccessType.RANDOM)
        {
            random = new Random(seed);
        }
        if(access == AccessType.RANDOM_NO_REPEATS)
        {
            random = new Random(seed);
            randoms = new ArrayList<>();
            for (int i = 0; i<arraySize; i++)
            {
                randoms.add(i);
            }
        }
    }

    /**
     * Generate random number by settings of random generator
     * @return Generated number
     */
    public int getRandom()
    {
        int result = 0;

        if (access == AccessType.QUEUE)
        {
            if (arrayIndex <= arraySize)
            {
                result = arrayIndex;
                arrayIndex++;
            }
            else
            {
                result = -1;
            }

        }
        if (access == AccessType.RANDOM)
        {
            result = random.nextInt(arraySize);
        }
        if(access == AccessType.RANDOM_NO_REPEATS)
        {
            int temp;
            temp = random.nextInt(randoms.size());
            result = randoms.get(temp);
            randoms.remove(temp);
        }
        return result;
    }

    /**
     * Getter for variable seed
     * @return Variable seed
     */
    public long getSeed() {
        return seed;
    }

    /**
     * Getter for variable access
     * @return Variable access
     */
    public AccessType getAccess() {
        return access;
    }
}
