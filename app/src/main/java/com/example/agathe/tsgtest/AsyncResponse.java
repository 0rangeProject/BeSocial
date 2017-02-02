package com.example.agathe.tsgtest;

import com.example.agathe.tsgtest.dto.Contact;

import java.util.List;

/**
 * Created by agathe on 02/02/17.
 */

public interface AsyncResponse {
    void processFinish(List<Contact> contacts);
}
