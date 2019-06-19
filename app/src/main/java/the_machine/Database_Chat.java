package the_machine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*

This class acts as a global database for The Machine. If we ever need to fetch information about someone for whatever reason, we use this class.

We'll also have chat specific databases too for chat specific stat commands.

*/

public class Database_Chat extends Database
{
    private static final String NAME = "The_Machine_Database.db";
    private static final String TABLE_NAME = "chatStats";
    private static final String LAST_MESSAGE_ROW = "lastMessage";
    private static final String LAST_MESSAGE_TIME_ROW = "lastMessageTime";
    private static final String FIRST_MESSAGE_TIME_ROW = "firstSeen";
    private static final String MESSAGES_SENT_ROW = "messagesSent";
    private static final String BLACKLISTED_ROW = "blacklisted";

    private Database_Chat()
    {
        super(NAME, TABLE_NAME);
    }

    private Database_Chat(final Group group)
    {
        super("The_Machine_" + group.getDatabaseID() + ".db", TABLE_NAME);
    }

    public static Database_Chat get()
    {
        return new Database_Chat();
    }

    public static Database_Chat get(final Group group)
    {
        return new Database_Chat(group);
    }

    @Override
    protected void createDatabase()
    {
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(this.getDatabasePath(), null);
        final String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + USERNAME_ROW + " TEXT, " + JID_ROW + " TEXT, " + LAST_MESSAGE_ROW + " TEXT, " +  LAST_MESSAGE_TIME_ROW + " INTEGER NOT NULL, " + FIRST_MESSAGE_TIME_ROW + " TEXT, " + MESSAGES_SENT_ROW + " INTEGER NOT NULL, " + BLACKLISTED_ROW + " INTEGER NOT NULL, " + IN_CHAT_ROW + " INTEGER NOT NULL)";
        database.execSQL(query);
        database.close();
    }

