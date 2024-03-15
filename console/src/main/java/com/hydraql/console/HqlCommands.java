package com.hydraql.console;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jline.console.CmdDesc;
import org.jline.console.CommandInput;
import org.jline.console.CommandMethods;
import org.jline.console.Printer;
import org.jline.reader.impl.completer.SystemCompleter;

import com.hydraql.manager.core.template.HydraqlTemplate;

/**
 * @author leojie 2023/7/29 21:15
 */
public class HqlCommands extends BaseCommands {

    private final Map<String, CommandMethods> commandExecute = new HashMap<>();
    private final Map<String, List<String>> commandInfo = new HashMap<>();
    private final Map<String, String> aliasCommand = new HashMap<>();

    public HqlCommands(Printer printer) {
        super(printer);
        commandExecute.put("showVirtualTables", new CommandMethods(this::showVirtualTables, this::defaultCompleter));
        commandExecute.put("showCreateVirtualTable", new CommandMethods(this::showCreateVirtualTable, this::defaultCompleter));
        commandExecute.put("createVirtualTable", new CommandMethods(this::create, this::defaultCompleter));
        commandExecute.put("dropVirtualTable", new CommandMethods(this::dropVirtualTable, this::defaultCompleter));
        commandExecute.put("select", new CommandMethods(this::select, this::defaultCompleter));
        commandExecute.put("upsert", new CommandMethods(this::upsert, this::defaultCompleter));
        commandExecute.put("delete", new CommandMethods(this::delete, this::defaultCompleter));
        registerCommands(commandExecute);
    }

    public Map<String, String> commandAliases() {
        return aliasCommand;
    }

    public Set<String> commandNames() {
        return commandExecute.keySet();
    }

    public List<String> commandInfo(String command) {
        return commandInfo.get(command(command));
    }

    public boolean hasCommand(String command) {
        return commandExecute.containsKey(command) || aliasCommand.containsKey(command);
    }

    private String command(String name) {
        if (commandExecute.containsKey(name)) {
            return name;
        }
        return aliasCommand.get(name);
    }

    public SystemCompleter compileCompleters() {
        SystemCompleter out = new SystemCompleter();
        for (Map.Entry<String, CommandMethods> entry : commandExecute.entrySet()) {
            out.add(entry.getKey(), entry.getValue().compileCompleter().apply(entry.getKey()));
        }
        out.addAliases(aliasCommand);
        return out;
    }

    public Object invoke(CommandSession session, String command, Object... args) throws Exception {
        return commandExecute.get(command(command)).execute().apply(new CommandInput(command, args, session));
    }

    public CmdDesc commandDescription(List<String> args) {
        // TODO
        return new CmdDesc(false);
    }

    private void showVirtualTables(CommandInput input) {
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        hql = hql.replace("showVirtualTables ", "");
        HydraqlTemplate sqlTemplate = getHydraqlTemplate(HClusterContext.getInstance().getCurrentClusterProperties());
        List<String> virtualTables = sqlTemplate.showVirtualTables(hql);
        for (String virtualTable : virtualTables) {
            println(virtualTable);
        }
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void showCreateVirtualTable(CommandInput input) {
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        hql = hql.replace("showCreateVirtualTable ", "");
        HydraqlTemplate sqlTemplate = getHydraqlTemplate(HClusterContext.getInstance().getCurrentClusterProperties());
        String virtualTable = sqlTemplate.showCreateVirtualTable(hql);
        println(virtualTable);
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void create(CommandInput input) {
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        hql = hql.replace("createVirtualTable ", "");
        HydraqlTemplate sqlTemplate = getHydraqlTemplate(HClusterContext.getInstance().getCurrentClusterProperties());
        sqlTemplate.createVirtualTable(hql);
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void dropVirtualTable(CommandInput input) {
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        hql = hql.replace("dropVirtualTable ", "");
        HydraqlTemplate sqlTemplate = getHydraqlTemplate(HClusterContext.getInstance().getCurrentClusterProperties());
        sqlTemplate.dropVirtualTable(hql);
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void select(CommandInput input) {
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        HydraqlTemplate sqlTemplate = getHydraqlTemplate(HClusterContext.getInstance().getCurrentClusterProperties());
        // HBaseDataSet dataSet = sqlTemplate.select(hql);
        // String table = dataSet.showTable(false);
        println(hql);
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void upsert(CommandInput input) {
        HydraqlTemplate sqlTemplate = getHydraqlTemplate(HClusterContext.getInstance().getCurrentClusterProperties());
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        sqlTemplate.upsert(hql);
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void delete(CommandInput input) {
        HydraqlTemplate sqlTemplate = getHydraqlTemplate(HClusterContext.getInstance().getCurrentClusterProperties());
        long start = System.currentTimeMillis();
        String hql = parseSql(input);
        sqlTemplate.delete(hql);
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private String parseSql(CommandInput input) {
        return this.parseCommand(input, true);
    }

    @Override
    public String name() {
        return "hql";
    }
}
