package xyz.morecraft.dev.scp.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.morecraft.dev.scp.dto.CurrencyHistoryTable;

import java.time.LocalDate;

@Service
public class CurrencyService {

    public ResponseEntity<CurrencyHistoryTable> getCurrencyHistory(String table, String code, LocalDate startDate, LocalDate endDate) {
        RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<CurrencyHistoryTable> entity = restTemplate.getForEntity(
                "http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + code + "/" + startDate + "/" + endDate + "/?format=json",
                CurrencyHistoryTable.class
        );
        return new ResponseEntity<>(entity.getBody(), HttpStatus.OK);
    }

}
