package fr.menage.valentin.cryptowalletmonitoring;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseInterface extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public DatabaseInterface(Context context){
        super(context,"cryptowalletmonitoring",null,3);
        this.db = getWritableDatabase();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        this.db = db;
        db.execSQL("CREATE TABLE BoughtCrypto(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, crypto_name TEXT, price_bought REAL, purchase_date TEXT, quantity REAL);");
        db.execSQL("CREATE TABLE EarnCrypto(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, crypto_name TEXT, purchase_date TEXT, quantity REAL);");
    }

    public void close(){
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS BoughtCrypto");
        db.execSQL("DROP TABLE IF EXISTS EarnCrypto");
        onCreate(db);
    }

    public void cleanTable(){
        db.execSQL("DELETE FROM BoughtCrypto;");
        db.execSQL("DELETE FROM EarnCrypto;");
    }
    //endregion Fin de section administration bdd

    //region Insertion dans les tables
    public void insertNewCryptoBought(Crypto cryptoToInsert){
        ContentValues values = new ContentValues();
        values.put("crypto_name",cryptoToInsert.getName());
        values.put("price_bought",cryptoToInsert.getBoughtPrice());
        // TODO: 19/02/2022  changer retour date
        values.put("purchase_date",cryptoToInsert.getBoughtDate().toString());
        values.put("quantity",cryptoToInsert.getQuantity());
        db.insert("BoughtCrypto",null,values);
    }

    public void insertNewCryptoEarn(Crypto cryptoToInsert){
        ContentValues values = new ContentValues();
        values.put("crypto_name",cryptoToInsert.getName());
        values.put("purchase_date",cryptoToInsert.getBoughtDate());
        values.put("quantity",cryptoToInsert.getQuantity());
        db.insert("EarnCrypto",null,values);
    }

    public ArrayList<Crypto> getAllCryptoBought(){
        ArrayList<Crypto> cryptoBoughtArrayList = new ArrayList<Crypto>();
        Cursor c = db.rawQuery("SELECT * FROM BoughtCrypto;",null);
        while (c.moveToNext()){
            Crypto crypto = new Crypto(c.getInt(0),c.getString(1),c.getDouble(2),c.getString(3),c.getDouble(4));
            cryptoBoughtArrayList.add(crypto);
        }
        c.close();
        return cryptoBoughtArrayList;
    }

    public ArrayList<Crypto> getAllCryptoEarn(){
        ArrayList<Crypto> cryptoEarnArrayList = new ArrayList<Crypto>();
        Cursor c = db.rawQuery("SELECT * FROM EarnCrypto;",null);
        while (c.moveToNext()){
            Crypto crypto = new Crypto(c.getInt(0),c.getString(1),0,c.getString(2),c.getDouble(3));
            cryptoEarnArrayList.add(crypto);
        }
        c.close();
        return cryptoEarnArrayList;
    }

    public Crypto getUniqueCryptoEarn(int id){
        Crypto crypto = null;
        // d parce qu'on sait pas si ça ne surcharge pas le curseur c
        Cursor d = db.rawQuery("SELECT * FROM EarnCrypto WHERE id="+id+";",null);

        while (d.moveToNext()) {
            crypto = new Crypto(d.getInt(0),d.getString(1),0,d.getString(3),d.getDouble(4));
            return crypto;
        }
        return crypto;
    }

    public Crypto getUniqueCryptoBought(int id){
        Crypto crypto = null;
        // d parce qu'on sait pas si ça ne surcharge pas le curseur c
        Cursor d = db.rawQuery("SELECT * FROM BoughtCrypto WHERE id="+id+";",null);

        while (d.moveToNext()) {
            crypto = new Crypto(d.getInt(0),d.getString(1),d.getDouble(2),d.getString(3),d.getDouble(4));
            return crypto;
        }
        return crypto;
    }

    public void deleteCrypto(Crypto crypto) {
        db.delete("BoughtCrypto", "id = ?", new String[]{String.valueOf(crypto.getId())});
    }
}
