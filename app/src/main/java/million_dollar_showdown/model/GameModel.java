package million_dollar_showdown.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import million_dollar_showdown.GameObserver;

public class GameModel {
    private static final String FILE_NAME = "questions.txt";
    private static final int easy_question_start = 1;
    private static final int easy_question_end = 8;
    private static final int medium_question_start = 9;
    private static final int medium_question_end = 15;
    private static final int hard_question_start = 16;
    private static final int hard_question_end = 21;

    private ArrayList<Integer> visited;
    private String question1;
    private String correctAnswer;
    private String[] a;
    ArrayList<GameObserver> observers;

    public GameModel() {
        visited = new ArrayList<>();
        correctAnswer = "";
        observers = new ArrayList<GameObserver>();
    }

    public String extractfromfile(int randomnumber) {
        String question = "";
        try {
            File file = new File(FILE_NAME);
            Scanner sc = new Scanner(file);
            int x = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (randomnumber == x) {
                    question = line;
                    break;
                }
                x++;
            }
            this.correctAnswer = question.substring(question.lastIndexOf(";") + 1);
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }

        this.question1 = question;
        return question;
    }

    public String extractquestion(int stage) {
        int start = 0;
        int end = 0;
        if (stage >= 0 && stage <= 4) {
            start = easy_question_start;
            end = easy_question_end;
        } else if (stage >= 5 && stage <= 7) {
            start = medium_question_start;
            end = medium_question_end;
        } else if (stage >= 8 && stage <= 10) {
            start = hard_question_start;
            end = hard_question_end;
        }
        int randomnumber = getnumber(start, end);
        String question = extractfromfile(randomnumber);
        notifyObservers();
        return question.substring(0, question.indexOf(";"));
    }

    public int getnumber(int start, int end) {
        Random random = new Random();
        int r = random.nextInt(end - start + 1) + start;
        if (visited.contains(r)) {
            return getnumber(start, end);
        } else {
            visited.add(r);
        }
        return r;
    }

    public ArrayList<String> options() {
        a = question1.split(";");
        ArrayList<String> x = new ArrayList<>();
        for (int i = 1; i < a.length - 1; i++) {
            x.add(a[i]);
        }
        return x;
    }

    public boolean checkAnswer(String answer) {
        return answer.equals(correctAnswer);
    }

    public String correctAnswer() {
        return this.correctAnswer;
    }

    public void register(GameObserver observer) {
        this.observers.add(observer);
    }

    private void notifyObservers(){
        for (GameObserver o: observers){
            o.update();
        }
    }
}
