import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import proxy.ApiGatewayProxyResponse;
import proxy.ApiGatewayRequest;

public class GetCustomersTests {

	private static Context context = null;
	private static LambdaLogger logger = null;

	@BeforeAll
	public static void beforeAll() {
		context = Mockito.mock(Context.class);
		logger = Mockito.mock(LambdaLogger.class);
		Mockito.when(context.getLogger()).thenReturn(logger);
	}

	@Test
	public void getAgencyById() {
		ApiGatewayRequest request = Mockito.mock(ApiGatewayRequest.class);
		Map<String, String> pathParameters = new HashMap<String, String>();
		pathParameters.put("agencyId", "1");
		Mockito.when(request.getPathParameters()).thenReturn(pathParameters);
		ApiGatewayProxyResponse response = new GetCustomers().handleRequest(request, context);
		assertEquals(200, response.getStatusCode());
	}

	@Test
	public void getAgencyById400() {
		ApiGatewayRequest request = Mockito.mock(ApiGatewayRequest.class);
		Map<String, String> pathParameters = new HashMap<String, String>();
		pathParameters.put("agencyId", "a");
		Mockito.when(request.getPathParameters()).thenReturn(pathParameters);
		ApiGatewayProxyResponse response = new GetCustomers().handleRequest(request, context);
		assertEquals(400, response.getStatusCode());
	}

	@Test
	public void getAgencyById404() {
		ApiGatewayRequest request = Mockito.mock(ApiGatewayRequest.class);
		Map<String, String> pathParameters = new HashMap<String, String>();
		pathParameters.put("agencyId", "0");
		Mockito.when(request.getPathParameters()).thenReturn(pathParameters);
		ApiGatewayProxyResponse response = (ApiGatewayProxyResponse) new GetCustomers().handleRequest(request,
				context);
		assertEquals(404, response.getStatusCode());
	}

}
