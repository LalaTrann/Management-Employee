package com.example.demo4.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectResponse {
    private int code;
    private String message;
    private Object data;
    public static final int STATUS_CODE_SUCCESS = 200;
    public static final int STATUS_CODE_BAD_REQUEST = 409;
    public static final int STATUS_CODE_NOT_FOUND = 404;
    public static final int STATUS_CODE_UNKNOWN_FAILED = 500;
    public static final int STATUS_CODE_UNAUTHORIZED = 401;
    public static final int STATUS_CODE_FORBIDDEN = 403;
    public static final int STATUS_CODE_NOT_SUPPORT = 300;
    public static final int STATUS_CODE_TOO_MANY_REQUEST = 309;

    public static final String MESSAGE_SUCCESS = "SUCCESS!";
    public static final String MESSAGE_NULL_ID = "ID MUST NOT BE NULL";

    public static final String MESSAGE_TITLE_EXISTED = "TITLE EXISTED";
    public static final String MESSAGE_UNAUTHORIZED = "UNAUTHORIZED TO DO THIS TASK";
    public static final String MESSAGE_USER_NOT_FOUND = "CAN NOT FIND USER WITH ID : ";
    public static final String MESSAGE_EMAIL_NOT_FOUND = "EMAIL NOT FOUND";
    public static final String MESSAGE_PASSWORD_INCORRECT = "PASSWORD INCORRECT";
    public static final String MESSAGE_NEWS_NOT_FOUND = "CAN NOT FIND NEWS WITH ID : ";
    public static final String MESSAGE_EVENT_NOT_FOUND = "CAN NOT FIND EVENT WITH ID : ";
    public static final String MESSAGE_OBJECT_NOT_FOUND = "CAN NOT FIND OBJECT WITH ID : ";
    public static final String MESSAGE_UNAUTHORIZED_TO_UPDATE_EVENT = "USER UNAUTHORIZED TO UPDATE EVENT WITH ID : ";
    public static final String MESSAGE_UNAUTHORIZED_TO_DELETE_EVENT = "USER UNAUTHORIZED TO DELETE EVENT WITH ID : ";
    public static final String MESSAGE_USER_ACCOUNT_INACTIVE = "ACCOUNT INACTIVE :";
    public static final String MESSAGE_USER_KEY_NULL = "KEY NULL: ";
    public static final String MESSAGE_EMAIL_EXISTED = "EMAIL EXISTED: ";
    public static final String MESSAGE_USERNAME_EXISTED = "USERNAME EXISTED: ";
    public static final String MESSAGE_STRING_PARSE_TO_DATE_ERROR = "FAIL TO PARSE FROM STRING TO DATE";
    public static final String MESSAGE_UNAUTHORIZED_TO_ANSWER_QUESTION = "UNAUTHORIZED TO ANSWER QUESTION WITH ID: ";
    public static final String MESSAGE_UNAUTHORIZED_TO_DELETE_QUESTION = "DELETE SUCCES";
}
