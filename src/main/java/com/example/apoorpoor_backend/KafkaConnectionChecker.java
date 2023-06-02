package com.example.apoorpoor_backend;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.errors.TimeoutException;

import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaConnectionChecker {
    public static void main(String[] args) {
        // Kafka 브로커의 부트스트랩 서버 주소 설정
        String bootstrapServers = "localhost:9092";

        // AdminClient 설정
        Properties adminConfig = new Properties();
        adminConfig.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        AdminClient adminClient = KafkaAdminClient.create(adminConfig);

        // Kafka 클러스터 정보 확인
        try {
            DescribeClusterResult describeClusterResult = adminClient.describeCluster();
            KafkaFuture<Collection<Node>> nodesFuture = describeClusterResult.nodes();
            Collection<Node> nodes = nodesFuture.get();

            System.out.println("Kafka 브로커 연결 확인:");
            for (Node node : nodes) {
                System.out.println("Broker ID: " + node.id() + ", Host: " + node.host() + ", Port: " + node.port());
            }
        } catch (TimeoutException e) {
            System.out.println("연결 시간 초과: " + e.getMessage());
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("연결 에러: " + e.getMessage());
        } finally {
            // AdminClient 종료
            adminClient.close();
        }
    }
}