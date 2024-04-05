package bankingapp.controller;


import bankingapp.entity.Account;
import bankingapp.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountServiceImpl;


    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody Account account){
        return new ResponseEntity<>(accountServiceImpl.createAccount(account), HttpStatus.CREATED);
    }
    @GetMapping("/{Id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long Id){
        return ResponseEntity.ok(accountServiceImpl.getAccountById(Id));
    }
    @PutMapping("/{Id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long Id,@RequestBody Account account){
        try{
         Account account1=accountServiceImpl.updateAccount(Id,account);
         return  new ResponseEntity<>(account1,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("{Id}/deposit")
    public  ResponseEntity<Account> deposit(@PathVariable Long Id,@RequestBody Map<String,Double>request){
        Double amount=request.get("amount");
        Account account=accountServiceImpl.deposit(Id,amount);
        return ResponseEntity.ok(account);
    }

    @PutMapping("{Id}/withdraw")
    public ResponseEntity<Account>withdraw(@PathVariable Long Id,@RequestBody Map<String,Double>request){
        Double amount=request.get("amount");
        Account account=accountServiceImpl.withDraw(Id,amount);
        return ResponseEntity.ok(account);
    }
    @GetMapping("allAccounts")
    public ResponseEntity<List<Account>> findAllAccounts(){
        List<Account>accList=accountServiceImpl.findAllAccounts();
        return ResponseEntity.ok(accList);
    }
   @DeleteMapping("{Id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long Id){
        accountServiceImpl.deleteAccount(Id);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
