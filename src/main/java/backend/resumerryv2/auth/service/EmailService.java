package backend.resumerryv2.auth.service;

import backend.resumerryv2.auth.domain.EmailValidation;
import backend.resumerryv2.auth.domain.repository.EmailValidationRepository;
import backend.resumerryv2.auth.service.template.EmailForm;
import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailValidationRepository emailValidationRepository;
    private final Integer expireTime = 10;
    private static final Integer randomNumberRange = 1000000;

    public void getValidationEmail(String email){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        Integer validationCode = this.getRandomValidationCode();
        simpleMailMessage.setText(EmailForm.getValidationCodeEmailForm(validationCode));
        this.sendEmail(email, simpleMailMessage);
        this.storeValidationCode(email, validationCode);
    }

    private void sendEmail(String email, SimpleMailMessage simpleMailMessage){
        simpleMailMessage.setTo(email);
        javaMailSender.send(simpleMailMessage);
    }

    public EmailValidation checkValidationCode(String email, Integer validationCode) throws CustomException{
        /**
         * 이메일 / validation code / expiretime 확인
         */
        Optional<EmailValidation> emailValidation = emailValidationRepository.findFirstByEmailOrderByExpireTimeDesc(email);
        Integer dbValidationCode = emailValidation.get().getValidationCode();
        LocalDateTime dbExpireTime = emailValidation.get().getExpireTime();

        if (LocalDateTime.now().compareTo(dbExpireTime) > 0){
            throw new CustomException(HttpStatus.CONFLICT, ErrorType.EXPIRED_VALID_CODE);
        }
        if(validationCode.compareTo(dbValidationCode) != 0){
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorType.NOT_VALID_CODE_ERROR);
        }

        return emailValidation.get();

    }

    private Integer getRandomValidationCode() {
        Random generator = new Random();
        generator.setSeed(System.currentTimeMillis());
        return generator.nextInt(randomNumberRange) % randomNumberRange;
    }
    private void storeValidationCode(String email, Integer validationCode) {
        EmailValidation emailValidation = EmailValidation.builder()
                .email(email)
                .validationCode(validationCode)
                .expireTime(LocalDateTime.now().plusMinutes(expireTime))
                .build();
        emailValidationRepository.save(emailValidation);
    }

    public void deleteValidationCode(EmailValidation emailValidation){
        emailValidationRepository.delete(emailValidation);
    }



}
