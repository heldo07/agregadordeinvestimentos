package tech.projava.agregadordeinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.projava.agregadordeinvestimentos.controller.CreateUserDto;
import tech.projava.agregadordeinvestimentos.controller.UpdateUserDto;
import tech.projava.agregadordeinvestimentos.entity.User;
import tech.projava.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){

        //DTO ->ENTITY
        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

       var userSaved = userRepository.save(entity);

       return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){

        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }


    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto){
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if(userEntity.isPresent()){
            var user = userEntity.get();

            if(updateUserDto.username() != null){
                user.setUsername(updateUserDto.password());
            }
            userRepository.save(user);
        }

    }

    public void deleteById(String userId){

        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);



        if(userExists){
            userRepository.deleteById(id);
        }
    }
}
