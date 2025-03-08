import java.time.LocalDate;

public class BirthdayMessage extends messageDecorator {
    private String birthdate;

    public BirthdayMessage(Message component, String birthdate) {
        super(component);
        this.birthdate = birthdate;
    }

    @Override
    public String getBirthdayMessage() {
        LocalDate today = LocalDate.now();
        String todayFormatted = today.getMonthValue() + "/" +today.getDayOfMonth() ;
        if (todayFormatted.equals(birthdate)) {
            return "Happy Birthday!";
        }
        return super.getBirthdayMessage();
    }
}
