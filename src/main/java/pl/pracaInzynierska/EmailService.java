///////*
////// * To change this license header, choose License Headers in Project Properties.
////// * To change this template file, choose Tools | Templates
////// * and open the template in the editor.
////// */
//package pl.pracaInzynierska;
//////
///////**
////// *
////// * @author m
////// */
//////
//////
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
////// 
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//    
//    //https://www.programmersought.com/article/69615023407/
//    
//    //https://howtodoinjava.com/spring-core/send-email-with-spring-javamailsenderimpl-example/
//
//    public void send(String to, String title, String contents) {
//        System.out.println("wewnątrz metody send");
//        MimeMessage mail = javaMailSender.createMimeMessage();
//        System.out.println(mail);
//        try {
//
//            System.out.println("wewnątrz try");
//            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
//            helper.setTo(to);
//            helper.setReplyTo("inzmailserv@gmail.com");
//            helper.setFrom("inzmailserv@gmail.com");
//            helper.setSubject(title);
//            helper.setText(contents);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } finally {
//        }
//        javaMailSender.send(mail);
//
//        System.out.println("ostatnia linia metody send");
//    }
//}