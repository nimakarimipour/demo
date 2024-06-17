package demo.taint;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuntimeExec {

  private static final String VAR_OPEN = "${";
  private static final String VAR_CLOSE = "}";
  private static final String DIRECTIVE_SPLIT = "SPLIT:";
  private static final Map<String, String> defaultProperties = new HashMap<>();

  private String[] processProperties;
  private File processDirectory;
  private String[] command;

  public void setCommand(String[] command) {
    this.command = command;
  }

  public void setProcessDirectory(File processDirectory) {
    this.processDirectory = processDirectory;
  }

  public void setProcessProperties(String[] processProperties) {
    this.processProperties = processProperties;
  }

  public ExecutionResult execute(Map<String, String> properties) {
    // check that the command has been set
    if (command == null) {
      throw new IllegalStateException("Command has not been set");
    }
    // create the properties
    Runtime runtime = Runtime.getRuntime();
    String[] commandToExecute;
    try {
      // execute the command with full property replacement
      commandToExecute = getCommand(properties);
      runtime.exec(commandToExecute, processProperties, processDirectory);
    } catch (IOException e) {
      // The process could not be executed here
      return ExecutionResult.FAILURE;
    }
    return ExecutionResult.SUCCESS;
  }

  public String[] getCommand(Map<String, String> properties) {
    Map<String, String> execProperties = null;
    if (properties == defaultProperties) {
      // we are just using the default properties
      execProperties = defaultProperties;
    } else {
      execProperties = new HashMap<String, String>(defaultProperties);
      // overlay the supplied properties
      execProperties.putAll(properties);
    }
    // Perform the substitution for each element of the command
    ArrayList<String> adjustedCommandElements = new ArrayList<String>(20);
    for (int i = 0; i < command.length; i++) {
      StringBuilder sb = new StringBuilder(command[i]);
      for (Map.Entry<String, String> entry : execProperties.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        // ignore null
        if (value == null) {
          value = "";
        }
        // progressively replace the property in the command
        key = (VAR_OPEN + key + VAR_CLOSE);
        int index = sb.indexOf(key);
        while (index > -1) {
          // replace
          sb.replace(index, index + key.length(), value);
          // get the next one
          index = sb.indexOf(key, index + 1);
        }
      }
      String adjustedValue = sb.toString();
      // Now SPLIT: it
      if (adjustedValue.startsWith(DIRECTIVE_SPLIT)) {
        String unsplitAdjustedValue = sb.substring(DIRECTIVE_SPLIT.length());

        // There may be quoted arguments here (see ALF-7482)
        ExecParameterTokenizer quoteAwareTokenizer =
            new ExecParameterTokenizer(unsplitAdjustedValue);
        List<String> tokens = quoteAwareTokenizer.getAllTokens();
        adjustedCommandElements.addAll(tokens);
      } else {
        adjustedCommandElements.add(adjustedValue);
      }
    }
    // done
    return adjustedCommandElements.toArray(new String[adjustedCommandElements.size()]);
  }
}
