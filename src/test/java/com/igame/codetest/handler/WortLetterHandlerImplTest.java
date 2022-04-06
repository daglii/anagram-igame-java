package com.igame.codetest.handler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WortLetterHandlerImplTest {

  WordLetterHandler wordLetter;

  public WortLetterHandlerImplTest() {
    wordLetter = new WordLetterHandlerImpl();
  }

  @Test
  public void contains_wordContainsLettersFromGame_returnTrue() {
    String letterString = "areallylongword";
    assertTrue(wordLetter.contains(letterString, "llyw"));
    assertTrue(wordLetter.contains(letterString, "d"));
    assertTrue(wordLetter.contains(letterString, "word"));
    assertTrue(wordLetter.contains(letterString, "woolly"));
    assertTrue(wordLetter.contains(letterString, "lll"));
  }

  @Test
  public void contains_wordDoesNotContainsLettersFromGame_returnFalse() {
    String letterString = "areallylongword";
    assertFalse(wordLetter.contains(letterString, "grt"));
    assertFalse(wordLetter.contains(letterString, "yyyyy"));
    assertFalse(wordLetter.contains(letterString, "worbbbbbd"));
    assertFalse(wordLetter.contains(letterString, "allaaldr"));
    assertFalse(wordLetter.contains(letterString, "lllaaa"));
    assertFalse(wordLetter.contains(letterString, "lllaaaaaaaaaaaaaaaaaaaa"));
  }
}
