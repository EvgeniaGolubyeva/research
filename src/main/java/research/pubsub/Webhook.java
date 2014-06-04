package research.pubsub;

import research.entity.UserData;
import research.websocket.WebSocketSessionHolder;

import javax.inject.Inject;
import javax.websocket.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Evgenia
 */

@Path("/webhook")
public class Webhook {
    private WebSocketSessionHolder holder;
    private UserData userData;

    @POST
    @Consumes("application/json")
    public Response callback(PubSubEvent[] events) {
        for (PubSubEvent event : events) {
            if ("bp".equals(event.getCollectionType())) {

                //dark night:
                //userData is not available = this is an unrelated to the session callback
                //notify all open clients?

                try {
                    for (Session session : holder.getSessions()) {
                        if (session.isOpen()) {
                            session.getBasicRemote().sendText("update");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

        return Response.ok().build();
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
