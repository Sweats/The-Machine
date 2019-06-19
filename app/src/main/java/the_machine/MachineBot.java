package the_machine;
// kek nice change.

import android.content.ContentValues;

public class MachineBot
{
    private String m_botReply = null;

    public MachineBot(final ContentValues contentValues)
    {
        try
        {
            Message message = new Message(contentValues);
            onMessage(message);
        }

        catch (Exception e)
        {
            e.printStackTrace();
            //Kik.messageGroup(Log.getStackTraceString(e), Group.createFromJID(MachineStrings.machineTeamJID));
        }
    }

    // Bot logic starts here. This gets called when a message is received.
    private void onMessage(final Message message)
    {
        if (message.getUser().isMe() || message.isImage())
        {
            return;
        }

        // Something happened to someone else in a group like getting kicked, added, banned, etc.
        if (message.isStatMessage())
        {
            handleStatMessage(message);
        }

        // Something happened to The Machine in a group like getting kicked, added, banned, etc.
        else if (message.isSystemMessage())
        {
            handleSystemMessage(message);
        }

        // Otherwise, we can assume it's just a normal message in a group.
        else
        {
            handleRegularMessage(message);
        }

        if (m_botReply != null)
        {
           if (!message.isPrivateMessage())
           {
               Kik.messageGroup(m_botReply, message.getGroup());
           }

           else
           {
               Kik.messageUser(m_botReply, message.getUser());
           }

           if (m_botReply.equalsIgnoreCase("Successfully removed support for this group."))
           {
               Kik.leaveGroup(message.getGroup());
           }
        }

        Database_Chat.get().update(message);
        Database_Chat.get(message.getGroup()).update(message);
    }

    private void handleStatMessage(final Message message)
    {
        if (!message.getGroup().isValidGroup())
        {
            Kik.leaveGroup(message.getGroup());
            return;
        }

        final Struct[] struct = Global.getCallbackStruct();
        final String messageText = message.getMessageText();

        for (int i = 0; i < struct.length; i++)
        {
            if (messageText.equals(struct[i].m_string))
            {
                m_botReply = struct[i].m_callback.execute(message);
                break;
            }
        }
    }

    private void handleRegularMessage(final Message message)
    {
        final String messageText = message.getMessageText();

        if (!message.getGroup().isValidGroup() && !messageText.startsWith(MachineStrings.COMMAND_SUPPORT_ADD))
        {
            Kik.leaveGroup(message.getGroup());
            return;
        }

        final Struct[] struct = Global.getCommandStruct();
        final String[] args = messageText.split(" ", 2);
        final String commandCheck = args[0];

        for (int i = 0; i < struct.length; i++)
        {
            if (messageText.startsWith(struct[i].m_string) && commandCheck.equals(struct[i].m_string))
            {
                m_botReply = struct[i].m_command.execute(message);
                break;
            }
        }
    }

    // Don't grab the User object from here. It's not a valid user.

    private void handleSystemMessage(final Message message)
    {
        final String messageText = message.getMessageText();

        if (messageText.equals(MachineStrings.CALLBACK_ADDED_YOU))
        {
            m_botReply = new Callback_AddedYou().execute(message);
        }

        else if (message.equals(MachineStrings.CALLBACK_YOU_HAVE_ADDED))
        {
            m_botReply = new Callback_YouHaveAdded().execute(message);
        }
    }
}
