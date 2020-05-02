package com.github.raphaelbluteau.cashback.listeners;

import com.github.raphaelbluteau.cashback.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.exceptions.data.GatewayException;
import com.github.raphaelbluteau.cashback.exceptions.data.SpotifyAuthException;
import com.github.raphaelbluteau.cashback.gateway.repository.AlbumRepository;
import com.github.raphaelbluteau.cashback.gateway.repository.CashbackParametersRepository;
import com.github.raphaelbluteau.cashback.gateway.repository.SaleRepository;
import com.github.raphaelbluteau.cashback.gateway.repository.entity.CashbackParametersEntity;
import com.github.raphaelbluteau.cashback.usecase.AlbumUseCase;
import com.github.raphaelbluteau.cashback.usecase.AuthorizationUseCase;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Log
@RequiredArgsConstructor
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {

    private final AuthorizationUseCase authorizationUseCase;
    private final AlbumUseCase albumUseCase;
    private final Environment env;
    private final CashbackParametersRepository cashbackParametersRepository;
    private final AlbumRepository albumRepository;
    private final SaleRepository saleRepository;
    private final AlbumConverter albumConverter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        databasesFreshStartup();
        populateCashbackParameters();
        Set<GenreEnum> genres = new HashSet<>();
        cashbackParametersRepository.findAll().forEach(c -> genres.add(c.getGenre()));

        String accessToken = null;
        try {
            accessToken = authorizationUseCase.getAuthorization();
        } catch (GatewayException | SpotifyAuthException e) {
            log.warning(e.getLocalizedMessage());
        }
        String finalAccessToken = String.format("Bearer %s", accessToken);
        genres.forEach(genre -> {
            try {
                List<Album> albumsByGenre = albumUseCase.getAlbumsByGenre(finalAccessToken, genre, Integer.valueOf(env.getProperty("albums.limit", "50")));
                albumRepository.saveAll(albumConverter.toEntity(albumsByGenre, genre));
            } catch (Exception e) {
                log.info(e.getLocalizedMessage());
            }
        });


    }

    private void databasesFreshStartup() {

        saleRepository.deleteAll();
        albumRepository.deleteAll();
        cashbackParametersRepository.deleteAll();
    }

    private void populateCashbackParameters() {

        // POP
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.POP)
                .dayOfWeek(DayOfWeek.SUNDAY)
                .percentage(BigDecimal.valueOf(25))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.POP)
                .dayOfWeek(DayOfWeek.MONDAY)
                .percentage(BigDecimal.valueOf(7))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.POP)
                .dayOfWeek(DayOfWeek.TUESDAY)
                .percentage(BigDecimal.valueOf(6))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.POP)
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .percentage(BigDecimal.valueOf(2))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.POP)
                .dayOfWeek(DayOfWeek.THURSDAY)
                .percentage(BigDecimal.valueOf(10))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.POP)
                .dayOfWeek(DayOfWeek.FRIDAY)
                .percentage(BigDecimal.valueOf(15))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.POP)
                .dayOfWeek(DayOfWeek.SATURDAY)
                .percentage(BigDecimal.valueOf(20))
                .build());

        // MPB
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.MPB)
                .dayOfWeek(DayOfWeek.SUNDAY)
                .percentage(BigDecimal.valueOf(30))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.MPB)
                .dayOfWeek(DayOfWeek.MONDAY)
                .percentage(BigDecimal.valueOf(5))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.MPB)
                .dayOfWeek(DayOfWeek.TUESDAY)
                .percentage(BigDecimal.valueOf(10))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.MPB)
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .percentage(BigDecimal.valueOf(15))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.MPB)
                .dayOfWeek(DayOfWeek.THURSDAY)
                .percentage(BigDecimal.valueOf(20))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.MPB)
                .dayOfWeek(DayOfWeek.FRIDAY)
                .percentage(BigDecimal.valueOf(25))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.MPB)
                .dayOfWeek(DayOfWeek.SATURDAY)
                .percentage(BigDecimal.valueOf(30))
                .build());

        // CLASSIC
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.CLASSIC)
                .dayOfWeek(DayOfWeek.SUNDAY)
                .percentage(BigDecimal.valueOf(35))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.CLASSIC)
                .dayOfWeek(DayOfWeek.MONDAY)
                .percentage(BigDecimal.valueOf(3))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.CLASSIC)
                .dayOfWeek(DayOfWeek.TUESDAY)
                .percentage(BigDecimal.valueOf(5))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.CLASSIC)
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .percentage(BigDecimal.valueOf(8))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.CLASSIC)
                .dayOfWeek(DayOfWeek.THURSDAY)
                .percentage(BigDecimal.valueOf(13))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.CLASSIC)
                .dayOfWeek(DayOfWeek.FRIDAY)
                .percentage(BigDecimal.valueOf(18))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.CLASSIC)
                .dayOfWeek(DayOfWeek.SATURDAY)
                .percentage(BigDecimal.valueOf(25))
                .build());

        // ROCK
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.ROCK)
                .dayOfWeek(DayOfWeek.SUNDAY)
                .percentage(BigDecimal.valueOf(40))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.ROCK)
                .dayOfWeek(DayOfWeek.MONDAY)
                .percentage(BigDecimal.valueOf(10))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.ROCK)
                .dayOfWeek(DayOfWeek.TUESDAY)
                .percentage(BigDecimal.valueOf(15))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.ROCK)
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .percentage(BigDecimal.valueOf(15))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.ROCK)
                .dayOfWeek(DayOfWeek.THURSDAY)
                .percentage(BigDecimal.valueOf(15))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.ROCK)
                .dayOfWeek(DayOfWeek.FRIDAY)
                .percentage(BigDecimal.valueOf(20))
                .build());
        cashbackParametersRepository.save(CashbackParametersEntity.builder()
                .genre(GenreEnum.ROCK)
                .dayOfWeek(DayOfWeek.SATURDAY)
                .percentage(BigDecimal.valueOf(40))
                .build());

    }
}
