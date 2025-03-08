
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
public class TaskPlannerView extends JFrame {
    TaskPlannerModelInterface model;
    TaskPlannerControllerInterface controller;
    JFrame viewFrame;
    JList taskList,notificationList;
    JPanel dayPanel, datePanel, birthdayMessagePanel, notificationPanel,viewPanel, taskPanel, buttonPanel;
    JButton addButton, editButton, removeButton;
    JLabel dayLabel, dateLabel, notificationLabel, birthdayMessageLabel, taskLabel;
    JTextField dayOutputField, dateOutputField,birthdayMessageField;

    public TaskPlannerView(TaskPlannerModelInterface model,TaskPlannerControllerInterface controller){
        this.model = model;
        this.controller = controller;
    }
    public void createView(){
    viewPanel = new JPanel(new GridLayout(4,4));
    viewFrame = new JFrame("Task Planner");
    viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    viewFrame.setSize(800, 600);
    JPanel dayDateContainer = new JPanel(new GridLayout(1, 2));
    JPanel notifTaskContainer = new JPanel(new GridLayout(1,2));


    dayLabel = new JLabel("Day",SwingConstants.CENTER);
    dayOutputField = new JTextField();
    dayOutputField.setEditable(false);
    dayPanel = new JPanel(new GridLayout(2,1));

    dateLabel = new JLabel("Date", SwingConstants.CENTER);
    dateOutputField = new JTextField();
    dateOutputField.setEditable(false);
    datePanel  = new JPanel(new GridLayout(2,1));

    birthdayMessagePanel = new JPanel(new GridLayout(2,4));
    birthdayMessageLabel = new JLabel("Birthday Celebration Message", SwingConstants.CENTER);
    birthdayMessageField = new JTextField("");
    birthdayMessageField.setEditable(false);
    JLabel gap = new JLabel("");


    notificationPanel= new JPanel(new BorderLayout());
    notificationLabel = new JLabel( "Notifications", SwingConstants.CENTER);
    notificationList = new JList(model.getNotificationList().toArray());
    JScrollPane notificationScrollPane = new JScrollPane(notificationList);
    notificationPanel.setPreferredSize(new Dimension(200, 100));


    taskPanel = new JPanel(new BorderLayout());
    taskLabel = new JLabel( "Tasks", SwingConstants.CENTER);
    taskList = new JList(model.getTaskList().toArray());
    JScrollPane taskScrollPane = new JScrollPane(taskList);
    taskScrollPane.setPreferredSize(new Dimension(200, 100));

    buttonPanel = new JPanel(new GridLayout(1,3));
    addButton = new JButton("Add");
    editButton = new JButton("Edit");
    removeButton = new JButton("Remove");



    dayPanel.add(dayLabel);
    dayPanel.add(dayOutputField);

    datePanel.add(dateLabel);
    datePanel.add(dateOutputField);

    birthdayMessagePanel.add(birthdayMessageLabel);
    birthdayMessagePanel.add(birthdayMessageField);

    dayDateContainer.add(dayPanel);
    dayDateContainer.add(datePanel);



    viewPanel.add(dayDateContainer);
    viewPanel.add(birthdayMessagePanel);

    notificationPanel.add(notificationLabel, BorderLayout.NORTH);
    notificationPanel.add(notificationScrollPane, BorderLayout.CENTER);

    buttonPanel.add(addButton);
    buttonPanel.add(editButton);
    buttonPanel.add(removeButton);

    taskPanel.add(taskLabel, BorderLayout.NORTH);
    taskPanel.add(buttonPanel, BorderLayout.CENTER);
    taskPanel.add(taskScrollPane, BorderLayout.SOUTH);



    notifTaskContainer.add(notificationPanel);
    notifTaskContainer.add(taskPanel);

    viewPanel.add(notifTaskContainer);
    viewFrame.add(viewPanel);
    viewFrame.setVisible(true);

    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.addTask();
        }
    });

    editButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.editTask();
        }
    });

    removeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.removeTask();
        }
    });
    }
    public void update(){
        Message decoratedMessage = model.generateDecoratedMessage();

        dayOutputField.setText(decoratedMessage.getDay());
        dateOutputField.setText(decoratedMessage.getDate());
        birthdayMessageField.setText(decoratedMessage.getBirthdayMessage());

        ArrayList<Notification> notifications = model.getNotificationList();
        ArrayList<String> notificationMessages = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationMessages.add(notification.getNotificationMessage());
        }
        notificationList.setListData(notificationMessages.toArray(new String[0]));

        ArrayList<String> tasks = model.getTaskList();
        taskList.setListData(tasks.toArray(new String[0]));
    }
    public int getSelectedTaskIndex() {
        return taskList.getSelectedIndex();
    }



}
