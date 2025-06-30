package de.hsrm.mi.web.projekt.messaging;

public class FrontendNachrichtEvent {

    public enum EventType {
        TOUR
    }

    public enum OperationType {
        CREATE, UPDATE, DELETE
    }

    private EventType eventType;
    private long id;
    private OperationType operationType;

    public FrontendNachrichtEvent(EventType eventType, long id, OperationType operationType) {
        this.eventType = eventType;
        this.id = id;
        this.operationType = operationType;
    }


    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
