package the_machine;

import java.util.ArrayList;

public class Global
{
    private Global(){};

    public static Struct[] getCommandStruct()
    {
        ArrayList<Struct> struct = new ArrayList<>();
        struct.add(new Struct(new Command_Help(), MachineStrings.COMMAND_HELP));
        struct.add(new Struct(new Command_Help2(), MachineStrings.COMMAND_HELP2));
        struct.add(new Struct(new Command_Help3(), MachineStrings.COMMAND_HELP3));
        struct.add(new Struct(new Command_Ban(), MachineStrings.COMMAND_BAN));
        struct.add(new Struct(new Command_Unban(), MachineStrings.COMMAND_UNBAN));
        struct.add(new Struct(new Command_BanInfo(), MachineStrings.COMMAND_BANINFO));
        struct.add(new Struct(new Command_Groups(), MachineStrings.COMMAND_GROUPS));
        struct.add(new Struct(new Command_Lockdown(), MachineStrings.COMMAND_LOCKDOWN));
        struct.add(new Struct(new Command_Purge(), MachineStrings.COMMAND_PURGE));
        struct.add(new Struct(new Command_Refresh(), MachineStrings.COMMAND_REFRESH));
        struct.add(new Struct(new Command_RefreshAll(), MachineStrings.COMMAND_REFRESH_ALL));
        struct.add(new Struct(new Command_SupportAdd(), MachineStrings.COMMAND_SUPPORT_ADD));
        struct.add(new Struct(new Command_SupportRemove(), MachineStrings.COMMAND_SUPPORT_REMOVE));
        struct.add(new Struct(new Command_Telegram(), MachineStrings.COMMAND_TELEGRAM));
        struct.add(new Struct(new Command_Top5(), MachineStrings.COMMAND_TOP5));
        struct.add(new Struct(new Command_Top5Global(), MachineStrings.COMMAND_TOP5_GLOBAL));
        struct.add(new Struct(new Command_KickInactives(), MachineStrings.COMMAND_KICK_INACTIVES));
        struct.add(new Struct(new Command_Kick(), MachineStrings.COMMAND_KICK));
        struct.add(new Struct(new Command_Test(), MachineStrings.COMMAND_TEST));
        struct.add(new Struct(new Command_ToDo(), MachineStrings.COMMAND_TODO));
        struct.add(new Struct(new Command_Version(), MachineStrings.COMMAND_VERSION));
        struct.add(new Struct(new Command_Alert(), MachineStrings.COMMAND_ALERT));
        struct.add(new Struct(new Command_Pm(), MachineStrings.COMMAND_PM));
        struct.add(new Struct(new Command_MuzzNuke(), MachineStrings.COMMAND_MUZZYNUKE));
        struct.add(new Struct(new Command_Announce(), MachineStrings.COMMAND_ANNOUNCE));
        struct.add(new Struct(new Command_AddAdmin(), MachineStrings.COMMAND_ADD_ADMIN));
        struct.add(new Struct(new Command_RemoveAdmin(), MachineStrings.COMMAND_REMOVE_ADMIN));
        struct.add(new Struct(new Command_GroupName(), MachineStrings.COMMAND_GROUPNAME));
        struct.add(new Struct(new Command_Inactive(), MachineStrings.COMMAND_INACTIVE));
        struct.add(new Struct(new Command_AddMe(), MachineStrings.COMMAND_ADDME));
        struct.add(new Struct(new Command_Stats(), MachineStrings.COMMAND_STATS));
        return struct.toArray(new Struct[0]);
    }

    public static Struct[] getCallbackStruct()
    {
        ArrayList<Struct> struct = new ArrayList<>();
        struct.add(new Struct(new Callback_HasBanned(), MachineStrings.CALLBACK_HAS_BANNED));
        struct.add(new Struct(new Callback_HasRemoved(), MachineStrings.CALLBACK_HAS_REMOVED));
        struct.add(new Struct(new Callback_HasLeftChat(), MachineStrings.CALLBACK_LEFT_CHAT));
        struct.add(new Struct(new Callback_HasUnbanned(), MachineStrings.CALLBACK_HAS_UNBANNED));
        struct.add(new Struct(new Callback_HasJoinedChat(), MachineStrings.CALLBACK_JOINED_CHAT));
        struct.add(new Struct(new Callback_YouHaveAdded(), MachineStrings.CALLBACK_YOU_HAVE_ADDED));

        return struct.toArray(new Struct[0]);
    }
}
