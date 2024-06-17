package edu.ucr.cs.riple.annotator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class Foo {

  List<String> list = new ArrayList<>();

  void bar(HttpServletRequest request) {
    String param = request.getParameter("param");
    list.add(param);
  }

  void exec() {
    sink(list.iterator().next());
  }

  void sink(String param) {
    ProcessBuilder processBuilder = new ProcessBuilder(param);
    try {
      processBuilder.start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
