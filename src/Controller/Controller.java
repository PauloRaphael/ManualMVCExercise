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
                char action = ' ';
                if(loggedInAccount == null) {
                    action = view.chooseMenuAction();
                } else {
                    action = view.chooseLoggedAction();
                }
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

        view.userCreationSuccess(person);
    }

    public void createAccount() {
        String cpf = view.getUserCpf();
        String password = view.getUserPassword();
        double initialBalance = view.getUserInitialBalance();

        Person owner = getPersonByCpf(cpf);
        Account newAccount = Account.getAccount(owner, initialBalance, password);
        accounts.add(newAccount);

        view.accountCreationSuccess(newAccount.getAccountNumber());
    }

    public void login() {
        int accountNumber = view.getAccountNumber();
        view.cleanInput();
        String password = view.getUserPassword();
        Account account = getAccountByNumber(accountNumber);

        if (authenticateUser(password, account)) {
            loggedInAccount = account;
            view.loginSuccess();
        } else {
            view.invalidPassword();
        }
    }

    public void logout() {
        loggedInAccount = null;
        view.logoutSuccess();
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

        return switch (action) {
            case 'N' -> {
                createPerson();
                yield true;
            }
            case 'X' ->
                // Deletar cadastro
                    true;
            case 'C' -> {
                createAccount();
                yield true;
            }
            case 'L' -> {
                login();
                yield true;
            }
            case 'O' -> {
                logout();
                yield true;
            }
            case 'D' ->
                // Deletar conta
                    true;
            case 'S' -> {
                withdraw();
                yield true;
            }
            case 'P' -> {
                deposit();
                yield true;
            }
            case 'T' -> {
                transfer();
                yield true;
            }
            case 'V' -> {
                view.showBalance(loggedInAccount);
                yield true;
            }
            case 'E' -> false;
            default -> {
                view.genericError("Invalid action");
                yield true;
            }
        };
    }
}
