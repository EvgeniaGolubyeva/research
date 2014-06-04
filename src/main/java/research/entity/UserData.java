package research.entity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Singleton;
import javax.websocket.Session;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Evgenia
 */

@SessionScoped
public class UserData implements Serializable {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private Date expires;

    //for pubsub, websocket session
    private Session session;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void reset() {
        setUserId(null);
        setAccessToken(null);
        setRefreshToken(null);
        setExpires(null);
        setSession(null);
    }
}
