package com.example.joserv_1.loginscreen;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitRequest extends Fragment {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private EditText edTitle, edLocation,edDesc;
    private ImageButton Viewmic,Viewcamera,Viewvideocam;
    private FloatingActionButton ActionGo;
    private final int GALLERY_REQUEST=1;
    private Uri imageUri = null;
    private StorageReference storageReference;
    private ProgressDialog dialog;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    public SubmitRequest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_submit_request, container, false);
        edTitle=(EditText)view.findViewById(R.id.edtTitle);
        edLocation=(EditText)view.findViewById(R.id.edtLocation);
        edDesc=(EditText)view.findViewById(R.id.edtDesc);

        Viewmic=(ImageButton)view.findViewById(R.id.imgMic);
        Viewcamera=(ImageButton)view.findViewById(R.id.imgCamera);
        Viewvideocam=(ImageButton)view.findViewById(R.id.imgVideo);

        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Request Up.....");

        storageReference = FirebaseStorage.getInstance().getReference();

        ActionGo=(FloatingActionButton)view.findViewById(R.id.fab);

        //Floating button pressed
        ActionGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Title=edTitle.getText().toString().trim();
                final String Location=edLocation.getText().toString().trim();
                final String Desc=edDesc.getText().toString().trim();

                if (!TextUtils.isEmpty(Title) && !TextUtils.isEmpty(Location) && !TextUtils.isEmpty(Desc) && imageUri !=null){
                    StorageReference reference= storageReference.child("Request Image").child(imageUri.getLastPathSegment());
                    reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            mAuth=FirebaseAuth.getInstance();
                            mDatabase= FirebaseDatabase.getInstance().getReference().child("Reqest");
                            dialog.show();

                            String user_id=mAuth.getCurrentUser().getUid();
                            final DatabaseReference Current_User_db= mDatabase.child(user_id);

                            Current_User_db.child("title").setValue(Title);
                            Current_User_db.child("location").setValue(Location);
                            Current_User_db.child("desc").setValue(Desc);
                            Current_User_db.child("Image Request").setValue(imageUri.toString());




                        }
                    });

                }

                ProfileFragment Profile_Fragment=new ProfileFragment();
                FragmentManager manager_Pro=getFragmentManager();
                manager_Pro.beginTransaction().replace(
                        R.id.content_requst,
                        Profile_Fragment ,
                        Profile_Fragment.getTag()).commit();



            }
        });


        Viewcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                Viewcamera.setImageBitmap(bitmap);

            }
        }
    }
}
