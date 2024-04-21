package com.hydraql.console;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jline.console.CmdDesc;
import org.jline.console.CommandInput;
import org.jline.console.CommandMethods;
import org.jline.console.Printer;
import org.jline.reader.impl.completer.SystemCompleter;

import com.hydraql.console.util.StringUtil;
import com.hydraql.manager.core.template.HydraQLTemplate;

/**
 * @author leojie 2023/7/29 21:15
 */
public class HClusterCommands extends BaseCommands {
    private final Map<String, CommandMethods> commandExecute = new HashMap<>();
    private final Map<String, List<String>> commandInfo = new HashMap<>();
    private final Map<String, String> aliasCommand = new HashMap<>();

    public HClusterCommands(Printer printer) {
        super(printer);
        commandExecute.put("list_clusters", new CommandMethods(this::listClusters, this::defaultCompleter));
        commandExecute.put("add_cluster", new CommandMethods(this::addCluster, this::defaultCompleter));
        commandExecute.put("remove_cluster", new CommandMethods(this::removeCluster, this::defaultCompleter));
        commandExecute.put("switch_cluster", new CommandMethods(this::switchCluster, this::defaultCompleter));
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


    private void listClusters(CommandInput input) {
        long start = System.currentTimeMillis();
        File clusterConfDirFile = HClusterContext.getInstance().getClusterConfDirFile();
        String[] confFiles = clusterConfDirFile.list();

        if (confFiles == null || confFiles.length < 1) {
            println("The cluster has not been added, please use the add_cluster command.");
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < confFiles.length; i++) {
                result.append(i).append(": ");
                result.append(confFiles[i], 0, confFiles[i].lastIndexOf(".properties"));
                result.append(" ");
            }
            println("Existing cluster list: \n");
            println(result);
        }

        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void addCluster(CommandInput input) {
        long start = System.currentTimeMillis();
        String command = parseCommand(input, true);
        String[] commands = command.split("\\s+");
        if (commands.length != 3) {
            println("Failed to add the cluster, please enter the correct command, such as:\n");
            println("add_cluster localhost hbase.zookeeper.quorum=localhost;" +
                    "hbase.zookeeper.property.clientPort=2181");
            return;
        }
        String clusterName = commands[1];
        String configText = commands[2];
        if (StringUtil.isBlank(configText)) {
            println("Failed to add the cluster, please enter the correct command, such as:\n");
            println("add_cluster localhost hbase.zookeeper.quorum=localhost;" +
                    "hbase.zookeeper.property.clientPort=2181");
            return;
        }
        String[] configs = configText.trim().split(";");
        boolean res = testClusterInfo(configs);
        if (!res) {
            return;
        }
        println("The newly added cluster connection test is successful.");
        String clusterConfDirPath = HClusterContext.getInstance().getClusterConfDirFile().getAbsolutePath();
        File clusterConfFile = new File(clusterConfDirPath.concat(File.separator).concat(clusterName)
                .concat(".properties"));
        if (clusterConfFile.exists()) {
            println(String.format("The configuration for cluster %s already exists.", clusterName));
            return;
        }
        try {
            if (clusterConfFile.createNewFile()) {
                BufferedWriter bfw = Files.newBufferedWriter(clusterConfFile.toPath());
                for (String config : configs) {
                    bfw.write(config + "\n");
                }
                bfw.flush();
                bfw.close();
            } else {
                println(String.format("Failed to create cluster configuration file %s .",
                        clusterConfFile.getAbsolutePath()));
            }
        } catch (IOException e) {
            println(String.format("Failed to create cluster configuration file %s .",
                    clusterConfFile.getAbsolutePath()));
            throw new IllegalStateException(e);
        }

        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private boolean testClusterInfo(String[] configs) {
        if (configs == null || configs.length == 0) {
            println("Please specify the cluster connection configuration, k1=v1;k2=v2 format.");
            return false;
        }
        String formatError = "Make sure that a single configuration item is in k=v format.";
        Map<String, String> p = new HashMap<>();
        for (String config : configs) {
            if (StringUtil.isBlank(config)) {
                println(formatError);
                return false;
            }
            String[] kv = config.split("=");
            if (kv.length != 2) {
                println(formatError);
                return false;
            }
            p.put(kv[0], kv[1]);
        }

        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            HydraQLTemplate hydraqlTemplate = getHydraqlTemplate(p);
            List<String> namespaces = hydraqlTemplate.listNamespaceNames();
            return namespaces != null && !namespaces.isEmpty();
        });
        boolean res = false;
        // 获取执行结果
        try {
            res = future.get(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            println("abnormal");
            future.cancel(true);
            throw new IllegalStateException(e);
        } catch (ExecutionException e) {
            println("An exception occurred during the cluster connection test.");
            future.cancel(true);
            throw new IllegalStateException(e);
        } catch (TimeoutException e) {
            // 超时了，结束该方法的执行
            println("The cluster connection test timed out, please check the configuration or cluster status.");
            future.cancel(true);
        }
        return res;
    }

    private void removeCluster(CommandInput input) {
        long start = System.currentTimeMillis();
        String command = parseCommand(input, true);
        String[] commands = command.split("\\s+");
        if (commands.length != 2) {
            println("Failed to remove cluster, please enter the correct command, such as:\n");
            println("remove_cluster localhost");
            return;
        }
        String clusterName = commands[1];
        String clusterConfDirPath = HClusterContext.getInstance().getClusterConfDirFile().getAbsolutePath();
        File clusterConfFile = new File(clusterConfDirPath.concat(File.separator).concat(clusterName)
                .concat(".properties"));
        if (!clusterConfFile.exists()) {
            println(String.format("Cluster %s does not exist.", clusterName));
            return;
        }
        boolean delete = clusterConfFile.delete();
        if (delete) {
            println(String.format("Cluster %s is successfully deleted.", clusterName));
        } else {
            println(String.format("Cluster %s failed to be deleted.", clusterName));

        }
        println("OK," + " cost: " + TimeConverter.humanReadableCost(System.currentTimeMillis() - start));
    }

    private void switchCluster(CommandInput input) {
        String command = parseCommand(input, true);
        String[] commands = command.split("\\s+");
        if (commands.length != 2) {
            println("Failed to switch cluster, please enter the correct command, such as:\n");
            println("switch_cluster localhost");
            return;
        }
        HClusterContext.getInstance().setCurrentSelectedCluster(commands[1].trim());
        println(String.format("The currently selected cluster is %s.", commands[1]));
    }

    @Override
    public String name() {
        return "hql";
    }
}
