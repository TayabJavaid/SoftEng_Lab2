package com.example.listcity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addButton, deleteButton;
    String cityClicked = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka"};
        dataList = new ArrayList<>(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        //onClickListener for add
        addButton.setOnClickListener(v -> promptForAdd());

        //onItemClickListener for tracking selected city
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            cityClicked = dataList.get(position); //update selectedvar
        });

        //onClickListener for delete
        deleteButton.setOnClickListener(v -> deleteSelectedCity());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void promptForAdd() {
        final EditText input = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Add City").setView(input)
                .setPositiveButton("OK", (dialog1, which) -> {
                    String cityName = input.getText().toString();
                    if (!cityName.isEmpty()) {
                        dataList.add(cityName);
                        cityAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", (dialog12, which) -> dialog12.cancel())
                .create();
        dialog.show();
    }

    private void deleteSelectedCity() {
        if (cityClicked != null) {
            dataList.remove(cityClicked);
            cityAdapter.notifyDataSetChanged(); // Update the list
            cityClicked = null;
        }
    }
}