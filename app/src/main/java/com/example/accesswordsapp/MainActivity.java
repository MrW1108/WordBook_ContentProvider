package com.example.accesswordsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolver = this.getContentResolver();

        Button btInsert = findViewById(R.id.insert);
        Button btDelete = findViewById(R.id.delete);
        Button btUpdate = findViewById(R.id.update);
        Button btSearch = findViewById(R.id.search);

        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strWord = "Banana";
                String strMeaning = "香蕉";
                String strSample = "This banana is very nice.";
                ContentValues values = new ContentValues();
                values.put(Words.Word.COLUMN_NAME_WORD,strWord);
                values.put(Words.Word.COLUMN_NAME_MEANING,strMeaning);
                values.put(Words.Word.COLUMN_NAME_SAMPLE,strSample);
                Uri newUri = resolver.insert(Words.Word.CONTENT_URI,values);
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = "1";   //简单起见，这里直接指定ID
                Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING+"/"+id);
                int result = resolver.delete(uri,null,null);
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = "3";
                String strWord = "Banana";
                String strMeaning = "香蕉";
                String strSample = "This banana is very nice.";
                ContentValues values = new ContentValues();
                values.put(Words.Word.COLUMN_NAME_WORD,strWord);
                values.put(Words.Word.COLUMN_NAME_MEANING,strMeaning);
                values.put(Words.Word.COLUMN_NAME_SAMPLE,strSample);
                Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING+"/"+id);
                int result = resolver.update(uri,values,null,null);
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = "3";
                Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING+"/"+id);
                Cursor cursor = resolver.query(Words.Word.CONTENT_URI,new String[]{Words.Word._ID,Words.Word.COLUMN_NAME_WORD,
                        Words.Word.COLUMN_NAME_MEANING,Words.Word.COLUMN_NAME_SAMPLE},null,null,null);
                if(cursor == null){
                    Toast.makeText(MainActivity.this,"没有找到记录",Toast.LENGTH_LONG).show();
                    return;
                }
                String msg = "";
                if(cursor.moveToFirst()){
                    do{
                        msg += "ID:"+cursor.getInt(cursor.getColumnIndex(Words.Word._ID))+",";
                        msg += "单词："+cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD))+",";
                        msg += "含义："+cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING))+",";
                        msg += "实例："+cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE))+"\n";
                    }while(cursor.moveToNext());
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

    }
}