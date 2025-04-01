package Main;

import Controller.Controller;
import View.BankView;

public class Main {
    public static void main(String[] args) {
        BankView bankView = new BankView();
        Controller controller = Controller.getController(bankView);

        controller.initApp();
    }
}