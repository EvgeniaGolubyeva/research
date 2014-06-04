package research.websocket;

import javax.inject.Singleton;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgenia
 */

@Singleton
public class WebSocketSessionHolder {
    private List<Session> webSocketSessions = new ArrayList<>();

    public void add(Session webSocketSession) {
        webSocketSessions.add(webSocketSession);
    }

    public void remove(Session webSocketSession) {
        webSocketSessions.remove(webSocketSession);
    }

    public List<Session> getSessions() {
        return webSocketSessions;
    }

}
