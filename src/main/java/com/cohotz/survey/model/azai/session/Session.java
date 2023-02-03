package com.cohotz.survey.model.azai.session;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document("ch_azai_session")
@Sharded(shardKey = { "tenant", "email" })
public class Session {
    public Session(LocalDateTime expiryDate){
        this.expiryAt = expiryDate;
        this.lastUpdatedAt = LocalDateTime.now(ZoneOffset.UTC);
        this.active = true;
        this.conversation = new ArrayList<>();
    }

    @Id
    private String id;
    private boolean active;
    @Field("expiry_ts")
    private LocalDateTime expiryAt;
    @Field("last_updated_ts")
    private LocalDateTime lastUpdatedAt;
    @Field("profile_id")
    private String profileId;
    private String email;
    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    private String tenant;
    private SessionContext context;
    private SessionStatus status;
    @Field("conversation_status")
    private ConversationStatus conversationStatus;
    private List<Event> conversation;
}
