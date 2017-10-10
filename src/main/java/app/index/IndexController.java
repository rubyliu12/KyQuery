package app.index;

import app.util.Path;
import app.util.ViewUtil;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController {

  /**
   * init index page
   */
  public static Route serveIndexPage = (Request request, Response response) -> {

    Map<String, Object> model = new HashMap<>();
    return ViewUtil.render(request, model, Path.Template.INDEX);
  };

}
