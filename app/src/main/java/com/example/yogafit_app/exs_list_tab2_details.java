package com.example.yogafit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class exs_list_tab2_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exs_list_tab2_details);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name_ex");
        String diff = intent.getStringExtra("diff_ex");
        String gifId = intent.getStringExtra("gif_id_ex");
        String text = intent.getStringExtra("text_ex");
        String iooo = intent.getStringExtra("iooo_ex");

        int resourceId = getResources().getIdentifier(gifId, "drawable", getPackageName());

        TextView nameTextView = findViewById(R.id.name_ex_exs_list_tab2_detail);
        TextView diffTextView = findViewById(R.id.diff_ex_exs_list_tab2_detail);
        ImageView gifImageView = findViewById(R.id.gif_id_ex_exs_list_tab2_detail);
        TextView textTextView = findViewById(R.id.text_time_ex_exs_list_tab2_detail);
        TextView ioooTextView = findViewById(R.id.iooo_ex_exs_list_tab2_detail);


        nameTextView.setText(name);
        diffTextView.setText(diff);
        textTextView.setText(text);
        ioooTextView.setText(iooo);


        if (resourceId != 0) {
            gifImageView.setImageResource(resourceId);
        } else {
            gifImageView.setImageResource(R.drawable.placeholder_image);
        }
    }


    //---------------------------------------кнопка назад-------------------------------------------
    public void exs_details_goback(View view) {

        Intent intent = new Intent(exs_list_tab2_details.this, exs_list_tab2.class);
        startActivity(intent);
        finish();
    }


    /**
     * Called when the activity has detected the user's press of the back
     * key. The {@link #getOnBackPressedDispatcher() OnBackPressedDispatcher} will be given a
     * chance to handle the back button before the default behavior of
     * {@link Activity#onBackPressed()} is invoked.
     *
     * @see #getOnBackPressedDispatcher()
     */
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(exs_list_tab2_details.this, exs_list_tab2.class);
        startActivity(intent);
        finish();

    }
}