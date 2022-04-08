package br.com.boasaude.gisa.user.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class DateBucket {
    private Instant minDate;
    private Instant maxDate;

}
