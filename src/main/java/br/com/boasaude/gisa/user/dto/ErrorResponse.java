package br.com.boasaude.gisa.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<String> details;


}