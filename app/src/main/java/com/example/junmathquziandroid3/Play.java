package com.example.junmathquziandroid3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.junmathquziandroid3.model.Question;

import java.util.ArrayList;
import java.util.Random;

public class Play extends AppCompatActivity implements View.OnClickListener {
    int answer = 0;
    int operator = 0;
    int operand1 = 0;
    int operand2 = 0;

    private final int ADD_OPERATOR = 0;
    private final int SUBTRACT_OPERATOR = 1;
    private final int MULTYPLY_OPERATOR = 2;
    private final int DIVIDE_OPERATOR = 3;

    private String[] operators = {"+", "-", "x", "/"};

    private Random random;

    public ArrayList<Question> questionsList;
    String question;
    Boolean right;
    int enteredAnswer;

    int correctAnswer = 0;
    int wrongAnswer = 0;

    TextView questionTextView, answerTextView, scoreTextView, responseTextView, timerTextView, playerNameTextView;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnMinus, btnDot, btnAnswer, btnClear, btnResults, generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initialize();
        myGetIntent();
    }

    private void initialize() {
        questionsList = new ArrayList<>();
        questionTextView = findViewById(R.id.question);
        answerTextView = findViewById(R.id.answer);
        scoreTextView = findViewById(R.id.score);
        timerTextView = findViewById(R.id.timer);
        responseTextView = findViewById(R.id.response);
        playerNameTextView = findViewById(R.id.playerNameTextView);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        btn0 = findViewById(R.id.btn0);
        btn0.setOnClickListener(this);

        btnMinus = findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(this);
        btnDot = findViewById(R.id.btnDot);
        btnDot.setOnClickListener(this);

        btnAnswer = findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnResults = findViewById(R.id.btnResults);

        generate = findViewById(R.id.generate);
        generate.setOnClickListener(this);

        responseTextView.setVisibility(View.INVISIBLE);

        random = new Random();

        chooseQuestion();
    }

    private void chooseQuestion() {
        answerTextView.setText("= ?");
        operator = random.nextInt(operators.length);
        operand1 = random.nextInt(10);
        operand2 = random.nextInt(10);
        switch (operator) {
            case ADD_OPERATOR:
                answer = operand1 + operand2;
                break;
            case SUBTRACT_OPERATOR:
                while (operand2 > operand1) {
                    operand1 = random.nextInt(10);
                    operand2 = random.nextInt(10);
                }
                answer = operand1 - operand2;
                break;
            case MULTYPLY_OPERATOR:
                answer = operand1 * operand2;
                break;
            case DIVIDE_OPERATOR:
                while ((((double) operand1 / (double) operand2) % 1 > 0) || (operand2 == 0)) {
                    operand1 = random.nextInt(10);
                    operand2 = random.nextInt(10);
                }
                answer = operand1 / operand2;
                break;
            default:
                break;
        }
        question = operand1 + " " + operators[operator] + " " + operand2;
        questionTextView.setText(question);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAnswer:
                userAnswer();
                break;
            case R.id.btnClear:
                cleanUserAnswer();
                break;
            case R.id.generate:
                chooseQuestion();
                break;
            default:
                responseTextView.setVisibility(View.INVISIBLE);
                if (answerTextView.getText().toString().endsWith("?"))
                    answerTextView.setText("= " + view.getTag().toString());
                else
                    answerTextView.append("" + view.getTag().toString());
        }
    }

    private void userAnswer() {
        String answerContent = answerTextView.getText().toString();
        if (!answerContent.endsWith("?")) {
            enteredAnswer = Integer.parseInt(answerContent.substring(2));
            int displayedScore = getScore();
            if (enteredAnswer == answer) {
                scoreTextView.setText("score: " + (displayedScore + 1));
                responseTextView.setText("Correct!!!");
                responseTextView.setVisibility(View.VISIBLE);
                correctAnswer++;
                right = true;
            } else {
                scoreTextView.setText("score: " + (displayedScore - 1));
                responseTextView.setText("Incorrect!!!" + "\nThe Answer is " + answer);
                responseTextView.setVisibility(View.VISIBLE);
                wrongAnswer++;
                right = false;
            }
            addQuestionToArray();
        }
    }

    private void cleanUserAnswer() {
        answerTextView.setText("= ?");
    }

    private int getScore() {
        String scoreStr = scoreTextView.getText().toString();
        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ") + 1));
    }

    public void addQuestionToArray() {
        Question questionObject = new Question(this.question, this.enteredAnswer, this.right);
        questionsList.add(questionObject);
    }

    public void showResults(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundleExtra", questionsList);

        Intent intent = new Intent(this, Scores.class);
        intent.putExtra("intentExtra", bundle);

        startActivity(intent);
    }

    private void myGetIntent() {
        String name = getIntent().getStringExtra("name");
        playerNameTextView.setText(String.valueOf(name));
    }
}