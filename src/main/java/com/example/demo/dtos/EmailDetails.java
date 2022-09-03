package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("message_body")
    private String messageBody;

    @JsonProperty("attachment")
    private String attachment;
}
