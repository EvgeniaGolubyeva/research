package research.websocket;

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

    @OnOpen
    public void openConnection(Session mySession) {
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
}
