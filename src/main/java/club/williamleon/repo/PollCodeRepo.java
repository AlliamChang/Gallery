package club.williamleon.repo;

import club.williamleon.domain.PollCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PollCodeRepo extends JpaRepository<PollCodeEntity, Long> {

    @Query("select 1" +
            " from PollCodeEntity p" +
            " where p.code = ?1 and p.freq > 0 and p.period > now()")
    List<Long> validateCode(String code);

    @Modifying
    @Query("update PollCodeEntity p set p.freq = p.freq - 1 where p.code = ?1")
    void reduceCodeFreq(String code);
}
