
public class Notification extends messageDecorator{
    private String notifications;

    public Notification(Message message, String notifications){
        super(message);
        this.notifications = notifications;
    }
    @Override
    public String getNotificationMessage() {
        if (notifications != null && !notifications.isEmpty()) {
            return String.join(", ", notifications);
        }
        return super.getNotificationMessage();
    }
}
