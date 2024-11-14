package com.jykim.moutube.services;

import com.jykim.moutube.entities.EmailTokenEntity;
import com.jykim.moutube.entities.UserEntity;
import com.jykim.moutube.exceptions.TransactionalException;
import com.jykim.moutube.mappers.UserMapper;
import com.jykim.moutube.results.CommonResult;
import com.jykim.moutube.results.Result;
import com.jykim.moutube.results.user.RegisterResult;
import com.jykim.moutube.utils.CryptoUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Result register(UserEntity user) {
        if (user == null ||
                user.getEmail() == null || user.getEmail().length() < 8 || user.getEmail().length() > 50 ||
                user.getPassword() == null || user.getPassword().length() < 6 || user.getPassword().length() > 50 || user.getNickname() == null || user.getNickname().length() < 2 || user.getNickname().length() > 10 ||
                user.getContact() == null || user.getContact().length() < 10 || user.getContact().length() > 12) {
            return CommonResult.FAILURE;
        }
        if (this.userMapper.selectUserByEmail(user.getEmail()) != null) {
            return RegisterResult.FAILURE_DUPLICATE_EMAIL;
        }
        if (this.userMapper.selectUserByNickname(user.getNickname()) != null) {
            return RegisterResult.FAILURE_DUPLICATE_NICKNAME;
        }
        if (this.userMapper.selectUserByContact(user.getContact()) != null) {
            return RegisterResult.FAILURE_DUPLICATE_CONTACT;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setDeletedAt(null);
        user.setAdmin(false);
        user.setSuspended(false);
        user.setVerified(false);
        if (this.userMapper.insertUser(user) == 0){
            throw new TransactionalException();
        }
        EmailTokenEntity emailToken = new EmailTokenEntity();
        emailToken.setUserEmail(user.getEmail());
        emailToken.setKey(CryptoUtils.hashSha512(String.format("%s%s%f%f", user.getEmail(), user.getPassword(), Math.random(), Math.random())));

        emailToken.setCreatedAt(LocalDateTime.now());
        emailToken.setExpiresAt(LocalDateTime.now().plusHours(24));
        emailToken.setUsed(false);
        // TODO emailToken ISERT 하기
        // TODO @Transactional 걸고 설명하기
        // TODO 이메일 보내기

        return this.userMapper.insertUser(user) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
