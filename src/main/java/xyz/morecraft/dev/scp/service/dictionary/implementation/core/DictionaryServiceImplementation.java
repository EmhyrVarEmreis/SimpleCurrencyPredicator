package xyz.morecraft.dev.scp.service.dictionary.implementation.core;

import org.springframework.stereotype.Service;
import xyz.morecraft.dev.scp.service.dictionary.DictionaryProvider;
import xyz.morecraft.dev.scp.service.dictionary.DictionaryProviderFor;
import xyz.morecraft.dev.scp.service.dictionary.DictionaryService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictionaryServiceImplementation implements DictionaryService {

    private Map<String, DictionaryProvider> providerMap = new HashMap<>();

    @Inject
    public DictionaryServiceImplementation(List<DictionaryProvider> providers) {
        for (DictionaryProvider provider : providers)
            providerMap.put(keyFor(provider), provider);
    }

    private String keyFor(DictionaryProvider provider) {
        DictionaryProviderFor annotation = provider.getClass().getAnnotation(DictionaryProviderFor.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Missing @DictionaryProviderFor on " + provider.getClass());
        }

        String value = annotation.value();
        if (providerMap.containsKey(value)) {
            throw new IllegalArgumentException("Duplicated dictionary key " + value);
        }

        return value;
    }

    @Override
    public List loadDictionary(String name) {
        if (!providerMap.containsKey(name))
            throw new IllegalArgumentException(name);

        return providerMap.get(name).loadProvider();
    }

}
