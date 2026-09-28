package com.devops.registration.controller;

import com.devops.registration.model.Registration;
import com.devops.registration.repository.RegistrationRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final RegistrationRepository registrationRepository;

    public RegistrationController(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registration", new Registration());
        return "registration";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registration") Registration registration,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (registrationRepository.existsByEmail(registration.getEmail())) {
            model.addAttribute("emailError", "This email is already registered.");
            return "registration";
        }

        registrationRepository.save(registration);

        model.addAttribute("successMessage",
                "Registration successful! Welcome to the DevOps Free Course.");

        model.addAttribute("registration", new Registration());

        return "registration";
    }
}
