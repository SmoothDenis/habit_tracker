package com.example.habitstracker.mappers;

import java.util.List;

import com.example.habitstracker.models.Habit;
import com.example.habitstracker.models.HabitList;
import com.example.openapi.dto.HabitDTO;
import com.example.openapi.dto.HabitListDTO;

public class HabitListMapper {
    public static HabitList toEntity(HabitListDTO habitListDTO) {
        Long id = habitListDTO.getId();
        String name = habitListDTO.getName();
        List<Habit> habits = habitListDTO.getHabits().stream().map(habitDTO -> HabitMapper.toEntity(habitDTO)).toList();

        HabitList habitList = new HabitList();
        habitList.setId(id);
        habitList.setName(name);
        habitList.setHabits(habits);

        return habitList;
    }

    public static HabitListDTO listDTO(HabitList habitList) {
        Long id = habitList.getId();
        String name = habitList.getName();

        List<HabitDTO> habits = habitList.getHabits().stream().map(habit -> HabitMapper.toDTO(habit)).toList();

        HabitListDTO habitListDTO = new HabitListDTO();
        habitListDTO.setId(id);
        habitListDTO.setName(name);
        habitListDTO.setHabits(habits);

        return habitListDTO;
    }
}
