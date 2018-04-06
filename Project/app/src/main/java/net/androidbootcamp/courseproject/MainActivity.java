package net.androidbootcamp.courseproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    // global variables
    double sex;
    int userWeight;
    double typeOfDrink;
    int numberOfDrinks;
    double elapsedTime;

    // Type spinner array
    String[] types = {"beer12oz","beer16oz","lightBeer12oz","lightBeer16oz","maltLiquor","ale",
        "porter","stout","brandy","martini","highball","manhattan","sake","rum","gin","vodka",
        "tequila","whiskey","airplaneBottle","wine"};
    Double[] content = {.54, .72, .44, .59, .71, .81, 1.1, 1.3, .9, 1.0, .6, 1.15, .8,
        .645, .645, .645, .645, .645, .7, .55}; // content in ounces

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    // Gender Spinner
        final Spinner gender = (Spinner)findViewById(R.id.gender);
        ArrayAdapter<CharSequence> genAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        genAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genAdapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                //int index = gender.getSelectedItemPosition(); // should just be 'i'
                if (i == 0) {
                    sex = 0.68; // if sex is male
                } else {
                    sex = 0.55; // if sex is female
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });
        final EditText weight = (EditText) findViewById(R.id.weight);
    // Type Spinner
        final Spinner type = (Spinner)findViewById(R.id.type);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                typeOfDrink = content[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });
        final EditText number = (EditText) findViewById(R.id.number);
        final EditText time = (EditText) findViewById(R.id.time);
        Button button = (Button) findViewById(R.id.btnBAC);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        button.setOnClickListener(new View.OnClickListener() {
//            SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
//                return edit.putLong(key, Double.doubleToRawLongBits(value));
//            }
            @Override
            public void onClick(View view) {
                long sexLong = Double.doubleToRawLongBits(sex); // convert double to long for sharedPref
                userWeight = Integer.parseInt(weight.getText().toString()); // int
                long typeLong = Double.doubleToRawLongBits(typeOfDrink); // convert double to long for sharedPref
                numberOfDrinks = Integer.parseInt(number.getText().toString()); // int
                elapsedTime = Double.parseDouble(time.getText().toString()); // double
                long timeLong = Double.doubleToRawLongBits(elapsedTime); // convert double to long for sharedPref
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong("key1", sexLong);
                editor.putInt("key2", userWeight);
                editor.putLong("key3", typeLong);
                editor.putInt("key4", numberOfDrinks);
                editor.putLong("key5", timeLong);
                editor.commit();
                startActivity(new Intent(MainActivity.this, CalcActivity.class));
            }
        });
    }
}
