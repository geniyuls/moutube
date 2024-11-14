package com.jykim.moutube.results.user;

import com.jykim.moutube.results.Result;

public enum RegisterResult implements Result {
    FAILURE_DUPLICATE_CONTACT,
    FAILURE_DUPLICATE_EMAIL,
    FAILURE_DUPLICATE_NICKNAME,
}
