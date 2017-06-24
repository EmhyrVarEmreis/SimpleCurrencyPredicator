package xyz.morecraft.dev.scp.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xyz.morecraft.dev.scp.dto.CurrencyHistoryTable;

@Slf4j
@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @GetMapping(value = "history/{table}/{code}/{startDate}/{endDate}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrencyHistoryTable> getCurrencyHistory(
            @PathVariable("table") String table,
            @PathVariable("code") String code,
            @PathVariable("startDate") String startDate,
            @PathVariable("endDate") String endDate
    ) {
        RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<CurrencyHistoryTable> entity = restTemplate.getForEntity(
                "http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + code + "/" + startDate + "/" + endDate + "/?format=json",
                CurrencyHistoryTable.class
        );
        return new ResponseEntity<>(entity.getBody(), HttpStatus.OK);
    }

}
