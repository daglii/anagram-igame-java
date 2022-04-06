package com.igame.codetest.handler;

/**
 * Implementations of this interface guarantee to be threadsafe.
 */
public interface WordLetterHandler {

  /**
   * Method compare user word submission and letters from game. True will be returned if word
   * contains letters from game.
   * letters = "areallylongword"
   * word = "yellow"
   *
   * @param letters Letters given in a game
   * @param word    User's submission. All submissions may be assumed to be lowercase and containing
   *                no whitespace or special characters.
   * @return true if the letters are contained in the word
   */
  public boolean contains(String letters, String word);
}
