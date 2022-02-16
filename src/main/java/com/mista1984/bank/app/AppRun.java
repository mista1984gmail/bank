package com.mista1984.bank.app;

import com.mista1984.bank.db.DataBase;
import com.mista1984.bank.repository.BankRepositoryImpl;
import com.mista1984.bank.service.BankServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class AppRun {
    private static final Logger logger = LogManager.getLogger(AppRun.class);
    public static final BankRepositoryImpl BANK_REPOSITORY = new BankRepositoryImpl();
    public static final BankServiceImpl BANK_SERVICE = new BankServiceImpl(BANK_REPOSITORY);

    public void runApplication() throws Exception{
        DataBase.getInstance().load();
        int userInput = 0;
        Scanner scanner = new Scanner(System.in);

        do {
            // instructions
            logger.info("Enter 0 to exit the application");//exiting the application
            logger.info("Enter 1 to show all banks");//show all banks
            logger.info("Enter 2 to add bank");//add bank
            logger.info("Enter 3 to delete bank");//removing a bank
            logger.info("Enter 4 to show users in the bank");//show users in a bank
            logger.info("Enter 5 to add the user in bank");//add user in a bank
            logger.info("Enter 6 to add user account");//to add user account
            logger.info("Enter 7 to top up account");//top up account
            logger.info("Enter 8 to transfer money");//to transfer money
            System.out.println("_______________________________________________________________________");
            // reading input
            userInput = scanner.nextInt();

            // choosing option
            switch (userInput) {
                case 0:
                    logger.info("Goodbye!");
                    break;
                case 1:
                    BANK_SERVICE.showInfo();
                    break;
                case 2:
                    BANK_SERVICE.addBank();
                    break;
                case 3:
                    BANK_SERVICE.deleteBank();
                    break;
                case 4:
                    BANK_SERVICE.showUsersInBank();
                    break;
                case 5:
                    BANK_SERVICE.addUserInBank();
                    break;
                case 6:
                    BANK_SERVICE.addUserAccount();
                    break;
                case 7:
                    BANK_SERVICE.topUpAccount();
                    break;
                case 8:
                    BANK_SERVICE.transferMoney();
                    break;
                default:
                    logger.info("There is no such option, please choose another option.");
            }

        } while (userInput != 0);

        DataBase.getInstance().save();
    }
    }

