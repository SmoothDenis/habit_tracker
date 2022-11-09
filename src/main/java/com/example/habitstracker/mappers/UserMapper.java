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
        String habitListName = userDTO.getHabitListName();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setHabitList(new HabitList(habitListName));

        return user;
    }

    public static UserDTO toDTO(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        HabitList habitList = user.getHabitList();

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        if (habitList != null)
            userDTO.setHabitListName(habitList.getName());

        return userDTO;
    }
}
