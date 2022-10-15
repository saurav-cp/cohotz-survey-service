package com.cohotz.survey.config;

import com.cohotz.survey.engine.score.record.EngineScoreRecord;
import com.cohotz.survey.exp.score.record.ExperienceScoreRecord;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

import static com.cohotz.survey.SurveyConstants.*;

@Configuration
public class KafkaConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.schemaRegistry}")
    private String schemaRegistry;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean(ENGINE_SCORE_RECORD_TOPIC)
    public NewTopic engineScoreRecordTopic() {
        return new NewTopic(ENGINE_SCORE_RECORD_TOPIC, 1, (short) 1);
    }

    @Bean(EXP_SCORE_RECORD_TOPIC)
    public NewTopic expScoreRecordTopic() {
        return new NewTopic(EXP_SCORE_RECORD_TOPIC, 1, (short) 1);
    }


    @Bean
    public ProducerFactory<String, ?> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        configProps.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CREATE_SURVEY_LISTENER_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(ENGINE_SCORE_RECORD_KAFKA_TEMPLATE)
    public KafkaTemplate<String, EngineScoreRecord> engineKafkaTemplate() {
        return new KafkaTemplate<>((ProducerFactory<String, EngineScoreRecord>)producerFactory());
    }

    @Bean(EXP_SCORE_RECORD_KAFKA_TEMPLATE)
    public KafkaTemplate<String, ExperienceScoreRecord> expKafkaTemplate() {
        return new KafkaTemplate<>((ProducerFactory<String, ExperienceScoreRecord>)producerFactory());
    }
}
