package research.rest;

import research.entity.UserData;
import research.utils.HealthLabsInfo;
import research.utils.HttpUtils;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.GregorianCalendar;

/**
 * @author Evgenia
 */

@Path("/bp")
@Interceptors(TokenStateInterceptor.class)
public class BloodPressureService {
    private UserData userData;

    @GET
    @Path("/user")
    public Response getDataOnBehalfOfUser() {
        UriBuilder uriBuilder = UriBuilder
                .fromPath(HealthLabsInfo.iHealthOpenApiUserUrl)
                .path("user/{userId}/bp.json/")
                .queryParam("client_id", HealthLabsInfo.clientId)
                .queryParam("client_secret", HealthLabsInfo.clientSecret)
                .queryParam("redirect_uri", HealthLabsInfo.redirectUri)
                .queryParam("access_token", userData.getAccessToken())
                .queryParam("sc", HealthLabsInfo.SC)
                .queryParam("sv", HealthLabsInfo.OpenApiBPSV)
                .queryParam("start_time", new GregorianCalendar(1970, 0, 1).getTimeInMillis());

        String uriResponse = HttpUtils.sendRequest(uriBuilder.build(userData.getUserId()));
        return Response.ok(uriResponse).build();
    }

    @GET
    @Path("/app")
    public Response getDataOnBehalfOfApplication() {
        UriBuilder uriBuilder = UriBuilder
                .fromPath(HealthLabsInfo.iHealthOpenApiApplicationUrl)
                .queryParam("client_id", HealthLabsInfo.clientId)
                .queryParam("client_secret", HealthLabsInfo.clientSecret)
                .queryParam("redirect_uri", HealthLabsInfo.redirectUri)
                .queryParam("access_token", userData.getAccessToken())
                .queryParam("sc", HealthLabsInfo.SC)
                .queryParam("sv", HealthLabsInfo.OpenApiBPSV)
                .queryParam("start_time", new GregorianCalendar(1970, 0, 1).getTimeInMillis());

        String uriResponse = HttpUtils.sendRequest(uriBuilder.build());
        return Response.ok(uriResponse).build();
    }

    @Inject
    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
