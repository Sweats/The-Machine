package the_machine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils
{
    private Utils(){}

    public static final String HELP_PAGE_1 = "HELP_PAGE_1.txt";
    public static final String HELP_PAGE_2 = "HELP_PAGE_2.txt";
    public static final String HELP_PAGE_3 = "HELP_PAGE_3.txt";

    public static final Long ONE_HOUR = 3600000L;
    public static final Long ONE_DAY = 86400000L;
    public static final Long ONE_WEEK = 604800000L;
    public static final Long ONE_MONTH = 2629746000L;

    /**
     * This function replaces every occurrence of ? inside the string parameter with the next string index inside the fillers array parameter. It goes from left to right.
     * @param string The string we want to modify
     * @param fillers The array string we want to replace every ? with.
     * @return The modified string. If the length of the fillers array does not equal the amount of ? in the string parameter, it will return the original passed in string.
     */
    public static String createString(final String string, final String[] fillers)
    {
        if (!check(string, fillers))
        {
            return string;
        }

        StringBuilder stringBuilder = new StringBuilder(string);
        int oldIndex = 0;

        for (int i = 0; i < fillers.length; i++)
        {
            oldIndex = stringBuilder.indexOf("?", oldIndex);
            stringBuilder = stringBuilder.deleteCharAt(oldIndex);
            stringBuilder = stringBuilder.insert(oldIndex, fillers[i]);
        }

        return stringBuilder.toString();
    }

    private static boolean check(final String string, final String[] fillers)
    {
        int count = 0;
        boolean result = false;

        for (int i = 0; i < string.length(); i++)
        {
            if (string.charAt(i) == '?')
            {
                count++;
            }
        }

        return count == fillers.length;
    }

    public static String getHelpCommands(final String page) throws IOException
    {
        StringBuffer stringBuffer = new StringBuffer();
        final File file = Kik.getKikApplication().getFileStreamPath(page);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = bufferedReader.readLine()) != null)
        {
            stringBuffer.append(line);
            stringBuffer.append("\n\n");
        }

        return stringBuffer.toString();
    }

    /**
     * This function simply makes The Machine send a message to The Machine Team. It is primarily used for sending stack traces.
     * @param message The message we want to send to The Machine Team.
     */

    public static void log(final String message)
    {
        Kik.messageGroup(message, Group.createFromJID(MachineStrings.machineTeamJID));
    }

    /**
     * This function creates a specified formatted date string.
     *
     * @param timestamp The timestamp in milliseconds to create our formatted string from.
     * @param format How we should format our string. See documentation for SimpleDateFormat for more information.
     * @return The formatted string.
     */

    public static String createDate(final Long timestamp, final String format)
    {
        return new SimpleDateFormat(format).format(new Date(timestamp));
    }

    /**
     * This function creates formatted date string for us.
     * The format string is EEEE, MMM d, yyyy @ K:mm a z
     *
     * @param timestamp The timestamp in milliseconds to create our formatted string from.
     * @return The formatted string.
     */

    public static String createDate(final Long timestamp)
    {
        final String dateFormat = "EEEE, MMM d, yyyy @ K:mm a z";
        return new SimpleDateFormat(dateFormat).format(new Date(timestamp));
    }

    public static boolean isValidNumberString(final String numberString)
    {
        boolean valid = true;

        try
        {
            Long value = Long.valueOf(numberString);
        }

        catch (NumberFormatException e)
        {
            valid = false;
        }

        return valid;
    }

    /**
     * This function is only used when a user invokes a command on a username and when The Machine fails to find the exact username.
     *
     * @param requestedUsername The exact username The Machine could not find in a database.
     * @param users A list of user names matching the requestedUsername.
     * @return A list of possible user names. If users is null, this will return "No username or anything matching to (requestedUsername) found"
     */

    public static String getPossibleUsernames(final String requestedUsername, final User[] users)
    {
        if (users == null || users.length == 0)
        {
            return createString("No username or anything matching to \"?\" found.", new String[]{requestedUsername});
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < users.length; i++)
        {
            stringBuilder.append(users[i].getUsername() + "\n\n");
        }

        stringBuilder.append("Multiple usernames matching \"" + requestedUsername + "\" found. Which one of those usernames did you mean?");
        return stringBuilder.toString();
    }

}