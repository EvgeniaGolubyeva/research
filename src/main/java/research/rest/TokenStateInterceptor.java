package research.rest;

import research.entity.UserData;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.util.Date;

/**
 * @author Evgenia
 */

//I decided to use interceptor to check if user is logged in and token is still valid.
//Because now we think only about BP and I have only 2 requests to iHealth,
//but we can decide to buy more things and will be handling more than 2 requests,
//repeating this logic in every method can be not very good solution in the long run.

public class TokenStateInterceptor {
    private UserData userData;
    private AuthorizationService authorizationService;

    @AroundInvoke
    public Object checkTokenState(InvocationContext context) throws Exception {
        try {
            if (userData.getAccessToken() == null) return Response.ok().build();
        //userData throws NullPointer when server is just started
        } catch (NullPointerException e) {
            return Response.ok().build();
        }

        //it's still not clear what expires means and when token really expires.
        //Is it time during token is valid or it's time until token is valid,
        //in sandbox expires always = 172800, about 2 min or 1969...
        //if I try to get data from iHealth after 2 min - data still comes without errors.
        //I guess will be clear only in real life application.
        if (userData.getExpires().before(new Date())) {
            boolean refreshed = authorizationService.refreshAccessToken();
            if (!refreshed) return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return context.proceed();
    }

    @Inject
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    @Inject
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
}
