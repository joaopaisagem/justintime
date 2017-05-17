package pt.ipca.justintime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Vacation;
import pt.ipca.justintime.repositories.VacationRepository;

import java.util.List;

@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;

    //////////////////////////////////////////////////////////
    //               CRUD METHOD`S                         //
    ////////////////////////////////////////////////////////
    public Vacation saveVacation(Vacation vacation) {
        return vacationRepository.save(vacation);
    }

    public Vacation getVacationById(Long id) {
        return vacationRepository.findOne(id);
    }

    public List<Vacation> getAllVacations() {
        return vacationRepository.findAll();

    }

    public void updateVacation(Vacation vacation) {
        vacationRepository.saveAndFlush(vacation);
    }

    public Long countTotalVacations() {

        return vacationRepository.count();
    }
}
