package com.example.TimeTracker.service;
import com.example.TimeTracker.exception.IncorrectPasswordException;
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

    public void updateInfo(PersonInfo personInfo) throws UserNotFoundException {
        Person person = personRepository.findByEmail(personInfo.getEmail()).orElseThrow();

        //TO DO Check that if new email is not equal old email, that new email doesnt exist in db

        person.setEmail(personInfo.getEmail());
        person.setFullName(personInfo.getFullName());
        person.setDepartment(personInfo.getDepartment());
        person.setPosition(personInfo.getPosition());
        if (personInfo.getGender() != null) person.setGender(Gender.valueOf(personInfo.getGender()));

        String leaderEmail = personInfo.getLeaderEmail();
        if (leaderEmail != null && !leaderEmail.equals("")){
            boolean existByEmail =  personRepository.existsByEmail(leaderEmail);
            //TO DO Check that person with leaderEmail has a role "LEADER"
            if(!existByEmail) {
                throw new UserNotFoundException("Leader email does not exist");
            }
        }
        if (leaderEmail.equals("")) {
            person.setLeaderEmail(null);
        } else {
            person.setLeaderEmail(leaderEmail);
        }
//        else user.setLeaderEmail(leaderEmail);

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
        person.setLeaderEmail(null);
        personRepository.save(person);

    }

}
