package com.example.users.service;

import com.example.users.entity.User;
import com.example.users.exception.*;
import com.example.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Value(value = "${data.value.limitOfUserAge}")
    private int limitOfAge;

    @Override
    public User getUserById(int id) {

        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User addUser(User user){
        if(userRepository.existsById(user.getId())){
            throw new UserAlreadyExistsException();
        }

        Date date = user.getBirthdate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int userAge = calendar.get(Calendar.YEAR);
        int currentYear = LocalDate.now().getYear();
        int ageLimit = currentYear - limitOfAge;

        if (userAge > currentYear) {
            throw new UserFutureDateException(userAge, currentYear);
        }

        if (userAge > ageLimit) {
            throw new UserYoungerThanLimitYearsException(limitOfAge);
        }

        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        Date birthdate = user.getBirthdate();
        String email = user.getEmail();

        if (firstname.isEmpty()|| lastname.isEmpty() || email.isEmpty() || birthdate == null) {
            throw new UserFieldsRequiredException();
        }

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (email == null || "".equals(email.trim()) || !Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) {
            throw new UserEmailException();
        }

        return userRepository.save(user);
    }

    @Override
    public User deleteUserById(int id) {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            user = userRepository.findById(id).get();
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }

        return user;
    }

    @Override
    public User saveOrUpdateUser(User newUser, int id) {

        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstname(newUser.getFirstname());
                    user.setLastname(newUser.getLastname());
                    user.setEmail(newUser.getEmail());
                    user.setBirthdate(newUser.getBirthdate());
                    user.setAddress(newUser.getAddress());
                    user.setTelephone(newUser.getTelephone());

                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);

                    return userRepository.save(newUser);
                });
    }

    @Override
    public User patch(Map<String, String> update, int id) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return userRepository.findById(id)
                .map(user -> {
                    String email = update.get("email");
                    if (email == null || "".equals(email.trim()) || !Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) {
                        throw new UserUnSupportedFieldPatchException(update.keySet());

                    } else {
                        user.setEmail(email);

                        return userRepository.save(user);
                    }
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByAgeBetween(Date fromDate, Date toDate) {

        if (fromDate.before(toDate)) {

            return userRepository.findAll().stream()
                    .filter(s -> s.getBirthdate().after(fromDate))
                    .filter(s -> s.getBirthdate().before(toDate))
                    .collect(Collectors.toList());
        } else {
            throw new FindUsersByAgeBetweenException(fromDate, toDate);
        }
    }

}
