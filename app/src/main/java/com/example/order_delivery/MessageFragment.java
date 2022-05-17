package com.example.order_delivery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CpuUsageInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_delivery.local_model.CurrentUserInfo;
import com.example.order_delivery.model.Notification;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
    This class allows messaging between customer employees and managers
 */
public class MessageFragment extends Fragment {
    private TextView tvMessageType;
    private EditText etMsgSubject;
    private EditText etMsgMessage;
    private ImageButton ibMsgForward;
    private String type;
    private String reportedPerson;
    private String filedPerson;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvMessageType = view.findViewById(R.id.tvMessageType);
        etMsgSubject = view.findViewById(R.id.etMsgSubject);
        etMsgMessage = view.findViewById(R.id.etMsgMessage);
        ibMsgForward = view.findViewById(R.id.ibMsgForward);
        type = getArguments().getString("type");
        filedPerson = getArguments().getString("filedPerson");
        reportedPerson = getArguments().getString("reportedPerson");


        tvMessageType.setText(type + " to " + reportedPerson);
        ibMsgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etMsgMessage.getText().toString().length() == 0 || etMsgSubject.getText().toString().length() == 0){
                    Toast.makeText(getContext(), "Missing subject or message", Toast.LENGTH_SHORT).show();;
                }
                else{
                    String sentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    //send message to manager
                    Notification notification = new Notification();
                    String complaintId = LoginActivity.loginUsername + sentTime;
                    notification.setType(type);
                    notification.setSubject(etMsgSubject.getText().toString());
                    notification.setMessage(etMsgMessage.getText().toString());
                    notification.setFromUser(filedPerson);
                    notification.setTarget(reportedPerson);
                    notification.setComplaintId(complaintId);
                    notification.setToUsername("manager");
                    notification.setFromUserType(LoginActivity.loginType);
                    notification.saveInBackground();
                    if(type.equals("complaint")){
                        notification = new Notification();
                        notification.setType(type);
                        notification.setSubject(etMsgSubject.getText().toString());
                        notification.setMessage(etMsgMessage.getText().toString());
                        notification.setFromUser(filedPerson);
                        notification.setTarget(reportedPerson);
                        notification.setToUsername(reportedPerson);
                        notification.setFromUserType(LoginActivity.loginType);
                        notification.setComplaintId(complaintId);
                        notification.saveInBackground();
                    }
                    getActivity().onBackPressed();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }
}