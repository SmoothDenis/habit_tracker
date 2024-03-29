package com.example.habitstracker.mappers;

import org.springframework.stereotype.Component;

import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.User;
import com.example.openapi.dto.UserDTO;

@Component
public class UserMapper {

    public static User toEntity(UserDTO userDTO) {
        String username = userDTO.getUsername();

        String password = userDTO.getPassword();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setHabitList(new HabitList(userDTO.getHabitListName()));
        return user;
    }

    public static UserDTO toDTO(User user) {
        Long id = user.getUserId();

        String username = user.getUsername();

        String password = user.getPassword();


        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);

        return userDTO;
    }
}
