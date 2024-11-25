package million_dollar_showdown.model;

import million_dollar_showdown.LifelineAction;
import java.util.ArrayList;
import java.util.Collections;

public class FiftyFiftylifeline implements LifelineAction {
  private FiftyFiftyCallback fiftyFiftyCallback;

  public FiftyFiftylifeline(FiftyFiftyCallback fiftyFiftyCallback) {
    this.fiftyFiftyCallback = fiftyFiftyCallback;
  }

  @Override
  public void performAction() {
    ArrayList<String> options = fiftyFiftyCallback.getQuestionOptions();
    String correctAnswer = fiftyFiftyCallback.getCorrectAnswer();
    int correctAnswerIndex = findCorrectAnswerIndex(options, correctAnswer);
    ArrayList<Integer> indexes = new ArrayList<>();
    for (int i = 0; i < options.size(); i++) {
      if (i != correctAnswerIndex) {
        indexes.add(i);
      }
    }

    Collections.shuffle(indexes);
    int indexToDisable1 = indexes.get(0);
    int indexToDisable2 = indexes.get(1);

    ArrayList<String> updatedOptions = new ArrayList<>();
    for (int i = 0; i < options.size(); i++) {
      if (i == indexToDisable1 || i == indexToDisable2) {
        updatedOptions.add("");
      } else {
        updatedOptions.add(options.get(i));
      }
    }
    fiftyFiftyCallback.onFiftyFiftyAction(updatedOptions);
  }

  private int findCorrectAnswerIndex(ArrayList<String> options, String correctAnswer) {
    for (int i = 0; i < options.size(); i++) {
      if (options.get(i).equals(correctAnswer)) {
        return i;
      }
    }
    return -1;
  }
}
