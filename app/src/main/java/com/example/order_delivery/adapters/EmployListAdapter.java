package com.example.order_delivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.R;
import com.example.order_delivery.model.Employee;

import java.util.List;

/*
    This class populates Customer screen of the manager
    manager sees a list of employees
    has option to add and remove employee
 */

public class EmployListAdapter extends RecyclerView.Adapter<EmployListAdapter.ViewHolder> {
    private Context context;
    private List<Employee> item_employ;

    public EmployListAdapter(Context context, List<Employee> item_employ){
        this.context = context;
        this.item_employ = item_employ;

    }
    @NonNull
    @Override
    //this class uses employee_layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employee_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee item = item_employ.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return item_employ.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvEmployListName;
        private TextView tvEmployListPosition;
        private TextView tvEmployListSalary;
        private TextView tvEmployListRating;
        private TextView tvEmployListId;
        private ImageButton ibFireEmployee;
        private ImageButton ibRaiseSalary;
        private ImageButton ibCutSalary;
        //booleans for employee that can be fired, promoted or demoted
        //based on # of warnings and compliments
        private boolean demoteOk = false;
        private boolean promoteOk = false;
        private boolean fireOk = false;

        //
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmployListName = itemView.findViewById(R.id.tvEmployListName);
            tvEmployListPosition = itemView.findViewById(R.id.tvEmployListPosition);
            tvEmployListSalary = itemView.findViewById(R.id.tvEmployListSalary);
            tvEmployListRating = itemView.findViewById(R.id.tvEmployRating);
            tvEmployListId = itemView.findViewById(R.id.tvEmployListId);
            ibFireEmployee = itemView.findViewById(R.id.ibFireEmployee);
            ibRaiseSalary = itemView.findViewById(R.id.ibRaiseSalary);
            ibCutSalary = itemView.findViewById(R.id.ibCutSalary);
        }


        //idea if chef rating is < 2, then manager can demote him her whenever
        public void determineStanding(Employee item){
            if(item.getRating() < 2){
                demoteOk = true;
            }
            else{
                //here find difference of compliment and warning
                //if difference >= 3 then promote ok
                //if difference <= -3 then demote ok
                //if inbetweeen (-2, 2), then neither promote demote ok
                int diff = item.getCompliment() - item.getWarning();
                System.out.println(diff);
                if(!(diff >= 3 || diff <= -3)){
                    demoteOk = false;
                    promoteOk = false;
                }
                else if(diff >= 3){
                    promoteOk = true;
                }
                else{
                    demoteOk = true;
                }
            }
            System.out.println(item.getCompliment());
        }

        //here just check number times employee gets demoted
        public void checkFire(Employee item){
            if(item.getDemoteNum() >= 2){
                fireOk = true;
            }
        }

        public void bind(Employee item) {
            tvEmployListName.setText(item.getName());
            tvEmployListPosition.setText("Position: " + item.getEmployTitle());
            tvEmployListSalary.setText("Salary: " + item.getSalary());
            tvEmployListRating.setText("Rating: " + item.getRating());
            tvEmployListId.setText("ID: " + item.getEmployId());


            ibRaiseSalary.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //check for employee standing
                    determineStanding(item);
                    System.out.println("demote ok: " + demoteOk + "\npromote ok: " + promoteOk);
                    if(promoteOk){
                        double newSalary = item.getSalary() + 100;
                        int newCompliment = item.getCompliment() -3;
                        int newDemoteNum = item.getDemoteNum() - 1;
                        if (newDemoteNum < 0){
                            newDemoteNum = 0;
                        }
                        if(newCompliment < 0){
                            newCompliment = 0;
                        }
                        //save new salary
                        item.setSalary(newSalary);
                        //remove 3 from compliment
                        item.setCompliment(newCompliment);
                        item.setDemoteNum(newDemoteNum);
                        item.saveInBackground();
                        tvEmployListSalary.setText("Salary: " + String.valueOf(newSalary));

                    }
                }
            });
            ibCutSalary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    determineStanding(item);
                    System.out.println("demote ok: " + demoteOk + "\npromote ok: " + promoteOk);
                    if(demoteOk){
                        double newSalary = item.getSalary() - 100;
                        int newWarning = item.getWarning() -3;
                        int newDemoteNum = item.getDemoteNum() + 1;
                        if(newWarning < 0){
                            newWarning = 0;
                        }
                        if (newSalary < 0 ){
                            newSalary = 0;
                        }
                        System.out.println(newDemoteNum);
                        item.setSalary(newSalary);
                        item.setWarning(newWarning);
                        item.setDemoteNum(newDemoteNum);
                        item.saveInBackground();
                        tvEmployListSalary.setText("Salary: " + String.valueOf(newSalary));
                    }

                }
            });
            ibFireEmployee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkFire(item);
                    if (fireOk){
                        item_employ.remove(getAdapterPosition());
                        item.deleteInBackground(); //delete item on server
                        notifyDataSetChanged(); //update recycler view
                    }
                    else{
                        Toast.makeText(context, "Cannot good standing employee", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}