package obause.example.gehirntrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView welcomeTextView;
    Button startButton;

    TextView timerTextView;
    TextView scoreTextView;
    TextView resultTextView;
    TextView sumTextView;
    Button button0, button1, button2, button3;
    ConstraintLayout gameLayout;

    ConstraintLayout resultLayout;
    Button playAgainButton;
    TextView correctTextView;
    TextView allTextView;
    RatingBar ratingBar;

    CountDownTimer countDownTimer;

    int locationOfCorrectAnswer;
    ArrayList<Integer> answerLocations = new ArrayList<Integer>();

    int score = 0;
    int numberOfQuestions = 0;

    public void startGame(View view) {
        gameLayout.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        welcomeTextView.setVisibility(View.INVISIBLE);

        playAgain(timerTextView);
    }

    public void chooseAnswer(View view) {
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            resultTextView.setText("KORREKT!");
            //Punkte erhöhen
            score++;
        }
        else {
            resultTextView.setText("FALSCH!");
        }
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        if(scoreTextView.length() > 3) {
            scoreTextView.setTextSize(26);
        }
        newQuestion();
    }

    public void newQuestion() {
        Random rnd = new Random();

        int firstNumber = rnd.nextInt(21);
        int secondNumber = rnd.nextInt(21);

        int operator = rnd.nextInt(3);
        String[] operators = {"+", "-", "*"};

        sumTextView.setText(Integer.toString(firstNumber) + " " + operators[operator] + " " + Integer.toString(secondNumber));

        int solution;
        switch(operators[operator]) {
            case "+":
                solution = firstNumber + secondNumber;
                break;
            case "-":
                solution = firstNumber - secondNumber;
                break;
            case "*":
                solution = firstNumber * secondNumber;
                break;
            default:
                solution = 0;
        }

        locationOfCorrectAnswer = rnd.nextInt(4);
        
        answerLocations.clear();
        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answerLocations.add(solution);
            }
            else {
                int wrongAnswer = rnd.nextInt(41);

                while (wrongAnswer == solution || answerLocations.contains(wrongAnswer) ) {
                    wrongAnswer = rnd.nextInt(41);
                }
                answerLocations.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answerLocations.get(0)));
        button1.setText(Integer.toString(answerLocations.get(1)));
        button2.setText(Integer.toString(answerLocations.get(2)));
        button3.setText(Integer.toString(answerLocations.get(3)));
    }

    public void playAgain(View view) {

        resultLayout.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);

        score = 0;
        numberOfQuestions = 0;
        scoreTextView.setText("0/0");
        scoreTextView.setTextSize(34);
        //playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setText("");

        newQuestion();

        startTimer(30000);
    }

    public void startTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long l) {
                updateTimer((int)l/1000);
            }

            @Override
            public void onFinish() {
                //resultTextView.setText("ZEIT\nABGELAUFEN!");
                //playAgainButton.setVisibility(View.VISIBLE);
                gameLayout.setVisibility(View.INVISIBLE);
                resultLayout.setVisibility(View.VISIBLE);

                correctTextView.setText(Integer.toString(score));
                allTextView.setText(Integer.toString(numberOfQuestions));

                if (score >= 20) {
                    ratingBar.setRating(5);
                }
                else if (score <= 20 && score >=15) {
                    ratingBar.setRating(4);
                }
                else if (score <= 15 && score >=10) {
                    ratingBar.setRating(3);
                }
                else if (score <= 10 && score >=5) {
                    ratingBar.setRating(2);
                }
                else {
                    ratingBar.setRating(1);
                }


            }
        }.start();
    }

    public void updateTimer(int secondsLeft) {
        String sSeconds = Integer.toString(secondsLeft);
        if (sSeconds.length() == 1) {
            sSeconds = "0" + sSeconds;
        }
        timerTextView.setText(sSeconds + "s");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setVisibility(View.VISIBLE);
        startButton = findViewById(R.id.startButton);
        startButton.setVisibility(View.VISIBLE);

        gameLayout = findViewById(R.id.gameLayout);
        gameLayout.setVisibility(View.INVISIBLE);
        timerTextView = findViewById(R.id.timerTextView);
        sumTextView = findViewById(R.id.sumTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        resultTextView = findViewById(R.id.resultTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        resultLayout = findViewById(R.id.resultLayout);
        resultLayout.setVisibility(View.INVISIBLE);
        playAgainButton = findViewById(R.id.playAgainButton);
        correctTextView = findViewById(R.id.correctTextView);
        allTextView = findViewById(R.id.allTextView);
        ratingBar = findViewById(R.id.ratingBar);

    }
}