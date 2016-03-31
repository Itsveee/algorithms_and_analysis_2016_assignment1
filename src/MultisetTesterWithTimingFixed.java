import java.io.*;


/**
 * Framework to test the multiset implementations.
 * 
 * @author jkcchan'
 *
 * Modified by Michael Vescovo
 */
public class MultisetTesterWithTimingFixed
{
	/** Name of class, used in error messages. */
	protected static final String progName = "MultisetTester";
	
	/** Standard outstream. */
	protected static final PrintStream outStream = System.out;

	protected static Multiset<String> multiset = null;
	/**
	 * Print help/usage message.
	 */
	public static void usage(String progName) {
		System.err.println(progName + ": <implementation> [fileName to output search results to]");
		System.err.println("<implementation> = <linkedlist | sortedlinkedlist | bst| hash | baltree>");
		System.exit(1);
	} // end of usage


	/**
	 * Process the operation commands coming from inReader, and updates the multiset according to the operations.
	 * 
	 * @param inReader Input reader where the operation commands are coming from.
	 * @param searchOutWriter Where to output the results of search.
	 * @param multiset The multiset which the operations are executed on.
	 * 
	 * @throws IOException If there is an exception to do with I/O.
	 */
	private static void processOperations(BufferedReader inReader, PrintWriter searchOutWriter, Multiset<String> multiset) throws IOException {
		String line;
		int lineNum = 1;
		boolean bQuit = false;

        long startTime = 0;

        // continue reading in commands until we either receive the quit signal or there are no more input commands
		while (!bQuit && (line = inReader.readLine()) != null) {
            String[] tokens = line.split(" ");

			// check if there is at least an operation command
			if (tokens.length < 1) {
                System.out.println("Tokens.length < 1");
                System.err.println(lineNum + ": not enough tokens.");
				lineNum++;
				continue;
			}

			String command = tokens[0];
			// determine which operation to execute
            String s = command.toUpperCase();


            if (s.equals("A")) {
                if (tokens.length == 2) {
                    multiset.add(tokens[1]);
                } else {
                    System.err.println(lineNum + ": not enough tokens.");
                }

                // search
            } else if (s.equals("S")) {
                if (tokens.length == 2) {
                    int foundNumber = multiset.search(tokens[1]);
                    searchOutWriter.println(tokens[1] + " " + foundNumber);
                } else {
                    // we print -1 to indicate error for automated testing
                    searchOutWriter.println(-1);
                    System.err.println(lineNum + ": not enough tokens.");
                }

                // remove one instance
            } else if (s.equals("RO")) {
                if (tokens.length == 2) {
                    multiset.removeOne(tokens[1]);
                } else {
                    System.err.println(lineNum + ": not enough tokens.");
                }

                // remove all instances
            } else if (s.equals("RA")) {
                if (tokens.length == 2) {
                    multiset.removeAll(tokens[1]);
                } else {
                    System.err.println(lineNum + ": not enough tokens.");
                }

                // print
            } else if (s.equals("P")) {
                multiset.print(outStream);

                // quit
            } else if (s.equals("Q")) {
                bQuit = true;

            } else if (s.equals("T")) {
                startTime = System.nanoTime();
            } else {
                System.err.println(lineNum + ": Unknown command.");
            }

			lineNum++;
		}

        long endTime = System.nanoTime();
        System.out.println("time taken = " + ((double)(endTime - startTime)) / Math.pow(10, 9) + " sec");
    } // end of processOperations()

	public static void createMultiset(String implementationType)
	{
		if (implementationType.equals("linkedlist")) {
            multiset = new LinkedListMultiset<String>();

        } else if (implementationType.equals("sortedlinkedlist")) {
            multiset = new SortedLinkedListMultiset<String>();

        } else if (implementationType.equals("bst")) {
            multiset = new BstMultiset<String>();

        } else if (implementationType.equals("hash")) {
            multiset = new HashMultiset<String>();

        } else if (implementationType.equals("baltree")) {
            multiset = new BalTreeMultiset<String>();

        } else {
            System.err.println("Unknown implmementation type.");
            usage(progName);
        }
	}

	/**
	 * Main method.  Determines which implementation to test.
	 */
	public static void main(String[] args) {

		// check number of command line arguments
		if (args.length > 2 || args.length < 1) {
			System.err.println("Incorrect number of arguments.");
			usage(progName);
		}

		String implementationType = args[0];
		
		String searchOutFilename = null;
		if (args.length == 2) {
			searchOutFilename = args[1];
		}


		// construct in and output streams/writers/readers, then process each operation.
		try {
			createMultiset(implementationType);
			BufferedReader reader = new BufferedReader(new FileReader("growing-10.txt"));
			PrintWriter searchOutWriter = new PrintWriter(System.out, true);
			if (searchOutFilename != null) {
				searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
			}
            System.out.print("growing-10: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("not-growing-10.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("not-growing-10: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("shrinking-10.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("shrinking-10: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("searching-10.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("searching-10: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("growing-20.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("growing-20: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("not-growing-20.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("not-growing-20: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("shrinking-20.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("shrinking-20: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("searching-20.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("searching-20: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("growing-40.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("growing-40: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("not-growing-40.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("not-growing-40: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("shrinking-40.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("shrinking-40: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // construct in and output streams/writers/readers, then process each operation.
        try {
        	createMultiset(implementationType);
            BufferedReader reader = new BufferedReader(new FileReader("searching-40.txt"));
            PrintWriter searchOutWriter = new PrintWriter(System.out, true);

            if (searchOutFilename != null) {
                searchOutWriter = new PrintWriter(new FileWriter(searchOutFilename), true);
            }
            System.out.print("searching-40: ");
            // process the operations
            processOperations(reader, searchOutWriter, multiset);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

	} // end of main()
} // end of class MultisetTester
