package edu.vlba.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;

import edu.vlba.databinding.FragmentHomeBinding;
import edu.vlba.dataserver.Student;
import edu.vlba.dataserver.StudentDataServer;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText editTextStudentId = binding.editTextStudentId;
        editTextStudentId.requestFocus();
        editTextStudentId.setNextFocusDownId(editTextStudentId.getId());
        editTextStudentId.setInputType(InputType.TYPE_NULL);
        editTextStudentId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String studentIdStr = editable.toString();
                String lastInputText = studentIdStr.isEmpty() ? "" : studentIdStr.substring(studentIdStr.length() - 1);
                Log.d("VLBA", "afterTextChanged studentID = " + studentIdStr + " lastInputText = " + lastInputText);
                if (studentIdStr.length() == 10) {
                    StudentDataServer.getInstance().setCurrentStudentId(studentIdStr);
                    Student currentStudent = StudentDataServer.getInstance().getCurrentStudentData();

                    TextView textViewName = binding.textViewStudentName;
                    textViewName.setText(currentStudent.getName());
                    TextView textViewClass = binding.textViewStudentClass;
                    textViewClass.setText(currentStudent.getStudentClass());
                    TextView textViewPhone = binding.textViewStudentPhone;
                    textViewPhone.setText(currentStudent.getPhone());

                    ImageView imageViewPhoto = binding.imageViewStudentPhoto;
                    try {
                        String studentPhotoFileName = currentStudent.getName();
                        studentPhotoFileName = studentPhotoFileName.toLowerCase();
                        studentPhotoFileName = studentPhotoFileName.replaceAll(" ", "_");
                        studentPhotoFileName += ".jpg";
                        Log.d("VLBA", "studentPhotoFileName: " + studentPhotoFileName);
                        String studentPhotoFilePath = getContext().getExternalFilesDir(null).toString() + // external files directory root path
                                File.separator + "photo" + // photo directory
                                File.separator + studentPhotoFileName; // photo file name
                        Log.d("VLBA", "studentPhotoFilePath: " + studentPhotoFilePath);

                        Bitmap bitmap = BitmapFactory.decodeFile(studentPhotoFilePath);
                        imageViewPhoto.setImageBitmap(bitmap);
                    } catch (Exception e)
                    {
                        Log.d("VLBA", "Exception: ", e);
                    }


                    // request SMS permission
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(currentStudent.getPhone(), null, currentStudent.getName() + " has arrived to school.", null, null);

                        //TODO:  Try out SMS send using intent
//                        Intent sendSmsIntent = new Intent(Intent.ACTION_VIEW);
//                        sendSmsIntent.setDataAndType(Uri.parse("smsto:"), "vnd.android-dir/mms-sms");
//                        sendSmsIntent.putExtra("address", "9141230016");
//                        sendSmsIntent.putExtra("sms_body", "Soumik has arrived to school.");
//                        startActivity(sendSmsIntent);

                        Toast.makeText(getContext(), "SMS Sent", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getContext(), "Send SMS Failed!!", Toast.LENGTH_LONG).show();
                    }

                    //TODO: Speak out 'Welcome Student'
                }
                else if (studentIdStr.length() == 11) {
                    editable.clear();
                    editTextStudentId.append(lastInputText);
                }
            }
        });

        editTextStudentId.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                int inputType = editTextStudentId.getInputType(); // backup the input type
//                editTextStudentId.setInputType(InputType.TYPE_NULL); // disable soft input
//                editTextStudentId.onTouchEvent(motionEvent); // call native handler
//                editTextStudentId.setInputType(inputType);
                return true; // consume touch event
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}