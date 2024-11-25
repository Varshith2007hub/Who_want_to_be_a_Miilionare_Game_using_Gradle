package million_dollar_showdown.model;

import java.util.ArrayList;

public interface FiftyFiftyCallback {
    ArrayList<String> getQuestionOptions();

    void onFiftyFiftyAction(ArrayList<String> updatedOptions);

    String getCorrectAnswer();
}
