package org.example.pastebinrestapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UrlLongDto {

    private String longUrl;

    private Date expiresDate;
}
