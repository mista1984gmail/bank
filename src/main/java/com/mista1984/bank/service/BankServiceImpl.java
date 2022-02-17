package com.mista1984.bank.service;

import com.mista1984.bank.domain.bank.Bank;
import com.mista1984.bank.domain.rates.ExchangeRates;
import com.mista1984.bank.domain.user.*;
import com.mista1984.bank.domain.user.Currency;
import com.mista1984.bank.exeption.IdIsNotAllowedOnDbException;
import com.mista1984.bank.exeption.NegativeValueOfAccountReplenishment;
import com.mista1984.bank.exeption.NotEnoughMoneyInTheAccount;
import com.mista1984.bank.repository.BankRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.function.Consumer;

public class BankServiceImpl implements BankService{
    private static final Logger logger = LogManager.getLogger(BankServiceImpl.class);
    public static final String SOURCES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    private BankRepository repository;
    public static final Consumer<Bank> LOG_ACTION = bank ->
            logger.info(bank.getNameBank() + ", id: " + bank.getId());

    public BankServiceImpl() {
    }

    public BankServiceImpl(BankRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addBank() throws Exception {
        Bank bank = new Bank();
        logger.info("Input information about Bank");
        Scanner scanner = new Scanner(System.in);

        logger.info("Name: ");
        String nameOfBank = scanner.nextLine();
        bank.setNameBank(nameOfBank);

        logger.info("Interest rate for individuals: ");
        double interestRateForIndividuals = scanner.nextDouble();
        bank.setInterestRateForIndividuals(interestRateForIndividuals);

        logger.info("Interest rate for organizations: ");
        double interestRateForOrganizations = scanner.nextDouble();
        bank.setInterestRateForOrganizations(interestRateForOrganizations);

        List<User>userList = new ArrayList<>();
        bank.setUserList(userList);

        saveBank(bank);
    }

    @Override
    public void saveBank(Bank bank) throws Exception {
        logger.info("Trying to save bank: {}", bank);
        boolean isBankSaved = repository.saveBank(bank);
        String success = isBankSaved ? "" : "not ";
        logger.info("Bank was {}saved: {}", success, bank);
    }

    @Override
    public Bank getBank(int id) throws Exception {
        logger.info("Trying to get bank with id = '{}'", id);
        Optional<Bank> bank = repository.getBank(id);
        if (bank.isPresent()) {
            logger.info("{} is gotten", bank.get());
            return bank.get();
        } else {
            logger.info("Creating new bouquet because no bouquet with id");
            return new Bank(id);
        }
    }

    @Override
    public void showInfo() throws Exception {
        logger.info("Showing info about bank");
        try{
            repository.getBanks().forEach(LOG_ACTION);}
        catch (IdIsNotAllowedOnDbException e){
            e.getMessage();
        }
    }

    @Override
    public void deleteBank() throws Exception {
        int idForDelete;
        logger.info("Input id Bank for delete");
        Scanner scanner = new Scanner(System.in);
        idForDelete = scanner.nextInt();
        deleteBank(idForDelete);
    }

    @Override
    public void deleteBank(int id) throws Exception {
        logger.info("Trying to delete bank with id= '{}'", id);
        logger.info("Bank with id= '{}' delete", id);
        repository.deleteBank(id);
    }

    @Override
    public void showUsersInBank() throws Exception {
        int id;
        logger.info("Input id Bank for show users");
        Scanner scanner = new Scanner(System.in);
        id = scanner.nextInt();
        showUserInfo(id);
    }

    @Override
    public void showUserInfo(int id) throws Exception {
        logger.info("Showing users in bank");
        logger.info(String.valueOf(repository.userList(id)));
    }

    @Override
    public void addUserInBank() throws Exception {
        logger.info("Input the Bank id to add user");
        Scanner scanner = new Scanner(System.in);
        int idBank=scanner.nextInt();
        Bank bank = getBank(idBank);
        List<User> userList= bank.getUserList();
        userList.add(addUser());
    }

    @Override
    public User addUser() throws Exception {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter type of user: 1 - INDIVIDUAL PERSON; 2 - ORGANIZATION;");
        int index = scanner.nextInt();

        logger.info("Enter name of user:");
        String nameUser = scanner.next();


        switch (index){
            case 1:
                BankServiceImpl bankService1 = new BankServiceImpl();
                List<BankAccount> bankAccountList1= new ArrayList<>();
                String idUser1 = bankService1.generateString(new Random(), SOURCES, 6);
                String numberOfBankAccount1 = bankService1.generateString(new Random(), SOURCES, 10);
                Map<LocalDateTime,String> accountTransactions1 = new TreeMap<>();
                LocalDateTime date1 = LocalDateTime.now();
                String transactions1 = "creating an account: " + numberOfBankAccount1 + ", balance: 0.0 BYN";
                accountTransactions1.put(date1,transactions1);
                BankAccount bankAccount1 = new BankAccount(numberOfBankAccount1,0.0, Currency.BYN,accountTransactions1);
                bankAccountList1.add(bankAccount1);
                IndividualPerson individualPerson = new IndividualPerson(idUser1,nameUser,bankAccountList1);
                logger.info("User added");
                return individualPerson;
            case 2:
                BankServiceImpl bankService2 = new BankServiceImpl();
                List<BankAccount> bankAccountList2= new ArrayList<>();
                String idUser2 = bankService2.generateString(new Random(), SOURCES, 6);
                String numberOfBankAccount2 = bankService2.generateString(new Random(), SOURCES, 10);
                Map<LocalDateTime,String> accountTransactions2 = new TreeMap<>();
                LocalDateTime date2 = LocalDateTime.now();
                String transactions2 = "creating an account: " + numberOfBankAccount2 + ", balance: 0.0 BYN";
                accountTransactions2.put(date2,transactions2);
                BankAccount bankAccount2 = new BankAccount(numberOfBankAccount2,0.0, Currency.BYN, accountTransactions2);
                bankAccountList2.add(bankAccount2);
                Orginization orginization = new Orginization(idUser2, nameUser,bankAccountList2);
                logger.info("User added");
                return orginization;

            default:
                logger.info("There is no such option, please choose another option.");
        }

        return null;
    }

    @Override
    public User findUser(int idBank, String idUser) throws Exception {
        logger.info("Try find bank id = '{}'",idBank);
        Optional<Bank> bank = repository.getBank(idBank);
        List<User>userList = bank.get().getUserList();
        logger.info("Find users in bank");
        User user;
        for (int i = 0; i < userList.size(); i++) {
            user=userList.get(i);
            if(user.getIdUser().equals(idUser)){
                logger.info("Find user in bank with id = '{}'", idUser);
                return user;
            }
        }
        return null;
    }

    @Override
    public BankAccount findBankAccount(User user, String numberOfAccount) throws Exception {
        logger.info("Try find bank account number= '{}'",numberOfAccount);
        List<BankAccount>accountList = user.getBankAccountList();
        BankAccount bankAccount;
        for (int i = 0; i < accountList.size(); i++) {
            bankAccount=accountList.get(i);
            if(bankAccount.getNumberOfBankAccount().equals(numberOfAccount)){
                logger.info("Find bank account number= '{}'",numberOfAccount);
                return bankAccount;
            }
        }
        return null;
    }

    @Override
    public synchronized void topUpAccount() throws Exception {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter id Bank:");
        int idBank = scanner.nextInt();

        logger.info("Enter id user:");
        String idUser = scanner.next();

        User user = findUser(idBank, idUser);

        logger.info("Enter number of account:");
        String numberOfAccount = scanner.next();

        BankAccount bankAccount = findBankAccount(user, numberOfAccount);
        logger.info("Enter the amount you want to top up your account:");

        try{
            double summ = scanner.nextDouble();
            if (summ>0){
                double accountBalance = bankAccount.getBalance();
                accountBalance+=summ;
                Map<LocalDateTime,String> accountTransactions = bankAccount.getAccountTransactions();
                LocalDateTime date = LocalDateTime.now();
                String transactions = "top up an account " + bankAccount.getNumberOfBankAccount() +
                        "on: " + summ + ", balance: " +
                        accountBalance + " " + bankAccount.getCurrency().name();
                accountTransactions.put(date,transactions);
                bankAccount.setBalance(accountBalance);
                logger.info("balance updated");
        }
            else throw new NegativeValueOfAccountReplenishment("Negative value of account replenishment");}
        catch (NegativeValueOfAccountReplenishment e){
            logger.info(e.getMessage());
        }
        catch (Exception e){
            logger.info(e.getMessage() + " Something went wrong!!!");
        }


    }

    @Override
    public synchronized void transferMoney() throws Exception {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter the id of the bank from which you want to make a transfer:");
        int idBankFrom = scanner.nextInt();

        logger.info("Enter the id of the user from which you want to make a transfer:");
        String idUserFrom = scanner.next();

        User userFrom = findUser(idBankFrom, idUserFrom);

        logger.info("Enter number of account:");
        String numberOfAccountFrom = scanner.next();

        BankAccount bankAccountFrom = findBankAccount(userFrom, numberOfAccountFrom);

        logger.info("Enter the id of the bank to which you want to transfer money:");
        int idBankOn = scanner.nextInt();

        logger.info("Enter the id of the user to which you want to transfer money:");
        String idUserOn = scanner.next();

        User userOn = findUser(idBankOn, idUserOn);

        logger.info("Enter number of account:");
        String numberOfAccountOn = scanner.next();

        BankAccount bankAccountOn = findBankAccount(userOn, numberOfAccountOn);

        logger.info("Enter the amount you want to top up your account:");

        try {
            double transferAmount = scanner.nextDouble();
            double accountBalanceFrom = bankAccountFrom.getBalance();
            double accountBalanceOn = bankAccountOn.getBalance();
            Bank bank = repository.getBank(idBankFrom).get();
            double commissionOfBank = 0;

            ExchangeRates exchangeRates = new ExchangeRates();
            Map<String, Double>map=exchangeRates.getExchangeRates();

            if(transferAmount<0){
                throw new NegativeValueOfAccountReplenishment("Negative value to transfer money");
            }

            if (userFrom instanceof IndividualPerson) {
                commissionOfBank = bank.getInterestRateForIndividuals();
            } else if (userFrom instanceof Orginization) {
                commissionOfBank = bank.getInterestRateForOrganizations();
            }
            double summCommissionOfBank = (transferAmount * commissionOfBank) / 100;


            if ((transferAmount + summCommissionOfBank) < accountBalanceFrom) {

                String currencyFrom = bankAccountFrom.getCurrency().name();
                String currencyOn = bankAccountOn.getCurrency().name();
                double exchangeRate = 1;


                    accountBalanceFrom -= summCommissionOfBank;
                    accountBalanceFrom -= transferAmount;

                    if(currencyFrom.equals("BYN") && currencyOn.equals("BYN")){
                        exchangeRate = map.get("BYN-BYN");
                    }
                    else if(currencyFrom.equals("BYN") && currencyOn.equals("RUB")){
                        exchangeRate = map.get("BYN-RUB");
                    }
                    else if(currencyFrom.equals("BYN") && currencyOn.equals("USD")){
                        exchangeRate = map.get("BYN-USD");
                    }
                    else if(currencyFrom.equals("RUB") && currencyOn.equals("BYN")){
                        exchangeRate = map.get("RUB-BYN");
                    }
                    else if(currencyFrom.equals("RUB") && currencyOn.equals("RUB")){
                        exchangeRate = map.get("RUB-RUB");
                    }
                    else if(currencyFrom.equals("RUB") && currencyOn.equals("USD")){
                        exchangeRate = map.get("RUB-USD");
                    }
                    else if(currencyFrom.equals("USD") && currencyOn.equals("BYN")){
                        exchangeRate = map.get("USD-BYN");
                    }
                    else if(currencyFrom.equals("USD") && currencyOn.equals("RUB")){
                        exchangeRate = map.get("USD-RUB");
                    }
                    else if(currencyFrom.equals("USD") && currencyOn.equals("USD")){
                        exchangeRate = map.get("USD-USD");
                    }
                    accountBalanceOn += (transferAmount*exchangeRate);

                    Map<LocalDateTime,String> accountTransactionsOn = bankAccountOn.getAccountTransactions();
                    LocalDateTime dateOn = LocalDateTime.now();
                    String transactionsOn = "transfer money from account: " + bankAccountFrom.getNumberOfBankAccount() +
                        " on: " + transferAmount + " " + bankAccountFrom.getCurrency().name() +
                        ", balance: " + accountBalanceOn + " "+bankAccountOn.getCurrency().name();
                    accountTransactionsOn.put(dateOn,transactionsOn);

                    Map<LocalDateTime,String> accountTransactionsFrom = bankAccountFrom.getAccountTransactions();
                    LocalDateTime dateFrom = LocalDateTime.now();
                    String transactionsFrom = "transfer money to account: " + bankAccountOn.getNumberOfBankAccount() +
                        " on: " + transferAmount + " " + bankAccountFrom.getCurrency().name() +
                        ", balance: " + accountBalanceFrom + " "+bankAccountFrom.getCurrency().name();
                    accountTransactionsFrom.put(dateFrom,transactionsFrom);

                    bankAccountFrom.setBalance(accountBalanceFrom);
                    bankAccountOn.setBalance(accountBalanceOn);

            }

            else {
                throw new NotEnoughMoneyInTheAccount("Not enough money in the account");
            }

        }
        catch (NotEnoughMoneyInTheAccount e){
            logger.info(e.getMessage());
        }
        catch (NegativeValueOfAccountReplenishment e){
            logger.info(e.getMessage());
        }
        catch (Exception e){
            logger.info(e.getMessage() + " Something went wrong!!!");
        }

    }

    @Override
    public void addUserAccount() throws Exception {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter id Bank:");
        int idBank = scanner.nextInt();

        logger.info("Enter id user:");
        String idUser = scanner.next();

        User user = findUser(idBank, idUser);

        logger.info("Enter currency account: BYN; RUB; USD;");
        String currencyOfAccount = scanner.next();

        switch (currencyOfAccount){
            case "BYN":
                BankServiceImpl bankService1 = new BankServiceImpl();
                List<BankAccount> bankAccountList1= user.getBankAccountList();
                String numberOfBankAccount1 = bankService1.generateString(new Random(), SOURCES, 10);
                Map<LocalDateTime,String> accountTransactions1 = new TreeMap<>();
                LocalDateTime date1 = LocalDateTime.now();
                String transactions1 = "creating an account: " + numberOfBankAccount1 + ", balance: 0.0 BYN";
                accountTransactions1.put(date1,transactions1);
                BankAccount bankAccount1 = new BankAccount(numberOfBankAccount1,0.0, Currency.BYN, accountTransactions1);
                bankAccountList1.add(bankAccount1);
                logger.info("Account added");
                break;
            case "RUB":
                BankServiceImpl bankService2 = new BankServiceImpl();
                List<BankAccount> bankAccountList2= user.getBankAccountList();
                String numberOfBankAccount2 = bankService2.generateString(new Random(), SOURCES, 10);
                Map<LocalDateTime,String> accountTransactions2 = new TreeMap<>();
                LocalDateTime date2 = LocalDateTime.now();
                String transactions2 = "creating an account: " + numberOfBankAccount2 + ", balance: 0.0 RUB";
                accountTransactions2.put(date2,transactions2);
                BankAccount bankAccount2 = new BankAccount(numberOfBankAccount2,0.0, Currency.RUB, accountTransactions2);
                bankAccountList2.add(bankAccount2);
                logger.info("Account added");
                break;
            case "USD":
                BankServiceImpl bankService3 = new BankServiceImpl();
                List<BankAccount> bankAccountList3= user.getBankAccountList();
                String numberOfBankAccount3 = bankService3.generateString(new Random(), SOURCES, 10);
                Map<LocalDateTime,String> accountTransactions3 = new TreeMap<>();
                LocalDateTime date3 = LocalDateTime.now();
                String transactions3 = "creating an account: " + numberOfBankAccount3 + ", balance: 0.0 USD";
                accountTransactions3.put(date3,transactions3);
                BankAccount bankAccount3 = new BankAccount(numberOfBankAccount3,0.0, Currency.USD, accountTransactions3);
                bankAccountList3.add(bankAccount3);
                logger.info("Account added");
                break;
            default:
                logger.info("There is no such option, please choose another option.");
        }
    }

    @Override
    public void showAccountTransactions() throws Exception {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter id Bank:");
        int idBank = scanner.nextInt();

        logger.info("Enter id user:");
        String idUser = scanner.next();

        User user = findUser(idBank, idUser);

        logger.info("Enter number of account:");
        String numberOfAccountOn = scanner.next();

        BankAccount bankAccount = findBankAccount(user, numberOfAccountOn);

        Map<LocalDateTime,String> accountTransactions = new TreeMap<>();

        accountTransactions = bankAccount.getAccountTransactions();

        for (Map.Entry entry : accountTransactions.entrySet()) {
            logger.info("Date: " + entry.getKey() + " Value: "
                    + entry.getValue());
        }


    }

    @Override
    public void showAccountTransactionsForPeriod() throws Exception {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter id Bank:");
        int idBank = scanner.nextInt();

        logger.info("Enter id user:");
        String idUser = scanner.next();

        User user = findUser(idBank, idUser);

        logger.info("Enter number of account:");
        String numberOfAccountOn = scanner.next();

        logger.info("Enter the date of transactions from which you want to make a selection (in format:dd.MM.yyyy):");
        String dateStart = scanner.next();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date docDate1= format.parse(dateStart);
        LocalDateTime dateStarts = docDate1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        logger.info("Enter the date of transactions to which you want to make a selection (in format:dd.MM.yyyy):");
        String dateEnd = scanner.next();
        Date docDate2= format.parse(dateEnd);
        LocalDateTime dateEnds = docDate2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();


        BankAccount bankAccount = findBankAccount(user, numberOfAccountOn);

        Map<LocalDateTime,String> accountTransactions = new TreeMap<>();

        accountTransactions = bankAccount.getAccountTransactions();

        for (Map.Entry entry : accountTransactions.entrySet()) {
            if(dateStarts.isBefore((ChronoLocalDateTime<?>) entry.getKey()) &&
                    dateEnds.isAfter((ChronoLocalDateTime<?>) entry.getKey())){
            logger.info("Date: " + entry.getKey() + " Value: "
                    + entry.getValue());}
        }
    }


    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

}
