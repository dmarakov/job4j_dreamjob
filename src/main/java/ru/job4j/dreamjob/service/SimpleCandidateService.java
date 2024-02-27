package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.MemoryCandidateRepository;

import java.util.Collection;
import java.util.Optional;

public class SimpleCandidateService implements CandidateService {

    private static final SimpleCandidateService INSTANCE = new SimpleCandidateService();
    private final MemoryCandidateRepository memoryCandidateRepository = MemoryCandidateRepository.getInstance();

    public static SimpleCandidateService getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        return memoryCandidateRepository.save(candidate);
    }

    @Override
    public boolean deleteById(int id) {
        return memoryCandidateRepository.deleteById(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return memoryCandidateRepository.update(candidate);
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return memoryCandidateRepository.findById(id);
    }

    @Override
    public Collection<Candidate> findAll() {
        return memoryCandidateRepository.findAll();
    }
}
