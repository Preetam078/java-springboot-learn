package com.preetamtech.Journal.services;

import com.preetamtech.Journal.entity.JournalEntry;
import com.preetamtech.Journal.entity.User;
import com.preetamtech.Journal.repositories.JournalEntryRepository;
import com.preetamtech.Journal.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUserEntry(User user) {
        userRepository.save(user);
    }

    public void saveUserEntryV2(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRoles().isEmpty()) {
            user.setRoles(List.of("USER"));
        }

        userRepository.save(user);
    }

    public List<User>getAllUserEntry() {
        return userRepository.findAll();
    }

    public Optional<User> getUserEntryById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteUserEntry(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void updateUserEntry(ObjectId id, User newEntry) {
        Optional<User> optionalOldEntry = userRepository.findById(id);

        if (optionalOldEntry.isPresent()) {
            User oldEntry = optionalOldEntry.get();

            if (!newEntry.getUsername().isEmpty() && !newEntry.getUsername().equals(oldEntry.getUsername())) {
                oldEntry.setUsername(newEntry.getUsername());
            }

            if (!newEntry.getPassword().isEmpty() && !newEntry.getPassword().equals(oldEntry.getPassword())) {
                oldEntry.setPassword(newEntry.getPassword());
            }

            userRepository.save(oldEntry);
        }
    }

    public User findByUserName (String username) {
        return userRepository.findByUsername(username);
    }
}
