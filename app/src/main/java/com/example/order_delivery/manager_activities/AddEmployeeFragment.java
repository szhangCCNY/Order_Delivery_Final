package com.example.order_delivery.manager_activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.order_delivery.R;
import com.example.order_delivery.model.Employee;
import com.parse.ParseException;
import com.parse.SaveCallback;

/*
    This class allows the manager to add employees
    employers must set a employee username, name, title, salary
 */
public class AddEmployeeFragment extends Fragment {

    private EditText etEmployeeUserName;
    private EditText etEmployeeName;
    private EditText etEmployeeTitle;
    private EditText etEmployeeSalary;
    private Button btnEmployeeAddSubmit;
    private Employee newEmployee;


    public AddEmployeeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEmployeeUserName = view.findViewById(R.id.etEmployeeUserName);
        etEmployeeName = view.findViewById(R.id.etEmployeeName);
        etEmployeeTitle = view.findViewById(R.id.etEmployeeTitle);
        etEmployeeSalary = view.findViewById(R.id.etEmployeeSalary);
        btnEmployeeAddSubmit = view.findViewById(R.id.btnEmployeeAddSubmit);
        newEmployee = new Employee();

        btnEmployeeAddSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmployUsername = etEmployeeUserName.getText().toString();
                String newEmployName = etEmployeeName.getText().toString();
                String newEmployTitle = etEmployeeTitle.getText().toString();
                String newEmploySalaryStr = etEmployeeSalary.getText().toString();


                //the following lines check validity of the new employee information
                if(newEmployUsername.length() == 0){
                    Toast.makeText(getContext(), "Employee needs a username", Toast.LENGTH_SHORT).show();
                }
                else if(newEmployName.length() == 0){
                    Toast.makeText(getContext(), "Employee needs a name", Toast.LENGTH_SHORT).show();
                }
                else if(newEmployTitle.length() == 0){
                    Toast.makeText(getContext(), "Employee needs a title", Toast.LENGTH_SHORT).show();
                }
                else if(newEmploySalaryStr.length() == 0){
                    Toast.makeText(getContext(), "Employee needs a salary", Toast.LENGTH_SHORT).show();
                }
                else {
                    //all fields are complete
                    //save item
                    Double newEmploySalary = (double) 0;
                    try{
                        newEmploySalary = Double.parseDouble(etEmployeeSalary.getText().toString());
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(getContext(), "Please enter a valid salary", Toast.LENGTH_SHORT).show();
                    }
                    if (newEmploySalary > 0) {
                        newEmployee.setNewEmployee(newEmployUsername, newEmployName, newEmployTitle, newEmploySalary);
                        newEmployee.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Toast.makeText(getContext(), "failed to save employee", Toast.LENGTH_SHORT).show();
                                } else {
                                    //employee saved
                                    //go back to employee fragment list
                                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                    // Create the CategoryDetailsFragment
                                    Fragment employListFrag = new EmployListFragment();
                                    activity.
                                            getSupportFragmentManager()
                                            .beginTransaction()
                                            //flmenucontainer in activity menu
                                            .replace(R.id.flManagerContainer, employListFrag)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(getContext(), "Salary cannot be negative", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_employee, container, false);
    }
}