package xyz.morecraft.dev.scp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.morecraft.dev.scp.web.util.JsonDateDeserializer;
import xyz.morecraft.dev.scp.web.util.JsonDateSerializer;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyHistoryTable {

    private String table;
    private String currency;
    private String code;
    private List<CurrencyHistoryEntry> rates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrencyHistoryEntry {

        private String no;
        @JsonDeserialize(using = JsonDateDeserializer.class)
        @JsonSerialize(using = JsonDateSerializer.class)
        private LocalDate effectiveDate;
        private Double mid;
        private boolean isPredicted = false;

    }

}
