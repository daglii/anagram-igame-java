package com.igame.codetest.handler;

/**
 * Implementations of this interface guarantee to be threadsafe.
 */
public interface WordDictionaryHandler {

  /**
   * @param word
   * @return true if the dictionary contains the word
   */
  public boolean contains(String word);

  //Code review:
  //1. This should be removed. There is currently no need for this method. If we would ever need it, we will add it then.
  public int size();
}
