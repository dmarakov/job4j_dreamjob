package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();
    private int nextId = 1;
    private final Map<Integer, Candidate> candidates = new HashMap<>();
    private final LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    public MemoryCandidateRepository() {
        save(new Candidate(0, "Ivanov Ivan", "HelloWorld", now, 1));
        save(new Candidate(0, "Petrov Petr", "HelloWorld", now, 2));
        save(new Candidate(0, "Nikolaev Nilolay", "HelloWorld", now, 3));
        save(new Candidate(0, "Olgina Olga", "HelloWorld", now, 1));
        save(new Candidate(0, "Dmitriev Dmitriy", "HelloWorld", now, 2));
        save(new Candidate(0, "Nikitin Nikita", "HelloWorld", now, 3));
    }

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldVacancy) -> new Candidate(oldVacancy.getId(), candidate.getTitle(),
                        candidate.getDescription(), candidate.getCreationDate(), candidate.getCityId())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
