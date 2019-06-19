package the_machine;

public class Command_Version implements Command
{
    @Override
    public String execute(Message message)
    {
        return MachineStrings.RESPONSE_VERSION;
    }
}
