package com.project.project_oop.service.impl;

import com.project.project_oop.dto.UserDTO;
import com.project.project_oop.model.EmailVerificationToken;
import com.project.project_oop.model.User;
import com.project.project_oop.model.constant.Status;
import com.project.project_oop.repository.EmailVerificationTokenRepository;
import com.project.project_oop.repository.UserRepository;
import com.project.project_oop.service.EmailVerificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;


@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${frontend.prefix}")
    private String OTP_HEADER;

    @Value("${frontend.port}")
    private String PORT;

    private String generateEmailVerificationURL(String otp) {
        return OTP_HEADER + PORT + "/verify?code=" + otp;
    }

    @Override
    public String generateEmailVerificationToken(User user) {
        return user.getEmail() + "." + user.getUsername();
    }

    @Override
    public boolean verify(String otp) {
        User user = userRepository.findByEmailVerificationToken(otp).orElse(null);
        if (user == null) {
            return false;
        } else {
            user.setStatus(Status.VERIFIED);
            userRepository.save(user);
            return true;
        }
    }

    private void revokeAllEmailVerificationToken(Long id) {
        List<EmailVerificationToken> emailVerificationTokenList = emailVerificationTokenRepository.findAllValidTokenByUser(id);
        emailVerificationTokenList.forEach(
                token -> {
                    token.setExpired(1L);
                }
        );
        emailVerificationTokenRepository.saveAll(emailVerificationTokenList);
    }

    @Override
    public boolean sendVerificationEmail(Long id, String address, String fullName, String otp) {
        revokeAllEmailVerificationToken(id);
        String fromAddress = "Your email address";
        String senderName = "Wjbu Lord Company";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(address);
            helper.setSubject(subject);

            content = content.replace("[[name]]", fullName);

            content = content.replace("[[URL]]", generateEmailVerificationURL(otp));

            helper.setText(content, true);

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
