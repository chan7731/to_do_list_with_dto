package com.chan7731.to_do_list_with_dto.controller;

import com.chan7731.to_do_list_with_dto.dto.SignupDto;
import com.chan7731.to_do_list_with_dto.model.User;
import com.chan7731.to_do_list_with_dto.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("signupDto", new SignupDto());

        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(
            @Valid @ModelAttribute("signupDto") SignupDto signupDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        User user = User.builder() //중복 가입 확인
                .username(signupDTO.getUsername())
                .password(signupDTO.getPassword())
                .build();
        userRepository.save(user);

        return "redirect:/login?registered"; // http://localhost:8080/login?registered=true
    }
}
