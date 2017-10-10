package app.query.baiqishi.Controller;

import static app.util.RequestUtil.clientAcceptsHtml;

import app.login.LoginController;
import app.query.baiqishi.BQSIVSQuery;
import app.util.HandleExcel;
import app.util.Path.Template;
import app.util.ViewUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * 白骑士IVS查询控制器
 */
public class IVSController {
  public static Route serverIvsQuery = (Request request, Response response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    Map<String, Object> model = new HashMap<>();

    if (clientAcceptsHtml(request)) {
      return ViewUtil.render(request, model, Template.BQS_IVS);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };

  public static Route ivsQueryExcel = (request, response) -> {
    LoginController.ensureUserIsLoggedIn(request, response);
    request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
//    uploadTool.save(request.raw().getPart("uploaded_file"));
    HandleExcel handleExcel = new HandleExcel();
    Part upload = request.raw().getPart("uploaded_file");
    Map<String, Object> model = new HashMap<>();
    if (!("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(upload.getContentType()))) {

      model.put("error_type", true);
      return ViewUtil.render(request, model, Template.BQS_IVS);
    }
    JSONArray uploadInfo = JSONArray.fromObject(handleExcel.handleExcelUsePOI(upload));
    Iterator<JSONObject>  queryLists = uploadInfo.iterator();
    if (queryLists.hasNext()) {
      queryLists.next();
    }

    JSONArray result = new JSONArray();
    JSONObject header = JSONObject.fromObject(uploadInfo.get(0));
    header.put("result", "审核结果");
    header.put("strategySet", "拒绝原因");

//    result.add(0, header);
    while (queryLists.hasNext()) {
      JSONObject query = queryLists.next();
      JSONObject ivsResult = JSONObject.fromObject(BQSIVSQuery.ivsQuery(query));
      if ("Reject".equals(ivsResult.get("finalDecision"))) {

        query.put("result", "拒绝");
        query.put("strategySet",ivsResult.get("strategySet"));
      } else if("Accept".equals(ivsResult.get("finalDecision"))) {
        query.put("result", "通过，无匹配记录");
        query.put("strategySet","");
      } else {
        query.put("result", "审核，低风险名单有击中，建议人工核实");
        query.put("strategySet","");
      }
      result.add(query);
    }
    String outHref = handleExcel.save(upload.getSubmittedFileName(), result.toString());
    //handleExcel.save(handleExcel.handleExcelUsePOI(upload));
    if (clientAcceptsHtml(request)) {
      model.put("header", header);
      model.put("rsList", result);
      return ViewUtil.render(request, model, Template.BQS_IVS);
    }
    return ViewUtil.notAcceptable.handle(request, response);
  };
}
