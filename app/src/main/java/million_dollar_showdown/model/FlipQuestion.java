package million_dollar_showdown.model;

import million_dollar_showdown.LifelineAction;

public class FlipQuestion implements LifelineAction {
    private QuestionCallback questionCallback;

    public FlipQuestion(QuestionCallback questionCallback) {
        this.questionCallback = questionCallback;
    }

    @Override
    public void performAction() {
        questionCallback.onFlipQuestionAction();
    }
}
