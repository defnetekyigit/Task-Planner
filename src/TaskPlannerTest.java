public class TaskPlannerTest {
    public static void main(String[] args) {
        TaskPlannerModelInterface model = new TaskPlannerModel("1/14");
        TaskPlannerControllerInterface controller = new TaskPlannerController(model);
        controller.Time();
        controller.updateView();

    }
}