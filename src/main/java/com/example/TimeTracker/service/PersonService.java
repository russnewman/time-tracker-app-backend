package com.example.TimeTracker.service;
import com.example.TimeTracker.exception.IncorrectPasswordException;
import com.example.TimeTracker.exception.UserAlreadyExistException;
import com.example.TimeTracker.exception.UserNotFoundException;
import com.example.TimeTracker.model.Gender;
import com.example.TimeTracker.model.Person;
import com.example.TimeTracker.payload.request.UpdatePasswordRequest;
import com.example.TimeTracker.payload.request.PersonInfo;
import com.example.TimeTracker.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder encoder;

    public void updateInfo(PersonInfo personInfo) throws UserAlreadyExistException {

        Person person = personRepository.findById(personInfo.getId()).orElseThrow();
        if(!person.getEmail().equals(personInfo.getEmail())){
            boolean existByEmail = personRepository.existsByEmail(personInfo.getEmail());
            if (existByEmail){
                throw new UserAlreadyExistException("This email is already in use!");
            }
            person.setEmail(personInfo.getEmail());
        }
        person.setFullName(personInfo.getFullName());
        person.setDepartment(personInfo.getDepartment());
        person.setPosition(personInfo.getPosition());
        if (personInfo.getGender() != null) person.setGender(Gender.valueOf(personInfo.getGender()));
        person.setHireDate(personInfo.getHireDate());
        personRepository.save(person);
    }

    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) throws IncorrectPasswordException {

        String email = updatePasswordRequest.getEmail();
        String password = updatePasswordRequest.getPassword();
        String newPassword = updatePasswordRequest.getNewPassword();

        Person person = personRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        if (encoder.matches(password, person.getPassword())){
            person.setPassword(encoder.encode(newPassword));
            personRepository.save(person);
        }else throw new IncorrectPasswordException("Password is incorrect");

    }

    public void updateEmployee(PersonInfo personInfo){
        Person person = personRepository.findByEmail(personInfo.getEmail()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        person.setDepartment(personInfo.getDepartment());
        person.setPosition(personInfo.getPosition());
        if (personInfo.getGender() != null)
            person.setGender(Gender.valueOf(personInfo.getGender()));
        person.setHireDate(personInfo.getHireDate());

        personRepository.save(person);
    }


    public void deleteEmployee(Long employeeId){
        Person person = personRepository.findById(employeeId).orElseThrow(()->new UsernameNotFoundException("User not found"));
        person.setManagerId(null);
        personRepository.save(person);

    }

    public void addOrDeleteManager(Long userId, Long managerId){
        Person person = personRepository.findById(userId).orElseThrow();
        person.setManagerId(managerId);
        personRepository.save(person);
    }

//    public void deleteManager(Long userId){
//        Person person = personRepository.findById(userId).orElseThrow();
//        person.setManagerId(null);
//        personRepository.save(person);
//    }

}
