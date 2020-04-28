
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import entity.CustomerQuery;
import proxy.ApiGatewayProxyResponse;
import proxy.ApiGatewayRequest;
import service.AgentService;

public class GetCustomers implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

	private AgentService agentService = new AgentService();

	public ApiGatewayProxyResponse handleRequest(ApiGatewayRequest request, Context context) {
		LambdaLogger logger = context.getLogger();
		try {
			Map<String, String> queryStringParameters = request.getQueryStringParameters();
			if (queryStringParameters == null || queryStringParameters.get("page") == null
					|| queryStringParameters.get("pagesize") == null) {
				return new ApiGatewayProxyResponse(400, null, null);
			}
			CustomerQuery customerQuery = agentService.getCustomers(Integer.parseInt(queryStringParameters.get("page")),
					Integer.parseInt(queryStringParameters.get("pagesize")), queryStringParameters.get("name"),
					queryStringParameters.get("address"), queryStringParameters.get("phone"));
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Access-Control-Allow-Origin", "*");
			return new ApiGatewayProxyResponse(200, headers, new Gson().toJson(customerQuery));
		} catch (NumberFormatException | SQLException e) {
			logger.log(e.getMessage());
			return new ApiGatewayProxyResponse(400, null, null);
		} catch (ClassNotFoundException e) {
			logger.log(e.getMessage());
			return new ApiGatewayProxyResponse(500, null, null);
		}
	}
}