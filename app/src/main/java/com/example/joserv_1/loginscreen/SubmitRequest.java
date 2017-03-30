package com.example.joserv_1.loginscreen;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitRequest extends Fragment {

    private static final int VIDEO_CAPTURE = 101;
    private static final int REQUEST_CAMERA = 1;
    private static final int GALLERY_Intent = 1;
    private EditText edTitle, edLocation,edDesc;
    private ImageButton Viewmic,Viewcamera,Viewvideocam;
    private FloatingActionButton ActionGo;
    private Uri imageUri = null;
    private Uri videoUri = null;
    private Uri uriAudio = null;
    private StorageReference storageReference;
    private ProgressDialog dialog;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Button Recorddone;
    private MediaRecorder mRecorder;
    private String mFileName =null;
    private static final String LOG_TAG = "Recorder_log";
    private TextView RecordView ,GalleryView , CameraView;


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

                if (!TextUtils.isEmpty(Title) && !TextUtils.isEmpty(Location) && !TextUtils.isEmpty(Desc) ){

                    mAuth=FirebaseAuth.getInstance();
                            mDatabase= FirebaseDatabase.getInstance().getReference().child("Request");
                            final DatabaseReference Current_User_db= mDatabase.push();

                            Current_User_db.child("Title").setValue(Title);
                            Current_User_db.child("Location").setValue(Location);
                            Current_User_db.child("Desc").setValue(Desc);

                           if (imageUri !=null){
                               StorageReference filePath=storageReference.child("Image Request").child(imageUri.getLastPathSegment());
                               filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                   @Override
                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                      Uri downloadUri = taskSnapshot.getDownloadUrl();
                                       Current_User_db.child("Images").setValue(downloadUri.toString());


                                   }
                         });

                               if (videoUri !=null){
                                   StorageReference filePathVideo=storageReference.child("Video Request").child(videoUri.getLastPathSegment());
                                   filePathVideo.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                       @Override
                                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                           Uri downloadUriVideo = taskSnapshot.getDownloadUrl();
                                           Current_User_db.child("Video").setValue(downloadUriVideo.toString());


                                       }
                                   });

                                   if (uriAudio !=null) {
                                       StorageReference filePathAudio = storageReference.child("Audio Request").child("new audio");
                                       filePathAudio.putFile(uriAudio).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>( ) {
                                           @Override
                                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                               Uri downloadUriAudio = taskSnapshot.getDownloadUrl( );
                                               Current_User_db.child("Audio").setValue(downloadUriAudio.toString( ));
                                           }
                                       });
                                   }}
                               dialog.show();



                }
                    dialog.dismiss();

                    PoolFragment poolFragment=new PoolFragment();
                    FragmentManager PoolManager=getFragmentManager();
                    PoolManager.beginTransaction().replace(
                            R.id.content_requst,
                            poolFragment,
                            poolFragment.getTag()).commit();



            }
        }});

        Viewvideocam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myIntent.setType("*/*");
                startActivityForResult(Intent.createChooser(myIntent,"VIDEO_CAPTURE"),101);
               // Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
             //  startActivityForResult(intent, VIDEO_CAPTURE);

              //  File mediaFile =
                       // new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                      //      + "/myvideo.mp4");

              // Intent intentVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

               // videoUri = Uri.fromFile(mediaFile);

               // intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
               // startActivityForResult(intentVideo, VIDEO_CAPTURE);
            }
        });

        Viewcamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater= LayoutInflater .from(getActivity());
                final View promptView = layoutInflater.inflate(R.layout.popup_image,null);

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setView(promptView);

                GalleryView=(TextView)promptView.findViewById(R.id.textViewGallery);
                CameraView=(TextView)promptView.findViewById(R.id.textViewCamer);

                final android.support.v7.app.AlertDialog alertDialog= builder.create();

             GalleryView.setOnClickListener(new View.OnClickListener( ) {
                 @Override
                 public void onClick(View v) {
                      Intent intentGallery=new Intent(Intent.ACTION_GET_CONTENT);
                      intentGallery.setType("image/*");
                      startActivityForResult(intentGallery, GALLERY_Intent);

                 }
             });

                CameraView.setOnClickListener(new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {

                        Intent IntentCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(IntentCamera, REQUEST_CAMERA );
                    }
                });
                alertDialog.show();





            }
        });

        Viewmic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater= LayoutInflater .from(getActivity());
                final View promptView = layoutInflater.inflate(R.layout.popup_audio,null);

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setView(promptView);


                // Record to the external cache directory for visibility
                mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                mFileName += "/recorded_audio.mp3";
                RecordView=(TextView)promptView.findViewById(R.id.textViewRecord);

                Recorddone=(Button)promptView.findViewById(R.id.butRecord);
                final android.support.v7.app.AlertDialog alertDialog= builder.create();


                Recorddone.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent Event) {

                        if (Event.getAction() == MotionEvent.ACTION_DOWN){
                            mRecorder = new MediaRecorder();
                            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                            mRecorder.setOutputFile(mFileName);
                            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                            try {
                                mRecorder.prepare();
                            } catch (IOException e) {
                                Log.e(LOG_TAG, "prepare() failed");
                            }
                            mRecorder.start();
                            RecordView.setText("Recording Started");


                        }else if (Event.getAction() == MotionEvent.ACTION_UP){
                            mRecorder.stop();
                            mRecorder.release();
                            mRecorder = null;
                            RecordView.setText("Recording Stopped");
                            uriAudio= Uri.fromFile(new File(mFileName));


                        }

                        return false;
                    }
                });

                alertDialog.show();
            }
        });
        return view;
    }


   public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == GALLERY_Intent) {
                imageUri = data.getData( );
                Viewcamera.setImageURI(imageUri);
            }else if (requestCode == REQUEST_CAMERA){
                imageUri = data.getData( );
                Viewcamera.setImageURI(imageUri);

            }



        }else if (resultCode==Activity.RESULT_CANCELED)
        {
            // action cancelled
        }
       if(resultCode==Activity.RESULT_OK)
       {
           // Create a storage reference from our app

             videoUri= data.getData();
           UploadTask uploadTask = storageReference.putFile(videoUri);

           // Register observers to listen for when the download is done or if it fails
           uploadTask.addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception exception) {
                   // Handle unsuccessful uploads

                   Toast.makeText(getActivity(), "Upload Failed", Toast.LENGTH_SHORT).show();
               }
           }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                   Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT).show();
               }
           });
       }
   }
}




