package com.example.order_delivery.customer_activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.order_delivery.R;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.example.order_delivery.model.Comments;
import com.example.order_delivery.model.Employee;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.ParseException;
import com.parse.SaveCallback;

/*
    This activity allows customer to add comments to employee or food item
 */
public class AddCommentFragment extends Fragment {
    private String itemName;
    private Employee commentEmployee;
    private sz_item_cust commentItem;
    private RatingBar rbRating;
    private EditText etComments;
    private Button btnSubmitComment;
    private Button btnAddCommentBack;
    private int codeReceived;
    private final int ITEM_COMMENT_CODE = 0;
    private final int EMPLOYEE_COMMENT_CODE = 1;
    public AddCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rbRating = view.findViewById(R.id.rbRating);
        etComments = view.findViewById(R.id.etComments);
        btnSubmitComment = view.findViewById(R.id.btnSubmitComment);
        btnAddCommentBack = view.findViewById(R.id.btnAddCommentBack);
        codeReceived = getArguments().getInt("Code");
        if(codeReceived == ITEM_COMMENT_CODE){
            System.out.println(codeReceived);
            commentItem = new sz_item_cust();
            commentItem = getArguments().getParcelable("commentItem");

        }
        else if(codeReceived == EMPLOYEE_COMMENT_CODE){
            commentEmployee = new Employee();
            commentEmployee = getArguments().getParcelable("commentEmployee");
            System.out.println(codeReceived);
            System.out.println(commentEmployee.getUsername());
        }


        btnSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitCommentClick();
            }
        });

        btnAddCommentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToCommentFragment();
            }
        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_comment, container, false);
    }


    //this method updates rating of food or items based on customer rating when submit
    public void onSubmitCommentClick() {
        Comments comment = new Comments();
        System.out.println(itemName);
        comment.setUsername(CurrentUserInfo.currentUserName);

        //these codes are used to check whether received is employee or item
        int pastRatingCount = 0;
        double pastAvgRate = 0;
        if(codeReceived == 0){
            pastRatingCount = commentItem.getRatingCount();
            pastAvgRate = commentItem.getItemRating();
        }
        if(codeReceived == 1){
            pastRatingCount = commentEmployee.getRateCount();
            pastAvgRate = commentEmployee.getRating();
        }
        //get Employee past rating and rating count

        if(!CurrentUserInfo.currentUserVip){
            //for nonvip
            comment.setStatus("register");
//            comment.setWeight(1);
        }
        else{
            //for vip
            comment.setStatus("vip");
//            comment.setWeight(2);
        }

        if(rbRating.getRating() != 0){
            comment.setRating((int) rbRating.getRating());
        }
        else{
            //rating == 0
            Toast.makeText(getContext(), "Please leave a rating", Toast.LENGTH_SHORT).show();
        }

        if(etComments.getText().toString() != null){
            comment.setComment(etComments.getText().toString());
        }
        else{
            Toast.makeText(getContext(), "Please leave a comment", Toast.LENGTH_SHORT).show();
        }

        if(etComments.getText().toString() != null && rbRating.getRating() != 0){
            double newRateAvg = averageRating(pastAvgRate, pastRatingCount, (int) rbRating.getRating());
            //save rating of dish
            //return to comment activity
            //save new rating
            //checkwhich one to save
            if (codeReceived == 0){
                comment.setItemName(commentItem.getItemName());
                comment.saveInBackground();
                commentItem.setRatingCount(pastRatingCount + 1);
                commentItem.setItemRating(newRateAvg);
                commentItem.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            Toast.makeText(getContext(), "comment failed to save", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            returnToCommentFragment();
                        }
                    }
                });
            }
            else if(codeReceived == 1){
                comment.setItemName(commentEmployee.getUsername());
                comment.saveInBackground();
                commentEmployee.setRateCount(pastRatingCount + 1);
                commentEmployee.setRating(newRateAvg);
                commentEmployee.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            Toast.makeText(getContext(), "comment failed to save", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            returnToCommentFragment();
                        }
                    }
                });

            }
            System.out.println(newRateAvg);
        }
    }


    public void returnToCommentFragment(){

        AppCompatActivity activity = (AppCompatActivity) getContext();
        // Create the CategoryDetailsFragment
        Fragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        //pass employee back to comment fragment
        if(codeReceived == 0){
            bundle.putInt("Code", 0);
            bundle.putParcelable("Item", commentItem);
        }
        else if(codeReceived == 1){
            bundle.putInt("Code", 1);
            bundle.putParcelable("Employee", commentEmployee);
        }
        commentFragment.setArguments(bundle);
        activity.
                getSupportFragmentManager()
                .beginTransaction()
                //flmenucontainer in activity menu
                .replace(R.id.flMenuContainer, commentFragment)
                .addToBackStack(null)
                .commit();
    }

    public double averageRating(double pastRating, int pastRatingCount, int commentRating){
        double newAverage = 0;
        System.out.println(pastRating + "past rating");
        System.out.println(pastRatingCount + "past rating count");
        System.out.println(commentRating + "comment rating");
        System.out.println("new calculating average");
        newAverage = ((pastRatingCount*pastRating) + commentRating)/(pastRatingCount + 1);
        System.out.println(newAverage + "new calculating average");
        return newAverage;
    }
}