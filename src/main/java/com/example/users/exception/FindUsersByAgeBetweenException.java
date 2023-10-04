package com.example.users.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FindUsersByAgeBetweenException extends RuntimeException {

    public FindUsersByAgeBetweenException(Date fromDate, Date toDate) {
        super("Date From: " + new SimpleDateFormat("dd-MM-yyyy").format(fromDate)
                + " must be less than date To: " + new SimpleDateFormat("dd-MM-yyyy").format(toDate));
    }

}
