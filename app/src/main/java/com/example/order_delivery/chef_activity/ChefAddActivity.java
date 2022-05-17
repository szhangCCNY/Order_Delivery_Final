package com.example.order_delivery.chef_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.order_delivery.R;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.File;

/*
    This class allows chef to add items to menu
    this class uses camera feature to add items
 */
public class ChefAddActivity extends AppCompatActivity {

    private EditText etAddItemName;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private EditText etAddItemDesc;
    private EditText etAddItemPrice;
    private Button btnCaptureImage;
    private ImageView ivAddItemImage;
    private Button btnSubmitItem;
    private File photoFile;
    public String photoFileName = "photo.jpg";
    public static final String TAG = "ABCEF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_add);
        etAddItemName = findViewById(R.id.etAddItemName);
        etAddItemDesc = findViewById(R.id.etAddItemDesc);
        etAddItemPrice = findViewById(R.id.etAddItemPrice);
        btnCaptureImage = findViewById(R.id.btnCaptureImage);
        ivAddItemImage = findViewById(R.id.ivAddItemImage);
        btnSubmitItem = findViewById(R.id.btnSubmitItem);

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        btnSubmitItem.setOnClickListener(new View.OnClickListener() {
            //when submit clicked, check validity of the new item
            //if valid add to item
            @Override
            public void onClick(View view) {
                String name = etAddItemName.getText().toString();
                String description = etAddItemDesc.getText().toString();
                Double price = (double) 0;
                try{
                    price = Double.valueOf(etAddItemPrice.getText().toString());
                }
                catch (NumberFormatException e) {
                    Toast.makeText(ChefAddActivity.this, "Cannot understand value, please try again", Toast.LENGTH_SHORT).show();
                }

                if (description.isEmpty() || name.isEmpty()) {
                    Toast.makeText(ChefAddActivity.this, "Name or Description cant be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (price <= 0){
                    Toast.makeText(ChefAddActivity.this, "new item price cannot be equal or less than 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFile == null || ivAddItemImage.getDrawable() == null){
                    Toast.makeText(ChefAddActivity.this, "There is no image!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //save item
                saveMenu(name, description, price, photoFile, CurrentEmployeeInfo.employee.getName());


            }
        });
    }

    //this method launches camera of the phone
    //this method uses library from codepath guides
    private void launchCamera() {
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);
        Uri fileProvider = FileProvider.getUriForFile(ChefAddActivity.this, "com.codepath.fileprovider.Order_Delivery", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if (intent.resolveActivity(getPackageManager()) != null) {
            // start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    //when image taking activity is done, save and set image
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // here photo is ready from camera
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                // load the image to screen
                ivAddItemImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public File getPhotoFileUri(String fileName) {
        // get safe storage directory for photos
        File mediaStorageDir = new File(getExternalFilesDir( Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    //save new menu item to database
    private void saveMenu(String name, String description, double price, File photoFile, String chef) {
        sz_item_cust menu = new sz_item_cust();
        menu.setItemDescription(description);
        menu.setItemName(name);
        menu.setItemPrice(price);
        menu.setItemImage(new ParseFile(photoFile));
        menu.setChef(chef);
        menu.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("TAG", "Error while saving!", e);
                    Toast.makeText(ChefAddActivity.this, "Error While Saving!", Toast.LENGTH_SHORT).show();
                }
                Log.i("TAG", "Post saved successfully!");
                etAddItemPrice.setText("0");
                etAddItemDesc.setText("");
                etAddItemName.setText("");
                ivAddItemImage.setImageResource(0);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });
    }
}