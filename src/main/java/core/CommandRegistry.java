package core;

import java.util.*;
import commands.BaseCommand;

public class CommandRegistry {

    private final Map<String, BaseCommand> commands = new HashMap<>();

    public void register(BaseCommand command) {
        commands.put(command.name(), command);
    }

    public BaseCommand get(String name) {
        return commands.get(name);
    }

    public Collection<BaseCommand> all() {
        return commands.values();
    }
}