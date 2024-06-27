package com.niksob.quoting_vk_bot.mapper.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niksob.quoting_vk_bot.exception.request.BadRequestException;
import com.niksob.quoting_vk_bot.model.callback.CallbackEvent;
import com.niksob.quoting_vk_bot.model.message.Message;
import com.niksob.quoting_vk_bot.model.group.GroupId;
import com.niksob.quoting_vk_bot.model.message.UserMessageDetails;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;

@Setter
@Getter
@Mapper(componentModel = "spring")
public abstract class CallbackEventMapper {
    public static final String MESSAGE_KEY = "message";
    @Autowired
    private ObjectMapper objectMapper;

    public GroupId toGroupId(CallbackEvent callbackEvent) {
        return new GroupId(callbackEvent.getGroupId());
    }

    public Message toMessage(CallbackEvent callbackEvent) {
        if (callbackEvent.getObject() instanceof Message) {
            return (Message) callbackEvent.getObject();
        }
        throw new BadRequestException("Illegal state of callback event: object field must be Message type");
    }

    public UserMessageDetails toUserMessageDetails(CallbackEvent callbackEvent) {
        if (!(callbackEvent.getObject() instanceof LinkedHashMap map)) {
            throw new BadRequestException("Illegal state of callback event: object field must be LinkedHashMap type");
        }
        final Message message = objectMapper.convertValue(map.get(MESSAGE_KEY), Message.class);
        return fromMessage(message);
    }

    @Mapping(source = "peerId", target = "userId.value")
    public abstract UserMessageDetails fromMessage(Message message);
}
