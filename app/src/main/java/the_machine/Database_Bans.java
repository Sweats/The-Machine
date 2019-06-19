package the_machine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Database_Bans extends Database
{
    public static final String NAME = "The_Machine_Bans.db";
    private static final String NAME_GROUP = "The_Machine_Bans_";
    public static final String TABLE_NAME = "bansTable";
    public static final String BAN_TIMESTAMP_ROW = "banTime";
    public static final String BAN_EXPIRATION_ROW = "banExpiration";
    public static final String BAN_REASON_ROW = "banReason";

    private static final String UNKNOWN = "Unknown";

    private Database_Bans()
    {
        super(NAME, TABLE_NAME);
    }

    public static Database_Bans get()
    {
        return new Database_Bans();
    }

    @Override
    protected void createDatabase()
    {
        final String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + USERNAME_ROW + " TEXT, " + JID_ROW + " TEXT, " + BAN_TIMESTAMP_ROW + " INTEGER NOT NULL, " + BAN_EXPIRATION_ROW + " INTEGER NOT NULL, " + BAN_REASON_ROW + " TEXT)";
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(this.getDatabasePath(), null);
        database.execSQL(query);
        database.close();

    }

    public boolean addUser(final User user, final Long expiration)
    {
        return addUser(user, expiration, "Reason not specified");
    }

    public boolean addUser(final User user, final Long expiration, final String reason)
    {
        SQLiteDatabase database = this.getReadAndWriteDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_ROW, user.getUsername());
        contentValues.put(JID_ROW, user.getJID());
        contentValues.put(BAN_TIMESTAMP_ROW, System.currentTimeMillis());
        contentValues.put(BAN_EXPIRATION_ROW, expiration);
        contentValues.put(BAN_REASON_ROW, reason);
        final Long result = database.insert(TABLE_NAME, null, contentValues);
        database.close();
        return result != -1;
    }

    public String getBanInformation(final User user)
    {
        String result = null;
        final String query = "SELECT " + BAN_TIMESTAMP_ROW + ", " + BAN_EXPIRATION_ROW + " FROM " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=?";
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{user.getUsername()});

        if (cursor.moveToFirst())
        {
            final String banExpirationDate = Utils.createDate(cursor.getLong(cursor.getColumnIndex(BAN_EXPIRATION_ROW)));
            final String banStartDate = Utils.createDate(cursor.getLong(cursor.getColumnIndex(BAN_TIMESTAMP_ROW)));
            result = "Ban information for " + user.getUsernameDisplay() + ":\n\nDate banned:\n\n " + banStartDate + "\n\nBan expiration date:\n\n" + banExpirationDate;
        }

        cursor.close();
        database.close();
        return result;
    }

    public String[] getBannedUsernames()
    {
        ArrayList<String> banList = new ArrayList<String>();
        final String query = "SELECT " + USERNAME_ROW + " FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do
            {
                banList.add(cursor.getString(cursor.getColumnIndex(USERNAME_ROW)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return banList.toArray(new String[0]);
    }

    public User getBannedUser(final String username)
    {
        User user = null;
        final String query = "SELECT " + USERNAME_ROW + ", " + JID_ROW + " FROM " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=?";
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst())
        {
            user = new User(cursor.getString(cursor.getColumnIndex(USERNAME_ROW)), cursor.getString(cursor.getColumnIndex(JID_ROW)));
        }

        cursor.close();
        database.close();
        return user;
    }

    public User[] getAllBannedUsers()
    {
        ArrayList<User> usersList = new ArrayList<User>();
        final String query = "SELECT " + USERNAME_ROW + ", " + JID_ROW + " FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do
            {
                usersList.add(new User(cursor.getString(cursor.getColumnIndex(USERNAME_ROW)), cursor.getString(cursor.getColumnIndex(JID_ROW))));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return usersList.toArray(new User[0]);
    }

    // Permanent ban by default.
    @Override
    public boolean addUser(final User user)
    {
        return addUser(user, Long.MAX_VALUE);
    }

    public User getOldestBannedUser()
    {
        final String query = "SELECT " + USERNAME_ROW + ", " + JID_ROW + ", " + BAN_TIMESTAMP_ROW + " FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, null);

        Long timestamp = System.currentTimeMillis();
        String oldUsername = null, oldJid = null;

        if (cursor.moveToFirst())
        {
            do
            {
                final String username = cursor.getString(cursor.getColumnIndex(USERNAME_ROW));
                final String userJid = cursor.getString(cursor.getColumnIndex(JID_ROW));
                final Long bannedTimestamp = cursor.getLong(cursor.getColumnIndex(BAN_TIMESTAMP_ROW));

                if (bannedTimestamp < timestamp)
                {
                    timestamp = bannedTimestamp;
                    oldUsername = username;
                    oldJid = userJid;
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        if (oldUsername == null && oldJid == null)
        {
            return null;
        }

        return new User(oldUsername, oldJid);
    }

    public User getOldestBannedUser(final Group group)
    {
        final String[] bannedUsernames = group.getBannedUsernames();
        final String query = "SELECT " + USERNAME_ROW + ", " + JID_ROW + ", " + BAN_TIMESTAMP_ROW + " FROM " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=?";
        SQLiteDatabase database = this.getReadOnlyDatabase();
        Cursor cursor = database.rawQuery(query, bannedUsernames);

        Long timestamp = System.currentTimeMillis();
        String oldUsername = null, oldJid = null;

        if (cursor.moveToFirst())
        {
            do
            {
                final String username = cursor.getString(cursor.getColumnIndex(USERNAME_ROW));
                final String userJid = cursor.getString(cursor.getColumnIndex(JID_ROW));
                final Long bannedTimestamp = cursor.getLong(cursor.getColumnIndex(BAN_TIMESTAMP_ROW));

                if (bannedTimestamp < timestamp)
                {
                    timestamp = bannedTimestamp;
                    oldUsername = username;
                    oldJid = userJid;
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        if (oldUsername == null && oldJid == null)
        {
            return null;
        }

        return new User(oldUsername, oldJid);
    }

    public boolean isBanTimeExpired(final User user)
    {
        boolean result = false;
        final String query = "SELECT " + USERNAME_ROW + ", " + JID_ROW + ", " + BAN_EXPIRATION_ROW + " FROM " + TABLE_NAME + " WHERE " + USERNAME_ROW + "=?";
        SQLiteDatabase database = this.getReadAndWriteDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{user.getUsername()});

        if (cursor.moveToFirst())
        {
            final Long timestamp = System.currentTimeMillis();
            final Long expirationTime = cursor.getLong(cursor.getColumnIndex(BAN_EXPIRATION_ROW));

            if (timestamp >= expirationTime)
            {
                result = true;
            }
        }

        database.close();
        cursor.close();
        return result;
    }
}
