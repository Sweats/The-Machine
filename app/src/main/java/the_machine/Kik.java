package the_machine;

/*
 * This class is a place holder while you are coding. The documentation here will tell you what each Kik function actually returns.
 */

import android.app.Application;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Kik
{
    private Kik (){}

    /**
     * This function makes The Machine kick someone from the group. It must be an admin.
     *
     * @param user The user we want to kick from the group.
     * @param group The group where the user will be kicked from.
     */

    public static void kickUser(final User user, final Group group)
    {

    }

    /**
     * This function makes The Machine ban someone from the group. It must be an admin.
     *
     * @param user The user we want to ban in the group.
     * @param group The group where the user will be banned in.
     */

    public static void banUser(final User user, final Group group)
    {

    }

    /**
     * This function makes The Machine un-ban someone from the group. It must be an admin.
     *
     * @param user The banned user we want to un-ban from the group.
     * @param group The group where the banned user will be un-banned in.
     */


    public static void unbanUser(final User user, final Group group)
    {

    }

    /**
     * Don't use this. Use getGroupMembers() inside the Group object instead.
     *
     * @param group group.
     * @return Always null
     */

    //TODO: Figure out how kik fetches group members when you open the group info fragment. This would be very useful.
    public static User[] getGroupMembers(final Group group)
    {
        return null;
    }

    /**
     * This function makes The Machine add someone to a group. For this to work, the following conditions must be met:
     *
     * - The Machine has to have private messaged the user at least once at some point.
     * - The user has to have private messaged The Machine at least once at some point and that they haven't "stopped chatting" with The Machine.
     *
     * @param user The user we want to add to the group.
     * @param group The group the user will be added into.
     */

    public static void addUser(final User user, final Group group)
    {

    }

    /**
     * This function makes The Machine add multiple users to a group. For now, this is only used for when we add dummy accounts to a group when an admin invokes !lockdown fill.
     *
     * @param users An array of users we want to add to the group.
     * @param group The group where the users will be added into.
     */

    public static void addUsers(final User[] users, final Group group)
    {

    }

    /**
     * This function makes The Machine send private message to a user. This should only be used when replying to a private message. Otherwise, we risk hitting Kik's fun captcha verification.
     *
     * @param message The message we want to send.
     * @param user The recipient of the message.
     */

    public static void messageUser(final String message, final User user)
    {

    }

    /**
     * This function makes The Machine send a message to a group.
     *
     * @param message The message we want to send.
     * @param group The group that will receive the message.
     */

    public static void messageGroup(final String message, final Group group)
    {

    }

    /**
     * This function makes The Machine leave the group.
     *
     * @param group The group we want to leave.
     *
     */

    public static void leaveGroup(final Group group)
    {

    }

    /**
     * This function makes The Machine change the group name. It must be an admin.
     *
     * @param newName The new group name.
     * @param group The group where we want to change the group name in.
     */

    public static void changeGroupName(final String newName, final Group group)
    {

    }

    /**
     * This function makes The Machine stop chatting with a user. This means that user we have stopped chatting with can no longer add The Machine to groups.
     *
     * @param user The user we want to stop chatting with.
     */

    public static void stopChatting(final User user)
    {

    }

    /**
     * This function has to be used if you need access to any of Kiks files. This includes shared preferences, databases, etc.
     *
     * @return Android application object for our apk.
     */

    public static Application getKikApplication()
    {
        return null;
    }
}
