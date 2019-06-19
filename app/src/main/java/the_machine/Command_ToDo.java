package the_machine;

public class Command_ToDo implements Command
{
    @Override
    public String execute(final Message message)
    {
        return MachineStrings.RESPONSE_TODO;
    }
}
