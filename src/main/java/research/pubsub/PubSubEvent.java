package research.pubsub;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Evgenia
 */



public class PubSubEvent {
    @XmlElement(name="CollectionType")
    private String collectionType;

    @XmlElement(name="MDate")
    private String mDate;

    @XmlElement(name="UserID")
    private String userId;

    @XmlElement(name="SubscriptionId")
    private String subscriptionId;

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
