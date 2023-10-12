package com.example.yogafit_app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class barbie_training_list_tab1 extends AppCompatActivity {

    ListView listViewww;
    private DatabaseHelper_tabl mDBHelper_barbie;
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbie_training_list_tab1);

        tableName = "barbietrainings";

        mDBHelper_barbie = new DatabaseHelper_tabl(this, "barbie_yogas.db", null, 7);
        mDBHelper_barbie.openDb();


        Cursor cursor = mDBHelper_barbie.getExs(tableName);
        cursor.moveToFirst();

        ArrayList<HashMap<String, Object>> tableName = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> Name;

        //-----------------------------------выбираем из базы данных, и вставляем в нужные места ----------------------------
        while (!cursor.isAfterLast()) {
            Name = new HashMap<String, Object>();

            Name.put("_id_ex",  cursor.getString(0));
            Name.put("name_ex",  cursor.getString(1));//---выбираем в таблице столбец 1 или 2 или 3 или 4 или 5
            Name.put("diff_ex",  cursor.getString(3));
            Name.put("gif_id_ex",  cursor.getString(5));
            Name.put("age_time_ex",  cursor.getString(2));
            Name.put("text_ex",  cursor.getString(4));
            Name.put("iooo_ex",  cursor.getString(6));
            tableName.add(Name);

            cursor.moveToNext();
        }
        cursor.close();

        String[] from = { "_id_ex", "name_ex", "diff_ex", "gif_id_ex"};//----------------выбираем столбец 1 или 2 или 3 или 4 или 5
        //Drawable[] from = {"gif_id_ex"}
        int[] to = { R.id._id_ex, R.id.name_ex_exs_list_tab2, R.id.diff_ex_exs_list_tab2, R.id.gif_id_ex_exs_list_tab2};//------------поле для вставки в проект из столбцов таблицы 1 или 2 или 3 или 4 или 5

        SimpleAdapter adapter = new SimpleAdapter(this, tableName, R.layout.adapter_item, from, to);
        ListView listView = (ListView) findViewById(R.id.barbie_listView_exs_list_tab2);
        listView.setAdapter(adapter);

        SimpleAdapter.ViewBinder viewBinder = new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view.getId() == R.id.gif_id_ex_exs_list_tab2) {
                    ImageView imageView = (ImageView) view;
                    String imageName = data.toString();
                    int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                    imageView.setImageResource(resourceId);
                    return true;
                }
                return false;
            }
        };

        adapter.setViewBinder(viewBinder);


        listViewww = findViewById(R.id.barbie_listView_exs_list_tab2);


        listViewww.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> selectedItem = (HashMap<String, Object>) parent.getItemAtPosition(position);

                String name = selectedItem.get("name_ex").toString();
                String diff = selectedItem.get("diff_ex").toString();
                String gifId = selectedItem.get("gif_id_ex").toString();
                String ageTime = selectedItem.get("age_time_ex").toString();
                String text = selectedItem.get("text_ex").toString();
                String iooo = selectedItem.get("iooo_ex").toString();

                Intent intent = new Intent(barbie_training_list_tab1.this, barbie_training_list_tab1_details.class);

                intent.putExtra("name_ex", name);
                intent.putExtra("diff_ex", diff);
                intent.putExtra("gif_id_ex", gifId);
                intent.putExtra("age_time_ex", ageTime);
                intent.putExtra("text_ex", text);
                intent.putExtra("iooo_ex", iooo);

                intent.putExtra("selected_position", position);

                startActivity(intent);
            }
        });
        mDBHelper_barbie.closeDb();
    }

    //---------------------------------------кнопка назад-------------------------------------------
    public void barbie_goback(View view) {

        Intent intent = new Intent(barbie_training_list_tab1.this, MainActivity.class);
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

        Intent intent = new Intent(barbie_training_list_tab1.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}