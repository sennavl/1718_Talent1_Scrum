package com.project.talent1.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TalentNotFoundException extends RuntimeException {
    public TalentNotFoundException(long talentId) {
        super("could not find talent '" + talentId + "'.");
    }
}
