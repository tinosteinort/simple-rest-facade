package de.tse.simplerestfacade.marshalling;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import de.tse.simplerestfacade.RestClientException;


public class MarshallingConfigProvider {

    private final List<MarshallingConfig> configs = new ArrayList<>();
    
    public MarshallingConfigProvider(final List<MarshallingConfig> additionalConfigs) {
        if (additionalConfigs != null) {
            configs.addAll(additionalConfigs);
        }
        loadMarshallingProvider();
    }

    private void loadMarshallingProvider() {
        for (MarshallingConfig marshallingConfig : ServiceLoader.load(MarshallingConfig.class)) {
            configs.add(marshallingConfig);
        }
    }
    
    public Marshaller getMarshaller(final String mediaType) {
        final Optional<MarshallingConfig> configOption = configs.stream()
                .filter(config -> config.supportsMediaType(mediaType)).findFirst();

        return configOption.orElseThrow(() -> new RestClientException("No Marshaller found")).getMarshaller();
    }
    
    public Unmarshaller getUnmarshaller(final String mediaType) {
        final Optional<MarshallingConfig> configOption = configs.stream()
                .filter(config -> config.supportsMediaType(mediaType)).findFirst();

        return configOption.orElseThrow(() -> new RestClientException("No Unmarshaller found")).getUnmarshaller();
    }
}
