package demo.interview.ekan.service.impl;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.interview.ekan.model.Beneficiario;
import demo.interview.ekan.repository.BeneficiarioRepository;
import demo.interview.ekan.service.IBeneficiarioService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BeneficiarioServiceImpl implements IBeneficiarioService {

    @Autowired
    private   BeneficiarioRepository repository;


    @Override
    public Beneficiario getByID(Long id) {
       Optional<Beneficiario> beneficario =  repository.findById(id);
       if(beneficario.isPresent()){
        return beneficario.get();
       }
       throw new EntityNotFoundException();
    }

    @Override
    public Beneficiario getByName(String name) {
     return repository.findByName(name);
    }

    @Override
    public List<Beneficiario> getAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public Beneficiario add(Beneficiario beneficiario) {
       return repository.save(beneficiario);
    }

    @Override
    public void removeAll() {
        repository.deleteAll();
    }

  
    
}
