import com.micka.borscha.UI.LoginUI;
import service.PaidSalaryService;
import view.Screen;

public class Application {
    public static void main(String[] args) {
//        System.out.println(PaidSalaryService.getInstance().findByEmployeeId(3));


        try {
//            new LoginUI();
            Screen mainScreen = new Screen();
            mainScreen.createAndShowGUI();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
