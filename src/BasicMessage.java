public class BasicMessage implements Message{
    private String day;
    private String date;
    public BasicMessage(String day, String date){
        this.day = day;
        this.date = date;
    }
    public String getDay(){
        return day;
    }
    public String getDate() {
        return date;
    }
    public String getBirthdayMessage() {
        return "";
    }

    public String getNotificationMessage() {
        return "";
    }
}
