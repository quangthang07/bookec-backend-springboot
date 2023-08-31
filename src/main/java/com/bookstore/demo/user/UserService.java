package com.bookstore.demo.user;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bookstore.demo.config.JwtService;

@Component
// @RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository repository, JwtService service, PasswordEncoder passwordEncoder) {
    this.userRepository = repository;
    this.jwtService = service;
    this.passwordEncoder = passwordEncoder;
  }

  public List<User> getAllUsers() {
    return this.userRepository.findAll();
  }

  public User getUserById(BigInteger id) {
    Optional<User> optionalUser = this.userRepository.findById(id);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    }
    return null;
  }

  public User getUserByEmail(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isPresent()){
      return optionalUser.get();
    }
    return null;
  }

  public String authenticate(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isPresent()){
      User user = optionalUser.get();
      return jwtService.generateToken(user);
    }
    return "";
  }

  public String registerUser(User user) {
    User newUser = this.addNewUser(user);
    if (newUser == null) {
      return "";
    }
    return jwtService.generateToken(newUser);
  }

  // Because param user is gotten from request body, so it'password is not encoded.
  // We create a new user object with encoded password
  public User addNewUser(User user) {
    Optional<User> optionalUser = this.userRepository.findByEmail(user.getEmail());
    if (optionalUser.isPresent()) {
      return null;
    }
    User newUser = User.builder()
      .firstName(user.getFirstName())
      .lastName(user.getLastName())
      .email(user.getEmail())
      .password(passwordEncoder.encode(user.getPassword()))
      .role(user.getRole())
      .build();
    this.userRepository.save(newUser);
    return newUser;
  }

  public User updateUser(BigInteger id, User user) {
    Optional<User> optionalUser = this.userRepository.findById(id);
    if (!optionalUser.isPresent()) {
      return null;
    }
    user.setId(id);
    this.userRepository.save(user);
    return optionalUser.get();
  }

  public User deleteUser(BigInteger id) {
    Optional<User> optionalUser = this.userRepository.findById(id);
    if (!optionalUser.isPresent()) {
      return null;
    }
    this.userRepository.deleteById(id);
    return optionalUser.orElseThrow();
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    List<User> userList = this.getAllUsers();
    for(User user : userList) {
      if (user.getEmail().equals(email)) {
        return  user;
      }
    }
    throw new UsernameNotFoundException("Email not found");
  }


}
