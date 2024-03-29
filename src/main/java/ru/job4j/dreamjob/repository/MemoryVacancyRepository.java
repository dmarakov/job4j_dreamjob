package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {
    private int nextId = 1;
    private final Map<Integer, Vacancy> vacancies = new HashMap<>();
    private final LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    public MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Intern", now, true, 1, 0));
        save(new Vacancy(0, "Junior Java Developer", "Junior", now, true, 2, 0));
        save(new Vacancy(0, "Junior+ Java Developer", "Junior+", now, true, 3, 0));
        save(new Vacancy(0, "Middle Java Developer", "Middle", now, true, 1, 0));
        save(new Vacancy(0, "Middle+ Java Developer", "Middle+", now, true, 2, 0));
        save(new Vacancy(0, "Senior Java Developer", "Senior", now, true, 3, 0));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(),
                (id, oldVacancy) -> new Vacancy(oldVacancy.getId(), vacancy.getTitle(),
                        vacancy.getDescription(), vacancy.getCreationDate(), vacancy.getVisible(), oldVacancy.getCityId(), vacancy.getFileId())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
