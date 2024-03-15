package com.hydraql.console;

import java.util.HashMap;
import java.util.Map;

import org.jline.console.CommandInput;
import org.jline.console.Printer;
import org.jline.console.impl.JlineCommandRegistry;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import com.hydraql.manager.core.conf.HydraqlHBaseConfiguration;
import com.hydraql.manager.core.conf.PropertyKey;
import com.hydraql.manager.core.template.HydraqlTemplate;

/**
 * @author leojie 2023/7/30 19:10
 */
public abstract class BaseCommands extends JlineCommandRegistry {
    private LineReader reader;
    private final Printer printer;
    public BaseCommands(Printer printer) {
        this.printer = printer;
    }

    protected void println(Object message) {
        String option = System.getProperty("hql.dev");
        if ("true".equalsIgnoreCase(option)) {
            this.terminal().writer().println(message);
        } else {
            Map<String, Object> options = new HashMap<>();
            options.put(Printer.STRUCT_ON_TABLE, true);
            options.put(Printer.VALUE_STYLE, "classpath:/hydraql/console/gron.nanorc");
            printer.println(options, message);
        }
    }

    protected Terminal terminal() {
        return reader.getTerminal();
    }

    public LineReader getReader() {
        return reader;
    }

    public void setReader(LineReader reader) {
        this.reader = reader;
    }

    protected String parseCommand(CommandInput input, boolean withCommand) {
        String[] args = input.args();
        StringBuilder sb;
        if (withCommand) {
            sb = new StringBuilder(input.command());
            sb.append(" ");
        } else {
            sb = new StringBuilder();
        }
        if (args != null && args.length > 0) {
            for (String arg : args) {
                sb.append(arg).append(" ");
            }
        }
        return sb.toString();
    }

    protected HydraqlTemplate getHydraqlTemplate(Map<String, String> properties) {
        HydraqlHBaseConfiguration conf = new HydraqlHBaseConfiguration();
        if (!properties.containsKey(PropertyKey.HYDRAQL_MANAGER_PLUGINS_DIR.getName())) {
            conf.set(PropertyKey.HYDRAQL_MANAGER_PLUGINS_DIR, HqlConsoleEnv.getHqlConsolePluginsHome());
        }
        properties.forEach((k, v) -> conf.set(PropertyKey.fromString(k), v));
        return HydraqlTemplate.Factory.create(conf);
    }

    protected HydraqlTemplate getHydraqlTemplate() {
        Map<String, String> properties = HClusterContext.getInstance().getCurrentClusterProperties();
        return getHydraqlTemplate(properties);
    }
}
