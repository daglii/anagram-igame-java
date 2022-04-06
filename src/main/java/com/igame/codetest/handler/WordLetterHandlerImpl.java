package com.igame.codetest.handler;

import java.util.HashMap;

public class WordLetterHandlerImpl implements WordLetterHandler {

  /**
   * The method compares user word with letters from game. True will be returned if the word is
   * composed of letters given in the game. The method does not maintain any state, neither relies
   * on external state what makes them thread-safe, and can be safely called by multiple threads.
   *
   * @param letters Letters given in a game
   * @param word    Word given by user
   * @return true or false, true is when all the letters from word are contained in the given
   * letters from game.
   */
  @Override
  public boolean contains(String letters, String word) {
    HashMap<Character, Short> letterMap = new HashMap<>();

    if (word.length() > letters.length()) {
      return false;
    }

    //int 4 byte-a
    //short 2 byte-a
    for (Character c : letters.toCharArray()) {
      if (!letterMap.containsKey(c)) {
        letterMap.put(c, (short) 1);
        continue;
      }
      Short count = letterMap.get(c);
      letterMap.put(c, ++count);
    }

    for (Character c : word.toCharArray()) {
      Short count = letterMap.get(c);
      if (count == null || count <= 0) {
        return false;
      }
      letterMap.put(c, --count);
    }
    return true;
  }
}
