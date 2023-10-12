package com.example.yogafit_app;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class barbie_training_list_tab1_details extends AppCompatActivity {

    private long mTimeLeftInMillis;
    private long START_TIME_IN_MILLIS;

    private TextView mTextViewCountDown;
    private TextView mNameTextView;
    private ImageView mGifImageView;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private DatabaseHelper_tabl mDBHelper_barbie;
    private SQLiteDatabase mDb;
    private List<HashMap<String, Object>> exercises;
    private int currentExerciseIndex;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    String tableName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbie_training_list_tab1_details);

        tableName = "barbietrainings";

        mDBHelper_barbie = new DatabaseHelper_tabl(this, "barbie_yogas.db", null, 7);
        mDBHelper_barbie.openDb();

        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            currentExerciseIndex = intent.getExtras().getInt("selected_position");
        } else {
            currentExerciseIndex = 0;
        }

        try {
            mDb = mDBHelper_barbie.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Cursor cursor = mDBHelper_barbie.getExs(tableName);
        exercises = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            HashMap<String, Object> exercise = new HashMap<String, Object>();

            exercise.put("_id_ex", cursor.getString(0));
            exercise.put("name_ex", cursor.getString(1));
            exercise.put("diff_ex", cursor.getString(3));
            exercise.put("gif_id_ex", cursor.getString(5));
            exercise.put("age_time_ex", cursor.getString(2));
            exercise.put("text_ex", cursor.getString(4));
            exercise.put("iooo_ex", cursor.getString(6));

            exercises.add(exercise);
            cursor.moveToNext();
        }
        cursor.close();

        //currentExerciseIndex = 0;//упражнение с которого начинаем

        updateUIForCurrentExercise();

        mButtonStartPause = findViewById(R.id.barbie_button_start_pause);
        mButtonReset = findViewById(R.id.barbie_button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void updateUIForCurrentExercise() {
        HashMap<String, Object> currentExercise = exercises.get(currentExerciseIndex);

        mNameTextView = findViewById(R.id.barbie_name_ex_exs_list_tab2_detail);
        mGifImageView = findViewById(R.id.barbie_gif_id_ex_exs_list_tab2_detail);
        mTextViewCountDown = findViewById(R.id.barbie_text_view_countdown);

        String name = (String) currentExercise.get("name_ex");
        String gifId = (String) currentExercise.get("gif_id_ex");
        String iooo = (String) currentExercise.get("iooo_ex");

        int resourceId = getResources().getIdentifier(gifId, "drawable", getPackageName());

        mNameTextView.setText(name);
        mTextViewCountDown.setText(iooo);

        if (resourceId != 0) {
            mGifImageView.setImageResource(resourceId);
        } else {
            mGifImageView.setImageResource(R.drawable.placeholder_image);
        }

        String tttStr = (String) currentExercise.get("age_time_ex");
        START_TIME_IN_MILLIS = Long.parseLong(tttStr);
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("NEXT EXERCISE");

                currentExerciseIndex = (currentExerciseIndex + 1) % exercises.size();
                updateUIForCurrentExercise();

                resetTimer();
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    //---------------------------------------кнопка назад-------------------------------------------
    public void barbie_details_goback(View view) {

        Intent intent = new Intent(barbie_training_list_tab1_details.this, barbie_training_list_tab1.class);
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

        Intent intent = new Intent(barbie_training_list_tab1_details.this, barbie_training_list_tab1.class);
        startActivity(intent);
        finish();

    }


}