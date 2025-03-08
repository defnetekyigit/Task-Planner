import java.util.ArrayList;

public interface TaskPlannerModelInterface {

 void registerObserver(Observer o);
 void removeObserver(Observer o);
 void notifyObserver();
 ArrayList<String> getTaskList();
 ArrayList<Notification> getNotificationList();
 String getNotification();
 void setNotification(String notification);
 Message generateDecoratedMessage();
 void addTask(String taskName,String description,String category,String deadline);
 void removeTask(int selectedIndex);
 void editTask(int selectedIndex, String newTaskName);
 void checkDeadlines();
}
