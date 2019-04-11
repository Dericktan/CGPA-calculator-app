package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.SubjectDBModel;

public class CGPACalculatorDB extends SQLiteOpenHelper {

    public static final String dbName = "dbCGPA_Calculator";
    public static final String tblNameSubject = "subjects";
    public static final String colSubCode = "subject_code";
    public static final String colSubName = "subject_name";
    public static final String colSubCrediHour = "subject_credit_hour";
    public static final String colSubGrade= "subject_grade";
    public static final String cobSubId = "subject_id";

    public static final String strCrtTblExpenses = "CREATE TABLE "+ tblNameSubject + " ("+ cobSubId +" INTEGER PRIMARY KEY, " + colSubCode +" TEXT, " + colSubName + " TEXT, " + colSubCrediHour +" REAL, "+ colSubGrade + " REAL)";
    public static final String strDropTblExpenses = "DROP TABLE IF EXISTS "+ tblNameSubject;

    public CGPACalculatorDB(Context context) {
        super(context, dbName, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(strCrtTblExpenses);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(strDropTblExpenses);
        onCreate(db);
    }

    public float fnInsertSubject(SubjectDBModel mySubject)
    {
        float retResult = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colSubCode, mySubject.getSubjectCode());
        values.put(colSubName, mySubject.getSubjectName());
        values.put(colSubCrediHour, mySubject.getSubjectCreditHour());
        values.put(colSubGrade, mySubject.getSubjectGrade());

        retResult = db.insert(tblNameSubject, null, values);
        return retResult;
    }

    public double fnGetCGPA()
    {
        double score = 0;
        double totalPointer = 0;
        int totalCredit = 0;

        String query = "SELECT * FROM " + tblNameSubject;
        Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst())
        {
            do {
                totalCredit += cursor.getDouble(cursor.getColumnIndex(colSubCrediHour));
                totalPointer += cursor.getDouble(cursor.getColumnIndex(colSubGrade)) * cursor.getDouble(cursor.getColumnIndex(colSubCrediHour));
            } while (cursor.moveToNext());
        }
        score = totalPointer / totalCredit;

        return score;
    }
}
