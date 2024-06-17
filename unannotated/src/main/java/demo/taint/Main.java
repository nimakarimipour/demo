package demo.taint;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class Main {

  void test(HttpServletRequest request){
    String input = request.getParameter("input");
    RuntimeExec re = new RuntimeExec();
    re.setCommand(new String[]{input});
    re.execute(new HashMap<>());
  }
}
