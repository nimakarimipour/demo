package demo.taint;

import java.io.File;

public class Executor {

  RuntimeExec runtimeExec = new RuntimeExec();

  public void setCommand(String[] command) {
    this.runtimeExec.setCommand(command);
  }

  public void setProcessDirectory(File processDirectory) {
    this.runtimeExec.setProcessDirectory(processDirectory);
  }

  public void setProcessProperties(String[] processProperties) {
    this.runtimeExec.setProcessProperties(processProperties);
  }
}

class ProtectedExecutor {

  RuntimeExec runtimeExec = new RuntimeExec();

  public void setCommand(String[] command) {
    //    runtimeExec.setCommand(sanitize(command));
  }

  public void setProcessDirectory(File processDirectory) {
    //    runtimeExec.setProcessDirectory(sanitize(processDirectory));
  }

  public void setProcessProperties(String[] processProperties) {
    //    runtimeExec.setProcessProperties(sanitize(processProperties));
  }
}
