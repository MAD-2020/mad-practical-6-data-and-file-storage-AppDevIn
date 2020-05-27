package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    /*
        1. This is the main page for user to log in
        2. The user can enter - Username and Password
        3. The user login is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user does not exist. This loads the level selection page.
        4. There is an option to create a new user account. This loads the create user page.
     */
    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    EditText mEtUsername, mEtPassword;
    Button mBtnLogin;
    TextView mTxtCreate;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Hint:
            This method creates the necessary login inputs and the new user creation ontouch.
            It also does the checks on button selected.
            Log.v(TAG, FILENAME + ": Create new user!");
            Log.v(TAG, FILENAME + ": Logging in with: " + etUsername.getText().toString() + ": " + etPassword.getText().toString());
            Log.v(TAG, FILENAME + ": Valid User! Logging in");
            Log.v(TAG, FILENAME + ": Invalid user!");

        */

        mEtUsername = findViewById(R.id.edUsername);
        mEtPassword = findViewById(R.id.edPassword);
        mBtnLogin = findViewById(R.id.btnLogin);
        mTxtCreate = findViewById(R.id.txtNewAccount);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, FILENAME + ": Logging in with: " + mEtUsername.getText().toString() + ": " + mEtPassword.getText().toString());

                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();

                if(isValidUser(username, password)){
                    Log.v(TAG, FILENAME + ": Valid User! Logging in");

                    MyDBHandler myDBHandler = new MyDBHandler(MainActivity.this);
                    UserData userData = myDBHandler.findUser(username);

                    moveToLevels(userData);
                }else{
                    Log.v(TAG, FILENAME + ": Invalid user!");

                    Toast.makeText(MainActivity.this, "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                }

            }
        });


        mTxtCreate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.v(TAG, FILENAME + ": Create new user!");

                moveToCreate();

                return false;
            }
        });







    }

    protected void onStop(){
        super.onStop();
        finish();
    }

    public boolean isValidUser(String userName, String password){

        /* HINT:
            This method is called to access the database and return a true if user is valid and false if not.
            Log.v(TAG, FILENAME + ": Running Checks..." + dbData.getMyUserName() + ": " + dbData.getMyPassword() +" <--> "+ userName + " " + password);
            You may choose to use this or modify to suit your design.
         */


        MyDBHandler myDBHandler = new MyDBHandler(this);
        UserData userData = myDBHandler.findUser(userName);

        //TODO: QUESTION
//        Log.v(TAG, FILENAME + ": Running Checks..." + dbData.getMyUserName() + ": " + dbData.getMyPassword() +" <--> "+ userName + " " + password);

        return userData != null;

    }

    private void moveToCreate(){

        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);

    }

    private void moveToLevels(UserData userData){


        Intent intent = new Intent(MainActivity.this, Main3Activity.class);


        intent.putExtra("myUser", userData);
        startActivity(intent);
    }

}
