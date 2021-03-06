package com.appian.intellij.k;

import junit.framework.Test;
import junit.framework.TestSuite;

public class RenameUnderRootTest extends RenameTestBase {

  public RenameUnderRootTest(int totalCarets, int currentCaret) {
    super(totalCarets, currentCaret);
  }

  public static Test suite() {
    final TestSuite suite = new TestSuite();
    int total = 12;
    for (int i = 1; i <= total; i++) {
      suite.addTest(new RenameUnderRootTest(total, i));
    }
    return suite;
  }

  @Override
  String getExpectedFileName(int currentCaret) {
    switch (currentCaret) {
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 8:
    case 9:
    case 10:
    case 11:
    case 12:
      return "after_1.q";
    case 6:
    case 7:
      return "after_2.q";
    }
    throw new IllegalStateException("Missing mapping for caret#" + currentCaret);
  }

  @Override
  boolean isExpectedHighlighting(String newName, int currentCaret) {
    switch (currentCaret) {
    case 6:
    case 7:
      return newName.contains(".");
    }
    return false;
  }

}
