import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskPlannerModel implements TaskPlannerModelInterface
{
    ArrayList observers = new ArrayList();
    String task;
    String day;
    String date;
    String notification;
    String userBirthday;
    ArrayList<String> taskList = new ArrayList<>();
    ArrayList<Notification> notificationsList = new ArrayList<>();

    private Connection connection;
    public TaskPlannerModel(String userBirthday){
        this.userBirthday = userBirthday;
        date = String.valueOf(LocalDate.now());
        day = String.valueOf(LocalDate.now().getDayOfWeek());
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskplannerdb", "root", "123456");
            fetchTasksFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
    int i = observers.indexOf(o);
    if(i>=0){
        observers.remove(o);
    }
    }

    @Override
    public void notifyObserver() {
        for(int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.update();
        }
    }
    public void fetchTasksFromDatabase() {
        taskList.clear();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT taskName FROM tasks")) {
            while (rs.next()) {
                taskList.add(rs.getString("taskName"));
            }
            notifyObserver();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void addTask(String taskName, String description, String category, String deadline) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO tasks (taskName, shortDescription, category, deadline) VALUES (?, ?, ?, ?)");
            ps.setString(1, taskName);
            ps.setString(2, description);
            ps.setString(3, category);
            ps.setDate(4, Date.valueOf(deadline));
            ps.executeUpdate();
            fetchTasksFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void editTask(int index, String newTaskName) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE tasks SET taskName = ? WHERE taskName = ?");
            ps.setString(1, newTaskName);
            ps.setString(2, taskList.get(index));
            ps.executeUpdate();
            fetchTasksFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(int index) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM tasks WHERE taskName = ?");
            ps.setString(1, taskList.get(index));
            ps.executeUpdate();
            fetchTasksFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notifications) {
        this.notification = notifications;
    }

    public ArrayList<String> getTaskList() {
        return taskList;
    }

    public ArrayList<Notification> getNotificationList() {
        return notificationsList;
    }

    public Message generateDecoratedMessage() {
        Message message = new BasicMessage(day, date);
        message = new BirthdayMessage(message, userBirthday);
        message = new Notification(message, notification);
        return message;
    }
    public void checkDeadlines() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        notificationsList.clear();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT taskName, deadline FROM tasks");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String taskName = rs.getString("taskName");
                Date deadlineDate = rs.getDate("deadline");

                LocalDate taskDeadline = deadlineDate.toLocalDate();

                if (taskDeadline.equals(tomorrow)) {
                    String notificationMessage = "Task '" + taskName + "' is due tomorrow!";
                    Notification notification = new Notification(new BasicMessage(day, date), notificationMessage);
                    notificationsList.add(notification);

                    setNotification(notificationMessage);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
