package de.tse.simplerestfacade.marshalling;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;


public class MarshallingConfigProvider {

    private final List<MarshallingConfig> configs = new ArrayList<>();
    
    public MarshallingConfigProvider() {
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

        return configOption.orElseThrow(() -> new IllegalStateException()).getMarshaller();
    }
    
    public Unmarshaller getUnmarshaller(final String mediaType) {
        final Optional<MarshallingConfig> configOption = configs.stream()
                .filter(config -> config.supportsMediaType(mediaType)).findFirst();

        return configOption.orElseThrow(() -> new IllegalStateException()).getUnmarshaller();
    }
}
