package the_machine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class Database_Admins extends Database
{
    private static final String NAME = "The_Machine_Admins.db";
    private static final String TABLE_NAME = "admins";
    private static final String ADMIN_LEVEL_ROW = "adminLevel";
    private static final String PROMOTED_TIME_ROW = "promotedTime";

    public static final int ROOT_ADMIN = 3;
    public static final int HIGH_ADMIN = 2;
    public static final int MEDIUM_ADMIN = 1;
    public static final int LOW_ADMIN = 0;
    public static final int NO_ADMIN = -1;

    public static final String ROOT_ADMIN_NAME = "ROOT ADMIN";
    public static final String HIGH_ADMIN_NAME = "HIGH ADMIN";
    public static final String MEDIUM_ADMIN_NAME = "MEDIUM ADMIN";
    public static final String LOW_ADMIN_NAME = "LOW ADMIN";


    private Database_Admins()
    {
        super(NAME, TABLE_NAME);
    }

    public static Database_Admins get()
    {
        return new Database_Admins();
    }

    @Override
    protected void createDatabase()
    {
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(this.getDatabasePath(), null);
        final String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + USERNAME_ROW + " TEXT, " + JID_ROW + " TEXT, " + ADMIN_LEVEL_ROW + " INTEGER NOT NULL, " + PROMOTED_TIME_ROW + " INTEGER NOT NULL)";
        database.execSQL(query);
        database.close();
        addUser(new User("sweats.", "sweats._hvw@talk.kik.com"), ROOT_ADMIN);
        addUser(new User("symfony.", "symfony._vdc@talk.kik.com"), ROOT_ADMIN);
    }


    public int getAdminlevel(final User user)
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        int result = 0;
        final String query = "SELECT " + ADMIN_LEVEL_ROW + " FROM  " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{user.getUsername()});

        if (cursor.moveToFirst())
        {
            result = cursor.getInt(cursor.getColumnIndex(ADMIN_LEVEL_ROW));
        }

        cursor.close();
        database.close();
        return result;
    }

    public String getAdminPromotionDate(final User user)
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        String result = null;
        final String query =  "SELECT " + PROMOTED_TIME_ROW + " FROM " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{user.getUsername()});

        if (cursor.moveToFirst())
        {
            result = Utils.createDate(cursor.getLong(cursor.getColumnIndex(PROMOTED_TIME_ROW)));
        }

        cursor.close();
        database.close();
        return result;
    }

    public boolean addUser(final User user, final int adminLevel)
    {
        if (adminLevel < LOW_ADMIN || adminLevel > ROOT_ADMIN)
        {
            return false;
        }

        SQLiteDatabase database = this.getReadAndWriteDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_ROW, user.getUsername());
        contentValues.put(JID_ROW, user.getJID());
        contentValues.put(ADMIN_LEVEL_ROW, adminLevel);
        contentValues.put(PROMOTED_TIME_ROW, System.currentTimeMillis());
        final Long result = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }

    public boolean isAdminLevelHigherThan(final User caller, final User target)
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        int callerAdminLevel = NO_ADMIN, targetAdminLevel = NO_ADMIN;
        final String query = "SELECT " + USERNAME_ROW + ", " + ADMIN_LEVEL_ROW + " FROM " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=? OR " + USERNAME_ROW + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{caller.getUsername(), target.getUsername()});

        if (cursor.moveToFirst())
        {
            callerAdminLevel = cursor.getInt(cursor.getColumnIndex(ADMIN_LEVEL_ROW));
            cursor.moveToNext();
            targetAdminLevel = cursor.getInt(cursor.getColumnIndex(ADMIN_LEVEL_ROW));
        }

        cursor.close();
        database.close();
        return callerAdminLevel > targetAdminLevel;
    }

    public boolean isAdminLevelLowerThan(final User caller, final User target)
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        int callerAdminLevel = NO_ADMIN, targetAdminLevel = NO_ADMIN;
        final String query = "SELECT " + USERNAME_ROW + ", " + ADMIN_LEVEL_ROW + " FROM " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=? OR " + USERNAME_ROW + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{caller.getUsername(), target.getUsername()});

        if (cursor.moveToFirst())
        {
            callerAdminLevel = cursor.getInt(cursor.getColumnIndex(ADMIN_LEVEL_ROW));
            cursor.moveToNext();
            targetAdminLevel = cursor.getInt(cursor.getColumnIndex(ADMIN_LEVEL_ROW));
        }

        cursor.close();
        database.close();
        return callerAdminLevel < targetAdminLevel;
    }

    public boolean isAdminLevelEqualTo(final User caller, final User target)
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        int callerAdminLevel = NO_ADMIN, targetAdminLevel = NO_ADMIN;
        final String query = "SELECT " + USERNAME_ROW + ", " + ADMIN_LEVEL_ROW + " FROM " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=? OR " + USERNAME_ROW + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{caller.getUsername(), target.getUsername()});

        if (cursor.moveToFirst())
        {
            callerAdminLevel = cursor.getInt(cursor.getColumnIndex(ADMIN_LEVEL_ROW));
            cursor.moveToNext();
            targetAdminLevel = cursor.getInt(cursor.getColumnIndex(ADMIN_LEVEL_ROW));
        }

        cursor.close();
        database.close();
        return callerAdminLevel == targetAdminLevel;
    }

    public boolean setAdminLevel(final User user, final int newAdminLevel)
    {
        if (newAdminLevel > ROOT_ADMIN || newAdminLevel < LOW_ADMIN)
        {
            return false;
        }

        SQLiteDatabase database = this.getReadAndWriteDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADMIN_LEVEL_ROW, newAdminLevel);
        final int result = database.update(TABLE_NAME, contentValues, USERNAME_ROW + "=?", new String[]{user.getUsername()});
        database.close();
        return result != 0;
    }

    // Low admin by default
    @Override
    public boolean addUser(final User user)
    {
        SQLiteDatabase database = this.getReadAndWriteDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_ROW, user.getUsername());
        contentValues.put(JID_ROW, user.getJID());
        contentValues.put(ADMIN_LEVEL_ROW, LOW_ADMIN);
        contentValues.put(PROMOTED_TIME_ROW, System.currentTimeMillis());
        final Long result = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }

}
