package com.example.android.iknowthat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static Activity mainActivity;

    ArrayList<String> question = new ArrayList<>();
    ArrayList<Boolean> known = new ArrayList<>();
    ArrayList<Boolean> answerBoolean = new ArrayList<>();
    ArrayList<String> answerString = new ArrayList<>();
    int actualQuestionNumber;
    int numberOfLearnedQuestions = 0;

    private void addNewQuestion(String question, boolean answerBoolean)
    {
        this.question.add(question);
        this.answerBoolean.add(answerBoolean);
        this.known.add(false);  // initial value
        this.answerString.add("Correct answer");
    }

    private void addNewQuestion(String question, boolean answerBoolean, String answerString)
    {
        this.question.add(question);
        this.answerBoolean.add(answerBoolean);
        this.known.add(false);  // initial value
        this.answerString.add(answerString);
    }

    private void deleteQuestion()
    {
        if(question.size() == 1) {

            Intent intent = new Intent(getApplicationContext(), EndScreen.class);

            startActivity(intent);
            finish();
            return;
        }
        else
        {
            question.remove(actualQuestionNumber);
            answerBoolean.remove(actualQuestionNumber);
            known.remove(actualQuestionNumber);
            answerString.remove(actualQuestionNumber);
        }
    }

    private void displayNumOfNewThings()
    {
        TextView number = (TextView) findViewById(
                R.id.number_new_things_text_view);
        number.setText("New things learned: " + numberOfLearnedQuestions);
    }

    private boolean isTrue()
    {
        return answerBoolean.get(actualQuestionNumber);
    }

    private void isKnown()
    {
        if(known.get(actualQuestionNumber))
        {
            ++numberOfLearnedQuestions;

            Context context = getApplicationContext();
            CharSequence text = "Correct answer - finally U have learned it!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else
        {
            Context context = getApplicationContext();
            CharSequence text = "Correct answer - U really know that!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        deleteQuestion();
    }

    private void uFuckedUp()
    {
        Context context = getApplicationContext();
        CharSequence text = "WRONG";
        if(answerString.get(actualQuestionNumber) != "Correct answer")
            text = text + ", correct answer: " + answerString.get(actualQuestionNumber);
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        known.set(actualQuestionNumber, true);
    }

    private void displayQuestion() {
        TextView questionTextView = (TextView) findViewById(R.id.question_text_view);
        if(question.get(actualQuestionNumber) != null)
            questionTextView.setText(question.get(actualQuestionNumber));
        displayNumOfNewThings();
    }

    private void nextQuestion()
    {
        if(numberOfLearnedQuestions >= 5)
        {
            numberOfLearnedQuestions = 0;

            Intent intent = new Intent(getApplicationContext(), Learned5Things.class);
            startActivity(intent);

            deleteQuestion();
        }

        if(question.size() > 1)
        {
            Random generator = new Random();
            actualQuestionNumber = generator.nextInt(question.size() - 1);
        }
        else actualQuestionNumber = 0;

        displayQuestion();
    }

    public void clickedTrue(View view)
    {
        if(isTrue()) isKnown();
        else uFuckedUp();

        nextQuestion();
    }

    public void clickedFalse(View view)
    {
        if (!isTrue()) isKnown();
        else uFuckedUp();

        nextQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        // standard questions to answer
        addNewQuestion("Walt Disney was born in 1901.", true);
        addNewQuestion("Canberra is the capital of Australia.", true);
        addNewQuestion("Developer, who wrote this app was born in 1996.", false, "1994");
        addNewQuestion("Developer, who wrote this app likes pizza.", true);
        addNewQuestion("There are 29 countries in EU.", false, "28");
        addNewQuestion("The biggest importer of gold is United Kingdom.", false, "India");
        addNewQuestion("Jupiter has 67 moons.", true);
        addNewQuestion("Difference between 'a' and 'A' in ASCII code equals 31", false, "32");
        addNewQuestion("Hex-code of white is #000000", false, "#FFFFFF");
        addNewQuestion("Massachusetts Institute of Technology is known as the best technical " +
                "university in the world.", true);
        addNewQuestion("Bangladesh is in Africa", false, "Asia");
        addNewQuestion("Google was founded in 1998", true);
        addNewQuestion("Larry Page was one of founders of Facebook.", false, "Google");
        addNewQuestion("Special theory of relativity was proposed in " +
                "1905 by Albert Einstein.", true);
        addNewQuestion("The Great Fire of Rome took place in 46 AD", false, "64 AD");

        // start the game
        nextQuestion();
    }

}
