package com.niksob.quoting_vk_bot.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @JsonProperty("peer_id")
    private Integer peerId;
    private String text;
    private String payload;
    private Object keyboard;
    @JsonProperty("reply_message")
    private Object replyMessage;
    @JsonProperty("admin_author_id")
    private Integer adminAuthorId;
    @JsonProperty("conversation_message_id")
    private Integer conversationMessageId;
    @JsonProperty("is_cropped")
    private Boolean isCropped;
    @JsonProperty("members_count")
    private Integer membersCount;
    @JsonProperty("update_time")
    private Integer updateTime;
    @JsonProperty("was_listened")
    private Boolean wasListened;
    @JsonProperty("pinned_at")
    private Integer pinnedAt;
    @JsonProperty("message_tag")
    private String messageTag;
    @JsonProperty("is_mentioned_user")
    private Boolean isMentionedUser;
}
