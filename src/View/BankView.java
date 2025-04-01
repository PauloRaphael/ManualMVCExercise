package View;

import Model.Account;
import Model.Person;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.Scanner;

public class BankView {

    private final Scanner scan;

    public BankView() {
        scan = new Scanner(System.in);
    }

    public char initApp() {
        System.out.println("==================================");
        System.out.println("  Bem-vindo ao Bank Crowck!  ");
        System.out.println("==================================");
        System.out.println("Deseja acessar o menu de opções? (S/N)");

        char response = scan.next().toUpperCase().charAt(0);
        if (response == 'S') {
            return 'S';
        } else {
            System.out.println("Obrigado por visitar o Bank Crowck!");
            return 'E';
        }
    }

    public char chooseAction() {
        System.out.println("O que deseja fazer agora?");
        System.out.println("[N] Cadastrar-se");
        System.out.println("[X] Deletar cadastro");
        System.out.println("[C] Criar conta");
        System.out.println("[D] Deletar conta");
        System.out.println("[S] Sacar");
        System.out.println("[P] Depositar");
        System.out.println("[T] Transferir");
        System.out.println("[V] Visualizar saldo");
        System.out.println("[E] Sair");

        return scan.next().toUpperCase().charAt(0);
    }

    public String getUserName() {
        System.out.print("Digite o nome do dono da conta: ");
        return scan.nextLine();
    }

    public String getUserCpf() {
        System.out.print("Digite o CPF do dono da conta: ");
        return scan.nextLine();
    }

    public LocalDate getUserDateOfBirth() {
        System.out.print("Digite a data de nascimento (YYYY-MM-DD): ");
        return LocalDate.parse(scan.nextLine());
    }

    public int getAccountNumber() {
        System.out.print("Digite o numero da conta: ");
        return scan.nextInt();
    }

    public double getUserInitialBalance() {
        System.out.print("Digite o saldo inicial da conta: ");
        return scan.nextDouble();
    }

    public String getUserPassword() {
        System.out.print("Digite a senha da conta: ");
        return scan.nextLine();
    }

    public String getNewUserPassword() {
        System.out.print("Digite a nova da conta: ");
        return scan.nextLine();
    }

    public double userDeposits() {
        System.out.print("Digite o valor de deposito");
        return scan.nextDouble();
    }

    public double userWithdraws() {
        System.out.print("Digite o valor de saque");
        return scan.nextDouble();
    }

    public void userCreationSucess(Person owner) {
        System.out.println("Usuario criado com sucesso!");
        System.out.println("Nome:  " + owner.getName());
        System.out.println("Cpf:  " + owner.getCpf());
    }

    public void accountCreationSucess(int accountNumber) {
        System.out.println("Conta criada com sucesso!");
        System.out.println("Anote o numero da sua conta para uso futuro:  " + accountNumber);
    }

    public double userTransfer() {
        System.out.print("Digite o valor de transferencia");
        return scan.nextDouble();
    }

    public void showBalance(Account account) {
        System.out.print("O saldo da conta do usuario " + account.getOwner() + " é:  " + account.getBalance());
    }

    public void invalidPassword() {
        System.out.print("Senha invaliada!");
    }

    public void loginSucess() {
        System.out.println("Logado com sucesso!");
    }

    public void logoutSucess() { System.out.println("Deslogado com sucesso"); }

    public void genericError(String error) { System.out.println("There was an error: " + error); }
}
