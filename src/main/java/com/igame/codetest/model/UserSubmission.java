package com.igame.codetest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSubmission implements Comparable<UserSubmission> {

  private String username;
  private String word;
  private Integer score;

  /**
   * Method used to compare user submission score with other scores in leaderboard.
   *
   * @param submission UserSubmission object which represent score, word and username
   * @return 1 if submission should be stored on the right side from current position, -1 for left
   */
  @Override
  public int compareTo(UserSubmission submission) {
    Integer score = submission.getScore();
    // only bigger set to top
    if (score > this.score) {
      // right
      return 1;
    }
    // left
    return -1;
  }
}
