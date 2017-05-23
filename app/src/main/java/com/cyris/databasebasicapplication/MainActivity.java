package com.cyris.databasebasicapplication;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    StudentSqliteOpenHelper studentSqliteOpenHelper;

    String strId, strName, strSurname, strMarks;
    Button btnAddStudent, btnShowStudent;
    EditText etName, etSurname, etMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddStudent=(Button)findViewById(R.id.btAdd);
        btnShowStudent=(Button)findViewById(R.id.btShow);
        etName=(EditText)findViewById(R.id.etName);
        etSurname=(EditText)findViewById(R.id.etSurname);
        etMarks=(EditText)findViewById(R.id.etMarks);

        studentSqliteOpenHelper=new StudentSqliteOpenHelper(this);

        btnShowStudent.setOnClickListener(this);
        btnAddStudent.setOnClickListener(this);

    }

    public void writeStudentData(String name, String surname, String marks)
    {
        boolean dataWrite=studentSqliteOpenHelper.addData(name, surname, marks);
        if(dataWrite==true) {
            Toast.makeText(this, "data written successfully", Toast.LENGTH_SHORT).show();
            //now we will reset all the edit texts
            etMarks.setText("");
            etName.setText("");
            etSurname.setText("");
        }
        else
            Toast.makeText(this, "data cannot be written", Toast.LENGTH_SHORT).show();
    }

    public void readStudentData()
    {
        Cursor resultCursor=studentSqliteOpenHelper.readData();

        StringBuffer stringBuffer=new StringBuffer();

        if(resultCursor!=null&&resultCursor.getCount()>0)
        {
            while(resultCursor.moveToNext())
            {
                strId=resultCursor.getString(resultCursor.getColumnIndex(StudentSqliteOpenHelper.COL_1_ID));
                strName=resultCursor.getString(resultCursor.getColumnIndex(StudentSqliteOpenHelper.COL_2_STUDENT_NAME));
                strSurname=resultCursor.getString(resultCursor.getColumnIndex(StudentSqliteOpenHelper.COL_3_STUDENT_SURNAME));
                strMarks=resultCursor.getString(resultCursor.getColumnIndex(StudentSqliteOpenHelper.COL_4_STUDENT_MARKS));

                stringBuffer.append(strId+" : ");
                stringBuffer.append(strName+" ");
                stringBuffer.append(strSurname+" - ");
                stringBuffer.append(strMarks+"\n\n");

                Log.i("databaseread", strId+" | "+strName+" | "+strSurname+" | "+strMarks);

            }
        }

        showMessage(stringBuffer.toString());

    }

    public void showMessage(String message)
    {
        AlertDialog.Builder alertDialogBuiler=new AlertDialog.Builder(this);
        alertDialogBuiler.setCancelable(true);
        alertDialogBuiler.setTitle("Student List :");
        alertDialogBuiler.setMessage(message);
        alertDialogBuiler.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btAdd:

                strName=etName.getText().toString();
                strSurname=etSurname.getText().toString();
                strMarks=etMarks.getText().toString();

                if((!strName.isEmpty()&&strName!=null)&&(!strSurname.isEmpty()&&strSurname!=null)&&(!strMarks.isEmpty()&&strMarks!=null))
                {
                    writeStudentData(strName, strSurname, strMarks);
                }
                else
                    Toast.makeText(this, "Enter appropriate value", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btShow:
                    readStudentData();
                break;
        }

    }
}
