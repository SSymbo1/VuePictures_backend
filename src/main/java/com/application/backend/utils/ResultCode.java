package com.application.backend.utils;

public interface ResultCode {
    Integer SUCCESS=7777;
    Integer ERROR=7776;
    Integer REGISTER_SUCCESS=2222;
    Integer REGISTER_ERROR=2221;
    Integer REGISTER_EXIST=2220;
    Integer REGISTER_ERROR_CAPTCHA=2223;
    Integer UPDATE_BACKGROUND_SUCCESS=9622;
    Integer UPDATE_BACKGROUND_ERROR=9644;
    Integer UPDATE_TITLE_SUCCESS=9722;
    Integer UPDATE_TITLE_ERROR=9744;
    Integer UPDATE_USER_INFO_SUCCESS=9922;
    Integer UPDATE_USER_INFO_ERROR=9944;
    Integer EMAIL_ERROR= 1443;
    Integer EMAIL_SUCCESS= 1444;
    Integer HISTORY_SUCCESS= 4444;
    Integer HISTORY_ERROR= 4443;
    Integer HISTORY_UPDATE=4445;
    Integer HISTORY_DELETE=4446;
}
