package the_machine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;

// This class is responsible for interacting with kiks database created by kik itself.

public class Database_Kik
{
    private static final String MEMEBER_TABLE_NAME = "memberTable";
    private static final String MESSAGES_TABLE_NAME = "messagesTable";
    private static final String CONTACT_TABLE_NAME = "KIKContactsTable";

    // for memberTable.
    private static final String GROUP_ID_ROW = "group_id";
    private static final String MEMBER_JID_ROW = "member_jid";
    private static final String IS_BANNED_ROW = "is_banned";
    private static final String IS_ADMIN_ROW = "is_admin";
    private static final String PERMISSION_LEVEL_ROW = "permission_level";

    // for KikContactsTable
    private static final String JID_ROW = "jid";
    private static final String USERNAME_ROW = "user_name";


    private Database_Kik(){}

    public static User[] getUsersInGroup(final Group group)
    {
        final File kikDatabaseDB = findKikDatabaseFile();

        if (!kikDatabaseDB.exists())
        {
            return null;
        }

        ArrayList<User> users = new ArrayList<User>();
        final String query = "SELECT * FROM " + MEMEBER_TABLE_NAME + " WHERE " + GROUP_ID_ROW + "=?";

        SQLiteDatabase database = SQLiteDatabase.openDatabase(kikDatabaseDB.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery(query, new String[]{group.getJID()});

        if (cursor.moveToFirst())
        {
            do
            {
                final String memberJID = cursor.getString(cursor.getColumnIndex(MEMBER_JID_ROW));
                final int isBanned = cursor.getInt(cursor.getColumnIndex(IS_BANNED_ROW));
                final int isAdmin = cursor.getInt(cursor.getColumnIndex(IS_ADMIN_ROW));
                users.add(new User(memberJID, isBanned, isAdmin));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return users.toArray(new User[0]);
    }

    private static File findKikDatabaseFile()
    {
        final String directoryPath = Kik.getKikApplication().getDatabasePath("kikDatabase.db").getParent();
        File file = new File(directoryPath);
        File databaseFile = null;

        if (file.exists())
        {
            final File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++)
            {
                if (files[i].getName().endsWith("kikDatabase.db"))
                {
                    databaseFile = files[i];
                    break;
                }
            }
        }

        return databaseFile;
    }

    public static User[] getSwagBots(final int limit)
    {
        final File kikDatabaseDB = findKikDatabaseFile();

        if (!kikDatabaseDB.exists())
        {
            return null;
        }

        ArrayList<User> users = new ArrayList<User>();
        final String query = "SELECT " + JID_ROW + ", " + USERNAME_ROW +  " FROM " + CONTACT_TABLE_NAME + " LIMIT " + limit;
        SQLiteDatabase database = SQLiteDatabase.openDatabase(kikDatabaseDB.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor =  database.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do
            {
                final String username = cursor.getString(cursor.getColumnIndex(USERNAME_ROW)).toLowerCase();

                if (username.startsWith("swag.bot"))
                {
                    final String userJid = cursor.getString(cursor.getColumnIndex(JID_ROW));
                    users.add(new User(username, userJid));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return users.toArray(new User[0]);
    }
}
