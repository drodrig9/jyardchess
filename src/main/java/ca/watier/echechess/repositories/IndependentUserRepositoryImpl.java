/*
 *    Copyright 2014 - 2018 Yannick Watier
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ca.watier.echechess.repositories;

import ca.watier.echechess.exceptions.UserException;
import ca.watier.echechess.exceptions.UserNotFoundException;
import ca.watier.echechess.models.UserGame;
import ca.watier.echechess.models.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IndependentUserRepositoryImpl extends AbstractUserRepository {

    @Autowired
    @Qualifier("userInformationRepository")
    private UserInformationRepository userInformationRepository;


    public IndependentUserRepositoryImpl(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @Override
    protected void saveOrUpdateUserInformation(@NotNull UserInformation userInformation) {
        userInformationRepository.save(userInformation);
    }

    @Override
    public UserInformation getUserByName(@NotBlank String username) throws UserException {

        UserInformation userInfo = userInformationRepository.findByName(username);

        return Optional.ofNullable(userInfo).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<UserInformation> getUserByEmail(@NotBlank @Email String email) throws UserException {
        Predicate<UserInformation> userCredentialsPredicate = userCredentials -> email.equals(userCredentials.getEmail());

        List<UserInformation> values = userInformationRepository.findByEmail(email);

        if (values.isEmpty()) {
            throw new UserNotFoundException();
        }

        return values;
    }

    @Override
    public void addGameToUser(@NotBlank String username, @NotNull UUID game) throws UserException {
        UserInformation userByName = getUserByName(username);
        UserGame userGame = new UserGame(game.toString(), userByName);
        userByName.addGame(userGame);

        userInformationRepository.save(userByName);
    }
}
