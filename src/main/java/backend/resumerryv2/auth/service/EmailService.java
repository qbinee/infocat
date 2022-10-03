package backend.resumerryv2.auth.service;

import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final Integer expireTime = 600;
    private static final Integer randomNumberRange = 1000000;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String email, SimpleMailMessage simpleMailMessage){
        simpleMailMessage.setTo(email);
        javaMailSender.send(simpleMailMessage);
    }

    public SimpleMailMessage setValidationCodeEmailForm(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("[Resummery] 인증코드 입니다");
        // 이메일 인증 코드 생성
        Integer validationCode = setValidationCode();
        // db 저장
        storeValidationCode(email, validationCode);
        simpleMailMessage.setText(String.valueOf(validationCode));
        return simpleMailMessage;
    }

    public void checkValidationCode(String email) {
        /**
         * 이메일 / validation code / expiretime 확인
         */
    }

    private static Integer setValidationCode() {
        /**
         * 랜덤 번호 6자리 생성
         */
        Random generator = new Random();
        generator.setSeed(System.currentTimeMillis());
        return generator.nextInt(randomNumberRange) % randomNumberRange;
    }
    private void storeValidationCode(String email, Integer validationCode) {
        /**
         * 이메일 / validation code / expiretime 생성
         */

        

    }



}
