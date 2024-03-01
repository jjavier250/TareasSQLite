package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Datacenter(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        const val DATABASE_NAME= "to_do_app.db"
        const val DATABASE_VERSION= 1  // la primera vez va a valer 1, si subimos la versión a 2 el va a llamar al metodo y borra y vuelve a crear la tabla, Esto es por si añadimos una columna nueva


        // siempre hay que hacer los create tables si tengo 20 pues 20 por que esta en local
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

        values.put("task","Poner la lavadora")
        values.put("done",false)

        newRowId = db.insert("Task", null, values)

        values.put("task","Pasar revisión")
        values.put("done",false)

        newRowId = db.insert("Task", null, values)

        Log.i("DATABASE","insertadas:$newRowId")
    }

     @SuppressLint("Range") // para omitir errores
     fun leerdatos(){

         val db=writableDatabase

         val cursor = db.query(
             "Task",   // The table to query
             arrayOf("id","task","done"),         // si pongo null es como poner * que te trae todas las columnas
             null,              // The columns for the WHERE clause
             null,          // The values for the WHERE clause
             null,                   // don't group the rows
             null,                   // don't filter by row groups
             "done desc"               // The sort order
         )

         // al leer los datos por las columnas 0,1,2 en el mismo orden de arriba
         if (cursor.moveToFirst()){

             do{
                 val id=cursor.getInt(0)  // opcion por indice
                // val id=cursor.getString(cursor.getColumnIndex("id")) // opcion 2 por nombre
                 val task=cursor.getString(1)
                 val done=cursor.getInt(2)==1

                 //pinto
                 Log.i("DATABASE","$id -> Task:$task,done:$done")

             }while(cursor.moveToNext())
                cursor.close()
         }

     }

    fun actualizarTabla(){

        val db=writableDatabase
        // metodo antiguo
       // db.execSQL("UPDATE Task set task='comprar leche',done=true WHERE id=1")

        val values=ContentValues()
        values.put("done",true) // campo a actualizar

        var updatetable = db.update("Task",  values,"id=? or id=?", arrayOf("1","3"))
       // var updatetable = db.update("Task",  values,"id in (1,3)", null) otra manera de haverlo son los ?

        Log.i("DATABASE","REGISTROS ACTUALIZADOS:$updatetable")
    }


    fun borrarDatos(){

        val db=writableDatabase

        val borraFilas=db.delete("Task","id=1",null)

        Log.i("DATABASE","Registros borrados$borraFilas")
    }
 }