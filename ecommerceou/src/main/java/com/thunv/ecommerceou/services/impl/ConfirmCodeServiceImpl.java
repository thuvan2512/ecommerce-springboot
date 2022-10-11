package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.ConfirmCode;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.ConfirmCodeRepository;
import com.thunv.ecommerceou.repositories.UserRepository;
import com.thunv.ecommerceou.services.ConfirmCodeService;
import com.thunv.ecommerceou.services.MailService;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfirmCodeServiceImpl implements ConfirmCodeService {
    @Autowired
    private ConfirmCodeRepository confirmCodeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private MailService mailService;
    @Override
    public ConfirmCode createOrUpdateCode(User user) throws RuntimeException{
        try {
            ConfirmCode confirmCode;
            List<ConfirmCode> list = this.confirmCodeRepository.findByAuthor(user);
            if (list.size() > 0){
                confirmCode = list.get(0);
            }else {
                confirmCode = new ConfirmCode();
                confirmCode.setAuthor(user);
            }
            String mailTo = user.getEmail();
            String subject = "Confirm to reset your password";
            String title = String.format("Dear %s,", user.getUsername());
            String code = this.utils.randCodeConfirm();
            String content = String.format("your confirmation code is %s. If confirmed, this is also your new password",code);
            String mailTemplate = "reset-password";
            String items = "";
            this.mailService.sendMail(mailTo,subject,title,content,items,mailTemplate);
            confirmCode.setCode(this.utils.passwordEncoder(code));
            return this.confirmCodeRepository.save(confirmCode);

        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean sendConfirm(User user, String code) throws RuntimeException{
        try {
            ConfirmCode confirmCode;
            List<ConfirmCode> list = this.confirmCodeRepository.findByAuthor(user);
            if (list.size() > 0){
                confirmCode = list.get(0);
            }else {
                throw new RuntimeException("Can not find code");
            }
            String ps = this.utils.passwordEncoder(code.strip());
            System.out.println(confirmCode.getCode());
            System.out.println(ps);
            if (confirmCode.getCode().equals(ps)){
                user.setPassword(this.passwordEncoder.encode(code));
                this.userRepository.save(user);
                this.confirmCodeRepository.deleteById(confirmCode.getId());
                return true;
            }else {
                throw new RuntimeException("Code wrong");
            }
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
