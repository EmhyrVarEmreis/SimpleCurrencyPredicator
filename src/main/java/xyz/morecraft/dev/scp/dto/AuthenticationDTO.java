package xyz.morecraft.dev.scp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.morecraft.dev.scp.web.util.JsonDateDeserializer;
import xyz.morecraft.dev.scp.web.util.JsonDateSerializer;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuthenticationDTO {

    private String token;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate passwordExpireDate;

}
