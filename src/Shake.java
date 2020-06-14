import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Shakespeare ("Shake") Class that will read in some text (may be multiple source files), and then generate a
 * document from the words read in. It can operate in manual or auto mode, number of words to output
 * and the config file name (which has a list of files to read).
 * command line parameters:
 *  - mode (auto or manual)
 *  - number of words to output (1 to some big number)
 *  - path and filename of the config file
 */
public class Shake {
    // Path for files
    public static final String path = new String(System.getProperty("user.dir") +  File.separator);
    private boolean isAuto;
    private int numWordsToOutput;
    private String configFilename;
    private ArrayList<String> filenames = new ArrayList<>();
    private Book book;

    /**
     * Main method
     */
    public static void main(String[] args) {
        Shake shake = new Shake();

        if(!processCommandLineArgs(shake, args))
            return;

        // Create a new book
        shake.book = new Book(shake.filenames.get(0));

        System.out.println("The book (" + shake.book.getSourceFileName() + ") has " + shake.book.getNumberOfWords() + " words in it");

        // generate the new document. Pass it the shake object so it has access to
        // isAuto, the book object(s) and number of words to output
        generateFile(shake);

    }

    /**
     * Method that will generate and output the new text file
     */
    private static void generateFile(Shake shake) {

        // if isAuto is true, call autoGenerateFile(), else manualGenerateFile()
        if(shake.isAuto)
            autoGenerateFile(shake);
        else
            manualGenerateFile(shake);
    }

    /**
     * Method that will generate and output the new text file
     */
    private static void autoGenerateFile(Shake shake) {
        int i = 0;

        // Get the first word and write it out
        String word = shake.book.getFirstWord();
        System.out.print(word + " ");

        // Get the rest of the words
        outputRemainingWords(shake, word);
    }

    /**
     * Method that will generate and output the new text file
     */
    private static void manualGenerateFile(Shake shake) {
        int i = 0;

        //output the word list

        //prompt for user to enter a starting word

        //get input, verify word is in the list, if not ask again

        //if word is in the list,

    }

    /**
     * Method that will take in the first word, and output the rest up to numWordsToOutput
     */
    private static void outputRemainingWords(Shake shake, String word) {
        int i = 1;

        // Get the rest of the words
        do
        {
            word = shake.book.getWordThatFollows(word);
            System.out.print(word + " ");
            i++;
        } while(i < shake.numWordsToOutput);

    }

    /**
     * Method to process the command line arguments
     */
    private static boolean processCommandLineArgs(Shake shake, String[] args) {
        int MODE_POS = 0;
        int NUM_WORDS_POS = 1;
        int CONFIG_FILE_POS = 2;
        int REQUIRED_ARGS = 3;
        boolean result = true;

        // if there aren't three arguments, print out instructions
        if (args.length != REQUIRED_ARGS) {
            printArgsErrorMessage();
            return false;
        }

        // Determine auto or manual mode
        if (args[MODE_POS].equals("auto"))
            shake.isAuto = true;
        else
            shake.isAuto = false;

        System.out.println("> Mode: " + args[MODE_POS]);

        shake.numWordsToOutput = Integer.parseInt(args[NUM_WORDS_POS]);
        System.out.println("> Number of words to output: " + args[NUM_WORDS_POS]);

        shake.configFilename = args[CONFIG_FILE_POS];
        System.out.println("> Config filename used: " + args[CONFIG_FILE_POS]);

        // read configFile and store input file names in filenames array
        result = shake.readConfigFile(shake);

        return result;
    }

    /**
     * Method to read the config file
     */
    private boolean readConfigFile(Shake shake) {
        boolean result = true;
        File configFile;
        String line;

        // make sure configFileName is not empty or null
        if(shake.configFilename.isEmpty() || shake.configFilename == null)
            return false;

        // try to open the config file
        try
        {
            configFile = new File(shake.path+shake.configFilename);

            // Create BufferedReader
            BufferedReader br = new BufferedReader(new FileReader(configFile));

            //while loop to read each line in the configFile, one line at a time add it to the array list
            // if the line read in is null, then you've read all lines
            while((line = br.readLine()) != null)
            {
                shake.filenames.add(line);
            }

        }
        catch (IOException e)
        {
            // print stacktrace to the console and return false
            e.printStackTrace();
            result = false;
        }

        // if we get here, everything went well
        return result;
    }

    /**
     * Print the command line arguments error message
     */
    private static void printArgsErrorMessage() {

        System.out.println("The Shake class requires three command line arguments:");
        System.out.println("1. 'auto' or 'manual'. If manual is specified,  you will be prompted to select the first word");
        System.out.println("2. number of words to output (an integer)");
        System.out.println("3. filename of the config file (which contains a list of files to read)");
        System.out.println("Directory path for files is: <userDirectory>/IdeaProjects/Shake/input");
    }
}