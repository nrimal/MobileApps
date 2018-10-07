package edu.osu.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Quiz_Activity extends AppCompatActivity {

    private Button mTrueButton, mFalseButton, mCheatButton;
    private boolean isCheater;
    private int REQUEST_CODE_CHEAT=0;

    private static final String EXTRA_ANSWER_IS_TRUE = "edu.osu.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "edu.osu.geoquiz.answer_shown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mCheatButton = findViewById(R.id.cheat_button);

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quiz_Activity.this, CheatActivity.class);
                intent.putExtra(EXTRA_ANSWER_IS_TRUE, true);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }
        });


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast temp = Toast.makeText(Quiz_Activity.this,R.string.correct_toast,Toast.LENGTH_SHORT);
                temp.setGravity(Gravity.TOP,0,0);
                temp.show();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
              Toast temp = Toast.makeText(Quiz_Activity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT);
              temp.setGravity(Gravity.TOP,0,0);
              temp.show();

           }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode!= Activity.RESULT_OK){return;}
        if(requestCode == REQUEST_CODE_CHEAT ){
            if(data==null){return;}
            isCheater = CheatActivity.wasAnswerShown(data);
        }
    }
}
