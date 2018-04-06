package net.androidbootcamp.courseproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        EditText bacOutput = (EditText)findViewById(R.id.bacOutput);
        EditText output08 = (EditText)findViewById(R.id.output08);
        EditText output04 = (EditText)findViewById(R.id.output04);
        EditText output00 = (EditText)findViewById(R.id.output00);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        long sexLong = sharedPref.getLong("key1", 0); // get long
        double sex = Double.longBitsToDouble(sexLong); // convert long back to double
        int userWeight = sharedPref.getInt("key2", 0);
        long typeLong = sharedPref.getLong("key3", 0); // get long
        double typeOfDrink = Double.longBitsToDouble(typeLong); // convert long back to double
        int numberOfDrinks = sharedPref.getInt("key4", 0);
        long timeLong = sharedPref.getLong("key5", 0); // get long
        double elapsedTime = Double.longBitsToDouble(timeLong); // convert long back to double
        DecimalFormat bacFormat = new DecimalFormat("##.###"); // reduce to two places
        bacFormat.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormat reason = new DecimalFormat("##.##"); // reduce to two places
        reason.setRoundingMode(RoundingMode.HALF_UP);
        double bac;     // bacOutput
        double drunk;   // output08
        double tipsy;   // output04
        double sober;   // output00
    // here comes the magic.
    // the number 14 represents the number of grams of alcohol in a standard drink
        // we will be using 28 because our alcohol content is in ounces
    // multiply this by the number representing the type compared to standard and the quantity
    // then divide by (body weight in pounds time 454) to convert to grams, multiplied by the gender constant (sex)
    // multiply by 100 to get BAC %
    // finally, account for the time that's passed while drinking
        double tempBAC = ((numberOfDrinks * typeOfDrink * 28) / (userWeight * 454 * sex)) * 100;
        bac = tempBAC - (elapsedTime * 0.015);
        if (bac < 0){
            bac = 0;
        }
        bacOutput.setText(bacFormat.format(bac) + " %"); // set BAC output field
        if (bac > 0.08){
            drunk = Math.abs(0.08 - bac) / 0.015;
            tipsy = Math.abs(0.04 - bac) / 0.015;
            sober = Math.abs(0.00 - bac) / 0.015;
            output08.setText(reason.format(drunk) + " hours");
            output04.setText(reason.format(tipsy) + " hours");
            output00.setText(reason.format(sober) + " hours");
            Toast.makeText(getBaseContext(), "DO NOT DRIVE!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "You are legally intoxicated.", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "Find somewhere to sleep it off.", Toast.LENGTH_SHORT).show();
        } else if (bac <= 0.08 && bac > 0.04){
            drunk = 0;
            tipsy = Math.abs(0.04 - bac) / 0.015;
            sober = Math.abs(0.00 - bac) / 0.015;
            output08.setText(reason.format(drunk) + " hours - Clear!");
            output04.setText(reason.format(tipsy) + " hours");
            output00.setText(reason.format(sober) + " hours");
            Toast.makeText(getBaseContext(), "Drink some water and DON'T drive.", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "Call an Uber, or ride with a friend.", Toast.LENGTH_SHORT).show();
        } else if (bac <= 0.04 && bac > 0) {
            drunk = 0;
            tipsy = 0;
            sober = Math.abs(0.00 - bac) / 0.015;
            output08.setText(reason.format(drunk) + " hours - Clear!");
            output04.setText(reason.format(tipsy) + " hours - Clear!");
            output00.setText(reason.format(sober) + " hours");
            Toast.makeText(getBaseContext(), "Don't risk it!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "You're almost there!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "Drink some water and chill out.", Toast.LENGTH_SHORT).show();
        } else {
            drunk = 0;
            tipsy = 0;
            sober = 0;
            output08.setText(reason.format(drunk) + " hours - Clear!");
            output04.setText(reason.format(tipsy) + " hours - Clear!");
            output00.setText(reason.format(sober) + " hours - Clear!");
            Toast.makeText(getBaseContext(), "Get going, you're sober!", Toast.LENGTH_SHORT).show();
        }

        Button btnReset = (Button)findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(
                                getBaseContext().getPackageName()).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}
