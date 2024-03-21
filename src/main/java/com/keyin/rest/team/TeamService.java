package com.keyin.rest.team;

import com.keyin.rest.division.Division;
import com.keyin.rest.division.DivisionRepository;
import com.keyin.rest.division.DivisionService;
import com.keyin.rest.player.Player;
import com.keyin.rest.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    DivisionService divisionService;

    public List<Team> getAllTeams() {
        return (List<Team>) teamRepository.findAll();
    }

    public Team getTeamById(long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);

        return teamOptional.orElse(null);
    }

    public void deleteTeamById(long id) {
        teamRepository.deleteById(id);
    }

    public Team createTeam(Team newTeam) {
        String divisionName = newTeam.getDivision().getName();

        if (divisionName != null) {
            Division division = divisionService.findByName(divisionName);

            if (division == null) {
                division = divisionService.createDivision(newTeam.getDivision());
            }

            newTeam.setDivision(division);
        }

        return teamRepository.save(newTeam);
    }

    public Team updateTeam(long id, Team updatedTeam) {
        Optional<Team> teamToUpdateOptional = teamRepository.findById(id);

        if (teamToUpdateOptional.isPresent()) {
            Team teamToUpdate = teamToUpdateOptional.get();

            teamToUpdate.setName(updatedTeam.getName());
            // update players

            return teamRepository.save(teamToUpdate);
        }

        return null;
    }
}
