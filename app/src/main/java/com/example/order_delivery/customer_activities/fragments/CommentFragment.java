package com.example.order_delivery.customer_activities.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.R;
import com.example.order_delivery.adapters.CommentsAdapter;
import com.example.order_delivery.model.Comments;
import com.example.order_delivery.model.Employee;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/*
    This class is the comment screen for the customer
    customer is able to see comments of items or employees
 */
public class CommentFragment extends Fragment {

    private RecyclerView rvComments;
    private Employee commentEmployee;
    private sz_item_cust commentItem;
    private List<Comments> all_item;
    private CommentsAdapter commentsAdapter;
    private Button btnAddComment;
    private Button btnActivityCommentBack;
    private int codeReceived;

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        all_item = new ArrayList<>();
//        Intent intent = getIntent();
//        itemName = intent.getExtras().getString("itemName");
        codeReceived = getArguments().getInt("Code");
        if (codeReceived == 0){

            commentItem = new sz_item_cust();
            commentItem = getArguments().getParcelable("Item");

        }
        else if (codeReceived == 1){
            commentEmployee = new Employee();
            commentEmployee = getArguments().getParcelable("Employee");
        }

        rvComments = view.findViewById(R.id.rvComments);
        queryPosts();
        commentsAdapter = new CommentsAdapter(getContext(), all_item);
        rvComments.setHasFixedSize(true);
        rvComments.setAdapter(commentsAdapter);
        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAddComment = view.findViewById(R.id.btnAddComment);
        btnActivityCommentBack = view.findViewById(R.id.btnActivityCommentBack);
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                // Create the CategoryDetailsFragment
                Fragment addCommentFragment = new AddCommentFragment();
                Bundle bundle = new Bundle();
                if(codeReceived == 0){
                    bundle.putInt("Code", 0);
                    bundle.putParcelable("commentItem", commentItem);
                }
                else if (codeReceived == 1){
                    bundle.putInt("Code", 1);
                    bundle.putParcelable("commentEmployee", commentEmployee);
                }
                addCommentFragment.setArguments(bundle);
                activity.
                        getSupportFragmentManager()
                        .beginTransaction()
                        //flmenucontainer in activity menu
                        .replace(R.id.flMenuContainer, addCommentFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

//        btnAddComment = view.findViewById(R.id.btnAddComment);
//        btnActivityCommentBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//            }
//        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    private void queryPosts() {
        ParseQuery<Comments> query = ParseQuery.getQuery(Comments.class);
        if(codeReceived == 0){
            System.out.println(codeReceived);
            System.out.println(commentItem.getItemName());
            query.whereEqualTo("itemName", commentItem.getItemName());
        }
        else if(codeReceived == 1){
            query.whereEqualTo("itemName", commentEmployee.getUsername());
        }
        query.findInBackground(new FindCallback<Comments>() {
            @Override
            public void done(List<Comments> items, ParseException e) {
                if (e != null){
                    Log.e("Comments Activity", "Issue with getting posts", e);
                    return;
                }
                for (Comments item: items){
                    Log.i("Comments Activity", "item:" + item.getItemName());
                }
                all_item.addAll(items);
                commentsAdapter.notifyDataSetChanged();
            }
        });
    }
}