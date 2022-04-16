package br.com.boasaude.gisa.user.kafka;


import br.com.boasaude.gisa.user.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserProducer {

    @Value("${user.topic}")
    private String userTopic;

    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(UserDto userDto, String evento) {
        Message<String> message = null;
        try {
            message = MessageBuilder.withPayload(objectMapper.writeValueAsString(userDto))
                    .setHeader(KafkaHeaders.TOPIC, userTopic)
                    .setHeader("evento", evento)
                    .build();
            kafkaTemplate.send(message);
        } catch (JsonProcessingException e) {
            log.error("erro durante parse", e);
        }

    }
}
