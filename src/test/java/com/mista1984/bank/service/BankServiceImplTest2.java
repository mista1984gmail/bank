package com.mista1984.bank.service;

import com.mista1984.bank.db.DataBase;
import com.mista1984.bank.domain.bank.Bank;
import com.mista1984.bank.repository.BankRepository;
import com.mista1984.bank.util.BankFixture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.testng.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class BankServiceImplTest2 {
    private static final Logger logger = LogManager.getLogger(BankServiceImplTest2.class);
    private static BankService underTest;
    private static BankRepository repoMock;

    @BeforeAll
    public static void init() {
        repoMock = mock(BankRepository.class);
        underTest = new BankServiceImpl(repoMock);
    }
    @AfterEach
    public void resetMock() {
        reset(repoMock);
    }
    @BeforeEach
    public void setUp() {
        logger.info("Test for 'BankServiceImpl' are started.");
    }

    @AfterEach
    public void tearDown() {
        logger.info("Test for 'BankServiceImpl' are finished.");
        logger.info("*****************************************************************************");
    }

    @Test
    public void testSaveBank() throws Exception {
        //GIVEN
        Bank bank = BankFixture.createBank();
        when(repoMock.saveBank(any(Bank.class))).thenReturn(true);

        //WHEN
        underTest.saveBank(bank);

        //THEN
        verify(repoMock).saveBank(bank);
    }

    @Test
    public void  testGetBank() throws Exception {
        // GIVEN
        Bank bank = BankFixture.createBank();
        when(repoMock.getBank(eq(0))).thenReturn(Optional.of(bank));

        // WHEN
        Bank bankFromRepo = underTest.getBank(0);

        // THEN
        Assertions.assertEquals(bank, bankFromRepo);
    }
    @Test
    public void testGetBanks() throws Exception {
        // GIVEN
        Bank bank = BankFixture.createBank();
        when(repoMock.getBanks()).thenReturn(Arrays.asList(bank));

        // WHEN
        underTest.showInfo();
        Collection<Bank> banks = repoMock.getBanks();

        // THEN
        Assertions.assertEquals(Arrays.asList(bank), banks);

    }
    @Test
    public void testDeleteBank() throws Exception {
        // GIVEN
        Bank bank = BankFixture.createBank();
        DataBase.getInstance().executeSaveOperation(bank);

        // WHEN
        underTest.deleteBank(0);
        List<Bank> banks;
        banks = repoMock.getBanks();

        // THEN
        Assert.assertEquals(banks.size(),0);
    }

}
