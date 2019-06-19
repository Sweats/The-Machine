package the_machine;

public interface Command
{
     /**
      * To properly add a command to The Machine, you need to do the following:
      *
      * 1. Create a new class that implements this interface and @Override String execute(final Message message)
      * 2. Add your command string in MachineStrings.java. They must start with !. Follow the same naming pattern as the other commands.
      * 3. Go to Global.java then add your command under the getCommandStruct() function.
      *
      * That's it. Do whatever you want inside your command :)
      *
      * @param message The message object that is automatically created for us. If you need to do anything related to Kik, use this parameter.
      *
      * @return The string that the bot will reply to the group or private message with.
      */

     String execute(final Message message);
}
