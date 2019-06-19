package the_machine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;

public abstract class Database
{
    private String m_databaseName;
    private String m_tableName;
    private String m_databasePath;
    protected static final String JID_ROW = "jid";
    protected static final String USERNAME_ROW = "username";
    protected static final String IN_CHAT_ROW = "inChat";

    abstract boolean addUser(final User user);

    abstract void createDatabase();

    public Database(final String databaseName, final String tableName)
    {
        m_databaseName = databaseName;
        m_tableName = tableName;

        File file = Kik.getKikApplication().getDatabasePath(m_databaseName);
        m_databasePath = file.getAbsolutePath();

        if (!file.exists())
        {
            createDatabase();
        }
    }

    protected SQLiteDatabase getReadOnlyDatabase()
    {
        return SQLiteDatabase.openDatabase(m_databasePath, null, SQLiteDatabase.OPEN_READONLY);
    }

    protected SQLiteDatabase getReadAndWriteDatabase()
    {
        return SQLiteDatabase.openDatabase(m_databasePath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    protected String getDatabasePath()
    {
        return m_databasePath;
    }


    public boolean isUserInDatabase(final User user)
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        boolean result = false;
        final String query = "SELECT " + USERNAME_ROW + " FROM " + m_tableName + " WHERE " + USERNAME_ROW + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{user.getUsername()});

        if (cursor.moveToFirst())
        {
            result = true;
        }

        cursor.close();
        database.close();
        return result;
    }

    public boolean removeUser(final User user)
    {
        SQLiteDatabase database = this.getReadAndWriteDatabase();
        int result = database.delete(m_tableName, USERNAME_ROW + "=?", new String[]{user.getUsername()});
        database.close();
        return result != 0;
    }

    public User getUser(final String username)
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        User user = null;
        final String query = "SELECT " + USERNAME_ROW + ", " + JID_ROW + " FROM " + m_tableName + " WHERE " + USERNAME_ROW + " LIKE \'" + username + "%\'";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            int jidColumnIndex = cursor.getColumnIndex(JID_ROW);
            int usernameIndex = cursor.getColumnIndex(USERNAME_ROW);
            String usernameString = cursor.getString(usernameIndex);
            String jid = cursor.getString(jidColumnIndex);

            if (cursor.getCount() == 1)
            {
                cursor.close();
                database.close();
                return new User(usernameString, jid);
            }

            do
            {
                usernameString = cursor.getString(usernameIndex);
                jid = cursor.getString(jidColumnIndex);

                if (usernameString.equalsIgnoreCase(username))
                {
                    user = new User(usernameString, jid);
                    break;
                }

            } while(cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return user;
    }

    public User[] getPossibleUsers(final String username)
    {
        ArrayList<User> users = new ArrayList<>();
        final String query = "SELECT " + USERNAME_ROW + ", " + JID_ROW + " FROM " + m_tableName + " WHERE " + USERNAME_ROW + " LIKE \'" + username + "%\'";
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            int jidColumnIndex = cursor.getColumnIndex(JID_ROW);
            int usernameIndex = cursor.getColumnIndex(USERNAME_ROW);

            do
            {
                final String usernameString = cursor.getString(usernameIndex);
                final String jid = cursor.getString(jidColumnIndex);
                users.add(new User(usernameString, jid));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return users.toArray(new User[0]);
    }

    public User[] getAllKnownUsers()
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        ArrayList<User> usersList = new ArrayList<User>();
        final String query = "SELECT " + USERNAME_ROW + ", " + JID_ROW + " FROM " + m_tableName;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            int usernameColumnIndex = cursor.getColumnIndex(USERNAME_ROW);
            int jidColumnIndex = cursor.getColumnIndex(JID_ROW);

            do
            {
                usersList.add(new User(cursor.getString(usernameColumnIndex), cursor.getString(jidColumnIndex)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return usersList.toArray(new User[0]);
    }

    public String getUserJID(final String username)
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        String result = null;
        final String query = "SELECT " + JID_ROW + " FROM " + m_tableName + " WHERE " + USERNAME_ROW + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst())
        {
            result = cursor.getString(cursor.getColumnIndex(JID_ROW));
        }

        cursor.close();
        database.close();
        return result;
    }

    public static boolean deleteDatabase(final String databaseId)
    {
        boolean result = false;
        final File directory = new File(Kik.getKikApplication().getDatabasePath(" ").getParent());

        if (directory.exists())
        {
            final File[] databaseFiles = directory.listFiles();

            for (int i = 0; i < databaseFiles.length; i++)
            {
                if (databaseFiles[i].getName().contains(databaseId))
                {
                    result = databaseFiles[i].delete();
                    break;
                }
            }
        }

        return result;
    }
}