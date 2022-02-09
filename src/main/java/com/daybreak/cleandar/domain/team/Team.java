package com.daybreak.cleandar.domain.team;

import com.daybreak.cleandar.domain.teamuser.TeamUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//테이블과 맵핑
@Entity
@Getter
//무분별한 객체 생성 방지
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//테이블의 이름 설정
@Table(name = "teams")
public class Team {

    //Pri key, Auto_increment 설정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Null 허용 x
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String leader;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "team")
    private List<TeamUser> teamUser = new ArrayList<>();

    //빌더
    @Builder
    public Team(String name, String leader){
        this.name = name;
        this.leader = leader;

    }

}
