package research.websocket;

import research.entity.UserData;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Evgenia
 */

@ServerEndpoint(value = "/server/changes")
public class ChangesEndpoint {
    private WebSocketSessionHolder holder;
    private UserData userData;

    @OnOpen
    public void openConnection(Session mySession) {
        //cannot connect session with userId at this point
        //http://stackoverflow.com/questions/21049387/using-a-cdi-sessionscoped-bean-from-a-websocket-serverendpoint
        holder.add(mySession);
    }

    @OnClose
    public void closeConnection(Session mySession) {
        holder.remove(mySession);
    }

    @Inject
    public void setHolder(WebSocketSessionHolder holder) {
        this.holder = holder;
    }

    @Inject
    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
