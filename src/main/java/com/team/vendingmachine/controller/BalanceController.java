package com.team.vendingmachine.controller;

import com.team.vendingmachine.enumeration.Coin;
import com.team.vendingmachine.enumeration.CoinFactory;
import com.team.vendingmachine.service.IVendingMachineClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/v1/balance")
public class BalanceController {
    private final IVendingMachineClient vendingMachineClient;

    public BalanceController(IVendingMachineClient vendingMachineClient) {
        this.vendingMachineClient = vendingMachineClient;
    }

    @GetMapping("")
    public double getBalance() {
        return this.vendingMachineClient.getBalance();
    }

    @PostMapping("/insertCoin/{coin}")
    public ResponseEntity<String> insertCoin(@PathVariable("coin") int coinValue) {
        Coin coin = CoinFactory.buildCoin(coinValue);
        if (coin == null) {
            return new ResponseEntity<>("Coin not accepted.", HttpStatus.BAD_REQUEST);
        }
        this.vendingMachineClient.insertCoin(coin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/returnMoney")
    public Collection<Coin> returnMoney() {
        return this.vendingMachineClient.returnMoney();
    }
}
