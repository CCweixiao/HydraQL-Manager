package com.hydraql.console;

import org.jline.builtins.ConfigurationPath;
import org.jline.builtins.Styles;
import org.jline.console.impl.DefaultPrinter;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;

import java.util.Map;

/**
 * @author leojie 2023/7/30 19:05
 */
public class HqlPrinter extends DefaultPrinter {
    private final Terminal terminal;

    public HqlPrinter(ConfigurationPath configPath, Terminal terminal) {
        super(configPath);
        this.terminal = terminal;
    }

    @Override
    protected Terminal terminal() {
        return terminal;
    }

    @Override
    protected void highlightAndPrint(Map<String, Object> options, Throwable exception) {
        if (options.getOrDefault("exception", "stack").equals("stack")) {
            exception.printStackTrace();
        } else {
            AttributedStringBuilder asb = new AttributedStringBuilder();
            asb.append(exception.getMessage(), Styles.prntStyle().resolve(".em"));
            asb.toAttributedString().println(terminal());
        }
    }
}
