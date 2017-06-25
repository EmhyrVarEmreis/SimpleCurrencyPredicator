package xyz.morecraft.dev.scp.service.dictionary.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.morecraft.dev.scp.dao.repository.CurrencyRepository;
import xyz.morecraft.dev.scp.service.dictionary.DictionaryProvider;
import xyz.morecraft.dev.scp.service.dictionary.DictionaryProviderFor;

import java.util.List;

@Service
@DictionaryProviderFor("currency")
public class CurrencyDictionaryProvider implements DictionaryProvider {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyDictionaryProvider(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List loadProvider() {
        return currencyRepository.findAll();
    }

}