    public boolean blacklist(final User user)
    {
        SQLiteDatabase database = this.getReadAndWriteDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BLACKLISTED_ROW, 1);
        final int result = database.update(TABLE_NAME, contentValues, USERNAME_ROW + "=?", new String[]{user.getUsername()});
        database.close();
        return result != 0;
    }

    public boolean unblacklist(final User user)
    {
        SQLiteDatabase database = this.getReadAndWriteDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BLACKLISTED_ROW, 0);
        final int result = database.update(TABLE_NAME, contentValues, USERNAME_ROW + "=?", new String[]{user.getUsername()});
        database.close();
        return result != 0;
    }

    public int getUserMessageCount(final User user)
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        int count = 0;
        final String query = "SELECT " + MESSAGES_SENT_ROW + " FROM " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=?";
        Cursor cursor = database.rawQuery(query, new String[]{user.getUsername()});

        if (cursor.moveToFirst())
        {
            count = cursor.getInt(cursor.getColumnIndex(MESSAGES_SENT_ROW));
        }

        cursor.close();
        database.close();
        return count;
    }

    public int getBlacklisted(final User user)
    {
        SQLiteDatabase database = this.getReadOnlyDatabase();
        int result = 0;
        final String query = "SELECT " + BLACKLISTED_ROW + " FROM " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=?";

        Cursor cursor = database.rawQuery(query, new String[]{user.getUsername()});

        if (cursor.moveToFirst())
        {
            result = cursor.getInt(cursor.getColumnIndex(BLACKLISTED_ROW));
        }

        cursor.close();
        database.close();
        return result;
    }


    // Used automatically by the machine.
    private boolean updateUser(final Message message)
    {
        SQLiteDatabase database = this.getReadAndWriteDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LAST_MESSAGE_ROW, message.getMessageText());
        contentValues.put(LAST_MESSAGE_TIME_ROW, System.currentTimeMillis());
        int newCount = getUserMessageCount(message.getUser()) + 1;
        contentValues.put(MESSAGES_SENT_ROW, newCount);
        final int result = database.update(TABLE_NAME, contentValues, USERNAME_ROW + "=?", new String[]{message.getUser().getUsername()});
        database.close();
        return result != 0;
    }

    // Used automatically by the machine. Small wrapper function
    // Our callbacks will handle database updates for when people join, leave, get kicked, etc.
    public boolean update(final Message message)
    {
        boolean result = false;

        if (message.getUser() != null && message.isRegularMessage())
        {
            result = isUserInDatabase(message.getUser()) ? updateUser(message) : addUser(message);
        }

        return result;
    }

    private boolean addUser(final Message message)
    {
        SQLiteDatabase database = this.getReadAndWriteDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_ROW, message.getUser().getUsername());
        contentValues.put(JID_ROW, message.getUser().getJID());
        contentValues.put(LAST_MESSAGE_ROW, message.getMessageText());
        contentValues.put(LAST_MESSAGE_TIME_ROW, System.currentTimeMillis());
        contentValues.put(FIRST_MESSAGE_TIME_ROW, System.currentTimeMillis());
        contentValues.put(MESSAGES_SENT_ROW, 1);
        contentValues.put(BLACKLISTED_ROW, 0);
        contentValues.put(IN_CHAT_ROW, 1);
        final Long result = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }

    // Used when we add someone manually
    @Override
    public boolean addUser(final User user)
    {
        return addUser(user, 0);
    }

    // Used for !refresh
    public boolean addUser(final User user, final int inChat)
    {
        SQLiteDatabase database = this.getReadAndWriteDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_ROW, user.getUsername());
        contentValues.put(JID_ROW, user.getJID());
        contentValues.put(LAST_MESSAGE_ROW, "");
        contentValues.put(LAST_MESSAGE_TIME_ROW, System.currentTimeMillis());
        contentValues.put(FIRST_MESSAGE_TIME_ROW, System.currentTimeMillis());
        contentValues.put(MESSAGES_SENT_ROW, 1);
        contentValues.put(BLACKLISTED_ROW, 0);
        contentValues.put(IN_CHAT_ROW, inChat);
        final Long result = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }

    /*

    public User[] findAllOccurencesOfUsername(final String username)
    {
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT ?, ? FROM ?";
        query = Utils.createString(query, new String[]{USERNAME_ROW, JID_ROW, TABLE_NAME});
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do
            {
                final String tempUsername = cursor.getString(cursor.getColumnIndex(USERNAME_ROW));
                final String jid = cursor.getString(cursor.getColumnIndex(JID_ROW));

                if (username.equals(tempUsername))
                {
                    users.clear();
                    users.add(new User(tempUsername, jid));
                    break;
                }

                else if (username.startsWith(tempUsername))
                {
                    users.add(new User(tempUsername, jid));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return users.toArray(new User[0]);
    }

    */

    public User[] getTop5()
    {
        ArrayList<Pair<User, Integer>> usersPair = new ArrayList<>();
        final String query = String.format("SELECT %s, %s, %s FROM %s WHERE %s = 0 ORDER BY %s DESC LIMIT 5", USERNAME_ROW, JID_ROW, MESSAGES_SENT_ROW, TABLE_NAME, BLACKLISTED_ROW, MESSAGES_SENT_ROW);
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do
            {
                final String username = cursor.getString(cursor.getColumnIndex(USERNAME_ROW));
                final String jid = cursor.getString(cursor.getColumnIndex(JID_ROW));
                final int messageCount = cursor.getInt(cursor.getColumnIndex(MESSAGES_SENT_ROW));
                usersPair.add(new Pair<User, Integer>(new User(username, jid), messageCount));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        Collections.sort(usersPair, new Comparator<Pair<User, Integer>>()
        {
            @Override
            public int compare(Pair<User, Integer> userIntegerPair, Pair<User, Integer> t1)
            {
                return t1.second - userIntegerPair.second;
            }
        });

        User[] users = new User[usersPair.size()];

        for (int i = 0; i < users.length; i++)
        {
            users[i] = usersPair.get(i).first;
        }

        return users;
    }

    public int getMessageCount(final User user)
    {
        int result = 0;
        final String query = String.format("SELECT %s FROM %s WHERE %s = \"%s\"", MESSAGES_SENT_ROW, TABLE_NAME, USERNAME_ROW, user.getUsername());
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            result = cursor.getInt(cursor.getColumnIndex(MESSAGES_SENT_ROW));
        }

        cursor.close();
        database.close();
        return result;
    }

    public User[] getInactives(final Long timestamp)
    {
        ArrayList<User> users = new ArrayList<User>();
        final String query = String.format("SELECT %s, %s, %s FROM %s WHERE %s = 1 AND %s = 0", USERNAME_ROW, JID_ROW, LAST_MESSAGE_TIME_ROW, TABLE_NAME, IN_CHAT_ROW, BLACKLISTED_ROW);
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, null);
        final Long currentTimestamp = System.currentTimeMillis();

        if (cursor.moveToFirst())
        {
            do
            {
                final String username = cursor.getString(cursor.getColumnIndex(USERNAME_ROW));
                final String jid = cursor.getString(cursor.getColumnIndex(JID_ROW));
                final Long oldTimestamp = cursor.getLong(cursor.getColumnIndex(LAST_MESSAGE_TIME_ROW));

                if (currentTimestamp - oldTimestamp >= timestamp)
                {
                    users.add(new User(username, jid));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return users.toArray(new User[0]);
    }

    public boolean updateinChatValue(final User user, int newValue)
    {
        if (newValue > 1 || newValue < 0)
        {
            return false;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(IN_CHAT_ROW, newValue);
        SQLiteDatabase database = this.getReadAndWriteDatabase();
        final int result = database.update(TABLE_NAME, contentValues, JID_ROW + "=?", new String[]{user.getJID()});
        database.close();
        return result != 0;
    }
}
