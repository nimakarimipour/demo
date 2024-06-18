package edu.ucr.cs.riple.annotator;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

import javax.annotation.Tainted;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.ucr.cs.riple.Sanitizer.sanitize;

public class Foo {

    List<String> list = new ArrayList<>();

    void bar(HttpServletRequest request){
        String param = request.getParameter("param");
        list.add(param);
    }

    void exec(){
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

    public void test(HttpServletRequest request) {
      Foo foo = new Foo();
      foo.bar(request);
      foo.exec();
    }
}
