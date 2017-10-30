package info.androidhive.firebase;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import info.androidhive.firebase.model.Rating;

import static android.content.ContentValues.TAG;


public class RatingFragment extends DialogFragment {

    Button confirm;
    RatingBar ratingBar;
    String count;
    Double newCount;
    private FirebaseAuth auth;
    private DatabaseReference mRef;
    private DatabaseReference mCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rating, container, false);
        getDialog().setTitle("Simple Dialog");

        confirm = (Button) rootView.findViewById(R.id.confirm);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int movieRating = Math.round(ratingBar.getRating());
                auth = FirebaseAuth.getInstance();
                mRef = FirebaseDatabase.getInstance().getReference("Ratings");

                Rating ratingObj = new Rating(123, movieRating, 1111);

                dismiss();
                mRef.child(mRef.push().getKey()).setValue(ratingObj);
            }
        });

        return rootView;
    }
}