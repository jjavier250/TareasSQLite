package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Datacenter(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        const val DATABASE_NAME= "to_do_app.db"
        const val DATABASE_VERSION= 1  // la primera vez va a valer 1

        private const val SQL_CREATE_TABLE =
            "CREATE TABLE Task (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "task TEXT," +
                    "done BOOLEAN)"

        private const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS Task"
    }

     override fun onCreate(db: SQLiteDatabase) {
         db.execSQL(SQL_CREATE_TABLE)
     }

     override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
         destroyDatabase(db)
         onCreate(db)
     }
     private fun destroyDatabase(db: SQLiteDatabase){
         db.execSQL(SQL_DELETE_TABLE)
     }

    fun insertarTabla(){

        val db=writableDatabase
       // db.execSQL("insert into task ('task','done') values ('comprar leche',false)")

        // esto sustituye al insert
        val values=ContentValues()
        values.put("task","comprar leche")
        values.put("done",false)

        // Insert the new row, returning the primary key value of the new row
        var newRowId = db.insert("Task", null, values)


        values.put("task","limpiar el coche")
        values.put("done",false)

        newRowId = db.insert("Task", null, values)
    }

     fun leerdatos(){

         val db=writableDatabase

         val cursor = db.query(
             "Task",   // The table to query
             arrayOf("id","task","done"),             // The array of columns to return (pass null to get all)
             null,              // The columns for the WHERE clause
             null,          // The values for the WHERE clause
             null,                   // don't group the rows
             null,                   // don't filter by row groups
             "done desc"               // The sort order
         )

         if (cursor.moveToFirst()){

             do{
                 val task=cursor.getString(0)
                 val done=cursor.getInt(1)==1

                 //pinto
                 Log.i("DATABASE"," -> Task:$task,done:$done")

             }while(cursor.moveToNext())
                cursor.close()
         }

     }

    fun actualizarTabla(){

        val db=writableDatabase

        val values=ContentValues()
        values.put("done",true)

        var updatetable = db.update("Task",  values)
    }
 }