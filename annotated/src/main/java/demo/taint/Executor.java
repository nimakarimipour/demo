package demo.taint;

import static edu.ucr.cs.riple.Sanitizer.sanitize;

import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;
import java.io.File;

public class Executor {

  RuntimeExec runtimeExec = new RuntimeExec();

  public void setCommand(@RUntainted String[] command) {
    this.runtimeExec.setCommand(command);
  }

  public void setProcessDirectory(@RUntainted File processDirectory) {
    this.runtimeExec.setProcessDirectory(processDirectory);
  }

  public void setProcessProperties(@RUntainted String[] processProperties) {
    this.runtimeExec.setProcessProperties(processProperties);
  }
}

class ProtectedExecutor {

  RuntimeExec runtimeExec = new RuntimeExec();

  public void setCommand(String[] command) {
    runtimeExec.setCommand(sanitize(command));
  }

  public void setProcessDirectory(File processDirectory) {
    runtimeExec.setProcessDirectory(sanitize(processDirectory));
  }

  public void setProcessProperties(String[] processProperties) {
    runtimeExec.setProcessProperties(sanitize(processProperties));
  }
}
