package com.mista1984.bank.service;

import com.mista1984.bank.db.DataBase;
import com.mista1984.bank.domain.bank.Bank;
import com.mista1984.bank.domain.user.User;
import com.mista1984.bank.repository.BankRepository;
import com.mista1984.bank.util.BankFixture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceImplTest {
    private static final Logger logger = LogManager.getLogger(BankServiceImplTest.class);
    @InjectMocks
    private static BankServiceImpl service;

    @Mock
    private static BankRepository repository;

    @AfterEach
    public void clear() {
        DataBase.getInstance().clearDb();
    }


    @Test
    public void testSaveBank() throws Exception{
        // GIVEN
        Bank bank = BankFixture.createBank();
        when(repository.saveBank(any(Bank.class))).thenReturn(true);

        // WHEN
        service.saveBank(bank);

        // THEN
        verify(repository).saveBank(bank);
    }
    @Test
    public void testGetBank() throws Exception {
        // GIVEN
        Bank bank = BankFixture.createBank();
        when(repository.getBank(0)).thenReturn(Optional.of(bank));

        // WHEN
        Bank bankFromRepo = service.getBank(0);

        // THEN
        assertThat(bankFromRepo).isEqualTo(bank);
        verify(repository).getBank(0);

    }
    @Test
    void testShowInfo() throws Exception {
        // GIVEN
        Bank bank = BankFixture.createBank();
        List<Bank> list=new ArrayList<>();
        list.add(bank);
        when(repository.getBanks()).thenReturn(list);
        // WHEN
        List <Bank> listFromRepo = repository.getBanks();
        // THEN
        Assert.assertEquals(list,listFromRepo);
    }
    @Test
    void testDeleteBank() throws Exception {
        // GIVEN
        Bank bank = BankFixture.createBank();
        when(repository.saveBank(any(Bank.class))).thenReturn(true);
        // WHEN
        service.saveBank(bank);
        service.deleteBank(0);
        List <Bank> listFromRepo = repository.getBanks();
        // THEN
        Assert.assertEquals(listFromRepo.size(),0);
    }

    @Test
    void testAddUserInBank() throws Exception {
        // GIVEN
        Bank bank = BankFixture.createBank();
        List<User>userList=new ArrayList<>();
        bank.setUserList(userList);
        when(repository.getBank(0)).thenReturn(Optional.of(bank));
        // WHEN
        Bank bankFromRepo = service.getBank(0);
        List<User>usersFromRepo=bankFromRepo.getUserList();
        // THEN
        Assert.assertEquals(usersFromRepo,userList);
    }

}
