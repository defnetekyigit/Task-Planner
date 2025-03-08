import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskPlannerController implements TaskPlannerControllerInterface{
    TaskPlannerModelInterface model;
    TaskPlannerView view;
    public TaskPlannerController(TaskPlannerModelInterface model){
        this.model = model;
        view = new TaskPlannerView(model,this );
        view.createView();
    }

    @Override
    public void addTask() {
        String taskName = JOptionPane.showInputDialog("Enter Task Name:");
        String description = JOptionPane.showInputDialog("Enter Short Description:");
        String category = JOptionPane.showInputDialog("Enter Category:");
        String deadline = JOptionPane.showInputDialog("Enter Deadline (YYYY-MM-DD):");

        model.addTask(taskName, description, category, deadline);
        updateView();
    }

    @Override
    public void removeTask() {
        int selectedIndex = view.getSelectedTaskIndex();
        if (selectedIndex != -1) {
            model.removeTask(selectedIndex);
            updateView();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a task to remove.");
        }
    }

    @Override
    public void editTask() {
        int selectedIndex = view.getSelectedTaskIndex();
        if (selectedIndex != -1) {
            String newTaskName = JOptionPane.showInputDialog("Enter New Task Name:");
            model.editTask(selectedIndex, newTaskName);
            updateView();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a task to edit.");
        }
    }
    public void updateView(){
        model.checkDeadlines();
        view.update();
    }

    public void Time() {
        Timer timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.checkDeadlines();
                view.update();
            }
        });
        timer.start();
    }
}
