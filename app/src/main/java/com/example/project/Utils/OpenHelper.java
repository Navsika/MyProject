package com.example.project.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.Model.ModelOfToDo;

import java.util.ArrayList;
import java.util.List;

public class OpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "databaseOfToDoList";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "_id";
    private static final String TASK = "task";
    private static final String DESCRIPTION = "description";
    private static final String STATUS = "status";
    private static final String STAGE = "stage";
    private static final String DONE = "done";

    private static final String CREATE_TODO_TABLE = "CREATE TABLE "+ TODO_TABLE + "("
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + DESCRIPTION + " TEXT," + STATUS + " INTEGER, " + STAGE + " TEXT," + DONE + " INTEGER);";

    private SQLiteDatabase db;

    public OpenHelper (Context context) { super(context, NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(CREATE_TODO_TABLE);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertTask(ModelOfToDo task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK, task.getTask());
        contentValues.put(DESCRIPTION, task.getDescription());
        contentValues.put(STATUS, task.getStatus());
        contentValues.put(STAGE, task.getStage());
        contentValues.put(DONE, 0);
        db.insert(TODO_TABLE, null, contentValues);
    }

    @SuppressLint("Range")
    public List<ModelOfToDo> getAllTasks(){
        List<ModelOfToDo> taskList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try{
            cursor = db.query(TODO_TABLE, null, null, null,
                    null, null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        ModelOfToDo task = new ModelOfToDo();
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                        task.setStage(cursor.getString(cursor.getColumnIndex(STAGE)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(DONE)));
                        taskList.add(task);
                    }
                    while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cursor != null;
            cursor.close();
        }
        return taskList;
    }


    public void UpdateStatus(int id, int status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS, status);
        db.update(TODO_TABLE, contentValues, ID + "= ?", new String[] {String.valueOf(id)});
    }
    public void UpdateDone(int id, int done) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DONE, done);
        db.update(TODO_TABLE, contentValues, ID + "= ?", new String[] {String.valueOf(id)});
    }
    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }
    public void updateDescription (int id, String description) {
        ContentValues cv = new ContentValues();
        cv.put(DESCRIPTION, description);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }
    public void updateStage(int id, String stage) {
        ContentValues cv = new ContentValues();
        cv.put(STAGE, stage);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask (int id) {
        db.delete(TODO_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }



}
