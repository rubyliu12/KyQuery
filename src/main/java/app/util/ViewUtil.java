package app.util;

import static app.util.RequestUtil.getSessionCurrentUser;

import app.util.tools.MessageBundle;
import java.util.HashMap;
import java.util.Map;
import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

public class ViewUtil {

  // Renders a template given a model and a request
  // The request is needed to check the user session for language settings
  // and to see if the user is logged in
  public static String render(Request request, Map<String, Object> model, String templatePath) {
    model.put("msg", new MessageBundle());
    model.put("currentUser", getSessionCurrentUser(request));
    model.put("WebPath", Path.Web.class); // Access application URLs from templates
    return strictVelocityEngine().render(new ModelAndView(model, templatePath));
  }

  public static Route notAcceptable = (Request request, Response response) -> {
    response.status(HttpStatus.NOT_ACCEPTABLE_406);
    return new MessageBundle().get("ERROR_406_NOT_ACCEPTABLE");
  };

  public static Route notFound = (Request request, Response response) -> {
    response.status(HttpStatus.NOT_FOUND_404);
    return render(request, new HashMap<>(), Path.Template.NOT_FOUND);
  };

  private static VelocityTemplateEngine strictVelocityEngine() {
    VelocityEngine configuredEngine = new VelocityEngine();
    configuredEngine.setProperty("runtime.references.strict", true);
    configuredEngine.setProperty("resource.loader", "class");
    configuredEngine.setProperty("class.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    configuredEngine
        .setProperty("file.resource.loader.modificationCheckInterval", 60); // This is in seconds
    configuredEngine.setProperty("file.resource.loader.cache", "true");
    return new VelocityTemplateEngine(configuredEngine);
  }
}
