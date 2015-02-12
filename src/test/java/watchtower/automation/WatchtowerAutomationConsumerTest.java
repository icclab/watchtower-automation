package watchtower.automation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import watchtower.automation.consumer.KafkaCommandsConsumer;
import watchtower.automation.provider.RundeckProvider;

public class WatchtowerAutomationConsumerTest {
  @Mock
  private KafkaCommandsConsumer kafkaCommandsConsumer;
  
  @Mock
  private RundeckProvider rundeckProvider;
  
  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void testKafkaCommandsConsumerLifecycle() throws Exception {
    kafkaCommandsConsumer.start();
    kafkaCommandsConsumer.stop();
  }
}