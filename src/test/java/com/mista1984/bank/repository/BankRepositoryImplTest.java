package com.mista1984.bank.repository;

import com.mista1984.bank.db.DataBase;
import com.mista1984.bank.domain.bank.Bank;
import com.mista1984.bank.domain.user.BankAccount;
import com.mista1984.bank.domain.user.IndividualPerson;
import com.mista1984.bank.domain.user.User;
import com.mista1984.bank.service.BankServiceImplTest;
import com.mista1984.bank.util.BankFixture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BankRepositoryImplTest {
    private static final Logger logger = LogManager.getLogger(BankRepositoryImplTest.class);

    private static BankRepository repository=new BankRepositoryImpl();

   /* @AfterEach
    public void resetMock() {
      reset(repository);
   }*/
    @AfterEach
    public void clear() {
        DataBase.getInstance().clearDb();
    }



    @Test
    public void testSaveBank() throws Exception {
        // GIVEN
        Bank bank = BankFixture.createBank();

        // WHEN
        boolean isBankSaved = repository.saveBank(bank);

        // THEN
        assertThat(isBankSaved).isTrue();
        Bank bankFromDB = DataBase.getInstance().executeGetOperation(0)
                .orElseThrow(RuntimeException::new);
        Bank bankForAssert = BankFixture.createBank();
        assertThat(bankForAssert).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(bankFromDB);
    }
    @Test
    public void  testGetBank() throws Exception {
        // GIVEN
        Bank bank = BankFixture.createBank();
        DataBase.getInstance().executeSaveOperation(bank);

        // WHEN
        Optional<Bank> bankFromRepo = repository.getBank(0);

        // THEN
        assertThat(bankFromRepo).contains(bank);

    }

    @Test
    public void testGetBanks() throws Exception {
        // GIVEN
        int expectedSize = 4;
        for (int i = 0; i < expectedSize; i++) {
            Bank bank = BankFixture.createBank();
            DataBase.getInstance().executeSaveOperation(bank);
        }

        // WHEN
        List<Bank> bouquets;
        bouquets = repository.getBanks();

        // THEN
        assertThat(bouquets).hasSize(expectedSize).doesNotContainNull();

    }
    @Test
    public void testDeleteBank() throws Exception {
        // GIVEN

        Bank bank = BankFixture.createBank();
        DataBase.getInstance().executeSaveOperation(bank);
        // WHEN
        DataBase.getInstance().executeDeleteOperation(0);
        List<Bank> banks;
        banks = repository.getBanks();

        // THEN
        Assert.assertEquals(0,banks.size());

    }
    @Test
    public void testUserslist() throws Exception {
        // GIVEN

        Bank bank = BankFixture.createBank();
        List<User> usersList=new ArrayList<>();
        List<BankAccount>bankAccounts=new ArrayList<>();
        User user = new IndividualPerson("AAAAAA", "Ivan",bankAccounts);
        usersList.add(user);
        bank.setUserList(usersList);
        DataBase.getInstance().executeSaveOperation(bank);
        // WHEN
        List<User> listUsersFromRepo=repository.userList(bank.getId());

        // THEN
        Assert.assertEquals(listUsersFromRepo,usersList);

    }

}
