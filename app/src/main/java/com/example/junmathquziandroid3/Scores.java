package com.example.junmathquziandroid3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.junmathquziandroid3.model.Question;

import java.io.Serializable;
import java.util.ArrayList;

public class Scores extends AppCompatActivity {

    RadioButton radioBtnAll, radioBtnRight, radioBtnWrong;
    TextView showResultTextView, percentageTextView;
    public ArrayList<Question> questionsList;
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        initialize();
    }

    private void initialize() {
        radioBtnAll = findViewById(R.id.radioBtnAll);
        radioBtnRight = findViewById(R.id.radioBtnRight);
        radioBtnWrong = findViewById(R.id.radioBtnWrong);

        showResultTextView = findViewById(R.id.showResultTextView);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intentExtra");
        Serializable bundledListOfQuestions = bundle.getSerializable("bundleExtra");
        questionsList = (ArrayList<Question>) bundledListOfQuestions;
    }

    public void showListOfQuestions(View view) {
        if (radioBtnRight.isChecked())
            iterateByType(true);
        else if (radioBtnWrong.isChecked())
            iterateByType(false);
        else if (radioBtnAll.isChecked())
            showListOfQuestion(questionsList);

        showResultTextView.setText(str);
    }

    void iterateByType(boolean b) {
        str = "";
        for (Question q : questionsList) {
            if (q.getRight().equals(b)) {
                str = str + q;
            }
        }
    }

    void showListOfQuestion(ArrayList<Question> questionsList) {
        str = "";
        for (Question q : questionsList) {
            str = str + q;
        }
    }

    public void quit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}