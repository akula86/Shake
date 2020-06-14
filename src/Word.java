import java.util.ArrayList;

/**
 * Class to encapsulate a string, and maintains a list of Words that follow it, and the number of times it has
 * occurred in the source document.
 * The constructor takes a String object.
 */
public class Word {
    private String identityWord;
    private ArrayList<Word> wordsThatFollow = new ArrayList();
    int numOccurrences = 0;

    /**
     * Method to add words that follow this particular word into wordsThatFollow ArrayList
     */
    public void addWordThatFollows(Word w) {
        // Check is w identity is not null or empty

        // Check to see if the word is already in the array list. If it is not, add it.
        // If it is in the list, just increment the count
        if (wordsThatFollow.contains(w))
            return;
        else
            wordsThatFollow.add(w);
    }

    /**
     * Constructor.
     * Sample usage: Word tmp = new Word("Kona Bear");
     */
    public Word(String str) {
        // store the string, and force it to lower case
        this.identityWord = str.toLowerCase();
        numOccurrences++;
    }

    /**
     * method to return String value of the Word object
     */
    public String toString() {
        return this.identityWord;
    }

    /**
     * method to increment the counter
     */
    public void incrementCounter() {
        this.numOccurrences++;
    }

    /**
     * method to get the counter
     */
    public int getNumOccurrences() {
        return numOccurrences;
    }

    /**
     * method to get the number of words that follow
     */
    public int getNumWordsThatFollow() {
        return wordsThatFollow.size();
    }

    /**
     * method to get the word (String version) at the specified position from wordsThatFollow
     */
    public String getWordThatFollows(int index) {
        return wordsThatFollow.get(index).toString();
    }

    /**
     * Override the default equals method so the list comparators (like List.contains) will work.
     */
    public boolean equals(Object o) {
        boolean result;

        // Cast "o" as a Word object
        Word w = (Word) o;

        //compare strings
        if (this.identityWord.equals(o.toString()))
            return true;
        else
            return false;
    }

}
