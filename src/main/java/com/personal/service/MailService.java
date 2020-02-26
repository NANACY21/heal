package com.personal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 邮件服务
 *
 * @author 李箎
 */
//日志管理
//@Slf4j
@Service
@Transactional
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMail(String to, String title, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from + "@qq.com");
        mailMessage.setTo(to);
        mailMessage.setSubject(title);
        mailMessage.setText(content);
        javaMailSender.send(mailMessage);
//        log.info("邮件发送成功");
    }
}
