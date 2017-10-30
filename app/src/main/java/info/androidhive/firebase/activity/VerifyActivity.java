package info.androidhive.firebase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
//import android.util.Log;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import info.androidhive.firebase.R;
import info.androidhive.firebase.SendEmail;
import info.androidhive.firebase.SignupActivity;

public class VerifyActivity extends AppCompatActivity {

    EditText otpedittext;
    Button verifybutton;
    TextView randomnumber;

    boolean emailVerification = false;
    String emailId;

    SignupActivity signUpActivity = new SignupActivity();

    Random rand = new Random();
    //int n = rand.nextInt(9999) + 1000;
    int n = showRandomInteger(1000, 9999, rand);

    public void goToMainPage()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        verifybutton = (Button) findViewById(R.id.verifyButton);
        otpedittext = (EditText) findViewById(R.id.otpEditText);
        otpedittext.setGravity(Gravity.CENTER_HORIZONTAL);
        randomnumber = (TextView) findViewById(R.id.randomNumber);
        randomnumber.setText(String.valueOf(n));

        Bundle bundle = getIntent().getExtras();
        emailId = bundle.getString("emailIdFromSignUp");

        sendEmail();

        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpedittext.getText().toString().matches(String.valueOf(n)))
                {
                    emailVerification = true;

                    try {
                        goToMainPage();
                    }

                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Log.e("", String.valueOf(n));
                    Log.e("", otpedittext.getText().toString());
                    Toast.makeText(VerifyActivity.this, "You have entered an incorrect OTP", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private static int showRandomInteger(int aStart, int aEnd, Random aRandom){
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * aRandom.nextDouble());
        int randomNumber =  (int)(fraction + aStart);

        return randomNumber;
    }

    public void sendEmail() {
        //Getting content for email
        String email = emailId;
        String subject = "OTP for movie recommendation system";
        String message = "Your OTP is " + n + ". Please use this to verify yourself on the MRS app.";

        //Creating SendMail object
        SendEmail sm = new SendEmail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }
}