package com.example.drachwallet.serviceImpl;

import com.example.drachwallet.exceptions.LoginException;
import com.example.drachwallet.model.CurrentUserSession;
import com.example.drachwallet.model.Customer;
import com.example.drachwallet.model.Login;
import com.example.drachwallet.repositories.CurrentUserSessionRepository;
import com.example.drachwallet.repositories.CustomerRepository;
import com.example.drachwallet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.bytebuddy.utility.RandomString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;
    @Override
    public String login(Login login) throws LoginException {
        List<Customer> customer= customerRepository.findCustomerByMobile(login.getMobileNumber());

        Customer existingCustomer = customer.get(0);

        if(existingCustomer == null) {
            throw new LoginException("Invalid MobileNumber!");
        }



        Optional<CurrentUserSession> optional =  currentUserSessionRepository.findByUserId(existingCustomer.getCustomerId());

        if(optional.isPresent()) {

            throw new LoginException("User Already Exists in the System.");

        }

        if(existingCustomer.getPassword().equals(login.getPassword())) {

            String key = RandomString.make(6);

            CurrentUserSession currentUserSession = new CurrentUserSession(existingCustomer.getCustomerId(),key, LocalDateTime.now());

            currentUserSessionRepository.save(currentUserSession);

            return currentUserSession.toString();
        }

        throw new LoginException("Wrong password");

    }

    @Override
    public String logout(String Key) throws LoginException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(Key);

        if(currentUserSession == null) {
            throw new LoginException("Invalid Unique userId (Session Key).");

        }

        currentUserSessionRepository.delete(currentUserSession);

        return "Logged Out Successfully!";
    }
}
