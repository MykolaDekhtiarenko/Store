import service.PaidSalaryService;
import view.Screen;

public class Application {
    public static void main(String[] args) {
//        System.out.println(PaidSalaryService.getInstance().findByEmployeeId(3));
        Screen mainScreen = new Screen();
        mainScreen.createAndShowGUI();
    }


}
