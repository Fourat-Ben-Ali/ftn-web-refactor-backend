package main.natationtn.Services;

import main.natationtn.Entities.ContenuPresse;

import main.natationtn.Repositories.CpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CpService {
    @Autowired
    private CpRepository cpRepository;


    public ContenuPresse ADDContenu(ContenuPresse cp) {
        return cpRepository.save(cp);
    }


    public List<ContenuPresse> getAllContenus() {
        List<ContenuPresse> result = cpRepository.findAll();

        return result;
    }


    public Optional<ContenuPresse> getContenuById(Long id) {
        return cpRepository.findById(id);
    }


    public ContenuPresse updateContenu(Long id, ContenuPresse updatedCp) {
        return cpRepository.findById(id)
                .map(cp -> {
                    cp.setTitre(updatedCp.getTitre());
                    cp.setContenu(updatedCp.getContenu());
                    cp.setDatePublication(updatedCp.getDatePublication());
                    return cpRepository.save(cp);
                })
                .orElseThrow(() -> new RuntimeException("ContenuPresse avec l'ID " + id + " introuvable"));
    }


    public void deleteContenu(Long id) {
        cpRepository.deleteById(id);
    }
}
