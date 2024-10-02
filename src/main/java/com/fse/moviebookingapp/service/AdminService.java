package com.fse.moviebookingapp.service;

import com.fse.moviebookingapp.dto.RegistrationDto;
import com.fse.moviebookingapp.exception.UserExceptions;
import com.fse.moviebookingapp.model.User;
import com.fse.moviebookingapp.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser(RegistrationDto registrationDto) {
        if (findUserByLoginId(registrationDto.getLoginId()).isPresent()) {
            throw new UserExceptions("USER_EXISTS");
        }
        if (adminRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new UserExceptions("EMAIL_EXISTS");
        }
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new UserExceptions("PASSWORD_MISMATCH");
        }
        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setContactNumber(registrationDto.getContactNumber());
        user.setLoginId(registrationDto.getLoginId());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        adminRepository.save(user);
        log.info("New User got created successfully with loginID {}",user.getLoginId());
    }

    public Optional<User> findUserByLoginId(String loginId) {
        return adminRepository.findByLoginId(loginId);
    }

    public Optional<User> authenticate(String loginId, String rawPassword) {
        Optional<User> user = findUserByLoginId(loginId);
        return (user.isPresent() && passwordEncoder.matches(rawPassword, user.get().getPassword())) ? user : Optional.empty();
    }

    public boolean resetUserPassword(User user, String newPassword, String confirmNewPassword){
        if(!newPassword.equals(confirmNewPassword)){
            throw new UserExceptions("PASSWORD_MISMATCH");
    }
        if(user!=null){
            user.setPassword(passwordEncoder.encode(newPassword));
            adminRepository.save(user);
            log.info("Password Reset Done Successfully for LoginID: {}",user.getLoginId());
            return true;
        }
        return false;
   }
}
