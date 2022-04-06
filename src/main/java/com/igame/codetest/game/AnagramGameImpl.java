package com.igame.codetest.game;

import static java.lang.Math.abs;

import com.igame.codetest.exception.InvalidPositionException;
import com.igame.codetest.model.UserSubmission;
import com.igame.codetest.handler.WordLetterHandler;
import com.igame.codetest.handler.WordDictionaryHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class implement AnagramWordMakingGame interface.
 */
public class AnagramGameImpl implements AnagramWordMakingGame {

  // this data can be moved to property files and can be configured externally, if it is needed.
  // For example for test env we can use leaderboard size 100 whereas on production we want to use leaderboard size 10
  private static final int LEADERBOARD_SIZE = 10;
  private static final int MIN_POSITION = 0;
  private static final int MAX_POSITION = 9;

  WordDictionaryHandler dictionary;
  WordLetterHandler wordLetters;
  String letters;
  List<UserSubmission> leaderboard;

  public AnagramGameImpl(String letters, WordDictionaryHandler dictionary,
      WordLetterHandler wordLetterComparator) {
    this.letters = letters;
    this.dictionary = dictionary;
    this.wordLetters = wordLetterComparator;
    this.leaderboard = new ArrayList<>(LEADERBOARD_SIZE);
  }

  @Override
  synchronized public int submitWord(String username, String word) {
    // 1. check if a word is accepted
    if (word == null || !dictionary.contains(word) || !wordLetters.contains(letters, word)) {
      return 0;
    }

    UserSubmission submission = getUserSubmission(username, word);

    int position = Collections.binarySearch(leaderboard, submission);
    // if position is not in leaderboard size, adding to leaderboard should be skipped.
    // e.g. leaderboard already contains 10 submissions, and position for new word is 11
    if (abs(position) > LEADERBOARD_SIZE) {
      return submission.getScore();
    }
    if (position < 0) {
      position = ~position;
    }

    leaderboard.add(position, submission);

    if (leaderboard.size() > LEADERBOARD_SIZE) {
      // size of array should not be greater than leaderboard size
      leaderboard.remove(LEADERBOARD_SIZE);
    }
    return submission.getScore();
  }

  /**
   * Method return user submission object
   *
   * @param username Username
   * @param word     User word
   * @return User submission object.
   */
  private UserSubmission getUserSubmission(String username, String word) {
    UserSubmission submission = new UserSubmission();
    submission.setUsername(username);
    submission.setWord(word);
    submission.setScore(word.length());
    return submission;
  }

  @Override
  public String getUserNameAtPosition(int position) {
    if (isInvalidPosition(position)) {
      return null;
    }
    return leaderboard.get(position).getUsername();
  }

  @Override
  public String getWordEntryAtPosition(int position) {
    if (isInvalidPosition(position)) {
      return null;
    }
    return leaderboard.get(position).getWord();
  }

  @Override
  public Integer getScoreAtPosition(int position) {
    if (isInvalidPosition(position)) {
      return null;
    }
    return leaderboard.get(position).getScore();
  }

  /**
   * Throw exception if position is less than 0 or bigger than 9, otherwise return true if position
   * is in leaderboard size, false if not.
   * <p>
   * For example: - position "-1" -> throw exception (position cannot be less than 0) - position "9"
   * -> throw exception (position cannot be bigger then 9) - leaderboard size "5", position "6" ->
   * return false (position must be in leaderboard size) - leaderboard suze "5", position "4" ->
   * return true (position is in leaderboard size)
   * </p>
   *
   * @param position Index on leaderboard
   * @return exception or invalid number or true/false if position is in leaderboard size.
   */
  private boolean isInvalidPosition(int position) {
    if (position < MIN_POSITION || position > MAX_POSITION) {
      throw new InvalidPositionException(
          "Position must be between " + MIN_POSITION + " and " + MAX_POSITION
              + ". " + MIN_POSITION + " being the highest (best score) and " + MAX_POSITION
              + " the lowest");
    }
    return leaderboard.size() < position;
  }
}
