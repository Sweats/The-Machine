package the_machine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;

public class Database_Support
{
    private String m_databasePath;

    private static final String JID_ROW = "jid";
    private static final String GROUP_TAG_ROW = "groupTag";
    private static final String TABLE_NAME = "supportedGroups";
    private static final String DATE_SINCE_SUPPORTED = "supportedDate";
    private static final String NAME = "The_Machine_Supported_Groups.db";

    private Database_Support()
    {
        File file = Kik.getKikApplication().getDatabasePath(NAME);
        m_databasePath = file.getAbsolutePath();

        if (!file.exists())
        {
            createDatabase();
        }
    }

    public static Database_Support get()
    {
        return new Database_Support();
    }

    private void createDatabase()
    {
        final String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + GROUP_TAG_ROW + " TEXT, " + JID_ROW + " TEXT, " + DATE_SINCE_SUPPORTED + " INTEGER NOT NULL)";
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(m_databasePath, null);
        database.execSQL(query);
        database.close();
    }

    public boolean isSupported(final Group group)
    {
        SQLiteDatabase database = SQLiteDatabase.openDatabase(m_databasePath, null, SQLiteDatabase.OPEN_READONLY);
        boolean result = false;
        final String query = "SELECT " + JID_ROW + " FROM " + TABLE_NAME + " WHERE " + JID_ROW + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{group.getJID()});

        if (cursor.moveToFirst())
        {
            result = true;
        }

        cursor.close();
        database.close();
        return result;
    }

    public Group getGroup(final String groupName)
    {
        SQLiteDatabase database = SQLiteDatabase.openDatabase(m_databasePath, null, SQLiteDatabase.OPEN_READONLY);
        final String query = "SELECT " + JID_ROW + " FROM " + TABLE_NAME + " WHERE " + GROUP_TAG_ROW + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{groupName});
        Group group = null;

        if (cursor.moveToFirst())
        {
            final String jid =  cursor.getString(cursor.getColumnIndex(JID_ROW));
            group = new Group(groupName, jid);
        }

        database.close();
        cursor.close();
        return group;
    }


    public boolean addSupport(final Group group, final String groupTag)
    {
        SQLiteDatabase database = SQLiteDatabase.openDatabase(m_databasePath, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues contentValues = new ContentValues();
        contentValues.put(JID_ROW, group.getJID());
        contentValues.put(GROUP_TAG_ROW, groupTag);
        contentValues.put(DATE_SINCE_SUPPORTED, System.currentTimeMillis());
        final Long result = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }

    public boolean removeSupport(final Group group)
    {
        SQLiteDatabase database = SQLiteDatabase.openDatabase(m_databasePath, null, SQLiteDatabase.OPEN_READWRITE);
        final int result = database.delete(TABLE_NAME, JID_ROW + "=?", new String[]{group.getJID()});
        database.close();
        return result != 0;
    }

    public Group[] getSupportedGroups()
    {
        ArrayList<Group> groups = new ArrayList<Group>();
        final String query = "SELECT " + GROUP_TAG_ROW + ", " + JID_ROW + " FROM " + TABLE_NAME;

        SQLiteDatabase database = SQLiteDatabase.openDatabase(m_databasePath, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst())
        {

            int groupTagIndex = cursor.getColumnIndex(GROUP_TAG_ROW);
            int jidIndex = cursor.getColumnIndex(JID_ROW);

            do
            {
                final String groupTag = cursor.getString(groupTagIndex);
                final String jid = cursor.getString(jidIndex);
                groups.add(new Group(groupTag, jid));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return groups.toArray(new Group[0]);
    }

}