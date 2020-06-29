package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Main4Activity extends AppCompatActivity {

    /* Hint:
        1. This creates the Whack-A-Mole layout and starts a countdown to ready the user
        2. The game difficulty is based on the selected level
        3. The levels are with the following difficulties.
            a. Level 1 will have a new mole at each 10000ms.
                - i.e. level 1 - 10000ms
                       level 2 - 9000ms
                       level 3 - 8000ms
                       ...
                       level 10 - 1000ms
            b. Each level up will shorten the time to next mole by 100ms with level 10 as 1000 second per mole.
            c. For level 1 ~ 5, there is only 1 mole.
            d. For level 6 ~ 10, there are 2 moles.
            e. Each location of the mole is randomised.
        4. There is an option return to the login page.
     */
    private static final String FILENAME = "Main4Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    CountDownTimer readyTimer;
    CountDownTimer newMolePlaceTimer;

    int advancedScore;
    Button btnMole, btnNewMole, btnBack;
    TextView txt_score;
    boolean isGamingRunning;
    int mLevel;

    UserData mUserData;


    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

      readyTimer =  new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
                Toast.makeText(getApplicationContext(), String.valueOf(millisUntilFinished / 1000), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT).show();

                placeMoleTimer();
                isGamingRunning = true;
            }
        }.start();
    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */




        newMolePlaceTimer =  new CountDownTimer(11000-(mLevel*1000), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {



            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");


                newMolePlaceTimer.start();
                setNewMole();


            }
        }.start();




    }
    private static final int[] BUTTON_IDS = {
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares level difficulty.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
            It also prepares the back button and updates the user score to the database
            if the back button is selected.
         */


        mLevel = getIntent().getIntExtra("level",1);
        mUserData = (UserData) getIntent().getSerializableExtra("myUser");

        txt_score = findViewById(R.id.textScore);

        txt_score.setText(String.valueOf(advancedScore));
        Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));

        isGamingRunning = false;
        readyTimer();
        setNewMole();


        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToLogin();
            }
        });


        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            final Button button = findViewById(id);

            //Set OnClickListener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isGamingRunning){
                        doCheck(button);
                    }else {
                        Toast.makeText(getApplicationContext(), "Please wait till countdown ends", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }
    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateUserScore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateUserScore();

    }

    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, FILENAME + ": Hit, score added!");
            Log.v(TAG, FILENAME + ": Missed, point deducted!");
            belongs here.
        */

        //Check if hit or miss
        if(btnMole == checkButton || btnNewMole == checkButton){
            advancedScore++;
            Log.v(TAG, "Hit, score added!");
        }else{
            advancedScore--;
            Log.v(TAG, "Missed, score deducted!");
        }

        txt_score.setText(String.valueOf(advancedScore));



    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole. Adds additional mole if the level difficulty is from 6 to 10.
         */

        Log.d(TAG, "New mole added");
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);


        //Set the mole for the button
        btnMole = findViewById(BUTTON_IDS[randomLocation]);
        btnMole.setText(R.string.mole);


        if(mLevel >= 6){
            int newRandomLocation;
            do{

                newRandomLocation = ran.nextInt(9);
            }
            while(randomLocation == newRandomLocation);

            btnNewMole = findViewById(BUTTON_IDS[newRandomLocation]);
            btnNewMole.setText(R.string.mole);

        }




        //Set the empty in the buttons
        for(final int id : BUTTON_IDS){

            Button button = findViewById(id);
            if(button != btnMole && button != btnNewMole){
                button.setText(R.string.empty);
            }

        }

    }

    private void updateUserScore()
    {

     /* Hint:
        This updates the user score to the database if needed. Also stops the timers.
        Log.v(TAG, FILENAME + ": Update User Score...");
      */

        Log.v(TAG, FILENAME + ": Update User Score...");


        MyDBHandler myDBHandler = new MyDBHandler(this);
        myDBHandler.deleteAccount(mUserData.getMyUserName());

        mUserData.getScores().set(mLevel-1, advancedScore);
        Log.d(TAG, "updateUserScore: " + mUserData.getScores());


        myDBHandler.addUser(mUserData);

        newMolePlaceTimer.cancel();
        readyTimer.cancel();

    }


    private void moveToLogin(){

        Intent intent = new Intent(Main4Activity.this, MainActivity.class);
        startActivity(intent);

    }
}


