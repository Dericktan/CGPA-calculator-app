package com.example.cgpacalculator;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import model.SubjectDBModel;
import sqlite.CGPACalculatorDB;

public class MainActivity extends AppCompatActivity {


    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String name;
    EditText edtSubjectName, edtSubjectCode, edtSubjectCreditHour;
    TextView txtCGPA;
    Spinner spnSubjectGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("CGPACalc_Pref", 0);
        editor = pref.edit();

        edtSubjectName = findViewById(R.id.edtSubjectName);
        edtSubjectCode = findViewById(R.id.edtSubjectCode);
        edtSubjectCreditHour = findViewById(R.id.edtCreditHour);
        spnSubjectGrade = findViewById(R.id.spnGrade);
        txtCGPA = findViewById(R.id.txtVwCGPA);

        String[] items = new String[]{"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "E"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spnSubjectGrade.setAdapter(adapter);

        if (pref.contains("initialized"))
        {
            name = pref.getString("name", null);
            Toast.makeText(this, "Welcome " + name + "!", Toast.LENGTH_SHORT).show();
        }
    }


    public void fnSaveAndCalculate(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("New Entry Confirmation");
        alertDialog.setMessage("Are you sure the entry is correct ?\n");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String name = edtSubjectName.getText().toString();
                    String code = edtSubjectCode.getText().toString();
                    Double creditHour = Double.parseDouble(edtSubjectCreditHour.getText().toString());
                    Double grade = getScoreByGrade(spnSubjectGrade.getSelectedItem().toString());

                    SubjectDBModel subjectDBModel = new SubjectDBModel(code, name, creditHour, grade);
                    CGPACalculatorDB cgpaCalculatorDB = new CGPACalculatorDB(getApplicationContext());
                    Log.d("content", subjectDBModel.print());
                    cgpaCalculatorDB.fnInsertSubject(subjectDBModel);

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Subject Saved!", Toast.LENGTH_SHORT).show();
                    calculateCGPA();
                }
            }
        );
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        alertDialog.show();
    }

    double getScoreByGrade(String grade)
    {
        double score = 0;
        switch (grade)
        {
            case "A":
                score = 4;
                break;
            case "A-":
                score = 3.70;
                break;
            case "B+":
                score = 3.30;
                break;
            case "B":
                score = 3.00;
                break;
            case "B-":
                score = 2.70;
                break;
            case "C+":
                score = 2.30;
                break;
            case "C":
                score = 2.00;
                break;
            case "C-":
                score = 1.70;
                break;
            case "D+":
                score = 1.30;
                break;
            case "D":
                score = 1;
                break;
            case "E":
                score = 0;
                break;
            default:
                score = 0;
                break;
        }

        return score;
    }

    public void calculateCGPA()
    {
        Double cgpa;
        CGPACalculatorDB cgpaCalculatorDB = new CGPACalculatorDB(getApplicationContext());
        cgpa = cgpaCalculatorDB.fnGetCGPA();

        txtCGPA.setText(cgpa.toString());
    }
}
