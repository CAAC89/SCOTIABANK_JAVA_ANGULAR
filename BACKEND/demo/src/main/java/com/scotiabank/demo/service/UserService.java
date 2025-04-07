package com.scotiabank.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scotiabank.demo.model.User;
import com.scotiabank.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // ✅ Cambiar a PasswordEncoder


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")); // ✅ Manejo de error seguro

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        return email;  /// generate token
    }

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // ✅ Encripta correctamente la contraseña
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public User updateUser(String email, User updatedUser) {
        return userRepository.findByEmail(email).map(existingUser -> {
            // Solo actualiza los campos permitidos
            existingUser.setNombre(updatedUser.getNombre());
            existingUser.setApellido(updatedUser.getApellido());
            existingUser.setTelefono(updatedUser.getTelefono());
            existingUser.setTipoIdentificacion(updatedUser.getTipoIdentificacion());
            existingUser.setNumeroIdentificacion(updatedUser.getNumeroIdentificacion());
    
            // Si envían una nueva contraseña, la encripta antes de actualizar
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
    
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}