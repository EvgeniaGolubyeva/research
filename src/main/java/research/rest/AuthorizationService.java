package research.rest;

import research.entity.UserData;
import research.utils.HealthLabsInfo;
import research.utils.HttpUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.*;
import java.util.Date;

/**
 * @author Evgenia
 */

@Path("/authorization")
@RequestScoped
public class AuthorizationService {
    private UserData userData;

    @GET
    @Path("/isAuthenticated")
    public Boolean isAuthenticated() {
        return userData.getAccessToken() != null;
    }

    @POST
    @Path("/signOut")
    public void signOut() {
        userData.reset();
    }

    @GET
    @Path("/iHealthLabsAuthorizationUrl")
    public Response getIHealthLabsAuthorizationUrl() {
        UriBuilder uriBuilder = UriBuilder
                .fromPath(HealthLabsInfo.iHealthAuthorizationUrl)
                .queryParam("client_id", HealthLabsInfo.clientId)
                .queryParam("response_type", HealthLabsInfo.responseCodeType)
                .queryParam("redirect_uri", HealthLabsInfo.loginCallBackUri)
                .queryParam("APIName", HealthLabsInfo.APIName);

        return Response.ok(uriBuilder.build().toString()).build();
    }

    @GET
    @Path("/requestAccessToken")
    public Response requestAccessToken(@QueryParam("code") String code,
                                       @Context HttpServletRequest request,
                                       @Context HttpServletResponse response) {

        UriBuilder uriBuilder = UriBuilder
                .fromPath(HealthLabsInfo.iHealthAuthorizationUrl)
                .queryParam("client_id", HealthLabsInfo.clientId)
                .queryParam("client_secret", HealthLabsInfo.clientSecret)
                .queryParam("grant_type", HealthLabsInfo.grantType)
                .queryParam("redirect_uri", HealthLabsInfo.redirectUri)
                .queryParam("code", code);

        String uriResponse = HttpUtils.sendRequest(uriBuilder.build());
        if (uriResponse.contains("Error")) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        JsonReader reader = Json.createReader(new StringReader(uriResponse));
        JsonObject jsonObject = reader.readObject();

        userData.setAccessToken(jsonObject.getString("AccessToken"));
        userData.setRefreshToken(jsonObject.getString("RefreshToken"));
        userData.setUserId(jsonObject.getString("UserID"));
        userData.setExpires(new Date(jsonObject.getInt("Expires") + new Date().getTime()));

        try {
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/");
            return Response.status(Response.Status.ACCEPTED).build();

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public boolean refreshAccessToken() {
        UriBuilder uriBuilder = UriBuilder
                .fromPath(HealthLabsInfo.iHealthAuthorizationUrl)
                .queryParam("client_id", HealthLabsInfo.clientId)
                .queryParam("client_secret", HealthLabsInfo.clientSecret)
                .queryParam("response_type", HealthLabsInfo.responseRefreshType)
                .queryParam("redirect_uri", HealthLabsInfo.redirectUri)
                .queryParam("refresh_token", userData.getRefreshToken())
                .queryParam("UserID", userData.getUserId());

        String uriResponse = HttpUtils.sendRequest(uriBuilder.build());
        if (uriResponse.contains("Error")) {
            return false;
        }

        JsonReader reader = Json.createReader(new StringReader(uriResponse));
        JsonObject jsonObject = reader.readObject();

        userData.setAccessToken(jsonObject.getString("AccessToken"));
        userData.setRefreshToken(jsonObject.getString("RefreshToken"));
        userData.setUserId(jsonObject.getString("UserID"));
        userData.setExpires(new Date(jsonObject.getInt("Expires") + new Date().getTime()));

        return true;
    }

    @Inject
    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
