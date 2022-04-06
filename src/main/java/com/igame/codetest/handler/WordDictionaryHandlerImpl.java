package com.igame.codetest.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

public class WordDictionaryHandlerImpl implements WordDictionaryHandler {

  //CODE REVIEW:

  // 1. Vector.
  // Vector is thread-safe what can be good for some approaches, but for this case thread-safe is not important.
  // We are only reading data from dictionary. Reading contents from any collections by multiple threads is thread-safe.
  // If we would initialize a dictionary for each user's submission, than vector should be good choice because it is thread-safe,
  // and for this case thread-safe is important, but this approach is not good. Dictionary should be created only once per instance!
  // Another reason, but not so much important why we would need to avoid Vector is that Vector is a legacy.

  // Suggestion: Bloom filter
  // Dictionary could contain more than 500,000 words. Having all these words stored in memory all the time is a bad idea.
  // Inserting all the words from the dictionary to the Bloom filter is much more efficient for memory. This is good choice in case
  // we need to care about memory and reading efficiency. Reading data using Bloom filter can be done in an extremely efficient way,
  // what is very good for our case.

  // 2. Dictionary initialization
  // It should be done only once. It can be done on application startup  or before first game after application startup,
  // or whatever. Most important thing that it should be initialized before game and only once.

  // 3. If dictionary cannot be changed, it should be declared with 'final'

  // 4. Naming variable with one character is not readable
  // Suggestion: dictionary, because it describes dictionary
  Vector v = new Vector();

  public WordDictionaryHandlerImpl() {
    try {
      URL url = new URL("https://www.scrabble.org.nz/assets/CSW15.txt");
      URLConnection dc = url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(dc.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        v.add(inputLine);
      }
      in.close();
    } catch (Exception e) {
      // 5. Exceptions should be logged in some logger and handled (depends on the case)
      e.printStackTrace();
      // 6. Thrown exception should be more specific. Throwing exception from top of hierarchy (Exception) is ok,
      // but it would much better if we can throw more specific exception. For this case it would be MalformedURLException or IOException.
      // Handling more specific exceptions are easier and much more readable.
    }
  }

  @Override
  public boolean contains(String word) {
    return v.contains(word.toUpperCase());
  }

  @Override
  public int size() {
    return v.size();
  }
}
