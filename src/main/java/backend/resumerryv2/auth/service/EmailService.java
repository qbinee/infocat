package backend.resumerryv2.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import backend.resumerryv2.auth.domain.EmailValidation;
import backend.resumerryv2.auth.domain.repository.EmailValidationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final EmailValidationRepository emailValidationRepository;
    private final Integer expireTime = 10;
    private static final Integer randomNumberRange = 1000000;

    public EmailService(JavaMailSender javaMailSender, EmailValidationRepository emailValidationRepository) {
        this.javaMailSender = javaMailSender;
        this.emailValidationRepository = emailValidationRepository;
    }

    public void sendEmail(String email, SimpleMailMessage simpleMailMessage){
        simpleMailMessage.setTo(email);
        javaMailSender.send(simpleMailMessage);
    }

    public SimpleMailMessage setValidationCodeEmailForm(String email, Integer validationCode) {
        String message = "안녕하세요 \n" +
                "\n" +
                "요청하신 이메일 인증번호를 안내 드립니다.\n" +
                "\n" +
                "아래 번호를 입력하여 인증 절차를 완료해 주세요.\n" +
                "\n" +
                "본 인증번호는 10분 후에 만료됩니다.\n" +
                "\n" +
                "인증번호: "
                ;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("[Resummery] 인증코드 입니다");
        simpleMailMessage.setText(message+String.valueOf(validationCode));
        // db 저장
        storeValidationCode(email, validationCode);
        return simpleMailMessage;
    }

    public void checkValidationCode(String email, Integer validationCode) throws ValidationException {
        /**
         * 이메일 / validation code / expiretime 확인
         */
        Optional<EmailValidation> emailValidation = emailValidationRepository.findByEmailOrderByExpireTime(email);
        Integer dbValidationCode = emailValidation.get().getValidationCode();
        LocalDateTime dbExpireTime = emailValidation.get().getExpireTime();

        if(validationCode.compareTo(dbValidationCode) != 0 || dbExpireTime.compareTo(LocalDateTime.now()) < 0 ){
            throw new ValidationException("유효번호가 일치하지 않습니다.");
        }
    }

    public Integer setValidationCode() {
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
        EmailValidation emailValidation = EmailValidation.builder()
                .email(email)
                .validationCode(validationCode)
                .expireTime(LocalDateTime.now().plusMinutes(expireTime))
                .build();

        emailValidationRepository.save(emailValidation);

    }



}
