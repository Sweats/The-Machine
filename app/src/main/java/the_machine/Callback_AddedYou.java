package the_machine;

public class Callback_AddedYou implements Callback
{
    @Override
    public String execute(final Message message)
    {
        return "Hello.\n\nIf you are are a root admin of me, please type in \"!support_add\".";
    }
}
