package bankingapp.service.impl;


import bankingapp.entity.Account;

import bankingapp.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl  {


    @Autowired
    private AccountRepository accountRepository;



    public Account createAccount(Account account){
        return accountRepository.save(account);
    }
    public Account getAccountById(Long Id){
        return accountRepository.findById(Id).orElseThrow(()-> new RuntimeException("Account doesn't exist"));
    }
    public Account updateAccount(Long Id,Account account){
        Optional<Account> accList=accountRepository.findById(Id);
        if(accList.isPresent()){
            Account account1=accList.get();
            account1.setAccountHolderName(account.getAccountHolderName());
            account1.setBalance(account.getBalance());
           return accountRepository.save(account1);
        }
        else{
         throw new EntityNotFoundException("Entity with id"+Id+"not found") ;
        }
    }
    public Account deposit (Long Id,double amount){
        Account account=accountRepository.findById(Id).orElseThrow(()-> new RuntimeException("Account doesn't exist"));
        double total=account.getBalance()+amount;
        account.setBalance(total);
        return accountRepository.save(account);
    }

    public Account withDraw(Long Id,double amount){
        Account account=accountRepository.findById(Id).orElseThrow(()-> new RuntimeException("Account doesn't exist"));
        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient funds");
        }
        double total=account.getBalance()-amount;
        account.setBalance(total);
        return accountRepository.save(account);
    }
    public List<Account> findAllAccounts(){
        List<Account>accList= accountRepository.findAll();
        if(!accList.isEmpty()){
            return accList;
        }
        else{
            throw new RuntimeException("no accounts found ");
        }
    }
    public void deleteAccount(Long Id){
        Account account=accountRepository.findById(Id).orElseThrow(()-> new RuntimeException("Account doesn't exist"));
        accountRepository.deleteById(Id);

    }
}
