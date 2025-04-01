package Controller;

import Model.Account;
import Model.Person;
import View.BankView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {
    private static Controller controller;
    private final List<Account> accounts;
    private final List<Person> people;
    private final BankView view;
    private Account loggedInAccount;

    private Controller(BankView view) {
        this.view = view;
        this.accounts = new ArrayList<>();
        this.people = new ArrayList<>();
    }

    public static Controller getController(BankView view) {
        if (controller == null) {
            controller = new Controller(view);
        }
        return controller;
    }

    public void initApp() {
        char init = view.initApp();
        boolean running = true;

        if (init == 'S') {
            while (running) {
                char action = view.chooseAction();
                running = handleAction(action);
            }
        }
        System.out.println("Obrigado por usar o Bank Crowck! Até a próxima.");
    }


    public void createPerson() {
        String name = view.getUserName();
        String cpf = view.getUserCpf();
        LocalDate localDob = view.getUserDateOfBirth();

        Person person = Person.getPerson(name, cpf, Date.from(localDob.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        people.add(person);

        view.userCreationSucess(person);
    }

    public void createAccount() {
        String cpf = view.getUserCpf();
        String password = view.getUserPassword();
        double initialBalance = view.getUserInitialBalance();

        Person owner = getPersonByCpf(cpf);
        Account newAccount = Account.getAccount(owner, initialBalance, password);
        accounts.add(newAccount);

        view.accountCreationSucess(newAccount.getAccountNumber());
    }

    public void login() {
        int accountNumber = view.getAccountNumber();
        String password = view.getUserPassword();
        Account account = getAccountByNumber(accountNumber);

        if (authenticateUser(password, account)) {
            loggedInAccount = account;
            view.loginSucess();
        } else {
            view.invalidPassword();
        }
    }

    public void logout() {
        loggedInAccount = null;
        view.logoutSucess();
    }

    public void changePassword() {
        String password = view.getUserPassword();
        if (authenticateUser(password, loggedInAccount)) {
            String newPassword = view.getNewUserPassword();
            loggedInAccount.setAccountPassword(newPassword);
        } else {
            view.invalidPassword();
        }
    }

    public void deposit() {
        double amount = view.userDeposits();
        loggedInAccount.deposit(amount);
        view.showBalance(loggedInAccount);
    }

    public void withdraw() {
        double amount = view.userWithdraws();
        loggedInAccount.withdrawal(amount);
        view.showBalance(loggedInAccount);
    }

    public void transfer() {
        int accountNumber = view.getAccountNumber();
        double amount = view.userTransfer();
        Account toAccount = getAccountByNumber(accountNumber);

        loggedInAccount.transfer(amount, toAccount);
        view.showBalance(loggedInAccount);
        view.showBalance(toAccount);
    }

    private Account getAccountByNumber(int accountNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber() == accountNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Account not found."));
    }

    private Person getPersonByCpf(String cpf) {
        return people.stream()
                .filter(person -> person.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Person not found."));
    }

    private boolean authenticateUser(String password, Account account) {
        return account.getAccountPassword().equals(password);
    }

    public boolean handleAction(char action) {
        
        switch (action) {
            case 'N':
                createPerson();
                return true;
            case 'X':
                // Deletar cadastro
                return true;
            case 'C':
                createAccount();
                return true;
            case 'L':
                login();
                return true;
            case 'O':
                logout();
                return true;
            case 'D':
                // Deletar conta
                return true;
            case 'S':
                withdraw();
                return true;
            case 'P':
                deposit();
                return true;
            case 'T':
                transfer();
                return true;
            case 'V':
                view.showBalance(loggedInAccount);
                return true;
            case 'E':
                return false;
            default:
                view.genericError("Invalid action");
                return true;
        }
    }
}
