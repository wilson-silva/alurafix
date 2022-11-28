package br.com.alura.videoflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflitException extends RuntimeException{
    public ConflitException(String message) {
        super(message);
    }
}
