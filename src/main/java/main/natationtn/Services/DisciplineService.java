package main.natationtn.Services;

import main.natationtn.Entities.Discipline;
import main.natationtn.Repositories.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    @Autowired
    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    // Create
    public Discipline saveDiscipline(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

    // Read
    public List<Discipline> getAllDisciplines() {
        return disciplineRepository.findAll();
    }

    public Optional<Discipline> getDisciplineById(Long id) {
        return disciplineRepository.findById(id);
    }

    public Optional<Discipline> getDisciplineByNom(String nom) {
        return disciplineRepository.findByNom(nom);
    }

    public List<Discipline> searchDisciplines(String query) {
        return disciplineRepository.findByNomContaining(query);
    }

    // Update
    public Discipline updateDiscipline(Discipline discipline) {
        return disciplineRepository.save(discipline);
    }

    // Delete
    public void deleteDiscipline(Long id) {
        disciplineRepository.deleteById(id);
    }
}