package demo.taint;

import static edu.ucr.cs.riple.Sanitizer.sanitize;

import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;
import java.io.File;

public class Builder {

  RuntimeExec runtimeExec = new RuntimeExec();

  public Builder setCommand(@RUntainted String[] command) {
    this.runtimeExec.setCommand(command);
    return this;
  }

  public Builder setProcessDirectory(@RUntainted File processDirectory) {
    this.runtimeExec.setProcessDirectory(processDirectory);
    return this;
  }

  public Builder setProcessProperties(@RUntainted String[] processProperties) {
    this.runtimeExec.setProcessProperties(processProperties);
    return this;
  }
}

class ProtectedBuilder {

  RuntimeExec runtimeExec = new RuntimeExec();

  public ProtectedBuilder setCommand(String[] command) {
    runtimeExec.setCommand(sanitize(command));
    return this;
  }

  public ProtectedBuilder setProcessDirectory(File processDirectory) {
    runtimeExec.setProcessDirectory(sanitize(processDirectory));
    return this;
  }

  public ProtectedBuilder setProcessProperties(String[] processProperties) {
    runtimeExec.setProcessProperties(sanitize(processProperties));
    return this;
  }
}
