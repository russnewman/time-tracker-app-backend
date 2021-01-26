package com.example.TimeTracker.service;
import com.example.TimeTracker.model.Gender;
import com.example.TimeTracker.model.User;
import com.example.TimeTracker.model.UserRole;
import com.example.TimeTracker.payload.request.UpdateRequest;
import com.example.TimeTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService {

    @Autowired
    UserRepository userRepository;

    public void update(UpdateRequest updateRequest){
        User user = userRepository.findByEmail(updateRequest.getEmail()).orElseThrow();

        user.setEmail(updateRequest.getEmail());
        user.setFullName(updateRequest.getFullName());
        user.setDepartment(updateRequest.getDepartment());
        user.setPosition(updateRequest.getPosition());
        if (updateRequest.getUserRole() != null) user.setRole(UserRole.valueOf(updateRequest.getUserRole()));
        if (updateRequest.getGender() != null) user.setGender(Gender.valueOf(updateRequest.getGender()));
        user.setLeaderEmail(updateRequest.getLeaderEmail());
        user.setHireDate(updateRequest.getHireDate());

        userRepository.save(user);
    }

}
