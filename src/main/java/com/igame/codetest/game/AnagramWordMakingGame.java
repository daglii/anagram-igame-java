package com.igame.codetest.game;

/**
 * Service to provide an anagram game, similar to the TV show Countdown.
 * <p/>
 * The game is constructed with a base set of letters, e.g. the string "areallylongword",
 * which may be assumed to be all in lowercase, and with no whitespace or special characters. Users then attempt to create
 * words out of the letters, and score one point for each letter used. The submissions must be "real words": use
 * the WordDictionary to check each submission.
 * <p/>
 * For example (see the test skeleton):
 * "no" is an acceptable submission, with score 2
 * "grow" is an acceptable submission, with score 4
 * "bold" is not an acceptable submission
 * "glly" is not an acceptable submission
 * "woolly" is an acceptable submission with score 6
 * "adder" is not an acceptable submission
 * <p/>
 * The service maintains a list of the top ten highest-scoring submissions (including username, word, and score).
 * Submissions are not summed: if a user enters multiple submissions, these should each be added to the high-score
 * list (if applicable) independently.
 * <p/>
 * Throughout this test, you need not worry about whitespace, special characters, or uppercase. You may assume
 * that both the original string of letters and all submissions contain none of these.
 */
public interface AnagramWordMakingGame {

    /**
     * Submit a word on behalf of a user. A word is accepted if its letters are contained in the original string
     * submitted in the constructor, and if it is in the WordDictionary. If the word is accepted and its score is high
     * enough, the user's submission should be added to the top-ten leaderboard. If there are multiple submissions with
     * the same score, all are accepted, but the first submission with that score should rank higher. If there are
     * multiple submissions with the same username, each should be treated as a separate entry and recorded independently.
     * <p/>
     * NOTE: This method must be threadsafe; multiple users may be submitting at the same time.
     *
     * @param username Name to record the submission under
     * @param word     User's submission. All submissions may be assumed to be lowercase and containing no whitespace
     *                 or special characters.
     * @return the user's score, 0 if the word is not accepted
     */
    public int submitWord(String username, String word);

    /**
     * Return username at given position in the leaderboard, 0 being the highest (best score) and 9 the lowest.
     * You may assume that this method will never be called with position > 9.
     *
     * @param position Index on leaderboard
     * @return username at given position in the leaderboard, or null if there is no entry at that position
     */
    String getUserNameAtPosition(int position);

    /**
     * Return word entry at given position in the leaderboard, 0 being the highest (best score) and 9 the lowest.
     * You may assume that this method will never be called with position > 9.
     *
     * @param position Index on leaderboard
     * @return word entry at given position in the leaderboard, or null if there is no entry at that position
     */
    String getWordEntryAtPosition(int position);

    /**
     * Return score at given position in the leaderboard, 0 being the highest (best score) and 9 the lowest.
     * You may assume that this method will never be called with position > 9.
     *
     * @param position Index on leaderboard
     * @return score at given position in the leaderboard, or null if there is no entry at that position
     */
    Integer getScoreAtPosition(int position);
}
