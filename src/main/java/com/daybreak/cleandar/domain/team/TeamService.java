package com.daybreak.cleandar.domain.team;

import com.daybreak.cleandar.domain.teamuser.TeamUser;
import com.daybreak.cleandar.domain.teamuser.TeamUserRepository;
import com.daybreak.cleandar.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;

    public List<Team> index(User user) {
        return teamUserRepository.findTeamUserByUser(user).stream().map(TeamUser::getTeam).collect(Collectors.toList());
    }

    public Team show(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        return team.orElse(null);
    }

    public Team create(@RequestBody TeamDto.Request request) {
        try {
            Team team = teamRepository.save(Team.builder().name(request.getName()).leader(request.getLeader()).build());
            teamUserRepository.save(TeamUser.builder().team(team).user(request.getLeader()).build());
            return team;
        } catch (DataIntegrityViolationException exception) {
            return null;
        }
    }

    public Team update(@RequestBody TeamDto.Request request) {
        try {
            Team team = teamRepository.findById(request.getId()).orElseThrow(() -> new IllegalArgumentException("Not Found Entity"));
            team.update(request.getName());
            return teamRepository.save(team);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    public Team delete(User leader, Long id) {
        try {
            Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found Entity"));
            if (!team.getLeader().getId().equals(leader.getId())) {
                throw new IllegalArgumentException("is not leader");
            }

            List<TeamUser> teamUsers = teamUserRepository.findByTeam(team);
            teamUserRepository.deleteAll(teamUsers);
            teamRepository.delete(team);

            return team;
        } catch (IllegalArgumentException exception) {
            System.out.println("error - " + exception.getMessage());
            return null;
        }
    }

    //TODO 초대 로직 작성
    public void invite() { //List<TeamUser> teamUsers

    }
}
