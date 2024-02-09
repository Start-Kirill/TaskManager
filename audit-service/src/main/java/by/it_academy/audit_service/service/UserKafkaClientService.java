package by.it_academy.audit_service.service;

import by.it_academy.audit_service.service.api.IUserClientService;
import by.it_academy.task_manager_common.dto.UserDto;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import by.it_academy.task_manager_common.exceptions.commonInternal.InternalServerErrorException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Primary
public class UserKafkaClientService implements IUserClientService {

    private static final String TOPIC_USER_REQUEST = "user_request";

    private static final String TOPIC_USER_RESPONSE = "user_response";


    private final KafkaTemplate<String, UUID> kafkaTemplate;

    private final ConsumerFactory<String, UserDto> userDtoConsumerFactory;

    public UserKafkaClientService(KafkaTemplate<String, UUID> kafkaTemplate,
                                  ConsumerFactory<String, UserDto> userDtoConsumerFactory) {
        this.kafkaTemplate = kafkaTemplate;
        this.userDtoConsumerFactory = userDtoConsumerFactory;
    }

    @Override
    public UserDto get(String token, UUID uuid) {
        CompletableFuture<SendResult<String, UUID>> send = this.kafkaTemplate.send(TOPIC_USER_REQUEST, uuid);
        UserDto userDto = null;
        try (Consumer<String, UserDto> consumer = userDtoConsumerFactory.createConsumer()) {
            consumer.subscribe(Arrays.asList(TOPIC_USER_RESPONSE));
            ConsumerRecords<String, UserDto> records = consumer.poll(Duration.ofSeconds(5));
            for (ConsumerRecord<String, UserDto> record : records) {
                UserDto recordUserDto = record.value();
                if (uuid.equals(recordUserDto.getUuid())) {
                    userDto = recordUserDto;
                    break;
                }
            }
        }
        if (userDto == null) {
            throw new InternalServerErrorException(List.of(new ErrorResponse(ErrorType.ERROR, "Response not retrieved")));
        }
        return userDto;
    }

}
