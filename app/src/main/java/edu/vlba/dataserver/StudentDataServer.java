package edu.vlba.dataserver;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDataServer {
    private static StudentDataServer studentDataServerInstance = null;
    private String currentStudentId;
    private Map<String, Student> mapStudentData = new HashMap<>();
    private StudentDataServer() {
        this.currentStudentId = "0000000000";
    }

    public static synchronized StudentDataServer getInstance()
    {
        if (null == studentDataServerInstance)
            studentDataServerInstance = new StudentDataServer();

        return studentDataServerInstance;
    }

    public void initialize(Context context)
    {
        boolean available = false;
        boolean readable = true;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            // Both Read and Write available
            available = true;
            readable = true;
        }
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            // Only read available
            readable = true;
        }
        else
        {
            // SD card not mounted
        }

        String studentDataFilePath = context.getExternalFilesDir(null).toString() + // external files directory root path
                File.separator + "data" + // data directory
                File.separator + "student_data.csv"; // data file name

//        String studentDataFilePath = "/storage/emulated/0/vlba/data/student_data.csv"; // data file name

        Log.d("VLBA", "initialize studentDataFilePath= " + studentDataFilePath);
        ReadStudentData(studentDataFilePath, context);
    }

    private void ReadStudentData(String filePathStudentData, Context context)
    {
        mapStudentData.clear();
        final File fileStudentData = new File(filePathStudentData);
        if (!fileStudentData.exists())
            Log.d("VLBA", "ReadStudentData " + filePathStudentData + " does not exist.");
        try
        {
            FileInputStream fStream = new FileInputStream(fileStudentData);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fStream));
            String line = reader.readLine(); // Read column header line
            line = reader.readLine(); // Read 1st student data
            while (line != null)
            {
                // process line
                String[] stringParts = line.split(",");
                String id = stringParts[0];
                String name = stringParts[1];
                String phone = stringParts[2];
                String studentClass = stringParts[3];
                Student student = new Student(id, name, phone, studentClass);
                Log.d("VLBA", "ReadStudentData id= " + id + ", name= " + name + ", phone= " + phone + ", class= " + studentClass);
                mapStudentData.put(id, student);
                line = reader.readLine();
            }
        } catch (Exception e) {
            // Handle FileNotFound Exception

            Toast.makeText(context, "File cannot be opened!!", Toast.LENGTH_LONG).show();
            Log.d("VLBA", "ReadStudentData exception!! ", e);
        }
    }

    public void setCurrentStudentId(String currentStudentId) {
        this.currentStudentId = currentStudentId;
    }

    public Student getCurrentStudentData()
    {
        return mapStudentData.get(currentStudentId);
    }
}
