package com.niksob.quoting_vk_bot.mapper.callback;

import com.niksob.quoting_vk_bot.exception.request.BadRequestException;
import com.niksob.quoting_vk_bot.model.callback.CallbackEvent;
import com.niksob.quoting_vk_bot.model.message.Message;
import com.niksob.quoting_vk_bot.model.group.GroupId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CallbackEventMapper {
    public GroupId toGroupId(CallbackEvent callbackEvent) {
        return new GroupId(callbackEvent.getGroupId());
    }

    public Message toMessage(CallbackEvent callbackEvent) {
        if (callbackEvent.getObject() instanceof Message) {
            return (Message) callbackEvent.getObject();
        }
        throw new BadRequestException("Illegal state of callback event: object field must be Message type");
    }
}
