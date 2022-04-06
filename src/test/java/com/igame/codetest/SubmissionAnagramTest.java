package com.igame.codetest;

import com.igame.codetest.handler.WordLetterHandler;
import com.igame.codetest.handler.WordLetterHandlerImpl;
import com.igame.codetest.handler.WordDictionaryHandler;
import com.igame.codetest.handler.WordDictionaryHandlerImpl;
import com.igame.codetest.exception.InvalidPositionException;
import com.igame.codetest.game.AnagramGameImpl;
import com.igame.codetest.game.AnagramWordMakingGame;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class SubmissionAnagramTest {

  WordDictionaryHandler dictionary;
  WordLetterHandler wordLetterComparator;
  AnagramWordMakingGame service;

  public SubmissionAnagramTest() {
    dictionary = new WordDictionaryHandlerImpl();
    wordLetterComparator = new WordLetterHandlerImpl();
  }

  @Before
  public void setUp() {
    service = new AnagramGameImpl("areallylongword", dictionary, wordLetterComparator);
  }

  @Test
  public void testSubmission() {
    assertEquals(2, service.submitWord("1", "no"));
    assertEquals(4, service.submitWord("2", "grow"));
    assertEquals(0, service.submitWord("3", "bold"));
    assertEquals(0, service.submitWord("4", "glly"));
    assertEquals(6, service.submitWord("5", "woolly"));
    assertEquals(0, service.submitWord("6", "adder"));
  }

  @Test
  public void testSubmission_exampleOneFromTestClarificationPdf() {
    service.submitWord("u1", "wrongdoer");
    service.submitWord("u2", "goodyear");
    service.submitWord("u3", "allergy");
    service.submitWord("u4", "delay");

    service.submitWord("u5", "yellow");

    //leaderboard (H->L): u1,u2,u3,u5,u4

    assertEquals("u1", service.getUserNameAtPosition(0));
    assertEquals("u2", service.getUserNameAtPosition(1));
    assertEquals("u3", service.getUserNameAtPosition(2));
    assertEquals("u5", service.getUserNameAtPosition(3));
    assertEquals("u4", service.getUserNameAtPosition(4));

  }

  @Test
  public void testSubmission_exampleTwoFromTestClarificationPdf() {
    service.submitWord("u1", "ray");
    service.submitWord("u2", "any");
    service.submitWord("u5", "delay");
    service.submitWord("u3", "year");
    service.submitWord("u4", "yoga");
    service.submitWord("u8", "allergy");
    service.submitWord("u6", "loader");
    service.submitWord("u7", "lonely");
    service.submitWord("u10", "wrongdoer");
    service.submitWord("u9", "goodyear");

    assertEquals("u10", service.getUserNameAtPosition(0));
    assertEquals("u9", service.getUserNameAtPosition(1));
    assertEquals("u8", service.getUserNameAtPosition(2));
    assertEquals("u6", service.getUserNameAtPosition(3));

    service.submitWord("u11", "long");
    assertEquals("u11", service.getUserNameAtPosition(8));

  }

  @Test
  public void testSubmission_exampleThreeFromTestClarificationPdf() {
    service.submitWord("u1", "wrongdoer");
    service.submitWord("u2", "goodyear");
    service.submitWord("u3", "allergy");
    service.submitWord("u4", "lonely");
    service.submitWord("u5", "loader");
    service.submitWord("u6", "delay");
    service.submitWord("u7", "yoga");
    service.submitWord("u8", "year");
    service.submitWord("u9", "any");
    service.submitWord("u10", "ray");

    assertEquals("u10", service.getUserNameAtPosition(9));

    service.submitWord("u11", "go");
    assertEquals("u10", service.getUserNameAtPosition(9));

  }


  @Test
  public void testSubmission_testLeaderboardMyExample() {
    service.submitWord("u1", "no");
    service.submitWord("u2", "grow");
    service.submitWord("u3", "bold");
    service.submitWord("u4", "glly");
    service.submitWord("u5", "woolly");
    service.submitWord("u6", "woolly");
    service.submitWord("u7", "woolly");
    service.submitWord("u8", "woolly");
    service.submitWord("u9", "adder");
    service.submitWord("u10", "adder");
    service.submitWord("u11", "goodyear");
    service.submitWord("u12", "yyyyyyyyyyyyyyyyyyyyyyy");
    service.submitWord("u12", null);

    //leaderboard (H->L): u11,u5,u6,u7,u8,u2,u1

    assertEquals("u11", service.getUserNameAtPosition(0));
    assertEquals("goodyear", service.getWordEntryAtPosition(0));
    assertEquals(Integer.valueOf(8), service.getScoreAtPosition(0));

    assertEquals("u5", service.getUserNameAtPosition(1));
    assertEquals("woolly", service.getWordEntryAtPosition(1));
    assertEquals(Integer.valueOf(6), service.getScoreAtPosition(1));

    assertEquals("u6", service.getUserNameAtPosition(2));
    assertEquals("woolly", service.getWordEntryAtPosition(2));
    assertEquals(Integer.valueOf(6), service.getScoreAtPosition(2));

    assertEquals("u1", service.getUserNameAtPosition(6));
    assertEquals("no", service.getWordEntryAtPosition(6));
    assertEquals(Integer.valueOf(2), service.getScoreAtPosition(6));


  }

  @Test
  public void testSubmission_givenInvalidPosition() {
    service.submitWord("u1", "wrongdoer");
    service.submitWord("u2", "goodyear");
    service.submitWord("u3", "allergy");
    service.submitWord("u4", "delay");
    service.submitWord("u5", "yellow");

    //only 5 positions in leaderboard
    assertNull(service.getUserNameAtPosition(7));
    assertNull(service.getScoreAtPosition(7));
    assertNull(service.getWordEntryAtPosition(7));

    assertNull(service.getUserNameAtPosition(8));
    assertNull(service.getScoreAtPosition(8));
    assertNull(service.getWordEntryAtPosition(8));

    Exception maxPositionException = assertThrows(InvalidPositionException.class,
        () -> service.getUserNameAtPosition(10));
    assertEquals(maxPositionException.getMessage(),
        "Position must be between 0 and 9. 0 being the highest (best score) and 9 the lowest");

    Exception minPositionException = assertThrows(InvalidPositionException.class,
        () -> service.getScoreAtPosition(-1));
    assertEquals(minPositionException.getMessage(),
        "Position must be between 0 and 9. 0 being the highest (best score) and 9 the lowest");

    Exception minPositionException2 = assertThrows(InvalidPositionException.class,
        () -> service.getWordEntryAtPosition(-11));
    assertEquals(minPositionException2.getMessage(),
        "Position must be between 0 and 9. 0 being the highest (best score) and 9 the lowest");

  }


  @Test
  public void testSubmission_11usersWithTheSameAnswer12UserWithTheBestAnswer() {
    service.submitWord("u1", "goodyear");
    service.submitWord("u2", "goodyear");
    service.submitWord("u3", "goodyear");
    service.submitWord("u4", "goodyear");
    service.submitWord("u5", "goodyear");
    service.submitWord("u6", "goodyear");
    service.submitWord("u7", "goodyear");
    service.submitWord("u8", "goodyear");
    service.submitWord("u9", "goodyear");
    service.submitWord("u10", "goodyear");
    service.submitWord("u11", "goodyear");
    service.submitWord("u12", "wrongdoer");

    //leaderboard (H->L): u12,u1,u2,u3,u4,u5,u6,u7,u8,u9

    assertEquals("u12", service.getUserNameAtPosition(0));
    assertEquals("u1", service.getUserNameAtPosition(1));
    assertEquals("u2", service.getUserNameAtPosition(2));
    assertEquals("u3", service.getUserNameAtPosition(3));
    assertEquals("u4", service.getUserNameAtPosition(4));
    assertEquals("u5", service.getUserNameAtPosition(5));
    assertEquals("u6", service.getUserNameAtPosition(6));
    assertEquals("u7", service.getUserNameAtPosition(7));
    assertEquals("u8", service.getUserNameAtPosition(8));
    assertEquals("u9", service.getUserNameAtPosition(9));

  }

  @Test
  public void testSubmission_concurrency() {
    Thread t1 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u1", "goodyear");
      }
    }, "Thread-1");

    Thread t2 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u2", "goodyear");
      }
    }, "Thread-2");

    Thread t3 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u3", "goodyear");
      }
    }, "Thread-3");

    Thread t4 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u4", "goodyear");
      }
    }, "Thread-4");

    Thread t5 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u5", "goodyear");
      }
    }, "Thread-5");

    Thread t6 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u6", "goodyear");
      }
    }, "Thread-6");

    Thread t7 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u7", "goodyear");
      }
    }, "Thread-7");

    Thread t8 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u8", "goodyear");
      }
    }, "Thread-8");

    Thread t9 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u9", "goodyear");
      }
    }, "Thread-9");

    Thread t10 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u10", "goodyear");
      }
    }, "Thread-10");

    Thread t11 = new Thread(new Runnable() {
      public void run() {
        service.submitWord("u11", "wrongdoer");
      }
    }, "Thread-11");

    try {
      t1.start();
      sleep(100);
      t2.start();
      sleep(100);
      t3.start();
      sleep(100);
      t4.start();
      sleep(100);
      t5.start();
      sleep(100);
      t6.start();
      sleep(100);
      t7.start();
      sleep(100);
      t8.start();
      sleep(100);
      t9.start();
      sleep(100);
      t10.start();
      sleep(100);
      t11.start();
      sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      t1.join();
      t2.join();
      t3.join();
      t4.join();
      t5.join();
      t6.join();
      t7.join();
      t8.join();
      t9.join();
      t10.join();
      t11.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //leaderboard (H->L): u11,u1,u2,u3,u4,u5,u6,u7,u8,u9,

    assertEquals("u11", service.getUserNameAtPosition(0));
    assertEquals("u1", service.getUserNameAtPosition(1));
    assertEquals("u2", service.getUserNameAtPosition(2));
    assertEquals("u3", service.getUserNameAtPosition(3));
    assertEquals("u4", service.getUserNameAtPosition(4));
    assertEquals("u5", service.getUserNameAtPosition(5));
    assertEquals("u6", service.getUserNameAtPosition(6));
    assertEquals("u7", service.getUserNameAtPosition(7));
    assertEquals("u8", service.getUserNameAtPosition(8));
    assertEquals("u9", service.getUserNameAtPosition(9));

  }

}
