package research.utils;

import javax.inject.Singleton;

/**
 * @author Evgenia
 */

public class HealthLabsInfo {
    public static final String iHealthAuthorizationUrl = "http://sandboxapi.ihealthlabs.com/openapiv2/OAuthv2/userauthorization/";
    public static final String iHealthOpenApiUserUrl = "http://sandboxapi.ihealthlabs.com/openapiv2/";
    public static final String iHealthOpenApiApplicationUrl = "http://sandboxapi.ihealthlabs.com/openapiv2/application/bp.json/";

    public static final String clientId     = "d949653473474f228b456e888250adfe";
    public static final String clientSecret = "38cb172583c84cf1b7d5ebb7a3be990b";
    public static final String responseCodeType = "code";
    public static final String responseRefreshType = "refresh_token";
    public static final String grantType    = "authorization_code";
    public static final String APIName  = "OpenApiBP";

    public static final String redirectUri  = "https://evgeniagolubyeva.github.io/research";
    public static final String loginCallBackUri  = "http://localhost:8080/research/server/authorization/requestAccessToken";

    public static final String SC = "23669750f8cb4836a2247c1eb28c6e22";
    public static final String OpenApiBPSV = "ac5908c855bb466b8886c94b9cfe9ce9";
}
