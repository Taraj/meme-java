package tk.tarajki.meme.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tk.tarajki.meme.models.User;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private void sendEmail(String email, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    public void sendConfirmationEmail(User user, int code) {
        String message = "Twój kod aktywacyjny to: " + code;
        sendEmail(user.getEmail(), "Aktywacja Konta", message);
    }

    public void sendResetPasswordRequest(User user, int code) {
        String message = "Twoj kod resetujący hasło to: " + code;

        sendEmail(user.getEmail(), "Reset Hasła", message);
    }

    public void sendNewPassword(User user, String password) {
        String message = "Twoje nowe hasło to: " + password;

        sendEmail(user.getEmail(), "Nowe Hasło", message);
    }
}
