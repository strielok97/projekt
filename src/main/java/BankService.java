import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashSet;

public class BankService {
    private Bank bank = new Bank(new HashSet<>());

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void addClient (Client client) {
        bank.getClients().add(client);
        System.out.println(client.getFirstName() + " " + client.getLastName() + " nowym klientem naszego banku!");
    }

    public void removeClient (Client client) {
        bank.getClients().remove(client);
        System.out.println(client.getFirstName() + " " + client.getLastName() + " narazie, elo, naura");
    }


}
