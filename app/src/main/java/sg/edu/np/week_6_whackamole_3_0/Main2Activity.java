package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    /* Hint:
        1. This is the create new user page for user to log in
        2. The user can enter - Username and Password
        3. The user create is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user already exists.
        4. For the purpose the practical, successful creation of new account will send the user
           back to the login page and display the "User account created successfully".
           the page remains if the user already exists and "User already exist" toastbox message will appear.
        5. There is an option to cancel. This loads the login user page.
     */


    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    EditText mEtUsername, mEtPassword;
    Button mBtnCreate, mBtnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /* Hint:
            This prepares the create and cancel account buttons and interacts with the database to determine
            if the new user created exists already or is new.
            If it exists, information is displayed to notify the user.
            If it does not exist, the user is created in the DB with default data "0" for all levels
            and the login page is loaded.

            Log.v(TAG, FILENAME + ": New user created successfully!");
            Log.v(TAG, FILENAME + ": User already exist during new user creation!");

         */

        mEtUsername = findViewById(R.id.edUsername);
        mEtPassword = findViewById(R.id.edPassword);
        mBtnCreate = findViewById(R.id.btnCreate);
        mBtnCancel = findViewById(R.id.btnCancel);
        
        
        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                
                
                if(!hasValidUser(username)){

                    UserData userData = new UserData(
                            username,
                            password,
                            new ArrayList<Integer>( Arrays.asList(1,2,3,4,5,6,7,8,9,10)),
                            new ArrayList<Integer>( Arrays.asList(0,0,0,0,0,0,0,0,0,0))
                            );
                    
                    userData.setMyUserName(username);
                    userData.setMyPassword(password);
                    
                    MyDBHandler myDBHandler = new MyDBHandler(Main2Activity.this);
                    myDBHandler.addUser(userData);

                    Toast.makeText(Main2Activity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, FILENAME + ": New user created successfully!");

                    moveToLogin();
                }else{

                    Toast.makeText(Main2Activity.this, "User Already Exist. Please Try Again", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, FILENAME + ": User already exist during new user creation!");
                }
            }
        });
        
        
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToLogin();
            }
        });
        
        
    }

    protected void onStop() {
        super.onStop();
        finish();
    }


    public boolean hasValidUser(String userName){

        /* HINT:
            This method is called to access the database and return a true if user is valid and false if not.
            Log.v(TAG, FILENAME + ": Running Checks..." + dbData.getMyUserName() + ": " + dbData.getMyPassword() +" <--> "+ userName + " " + password);
            You may choose to use this or modify to suit your design.
         */


        MyDBHandler myDBHandler = new MyDBHandler(this);
        UserData userData = myDBHandler.findUser(userName);

        return userData != null;

    }

    private void moveToLogin(){

        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(intent);

    }
}
