package edu.osu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Add_Closet_Item_Activity extends AppCompatActivity {
    private Spinner spinner1, spinner2;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_closet_item);

        addListenerOnButton();
    }


        // get the selected dropdown list value
        public void addListenerOnButton() {

            spinner1 = (Spinner) findViewById(R.id.spinner1);
            spinner2 = (Spinner) findViewById(R.id.spinner2);
            btnSubmit = (Button) findViewById(R.id.btnSubmit);

            btnSubmit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                }

            });
        }



}
