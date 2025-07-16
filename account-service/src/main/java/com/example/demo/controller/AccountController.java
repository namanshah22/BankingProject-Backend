package com.example.demo.controller;
import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import com.example.demo.dto.TransactionResponse;
import com.example.demo.dto.CreditScoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/api/v1/accounts")
@Validated
public class AccountController {
    @Autowired
    private AccountService accountService;
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    @GetMapping("/{accountNo}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountNo) {
        return accountService.getAccountById(accountNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/aadhaar/{aadhaar}")
    public ResponseEntity<Account> getAccountByAadhaar(@PathVariable String aadhaar) {
        return accountService.getAccountByAadhaar(aadhaar)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/branch/{branch}")
    public ResponseEntity<List<Account>> getAccountsByBranch(@PathVariable String branch) {
        return ResponseEntity.ok(accountService.getAccountsByBranch(branch));
    }
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }
    @PutMapping("/{accountNo}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountNo,
                                               @Valid @RequestBody Account account) {
        Account updatedAccount = accountService.updateAccount(accountNo, account);
        return ResponseEntity.ok(updatedAccount);
    }
    @DeleteMapping("/{accountNo}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountNo) {
        accountService.deleteAccount(accountNo);
        return ResponseEntity.noContent().build();
    }
    // Transaction endpoints
    @PostMapping("/{fromAccount}/transfer/{toAccount}")
    public ResponseEntity<TransactionResponse> transferFunds(
            @PathVariable Long fromAccount,
            @PathVariable Long toAccount,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description) {
        TransactionResponse response = accountService.transferFunds(fromAccount, toAccount, amount, description);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{accountNo}/deposit")
    public ResponseEntity<TransactionResponse> deposit(
            @PathVariable Long accountNo,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description) {
        TransactionResponse response = accountService.deposit(accountNo, amount, description);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{accountNo}/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(
            @PathVariable Long accountNo,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description) {
        TransactionResponse response = accountService.withdraw(accountNo, amount, description);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{accountNo}/balance")
    public ResponseEntity<TransactionResponse> getBalance(@PathVariable Long accountNo) {
        TransactionResponse response = accountService.getBalance(accountNo);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{accountNo}/history")
    public ResponseEntity<TransactionResponse[]> getTransactionHistory(
            @PathVariable Long accountNo,
            @RequestParam(defaultValue = "10") int limit) {
        TransactionResponse[] history = accountService.getTransactionHistory(accountNo, limit);
        return ResponseEntity.ok(history);
    }
    @GetMapping("/{aadhaar}/credit-score")
    public ResponseEntity<CreditScoreResponse> getCreditScore(@PathVariable String aadhaar) {
        CreditScoreResponse response = accountService.getCreditScore(aadhaar);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{aadhaar}/loan-eligibility")
    public ResponseEntity<CreditScoreResponse> checkLoanEligibility(
            @PathVariable String aadhaar,
            @RequestParam BigDecimal loanAmount) {
        CreditScoreResponse response = accountService.checkLoanEligibility(aadhaar, loanAmount);
        return ResponseEntity.ok(response);
    }
}
