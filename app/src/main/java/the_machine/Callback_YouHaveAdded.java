package the_machine;

public class Callback_YouHaveAdded implements Callback
{
    @Override
    public String execute(final Message message)
    {
        final String groupTag = message.getGroup().getGroupTag();
        return Utils.createString(MachineStrings.WELCOME, new String[]{groupTag});

    }
}
