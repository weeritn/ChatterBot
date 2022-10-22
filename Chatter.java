import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Chatter {

	// Put instance variables here
	private String greeting;
	private String goodbye;
	private HashMap<String, String> responses;

	/**
	 * The constructor builds a Chatter object, with set greeting, goodbye, and
	 * potential response phrases.
	 * 
	 * @param greeting      a fixed String greeting
	 * @param goodbye       a fixed String goodbye
	 * @param responsesPath the filename for a file with possible chatbot responses
	 */
	public Chatter(String greeting, String goodbye, String responsesPath) {
		this.greeting = greeting;
		this.goodbye = goodbye;
		this.responses = loadResponsesFromFile(responsesPath);

	}

	/**
	 * Loads lines from a file into a chatbot response map. The lines have the
	 * format of a word followed by a response for the word. The word should be all
	 * lowercase and the response can be any String.
	 * 
	 * If the file is not found, prints an error and returns an empty HashMap. Do
	 * not use a throws to send the error somewhere else. Deal with it in this
	 * method.
	 * 
	 * @param filePath the String path to the file
	 * @return a HashMap with each word as a key and response as a value
	 */
	private static HashMap<String, String> loadResponsesFromFile(String filePath) {
		HashMap<String, String> responses = new HashMap<>();
		File file = new File(filePath);

		try {
			Scanner responsesReader = new Scanner(file);
			while (responsesReader.hasNextLine()) {
				String key = responsesReader.next();
				String value = responsesReader.nextLine().trim();
				responses.put(key, value);
			}
			return responses;

		} catch (FileNotFoundException e) {
			System.out.println(e);
			return new HashMap<String, String>();
		}

	}

	/**
	 * Gives the single, set greeting phrase.
	 * 
	 * @return the greeting String.
	 */
	public String sayHello() {
		return greeting;
	}

	/**
	 * Gives the single, set goodbye phrase.
	 * 
	 * @return the goodbye String.
	 */
	public String sayGoodbye() {
		return goodbye;
	}

	/**
	 * Picks a response phrase based on userPhrase. This method compares every word
	 * in userPhrase with the keys in the map of words and responses. If the map has
	 * the key, than add that response to a list of possible responses. After
	 * examining all the words in the userPhrase, choose one of responses randomly.
	 * You can use the Random class nextInt(upperbound) here, but the form
	 * nextInt(lower, upper) is not supported by the autograder.
	 * 
	 * Provides a default response if no match is found. The default response should
	 * be "Please tell me more about " followed by the last word in the userPhrase.
	 * Assume there is at least one word in the userPhrase.
	 * 
	 * @param userPhrase a sentence of lower-case words and no punctuation. Assume
	 *                   there is at least one word.
	 * @return a response that matches one of the words in userPhrase or a default
	 *         response.
	 */
	public String respondToPhrase(String userPhrase) {
		String[] array = userPhrase.split(" ");

		ArrayList<String> possibleResponses = new ArrayList<String>();
		for (String words : array) {
			if (responses.containsKey(words)) {
				possibleResponses.add(responses.get(words));
			}
		}
		if (possibleResponses.size() > 0) {
			Random random = new Random();
			int randomGen = random.nextInt(possibleResponses.size());
			return possibleResponses.get(randomGen);
		} else {
			return "Please tell me more about " + array[array.length - 1];
		}
	}
}
