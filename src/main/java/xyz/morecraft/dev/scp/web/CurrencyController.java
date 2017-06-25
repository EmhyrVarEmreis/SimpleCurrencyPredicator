package xyz.morecraft.dev.scp.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xyz.morecraft.dev.scp.dto.CurrencyHistoryTable;
import xyz.morecraft.dev.scp.service.CurrencyService;
import xyz.morecraft.dev.scp.web.util.JsonDateDeserializer;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping(value = "history/{table}/{code}/{startDate}/{endDate}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrencyHistoryTable> getCurrencyHistory(
            @PathVariable("table") String table,
            @PathVariable("code") String code,
            @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        return currencyService.getCurrencyHistory(
                table,
                code,
                startDate,
                endDate
        );
    }

}
