package com.example.order_delivery;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.adapters.BidAdapter;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.example.order_delivery.model.Bid;
import com.example.order_delivery.model.CompleteOrder;
import com.example.order_delivery.model.Employee;
import com.example.order_delivery.model.Notification;
import com.example.order_delivery.model.sz_customer;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;


/*
    notification adapter populates messages to notification screens
    manager sees buttons to accept deny and dimiss complaints
    manager also notifies party when decision is made
    customer sees complaints statuses
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private List<Notification> item_order;
    public NotificationAdapter(Context context, List<Notification> item_order){
        this.context = context;
        this.item_order = item_order;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification item = item_order.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return item_order.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNotifSubject;
        private TextView tvNotifFrom;
        private TextView tvNotifMessage;
        private TextView tvNotifType;
        //private TextView tvNotifTarget;
        private RelativeLayout llMsgContainer;
        private ImageButton ibNotifAccept;
        private ImageButton ibNotifDeny;
        private ImageButton ibNotifIgnore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotifFrom = itemView.findViewById(R.id.tvNotifFrom);
            tvNotifSubject = itemView.findViewById(R.id.tvNotifSubject);
            tvNotifMessage = itemView.findViewById(R.id.tvNotifMessage);
            tvNotifType = itemView.findViewById(R.id.tvNotifType);
            //tvNotifTarget = itemView.findViewById(R.id.tvNotifTarget);
            llMsgContainer = itemView.findViewById(R.id.llMsgContainer);
            ibNotifAccept = itemView.findViewById(R.id.ibNotifAccept);
            ibNotifDeny = itemView.findViewById(R.id.ibNotifDeny);
            ibNotifIgnore = itemView.findViewById(R.id.ibNotifIgnore);
        }



        public void bind(Notification item) {
            tvNotifFrom.setText(item.getFromUser());
            tvNotifMessage.setText(item.getMessage());
            tvNotifSubject.setText(item.getSubject());
            tvNotifType.setText(item.getType());
            //tvNotifTarget.setText(item.getTarget());
            //this need to be general
            String currentUserType = LoginActivity.loginType;

            //this is important - remember to set onclick for container if type is complaint
            if(!(currentUserType.equals("manager"))){
                ibNotifIgnore.setVisibility(View.GONE);
                ibNotifAccept.setVisibility(View.GONE);
                ibNotifDeny.setVisibility(View.GONE);
            }
            //manager access
            ibNotifAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resolveAccept(item.getFromUserType(), item);
                }
            });

            ibNotifDeny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = "Complaint is denied by the manager";
                    notifyParty(item.getFromUser(), message, item);
                }
            });

            ibNotifIgnore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = "Complaint is dimissed by the manager";
                    notifyParty(item.getFromUser(), message, item);
                }
            });

            llMsgContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tvNotifType.getText().equals("complaint")){
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        Fragment compResponseFragment = new CompResponseFragment();
                        Bundle bundle = new Bundle();
//                        get target and get get type
                        bundle.putString("complaintId", item.getComplaintid());
                        compResponseFragment.setArguments(bundle);
                        System.out.println(LoginActivity.messageType);
                        int currentContainer;
                        if (LoginActivity.loginType.equals("manager")){
                            currentContainer = getContainerId(LoginActivity.loginType);
                        }
                        else{
                            currentContainer = getContainerId(LoginActivity.messageType);
                        }
                        activity.
                                getSupportFragmentManager()
                                .beginTransaction()
                                //flmenucontainer in activity menu
                                .replace(currentContainer, compResponseFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        }
    }

    public int getContainerId(String type){
        if (type.equals("manager")){
            return R.id.flManagerContainer;
        }
        else if(type.equals("delivery")){
            return R.id.flDeliveryContainer;
        }
        //add chef here
        else if(type.equals("chef")){
            return R.id.flChefContainer;
        }
        else{
            return R.id.flMenuContainer;
        }
    }


    // eed function to notify both user


    public void notifyParty(String target, String message, Notification item){
        Notification notification = new Notification();
        notification.setSubject("Regarding your recent complaint issue");
        notification.setFromUser("manager");
        notification.setTarget("");
        notification.setToUsername(target);
        notification.setFromUserType("manager");
        notification.setMessage(message);
        notification.setType("alert");
        notification.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Toast.makeText(context, "issue with requests", Toast.LENGTH_SHORT).show();
                }
                //request save delete notification from list
                item.deleteInBackground();
                item_order.remove(item);
                notifyDataSetChanged();
            }
        });
    }

    public void resolveAccept(String userType, Notification item){
        System.out.println(userType);
        System.out.println(item.getTarget());
        if(userType.equals("employee")){
            ParseQuery<sz_customer> query = ParseQuery.getQuery(sz_customer.class);
            query.whereEqualTo("username", item.getTarget());
            //here manager accepts complaints, hence give warning to the person reeceivng complaint
            query.getFirstInBackground(new GetCallback<sz_customer>() {
                @Override
                public void done(sz_customer object, ParseException e) {
                    System.out.println(object.getName());
                    if(item.getType().equals("complaint")){
                        System.out.println(object.getName());
                        object.setWarning(object.getWarning() + 1);
                        object.saveInBackground();
                        //notify parties
                        notifyParty(object.getUserName(), "You are receiving a warning from a past complaint made against you", item);
                        notifyParty(item.getFromUser(), "Your complaint is accepted", item);
                    }
                    //remove item once complete
                    item.deleteInBackground();
                    item_order.remove(item);
                    notifyDataSetChanged();

                }
            });
        }
        else if(userType.equals("customer")){
            ParseQuery<Employee> query = ParseQuery.getQuery(Employee.class);
            query.whereEqualTo("name", item.getTarget());
            System.out.println(item.getTarget());
            query.getFirstInBackground(new GetCallback<Employee>() {
                @Override
                public void done(Employee object, ParseException e) {
                    if(item.getType().equals("compliment")){
                        System.out.println(object.getCompliment());
                        object.setCompliment(object.getCompliment() + 1);
                        System.out.println(object.getCompliment());
                        object.saveInBackground();
                    }
                    //set notify parties
                    else if (item.getType().equals("complaint")){
                        System.out.println(object.getWarning());
                        object.setWarning(object.getWarning() + 1);
                        System.out.println(object.getWarning());
                        object.saveInBackground();
                        notifyParty(object.getName(), "You are receiving a warning from a past complaint made against you", item);
                        notifyParty(item.getFromUser(), "Your complaint is accepted", item);
                    }
                    //remove item once complete
                    item.deleteInBackground();
                    item_order.remove(item);
                    notifyDataSetChanged();
                }
            });
        }
    }
}