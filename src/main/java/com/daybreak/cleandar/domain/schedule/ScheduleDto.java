package com.daybreak.cleandar.domain.schedule;

import com.daybreak.cleandar.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleDto {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Getter
    @NoArgsConstructor
    public static class Request{
        private String start;
        private String end;
        private String title;
        private String description;

        @Builder
        public Request(String start, String end, String title, String description){
            this.start = start;
            this.end = end;
            this.title = title;
            this.description = description;
        }

        public Schedule toEntity(User user) {
            return Schedule.builder()
                    .start(LocalDateTime.parse(start, formatter))
                    .end(LocalDateTime.parse(end, formatter))
                    .title(title)
                    .description(description)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String start;
        private String end;
        private String title;
        private String description;

        public Response(Schedule schedule){
            id = schedule.getId();
            start = schedule.getStart().format(formatter);
            end = schedule.getEnd().format(formatter);
            title = schedule.getTitle();
            description = schedule.getDescription();
        }
    }
}