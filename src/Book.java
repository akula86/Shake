import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Iterator;

/**
 * Class to encapsulate a book (some chunk of text). Right now it maintains an ArrayList of Words.
 * The constructor takes a String object.
 * @see Word
 */
public class Book {
    // Path for files
    public static final String path = new String(System.getProperty("user.dir") +  File.separator);
    private ArrayList<Word> words = new ArrayList();
    private String sourceFile;
    private String sampleData = "Four score and seven years ago our fathers brought forth on this continent, a new nation, conceived in Liberty, and dedicated to the proposition that all men are created equal.\n" +
            "\n" + "Now we are engaged in a great civil war, testing whether that nation, or any nation so conceived and so dedicated, can long endure. We are met on a great battle-field of that war. We have come to dedicate a portion of that field, as a final resting place for those who here gave their lives that that nation might live. It is altogether fitting and proper that we should do this.\n" +
            "\n" + "But, in a larger sense, we can not dedicate, we can not consecrate, we can not hallow this ground. The brave men, living and dead, who struggled here, have consecrated it, far above our poor power to add or detract. The world will little note, nor long remember what we say here, but it can never forget what they did here. It is for us the living, rather, to be dedicated here to the unfinished work which they who fought here have thus far so nobly advanced. It is rather for us to be here dedicated to the great task remaining before us, that from these honored dead we take increased devotion to that cause for which they gave the last full measure of devotion, that we here highly resolve that these dead shall not have died in vain, that this nation, under God, shall have a new birth of freedom, and that government of the people, by the people, for the people, shall not perish from the earth.";


    /**
     * Constructor that takes a filename to read in data. if filename is invalid, sample data will be used
     * @param filename
     */
    public Book(String filename) {
        boolean result;

        this.sourceFile = filename;

        // if filename is null or empty, load the sampleData
        if (filename.isEmpty() || filename == null) {
            loadSampleData();
        }
        else {
            // open file, read
            result = loadDataFromFile(filename);
            if(!result)
                System.out.println("unable to read from file: "+ filename);
        }
    }

    /**
     * Method to read in the text from a file and store it in the ArrayList.
     */
    private boolean loadDataFromFile(String filename) {
        boolean result = true;
        File dataFile;
        String line;
        String sMain, sNext;
        Word wMain, wNext;

        // make sure filename is not empty or null
        if(filename.isEmpty() || filename == null)
            return false;

        // try to open the file
        try
        {
            dataFile = new File(this.path+filename);

            // Create BufferedReader
            BufferedReader br = new BufferedReader(new FileReader(dataFile));

            //while loop to read each line in the configFile, one line at a time
            // split the string, add each word to the words ArrayList
            // if the line read in is null, then you've read all lines
            while((line = br.readLine()) != null)
            {
                // split line into an array, with delimters
                String[] arr = line.split("\\W+");

                // get first word (before entering the loop)
                int i = 0;
                sMain = arr[i];

                // Add each word in the String array to the words ArrayList
                do {

                    // Get next word, unless we're on the last element. If so, set it to empty
                    if (i + 1 == arr.length)
                        sNext = "";
                    else
                        sNext = arr[i+1];

                    // Create Word objects
                    wMain = new Word (sMain);
                    wNext = new Word (sNext);

                    // add the word to the list
                    addWord(wMain, wNext);

                    // Set sMain to sNext to get ready for next loop
                    sMain = sNext;

                    i++;
                } while (i < arr.length);

            }

        }
        catch (IOException e)
        {
            // print stacktrace to the console and return false
            e.printStackTrace();
            result = false;
        }

        //just for fun
        //printBookWithCounts();

        // if we get here, everything went well
        return result;

    }

    /**
     * Method to read in the text and store it in the ArrayList. In the future, a constructor will be created
     * to take a file name or file object and it will call initialize.
     */
    private void loadSampleData() {
        String sMain, sNext;
        Word wMain, wNext;

        // Let user know that sample data is being used
        System.out.println("==> input file invalid, using sample data");

        // Split sampleData into an array of strings using space as a delimiter (each will be a word)
        String[] arr = sampleData.split("\\W+");

        // get first word (before entering the loop)
        int i = 0;
        sMain = arr[i];

        // Add each word in the sampleData String to the ArrayList
        do {

            // Get next word, unless we're on the last element. If so, set it to empty
            if (i + 1 == arr.length)
                sNext = "";
            else
                sNext = arr[i+1];

            // Create Word objects
            wMain = new Word (sMain);
            wNext = new Word (sNext);

            // add the word to the list
            addWord(wMain, wNext);

            // Set sMain to sNext to get ready for next loop
            sMain = sNext;

            i++;
        } while (i < arr.length);

        //just for fun
        printBookWithCounts();
    }

    /**
     * Method to add a word to the ArrayList. Checks if it is in the arraylist already. If so, it increments
     * the counter and adds the following word. If it doesn't exist, it adds it and adds the following word.
     */
    private void addWord(Word wMain, Word wNext) {
        boolean wordExists;

        // Check to see if the word is already in the array list.
        wordExists = words.contains(wMain);

        // If it is in the list, increment the count, and give it wNext to store
        if (wordExists)
        {
            // get the object and increment it's counter
            int index = words.indexOf(wMain);
            Word w = words.get(index);
            w.incrementCounter();

            // if wNext is not empty give w wNext so it can store it
            if(wNext.toString() != "")
                w.addWordThatFollows(wNext);
        }
        else
        {
            // if wNext is not empty give wMain wNext so it can store it
            if(wNext.toString() != "")
                wMain.addWordThatFollows(wNext);

            // add wMain
            words.add(wMain);
        }
    }

    /**
     * Method to get the first word to start a new document. Logic to select the first word is to pick one out of
     * the ArrayList at random.
     * @return  String for the first word in a new document
     */
    public String getFirstWord() {
        int randomPosition = ThreadLocalRandom.current().nextInt(0, words.size());
        return (words.get(randomPosition).toString());

    }

    /**
     * Method to get a random word from the list of works that follow the one passed in.
     * @param word to look up
     * @return a String that follows the word passed in
     */
    public String getWordThatFollows(String word) {
        int index, randomPosition=0;
        Word wMain = new Word(word);
        Word wNext;

        // Get the word object from the words ArrayList
        index = words.indexOf(wMain);
        wMain = words.get(index);

        // if a word doesn't have any followers, return a random word from the book
        if(wMain.getNumWordsThatFollow() == 0)
        {
            randomPosition = ThreadLocalRandom.current().nextInt(0, this.words.size());
            return this.words.get(randomPosition).toString();
        }

        // get a random position within the count of number of words in the wordsThatFollow ArrayList
        randomPosition = ThreadLocalRandom.current().nextInt(0, wMain.getNumWordsThatFollow());


        // return one of the following words
        return (wMain.getWordThatFollows(randomPosition));
    }

    /**
     * Fun method to print all words in the book with the number of occurrences
     */
    public void printBookWithCounts() {
        // for fun - output all words and their count
        Iterator i = words.iterator();
        System.out.println("The words in the book are:");
        while (i.hasNext()) {
            Word ww = (Word) i.next();
            System.out.println(ww.toString() + " " + ww.getNumOccurrences());

        }
    }

    /**
     * Get the number of words in the Book
     */
    public int getNumberOfWords() {
        return words.size();
    }

    /**
     * Get the name of the source file
     */
    public String getSourceFileName() {
        return this.sourceFile;
    }
}
