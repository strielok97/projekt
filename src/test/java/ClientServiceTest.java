import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;


class ClientServiceTest {
    ClientService clientService;
    Client client;

    @BeforeEach
    public void setUp() {
        clientService = new ClientService();
        client = Client.createClient(1, "ola", "nowak");
        client.getAccountList().add(0, new Account(1, new BigDecimal(200), Currency.PLN));
    }

    @Test
    void createAccount() {
        //WHEN
        Account account = clientService.createAccount("Savings", Currency.PLN);
        //THEN
        Assertions.assertThat(account.getBalance()).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    void addAccountToClient() {
        //GIVEN
        int currentSize = client.getAccountList().size();
        Account account = new Account(10, BigDecimal.valueOf(200), Currency.EUR);
        //WHEN
        clientService.addAccountToClient(client, account);
        //THEN
        int newCurrentSize = client.getAccountList().size();
        Assertions.assertThat(newCurrentSize).as("Test failure").isEqualTo(currentSize + 1);

    }

    @Test
    void deleteAccount() {
        //GIVEN
        int currentSize = client.getAccountList().size();
        //WHEN
        clientService.deleteAccount(client, 1);
        //THEN
        int newCurrentSize = client.getAccountList().size();
        Assertions.assertThat(newCurrentSize).as("Test failure").isEqualTo(currentSize - 1);

    }

    @Test
    void deposit() {
        //GIVEN
        BigDecimal amount = new BigDecimal(100);

        //WHEN
        clientService.deposit(client, amount, 1);
        //THEN
        BigDecimal newBalance = client.getAccountList().get(0).getBalance();
        Assertions.assertThat(newBalance).as("Test failure").isEqualTo(BigDecimal.valueOf(300));

    }


    @Test
    void cashOut() {
        //GIVEN
        BigDecimal amount = new BigDecimal(100);
        //WHEN
        clientService.cashOut(client, amount, 1);
        //THEN
        BigDecimal afterCashOut = client.getAccountList().get(0).getBalance();
        Assertions.assertThat(afterCashOut).as("Test failure").isEqualTo(new BigDecimal(100));
    }

    @Test
    void shouldNotCashOutWhenAmountIsBiggerThanBalance() {
        //GIVEN
        BigDecimal amount = new BigDecimal(1000);
        //WHEN
        clientService.cashOut(client, amount, 1);
        //THEN
        BigDecimal afterCashOut = client.getAccountList().get(0).getBalance();
        Assertions.assertThat(afterCashOut).as("Test failure").isEqualTo(new BigDecimal(200));
    }

    // dzien dobry x33
    @Test
    void shouldNotCashOutWhenAccountIdIsInvalid() {
        //GIVEN
        BigDecimal amount = new BigDecimal(100);
        //WHEN
        clientService.cashOut(client, amount, 3);
        //THEN
        BigDecimal afterCashOut = client.getAccountList().get(0).getBalance();
        Assertions.assertThat(afterCashOut).as("Test failure").isEqualTo(new BigDecimal(200));
    }

    @Test
    void shouldNotDepositWhenAccountIdIsInvalid() {
        //GIVEN
        BigDecimal amount = new BigDecimal(100);
        //WHEN
        clientService.deposit(client, amount, 3);
        //THEN
        BigDecimal afterCashOut = client.getAccountList().get(0).getBalance();
        Assertions.assertThat(afterCashOut).as("Test failure").isEqualTo(new BigDecimal(200));
    }

}