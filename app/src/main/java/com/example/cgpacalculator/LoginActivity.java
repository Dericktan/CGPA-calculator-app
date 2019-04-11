package com.example.cgpacalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    EditText edtName, edtStudentNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("CGPACalc_Pref", 0);
        editor = pref.edit();

        edtName = findViewById(R.id.edtName);
        edtStudentNo = findViewById(R.id.edtStudentNo);

        if (pref.contains("initialized"))
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void fnLogin(View view) {
        Boolean valid = false;
        String name = edtName.getText().toString();
        String studentNo = edtStudentNo.getText().toString();
        if (name == "" || name == null)
        {
            Toast.makeText(getApplicationContext(), "You need to fill the name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (studentNo == "" || studentNo == null)
        {
            Toast.makeText(getApplicationContext(), "You need to fill the student number!", Toast.LENGTH_SHORT).show();
            return;
        }
        editor.putString("name", name);
        editor.putString("studentNo", studentNo);
        editor.putBoolean("initialized", true);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
