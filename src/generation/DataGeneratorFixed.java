package generation;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Generates collection of integers from sampling a uniform distribution.
 *
 * @author jkcchan
 *
 * Modified by Michael Vescovo and Loy Rao
 */
public class DataGeneratorFixed {

    /** Program name. */
    protected static final String progName = "DataGenerator";

    /** Start of integer range to generate values from. */
    protected int mStartOfRange;
    /** End of integer range to generate values from. */
    protected int mEndOfRange;
    /** Random generator to use. */
    Random mRandGen;
    private ArrayList<String> dictList = new ArrayList<String>();

    /**
     * Constructor.
     *
     * @param startOfRange Start of integer range to generate values.
     * @param endOfRange End of integer range to generate values.
     * @throws IllegalArgumentException If range of integers is inappropriate
     */
    public DataGeneratorFixed(int startOfRange, int endOfRange) throws IllegalArgumentException {
        if (startOfRange < 0 || endOfRange < 0 || startOfRange > endOfRange) {
            throw new IllegalArgumentException("startOfRange or endOfRange is invalid.");
        }
        mStartOfRange = startOfRange;
        mEndOfRange = endOfRange;
        // use current time as seed
        mRandGen = new Random(System.currentTimeMillis());

        try {
            String word;
            BufferedReader dictFile = new BufferedReader(new FileReader("words.txt"));
            while ((word = dictFile.readLine()) != null) {
                dictList.add(word);
            }
            Collections.shuffle(dictList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    } // end of DataGenerator()


    /**
     * Generate one sample, using sampling with replacement.
     */
    public String sampleWithReplacement() {
        int random = mRandGen.nextInt(mEndOfRange - mStartOfRange + 1) + mStartOfRange;
        return dictList.get(random);
    } // end of sampleWithReplacement()


    /**
     * Generate 'sampleSize' number of samples, using sampling with replacement.
     *
     * @param sampleSize Number of samples to generate.
     */
    public String[] sampleWithReplacement(int sampleSize) {
        String[] samples = new String[sampleSize];

        for (int i = 0; i < sampleSize; i++) {
            samples[i] = sampleWithReplacement();
        }

        return samples;
    } // end of sampleWithReplacement()


    /**
     * Sample without replacement, using "Algorithm R" by Jeffrey Vitter, in paper "Random sampling without a reservoir".
     * This algorithm has O(size of range) time complexity.
     *
     * @param sampleSize Number of samples to generate.
     * @throws IllegalArgumentException When sampleSize is greater than the valid integer range.
     */
    public String[] sampleWithOutReplacement(int sampleSize) throws IllegalArgumentException {
        int populationSize = mEndOfRange - mStartOfRange + 1;

        if (sampleSize > populationSize) {
            throw new IllegalArgumentException("SampleSize cannot be greater than populationSize for sampling without replacement.");
        }

        String[] samples = new String[sampleSize];
        // fill it with initial values in the range
        for (int i = 0; i < sampleSize; i++) {
            samples[i] = dictList.get(i + mStartOfRange);
        }

        // replace
        for (int j = sampleSize; j < populationSize; j++) {
            int t = mRandGen.nextInt(j+1);
            if (t < sampleSize) {
                samples[t] = dictList.get(j + mStartOfRange);
            }
            System.out.println(j + " " + t);
        }
        System.out.println();

        return samples;
    } // end of sampleWithOutReplacement()


    /**
     * Error message.
     */
    public static void usage() {
        System.err.println(progName + ": <start of range to sample from> <end of range to sample from> <number of values to sample> <type of sampling>");
        System.exit(1);
    } // end of usage()


    /**
     * Main method.
     */
    public static void main(String[] args) {

        // check correct number of command line arguments
        if (args.length != 4) {
            usage();
        }

        try {
            // integer range
            int startOfRange = Integer.parseInt(args[0]);
            int endOfRange = Integer.parseInt(args[1]);

            // number of values to sample
            int sampleSize = Integer.parseInt(args[2]);

            // type of sampling
            String samplingType = args[3];
            DataGeneratorFixed gen = new DataGeneratorFixed(startOfRange, endOfRange);
            String[] samples1 = null;
            String[] samples2 = null;
            ArrayList<String> currentList = new ArrayList<String>();

            if (samplingType.equals("with")) {
                samples1 = gen.sampleWithReplacement(sampleSize);
                samples2 = gen.sampleWithReplacement(sampleSize);

                // sampling without replacement
            } else if (samplingType.equals("without")) {
                samples1 = gen.sampleWithOutReplacement(sampleSize);
                samples2 = gen.sampleWithOutReplacement(sampleSize);

            } else {
                System.err.println(samplingType + " is an unknown sampling type.");
                usage();
            }
            Random mRandGen = new Random(System.currentTimeMillis());

            // output samples to file
            if ((samples1 != null) && (samples2 != null)) {
                PrintWriter printWriter = new PrintWriter(new FileWriter("growing-80.txt"), true);
                // Issue command to start the timer
                printWriter.println("T");
                for (int i = 0; i < samples2.length; i++) {
                    printWriter.println("A " + samples2[i]);
                }
                printWriter.println("Q");
            }
            if ((samples1 != null) && (samples2 != null)) {
                PrintWriter printWriter = new PrintWriter(new FileWriter("not-growing-80.txt"), true);
                currentList.addAll(Arrays.asList(samples1));
                currentList.addAll(Arrays.asList(samples2));
                for (int i = 0; i < samples1.length; i++) {
                    printWriter.println("A " + samples1[i]);
                }
                // Issue command to start the timer
                printWriter.println("T");
                // Add a new word, then randomly remove a word from the updated list
                for (int i = 0; i < samples2.length; i++) {
                    int random = mRandGen.nextInt(sampleSize);
                    printWriter.println("A " + samples2[i]);
                    currentList.add(samples2[i]);
                    String removedWord = currentList.get(random);
                    printWriter.println("RO " + removedWord);
                    currentList.remove(removedWord);
                }
                printWriter.println("Q");
                // Reset the current list back to samples1 so the next data file can use it
                currentList.clear();
            }

            if ((samples1 != null) && (samples2 != null)) {
                PrintWriter printWriter = new PrintWriter(new FileWriter("shrinking-80.txt"), true);
                currentList.addAll(Arrays.asList(samples2));
                for (int i = 0; i < currentList.size(); i++) {
                    printWriter.println("A " + currentList.get(i));
                }
                // Issue command to start the timer
                printWriter.println("T");
                Collections.shuffle(currentList);
                for (int i = 0; i < currentList.size(); i++) {
                    printWriter.println("RO " + currentList.get(i));
                }
                printWriter.println("Q");
                // Reset the current list back to samples1 so the next data file can use it
                currentList.clear();
            }

            if (samples1 != null) {
                PrintWriter printWriter = new PrintWriter(new FileWriter("searching-80.txt"), true);
                for (int i = 0; i < samples1.length; i++) {
                    printWriter.println("A " + samples1[i]);
                }
                // Issue command to start the timer
                printWriter.println("T");
                for (int i = 0; i < samples1.length; i++) {
                    int random = mRandGen.nextInt(sampleSize);
                    printWriter.println("S " + samples1[random]);
                }
                printWriter.println("Q");
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            usage();
        }

    } // end of main()
} // end of class DataGenerator
