///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package pl.pracaInzynierska;
//
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import pl.pracaInzynierska.model.Uzytkownik;
//import pl.pracaInzynierska.repository.UzytkownikRepository;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// *
// * @author maciek
// */
//@Controller
//public class PasswordController extends WebMvcConfigurationSupport{
////    @Autowired
////    private UzytkownikRepository uzytkownikRepository;
//    
//    @Autowired
//    BCryptPasswordEncoder passwordEncoder;
//    
//    public void processPassword(@Valid Uzytkownik user){
//        user.setPasswordEncrypted(passwordEncoder.encode(user.getPassword()));
//    }
//}
