public abstract class messageDecorator implements Message{
    Message decoratedMessage;
    public messageDecorator(Message message){
        this.decoratedMessage = message;
    }
    @Override
    public String getDay() {
        return decoratedMessage.getDay();
    }

    @Override
    public String getDate() {
        return decoratedMessage.getDate();
    }
    @Override
    public String getBirthdayMessage() {
        return decoratedMessage.getBirthdayMessage();
    }
    @Override
    public String getNotificationMessage() {
        return decoratedMessage.getNotificationMessage();
    }}
