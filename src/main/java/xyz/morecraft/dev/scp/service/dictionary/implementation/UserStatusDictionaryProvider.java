package xyz.morecraft.dev.scp.service.dictionary.implementation;

import org.springframework.stereotype.Service;
import xyz.morecraft.dev.scp.dao.dictionary.UserStatus;
import xyz.morecraft.dev.scp.service.dictionary.DictionaryProvider;
import xyz.morecraft.dev.scp.service.dictionary.DictionaryProviderFor;

import java.util.Arrays;
import java.util.List;

@Service
@DictionaryProviderFor("userStatus")
public class UserStatusDictionaryProvider implements DictionaryProvider {

    @Override
    public List loadProvider() {
        return Arrays.asList(UserStatus.values());
    }

}
