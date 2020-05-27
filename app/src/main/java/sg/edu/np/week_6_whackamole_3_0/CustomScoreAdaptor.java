package sg.edu.np.week_6_whackamole_3_0;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {
    /* Hint:
        1. This is the custom adaptor for the recyclerView list @ levels selection page

     */
    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    UserData mUserData;
    Context mContext;
    public CustomScoreAdaptor(Context context, UserData userdata){
        /* Hint:
        This method takes in the data and readies it for processing.
         */
        this.mContext = context;
        this.mUserData = userdata;
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        /* Hint:
        This method dictates how the viewholder layuout is to be once the viewholder is created.
         */

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_select, parent, false);

        return new CustomScoreViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(final CustomScoreViewHolder holder, final int position){

        /* Hint:
        This method passes the data to the viewholder upon bounded to the viewholder.
        It may also be used to do an onclick listener here to activate upon user level selections.

        Log.v(TAG, FILENAME + " Showing level " + level_list.get(position) + " with highest score: " + score_list.get(position));
        Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + list_members.getMyUserName());
         */

        final ArrayList<Integer> level_list = mUserData.getLevels();
        ArrayList<Integer> score_list = mUserData.getScores();

        Log.v(TAG, FILENAME + " Showing level " + level_list.get(position) + " with highest score: " + score_list.get(position));
        Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + mUserData.getMyUserName());

        holder.mTxtLevel.setText("Level " + level_list.get(position));
        holder.mTxtScore.setText("Highest Score: " + score_list.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToGame(mUserData,position);
            }
        });
    }

    public int getItemCount(){
        /* Hint:
        This method returns the the size of the overall data.
         */

        return mUserData.getLevels().size();
    }

    private void moveToGame(UserData userData,int position){



        Intent intent = new Intent(mContext, Main4Activity.class);
        intent.putExtra("level", mUserData.getLevels().get(position));
        intent.putExtra("myUser", userData);

        mContext.startActivity(intent);
    }
}